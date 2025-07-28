# API Documentation

Ixeris should be able to ensure the thread safety of most GLFW functions. An exception, however, is when your mod directly calls native functions without using GLFW. If executed on the render thread, such calls may fail. The API is thus provided for executing codes on the main thread.

## Gradle Setup

Modrinth Maven may be used to get the Ixeris artifact:

```groovy
repositories {
    exclusiveContent {
        forRepository {
            maven {
                name = "Modrinth"
                url = "https://api.modrinth.com/maven"
            }
        }
        filter {
            includeGroup "maven.modrinth"
        }
    }
}

dependencies {
    modImplementation "maven.modrinth:ixeris:$version"
}
```

where `$version` should be replaced with the corresponding version number found on Modrinth.

## API Usage

The main API class is `me.decce.ixeris.api.IxerisApi`. It provides methods for executing code on the main thread and other purposes.
