package com.cyd.plugin

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import com.cyd.plugin.visitor.MethodFindRefVisitor
import com.cyd.plugin.visitor.MethodRemoveVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

abstract class LogTransform : AsmClassVisitorFactory<InstrumentationParameters.None> {

    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
//        return MethodFindRefVisitor(Opcodes.ASM5,nextClassVisitor,"android/util/Log","i","(Ljava/lang/String;Ljava/lang/String;)I")
        return MethodRemoveVisitor(Opcodes.ASM5,nextClassVisitor)
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        // 处理className: com.silencefly96.module_base.base.BaseActivity
        val className = with(classData.className) {
            val index = lastIndexOf(".") + 1
            substring(index)
        }

        // 筛选要处理的class
        return !className.startsWith("R$")
                && "R" != className
                && "BuildConfig" != className
                // 这两个我加的，代替的类小心无限迭代
                && !classData.className.startsWith("android")
                && "AsmMethods" != className
    }
}