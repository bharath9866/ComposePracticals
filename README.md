# AdaptiveStreamingPlayer

## Build Performance Optimizations

This project has been optimized for faster build times through the following improvements:

### Gradle Configuration

1. **Gradle Daemon Settings**
    - Enabled Gradle daemon to keep Gradle running in the background
    - Configured maximum worker count to utilize system resources efficiently
    - Enabled parallel task execution for both project-level and intra-project tasks

2. **Cache Optimizations**
    - Enabled Gradle build cache for faster incremental builds
    - Configured file system watching for improved performance on incremental builds
    - Enabled configuration cache to reuse configuration between builds

3. **Kotlin Compilation Speed**
    - Enabled incremental Kotlin compilation
    - Set up classpath snapshot for faster incremental builds
    - Configured compiler options for optimal processing

4. **Android Specific Optimizations**
    - Disabled unnecessary Android build features (aidl, renderscript, etc.)
    - Enabled R8 in full mode for better optimization
    - Configured incremental annotation processing for Kapt/KSP
    - Optimized resource processing

### Build Toolchain

1. **Java/Kotlin Configuration**
   - Configured for Java 8 compatibility (default)
   - For best performance, using Java 11 is recommended if available on your system
   - Note: Data binding requires Java 11+ to avoid class version conflicts
   - Configured Kotlin JVM target for optimized bytecode

2. **Gradle Wrapper**
    - Using Gradle 8.10.2 with all distribution for better IDE support
    - Configured network timeouts to avoid sync failures

### Custom Tasks

1. **Cleaning Tasks**
    - Added `deepClean` task for thoroughly cleaning the project when needed
    - Optimized standard clean task

2. **Task Filtering**
    - Disabled unnecessary linting and testing tasks during regular builds
    - Skip PNG crunching during debug builds

### How to Use

1. **Regular Build**