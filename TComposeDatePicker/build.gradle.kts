plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("signing")
}

apply(plugin = "maven-publish")
apply(plugin = "signing")


java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

}


afterEvaluate {
    tasks.withType<PublishToMavenLocal> {
        // Make 'publishReleasePublicationToMavenLocal' depend on 'assembleRelease'
        dependsOn("assembleRelease")
    }
    publishing {

        publications.create<MavenPublication>("release") {
            groupId = "io.github.the-best-is-best"
            artifactId = "compose_date_time_picker"
            version = "1.0.1"
            from(components["release"])



            //  artifact("$buildDir/outputs/aar/ComposeQuill-release.aar")
            //artifact("$buildDir/libs/ComposeQuill-release.jar")
            // Provide artifacts information required by Maven Central
            pom {
                name.set("Compose Searchable Dropdown")
                description.set("A Jetpack Compose Android Library to create a searchable dropdown.")
                url.set("https://github.com/the-best-is-best/TComposeDateTimePicker")
                licenses {
                    license {
                        name.set("Apache-2.0")
                        url.set("https://opensource.org/licenses/Apache-2.0")
                    }
                }
                issueManagement {
                    system.set("Github")
                    url.set("https://github.com/the-best-is-best/TComposeDateTimePicker/issues")
                }
                scm {
                    connection.set("https://github.com/the-best-is-best/TComposeDateTimePicker.git")
                    url.set("https://github.com/the-best-is-best/TComposeDateTimePicker")
                }
                developers {
                    developer {
                        id.set("MichelleRaouf")
                        name.set("Michelle Raouf")
                        email.set("eng.michelle.raouf@gmail.com")
                    }
                }
            }
        }
        repositories {
//            maven {
//                name = "OSSRH-snapshots"
//                url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
//                credentials {
//                    username = System.getenv("MAVEN_NAME")
//                    password = System.getenv("MAVEN_TOKEN")
//                }
//            }

            maven {
                name = "OSSRH"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = System.getenv("MAVEN_NAME")
                    password = System.getenv("MAVEN_TOKEN")
                }
            }
//            maven {
//                name = "LocalMaven"
//                url = uri("$buildDir/maven")
//                   }
//                maven {
//                    name = "GitHubPackages"
//                    url = uri("https://github.com/the-best-is-best/TComposeDateTimePicker")
//                    credentials {
//                        username = "the-best-is-best"
//                        password =
//                            System.getenv("BUILD_MAVEN")
//                    }
            //      }
        }
    }

}



signing {
    useGpgCmd()
    sign(publishing.publications)
}
android {
    namespace = "io.tbib.tcomposedatepicker"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.01.00"))
    implementation("androidx.compose.material3:material3:1.2.0-rc01")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("io.github.vanpra.compose-material-dialogs:datetime:0.9.0")
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.0.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}