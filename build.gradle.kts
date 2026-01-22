// build.gradle.kts (Project level)

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
}

allprojects {
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "com.squareup" && requested.name == "javapoet") {
                useVersion("1.13.0")
                because("Hilt AggregateDeps requires ClassName.canonicalName()")
            }
        }
    }
}
