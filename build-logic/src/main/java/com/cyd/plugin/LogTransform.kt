package com.cyd.plugin

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import com.cyd.plugin.visitor.MethodFindRefVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

abstract class LogTransform : AsmClassVisitorFactory<InstrumentationParameters.None> {

    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        return MethodFindRefVisitor(Opcodes.ASM5,nextClassVisitor,"android/util/Log","i","(Ljava/lang/String;Ljava/lang/String;)I")
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        return true
    }
}