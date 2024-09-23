package com.cyd.plugin.visitor

import org.objectweb.asm.Handle
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor

/**
 *
 * @author: cyd
 * @date: 2024/9/23
 * @since:
 */
abstract class MethodPatternAdapter(api: Int, methodVisitor: MethodVisitor) :
    MethodVisitor(api, methodVisitor) {

    protected val SEE_NOTHING = 0

    protected var state = 0

    protected abstract fun visitInsn()

    override fun visitInsn(opcode: Int) {
        visitInsn()
        super.visitInsn(opcode)
    }

    override fun visitIntInsn(opcode: Int, operand: Int) {
        visitInsn()
        super.visitIntInsn(opcode, operand)
    }

    override fun visitVarInsn(opcode: Int, varIndex: Int) {
        visitInsn()
        super.visitVarInsn(opcode, varIndex)
    }

    override fun visitTypeInsn(opcode: Int, type: String?) {
        visitInsn()
        super.visitTypeInsn(opcode, type)
    }

    override fun visitFieldInsn(opcode: Int, owner: String?, name: String?, descriptor: String?) {
        visitInsn()
        super.visitFieldInsn(opcode, owner, name, descriptor)
    }

    override fun visitMethodInsn(
        opcode: Int,
        owner: String?,
        name: String?,
        descriptor: String?,
        isInterface: Boolean
    ) {
        visitInsn()
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
    }

    override fun visitMethodInsn(opcode: Int, owner: String?, name: String?, descriptor: String?) {
        visitInsn()
        super.visitMethodInsn(opcode, owner, name, descriptor)
    }

    override fun visitInvokeDynamicInsn(
        name: String?,
        descriptor: String?,
        bootstrapMethodHandle: Handle?,
        vararg bootstrapMethodArguments: Any?
    ) {
        visitInsn()
        super.visitInvokeDynamicInsn(
            name,
            descriptor,
            bootstrapMethodHandle,
            *bootstrapMethodArguments
        )
    }

    override fun visitJumpInsn(opcode: Int, label: Label?) {
        visitInsn()
        super.visitJumpInsn(opcode, label)
    }

    override fun visitLdcInsn(value: Any?) {
        visitInsn()
        super.visitLdcInsn(value)
    }

    override fun visitIincInsn(varIndex: Int, increment: Int) {
        visitInsn()
        super.visitIincInsn(varIndex, increment)
    }

    override fun visitTableSwitchInsn(min: Int, max: Int, dflt: Label?, vararg labels: Label?) {
        visitInsn()
        super.visitTableSwitchInsn(min, max, dflt, *labels)
    }

    override fun visitLookupSwitchInsn(dflt: Label?, keys: IntArray?, labels: Array<out Label>?) {
        visitInsn()
        super.visitLookupSwitchInsn(dflt, keys, labels)
    }

    override fun visitMultiANewArrayInsn(descriptor: String?, numDimensions: Int) {
        visitInsn()
        super.visitMultiANewArrayInsn(descriptor, numDimensions)
    }

    override fun visitTryCatchBlock(start: Label?, end: Label?, handler: Label?, type: String?) {
        visitInsn()

        super.visitTryCatchBlock(start, end, handler, type)
    }

    override fun visitLabel(label: Label?) {
        visitInsn()

        super.visitLabel(label)
    }

    override fun visitFrame(
        type: Int,
        numLocal: Int,
        local: Array<out Any>?,
        numStack: Int,
        stack: Array<out Any>?
    ) {
        visitInsn()

        super.visitFrame(type, numLocal, local, numStack, stack)
    }

    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
        visitInsn()

        super.visitMaxs(maxStack, maxLocals)
    }

}