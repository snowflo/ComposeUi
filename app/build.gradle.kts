plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.composeuisample"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.composeuisample"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // Android Gradle Plugin(AGP)의 특정 기능을 활성화 하는 블록
    buildFeatures {
        // Jetpack Compose를 활성화
        compose = true
    }
    // 컴포저블을 컴파일하기 위한 Kotlin 컴파일러 버전을 설정 하는 블록
    composeOptions {
        // 컴파일러 버전은 하위 링크 참조
        // https://kotlinlang.org/docs/compose-compiler-migration-guide.html
        kotlinCompilerExtensionVersion = "2.0.21"
    }
    // Kotlin 코드가 타겟팅하는 JVM 바이트코드 버전을 설정 하는 블록
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    // BOM을 통해 모든 Compose 라이브러리들의 버전을 한 번에 관리
    /*
     * BOM(Bill of Materials)
     *  - 여러 라이브러리들의 호환되는 버전을 한 번에 관리해 주는 역할
     *  - 버전 충돌을 막고 일관성을 유지
     */
    implementation(platform(libs.androidx.compose.bom))
    // BOM 덕분에 버전 명시가 필요 없음
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}