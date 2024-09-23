package com.cyd.plugin.visitor

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

/**
 *
 * @author: cyd
 * @date: 2024/9/23
 * @since:
 * 检测某个方法在哪里被调用
 */
class MethodFindRefVisitor(
    api: Int,
    classVisitor: ClassVisitor,
    private val methodOwner: String,
    private val methodName: String,
    private val methodDesc: String,
) : ClassVisitor(api, classVisitor) {

    private var owner = ""

    private val resultList = ArrayList<String>()

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.owner = name ?: ""
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor? {

        val isAbstractMethod = (access and Opcodes.ACC_ABSTRACT) !== 0
        val isNative = (access and Opcodes.ACC_NATIVE) !== 0

        if (!isAbstractMethod && !isNative) {
            return MethodFindRefAdapter(
                api, null, access, owner, name, descriptor,
                methodOwner, methodName, methodDesc, resultList
            )
        }
        return null
    }

    override fun visitEnd() {
        resultList.forEach {
            println(it)
        }
        super.visitEnd()
    }

    class MethodFindRefAdapter(
        api: Int,
        methodVisitor: MethodVisitor?,
        access: Int,
        private val curOwnerClass: String,
        private val curMethodName: String?,
        private val curMethodDesc: String?,
        private val targetMethodOwner: String,
        private val targetMethodName: String,
        private val targetMethodDesc: String,
        private val resultList: ArrayList<String>
    ) : AdviceAdapter(api, methodVisitor, access, curMethodName, curMethodDesc) {

        override fun visitMethodInsn(
            opcodeAndSource: Int,
            owner: String?,
            name: String?,
            descriptor: String?,
            isInterface: Boolean
        ) {
            if (targetMethodOwner == owner && targetMethodName == name && targetMethodDesc == descriptor) {
                val info = String.format("%s.%s%s", curOwnerClass, curMethodName, curMethodDesc)
                if (!resultList.contains(info)) {
                    resultList.add(info)
                }
            }
            super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
        }
    }
}