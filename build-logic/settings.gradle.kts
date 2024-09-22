dependencyResolutionManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/jcenter")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        mavenCentral()
        google()
    }
}

include(":convention")