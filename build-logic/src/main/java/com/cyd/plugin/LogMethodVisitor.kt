package com.cyd.plugin

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

/**
 * 在方法中添加代码
 */
class LogMethodVisitor(
    private val className: String,
    nextMethodVisitor: MethodVisitor,
    access: Int,
    name: String?,
    descriptor: String?
):AdviceAdapter(Opcodes.ASM5,nextMethodVisitor,access,name,descriptor) {

    override fun onMethodEnter() {
        mv.visitLdcInsn("LogMethodVisitor")
        mv.visitLdcInsn("enter: $className.$name")
        // 调用 android.util.Log 函数
        mv.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            "android/util/Log",
            "i",
            "(Ljava/lang/String;Ljava/lang/String;)I",
            false
        )
        super.onMethodEnter()
    }

    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
    }
}