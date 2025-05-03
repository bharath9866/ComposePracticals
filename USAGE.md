# AdaptiveStreamingPlayer Usage Guide

## Using the deepClean Task

The `deepClean` task helps resolve build issues by cleaning more thoroughly than the standard
`clean` task:

1. Stop the Gradle daemon first:
   ```bash
   ./gradlew --stop
   ```

2. Run the deepClean task:
   ```bash
   ./gradlew deepClean
   ```

3. For a complete clean (when having persistent issues):
    - Stop the Gradle daemon as shown above
    - Manually delete the `.gradle` directory in your project root
    - Run a clean build: `./gradlew clean build`

## Java Version Requirements

This project has special requirements for Java versions:

1. **Default Setup (Java 8)**
    - The project is configured to work with Java 8 by default
    - Data binding is disabled to ensure compatibility

2. **Enabling Data Binding (requires Java 11)**
    - Install JDK 11 or higher
    - Set JAVA_HOME to point to your JDK 11 installation
    - Edit `app/build.gradle.kts` and change `dataBinding = false` to `dataBinding = true`
    - Run `./gradlew deepClean` followed by your build command

3. **Checking Your Java Version**
   ```bash
   java -version
   ```

## Common Build Commands

1. Regular debug build:
   ```bash
   ./gradlew assemblePreprodDebug --build-cache
   ```

2. Release build:
   ```bash
   ./gradlew assemblePreprodRelease
   ```

3. Build with performance scan:
   ```bash
   ./gradlew assemblePreprodDebug --scan
   ```

4. Run specific tests:
   ```bash
   ./gradlew test --tests "com.example.adaptivestreamingplayer.YourTestClass"
   ```

@@ ... @@