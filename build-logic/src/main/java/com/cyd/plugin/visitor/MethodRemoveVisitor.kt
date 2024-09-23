package com.cyd.plugin.visitor

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 *
 * @author: cyd
 * @date: 2024/9/23
 * @since:
 */
class MethodRemoveVisitor
    (api: Int, classVisitor: ClassVisitor) : ClassVisitor(api, classVisitor) {

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        var mv = cv.visitMethod(access, name, descriptor, signature, exceptions)
        if (mv != null && "<init>" !== name && "<clinit>" !== name) {
            val isAbstract = (access and Opcodes.ACC_ABSTRACT) != 0
            val isNative = (access and Opcodes.ACC_NATIVE) != 0
            if (!isAbstract && !isNative) {
                mv = MethodRemoveAddZeroAdapter(api,mv)
            }
        }
        return mv
    }
}

class MethodRemoveAddZeroAdapter(api: Int, methodVisitor: MethodVisitor) :
    MethodPatternAdapter(api, methodVisitor) {

    val SEEN_ICONST_0 = 1;

    override fun visitInsn(opcode: Int) {
        when (state) {
            SEE_NOTHING -> {
                if (opcode == Opcodes.ICONST_0) {
                    state = SEEN_ICONST_0
                    return
                }
            }

            SEEN_ICONST_0 -> {
                if (opcode == Opcodes.ICONST_0) {
                    mv.visitInsn(opcode)
                    return
                } else if (opcode == Opcodes.IADD) {
                    state = SEE_NOTHING
                    return
                }
            }
        }
    }

    override fun visitInsn() {
        if (state == SEEN_ICONST_0) {
            mv.visitInsn(Opcodes.ICONST_0)
        }
        state = SEE_NOTHING
    }
}