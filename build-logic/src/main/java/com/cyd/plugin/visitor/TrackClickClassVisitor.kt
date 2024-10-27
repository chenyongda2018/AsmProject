package com.cyd.plugin.visitor

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.AdviceAdapter

/**
 *
 * @author: cyd
 * @date: 2024/9/24
 * @since:
 */
class TrackClickClassVisitor(
    api: Int,
    private val trackOn: Boolean,
    classVisitor: ClassVisitor
) : ClassVisitor(api, classVisitor) {

    var className = ""

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
        className = name ?: ""
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        var mv = super.visitMethod(access, name, descriptor, signature, exceptions)
        if (trackOn) {
            if (name == "onClick" && descriptor == "(Landroid/view/View;)V") {
                mv = TrackClickMethodVisitor(className, this.api, mv, access, name, descriptor)
            }
        }
        return mv
    }
}

class TrackClickMethodVisitor(
    private val className: String,
    api: Int,
    methodVisitor: MethodVisitor,
    access: Int,
    name: String,
    descriptor: String
) : AdviceAdapter(api, methodVisitor, access, name, descriptor) {


    override fun onMethodEnter() {
        super.onMethodEnter()
        println("cyyyd,${this.className},$name")
    }

}