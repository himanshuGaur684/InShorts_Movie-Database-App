// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.dagger) apply false
    alias(libs.plugins.ktlint) apply true
    alias(libs.plugins.spotless) apply true
}

spotless {
    kotlin {
        target("**/*.kt")
        ktlint(libs.versions.spotlessKtLint.get())
            .userData(mapOf("indent_size" to "4", "continuation_indent_size" to "4"))
        trimTrailingWhitespace()
        endWithNewline()
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        ktlint(libs.versions.spotlessKtLint.get())
    }
}
