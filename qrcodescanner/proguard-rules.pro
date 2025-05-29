# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep classes and methods for Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Keep Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.android.** { *; }

# Keep ML Kit classes
-keep class com.google.mlkit.** { *; }
-dontwarn com.google.mlkit.**

# Keep CameraX classes
-keep class androidx.camera.** { *; }
-dontwarn androidx.camera.**

# Keep serialization classes
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

-keep,includedescriptorclasses class com.adaptive.qrcodescanner.**$$serializer { *; }
-keepclassmembers class com.adaptive.qrcodescanner.** {
    *** Companion;
}
-keepclasseswithmembers class com.adaptive.qrcodescanner.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile