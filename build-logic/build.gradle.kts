plugins {
    // 使用 kotlin dsl 作为 gradle 构建脚本语言
    `kotlin-dsl`
}

// 配置字节码的兼容性
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    val agpVersion = "7.4.2"
    val kotlinVersion = "1.9.0"
    val asmVersion = "9.3"
    // AGP 依赖
    implementation("com.android.tools.build:gradle:$agpVersion") {
        exclude(group = "org.ow2.asm")
    }
    // Kotlin 依赖 —— 插件使用 Kotlin 实现
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion") {
        exclude(group = "org.ow2.asm")
    }
    // ASM 依赖库
    implementation("org.ow2.asm:asm:$asmVersion")
    implementation("org.ow2.asm:asm-commons:$asmVersion")
    implementation("org.ow2.asm:asm-util:$asmVersion")
}

gradlePlugin {
    plugins {
        // 注册插件，这样可以在其他地方 apply
        register("LogPlugin") {
            // 注册插件的 id，需要应用该插件的模块可以通过 apply 这个 id
            id = "com.cyd.gradle.plugin.log"
            implementationClass = "com.cyd.plugin.LogPlugin"
        }
    }
}