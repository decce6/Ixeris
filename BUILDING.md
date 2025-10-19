This project uses [Stonecutter](https://stonecutter.kikugie.dev/) to facilitate development for multiple Minecraft versions simultaneously.

For more information on versioning code, visit the [Stonecutter Wiki](https://stonecutter.kikugie.dev/wiki/).

## Developing

- Use the "Set active project to ..." gradle task to switch to a specific version. The gradle tasks for that version can then be used.
- During development, use [Stonecutter comments](https://stonecutter.kikugie.dev/wiki/start/comments.html) for code that is different between Minecraft versions
- Make sure to run the "Reset active project" gradle task before committing to VCS

## Building

Running `gradle buildAndCollect` will build for all versions and loaders and store the resulting artifacts in `build/libs`. If you want to build for a specific version and skip configuration of others, you can pass the `-Ptarget_versions` and `-Ptarget_loaders` arguments to gradle.

- Build `1.21.8-fabric` only: `gradle -Ptarget_versions=1.21.8 -Ptarget_loaders=fabric buildAndCollect`
- Build for multiple versions: `gradle -Ptarget_versions=1.20.1,1.21.1 buildAndCollect`
- Build for versions newer than 1.21.1: `gradle -Ptarget_versions=>=1.21.1 buildAndCollect`
