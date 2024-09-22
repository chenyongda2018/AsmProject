package com.cyd.plugin

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class LogPlugin : Plugin<Project> {

    companion object {
        private const val TAG = "LogPlugin"
    }

    override fun apply(target: Project) {
        log("======== start apply ========")

        val appComponentsExtension =
            target.extensions.getByType(AndroidComponentsExtension::class.java)

        appComponentsExtension.onVariants { variant ->
            log("variant: ${variant.name}")

            variant.instrumentation.apply {
                transformClassesWith(LogTransform::class.java, InstrumentationScope.PROJECT) {}
                setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS)
            }
        }


        log("apply target: ${target.displayName}")
        log("========  end apply  ========")
    }

    private fun log(msg: String) {
        println("[$TAG]: $msg")
    }

}