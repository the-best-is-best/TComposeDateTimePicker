@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform.getCurrentOperatingSystem
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.library)
    id("maven-publish")
    id("signing")
    alias(libs.plugins.maven.publish)
}




apply(plugin = "maven-publish")
apply(plugin = "signing")


tasks.withType<PublishToMavenRepository> {
    val isMac = getCurrentOperatingSystem().isMacOsX
    onlyIf {
        isMac.also {
            if (!isMac) logger.error(
                """
                    Publishing the library requires macOS to be able to generate iOS artifacts.
                    Run the task on a mac or use the project GitHub workflows for publication and release.
                """
            )
        }
    }
}


extra["packageNameSpace"] = "io.github.tcompose_date_time_picker"
extra["groupId"] = "io.github.the-best-is-best"
extra["artifactId"] = "compose_date_time_picker"
extra["version"] = "3.4.2"
extra["packageName"] = "TKCompose Date Picker "
extra["packageUrl"] = "https://github.com/the-best-is-best/TComposeDateTimePicker"
extra["packageDescription"] =
    "Compose Date Picker is library for Compose multi-platform to make date or time picker and date time\n" +
            "picker."
extra["system"] = "GITHUB"
extra["issueUrl"] = "https://github.com/the-best-is-best/TComposeDateTimePicker/issues"
extra["connectionGit"] = "https://github.com/the-best-is-best/TComposeDateTimePicker.git"

extra["developerName"] = "Michelle Raouf"
extra["developerNameId"] = "MichelleRaouf"
extra["developerEmail"] = "eng.michelle.raouf@gmail.com"


mavenPublishing {
    coordinates(
        extra["groupId"].toString(),
        extra["artifactId"].toString(),
        extra["version"].toString()
    )

    publishToMavenCentral(true)
    signAllPublications()

    pom {
        name.set(extra["packageName"].toString())
        description.set(extra["packageDescription"].toString())
        url.set(extra["packageUrl"].toString())
        licenses {
            license {
                name.set("Apache-2.0")
                url.set("https://opensource.org/licenses/Apache-2.0")
            }
        }
        issueManagement {
            system.set(extra["system"].toString())
            url.set(extra["issueUrl"].toString())
        }
        scm {
            connection.set(extra["connectionGit"].toString())
            url.set(extra["packageUrl"].toString())
        }
        developers {
            developer {
                id.set(extra["developerNameId"].toString())
                name.set(extra["developerName"].toString())
                email.set(extra["developerEmail"].toString())
            }
        }
    }

}


signing {
    useGpgCmd()
    sign(publishing.publications)
}

val packageNameSpace = extra["packageNameSpace"].toString()



kotlin {
    jvmToolchain(17)
    androidTarget()




    jvm()

    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = packageNameSpace
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)

            api(libs.kotlinx.datetime)
            api(libs.kotlinx.serialization.json)
            api(libs.calf.ui)




        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }

        androidMain.dependencies {
            implementation(compose.uiTooling)
            implementation(libs.androidx.activityCompose)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }

        jsMain.dependencies {
            implementation(compose.html.core)
        }

        iosMain.dependencies {

        }

    }
}

android {
    namespace = packageNameSpace
    compileSdk = 36
    defaultConfig {
        minSdk = 21
    }
}
