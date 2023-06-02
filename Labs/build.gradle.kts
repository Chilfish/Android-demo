buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
    }
}


plugins {
    id("com.android.application") version ("8.0.0") apply false
    id("com.android.library") version ("8.0.0") apply false
    kotlin("android") version ("1.8.10") apply false
    kotlin("plugin.serialization") version ("1.8.20")
}
