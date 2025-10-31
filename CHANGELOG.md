## 3.6.1

- Fixed a regression in 3.6.0 that caused various crashes on startup
- Fixed incorrect version requirements in mod metadata
- Fixed framebuffer size on macOS when not using Retina framebuffer

## 3.6.0

- Forge support!
- Added 1.21.10 support
- Added option for verbose logging about cache issues (debug only)

Note: NeoForge is not yet supported on 1.21.9 and 1.21.10

## 3.5.3

- Added 1.21.9 Fabric support (NeoForge is not yet supported)
- [NeoForge] Fixed crash when used on dedicated server

## 3.5.2

Hotfix for game freezing on launch.

## 3.5.1

- [NeoForge] Fixed compatibility with other mods that implement ITransformationService

Fabric builds are not provided for this version because the changes in this version are exclusive to NeoForge.

## 3.5.0

NeoForge support!

This is an early preview version and is not considered stable. Bug reports welcome!

Fabric builds are not provided for this version because not much has changed compared with 3.4.2.

## 3.4.2

- Fixed window minimized state (crash when tabbing out on Intel GPUs)
- Optimized cursor functions by allowing them to be delayed
- Fixed compatibility with [FPS to the trash](https://modrinth.com/mod/fps-to-the-trash) mod

## 3.4.1

- Fixed callback breakage in certain scenarios
- Slightly improved performance when setting callbacks
- Fixed glfwSetCursor
- Implemented window hovered state cache

## 3.4.0

This version resolved rare crashes, improved stability and also performance.

- Many awesome thread safety and scheduling strategy improvements, courtesy of [@LitnhJacuzzi](https://github.com/LitnhJacuzzi). These changes bring improved stability and a decent FPS boost.
- Added 1.19.4 support (courtesy of [LitnhJacuzzi](https://github.com/LitnhJacuzzi))
- Refactored the callback threading system to be simpler yet more reliable
- Fixed slow memory leak when setting callbacks

## 3.3.1

Small hotfix release to fix regressions in 3.3.0 and Java 17 compatibility.

- Fixed freezes introduced in 3.3.0
- Fixed Java 17 compatibility (Thanks @LitnhJacuzzi!)

## 3.3.0

This version consists of some internal refactors that aim to significantly enhance mod compatibility. 

- Implemented a new system for ensuring the thread safety of GLFW callbacks. Compared with the old system that wraps and schedules every callback, this is compatible with more mods
- Refactored the cache system to be more reliable
- Fixed segmentation fault caused by standard cursor cache
- Fixed monitor cache
- Added cache for window content scale, window size, framebuffer size, and window focused & iconified state to improve performance
- Slightly optimized memory usage

## 3.2.2

- Improved input latency when using the enhanced FPS limiter
- [1.20.4] Fixed usage of methods that do not exist in this version
- Added 1.21.8 support

## 3.2.1

This is a bugfix release that fixes known issues in 3.2.0.

- Fully fixed the camera flickering issue when closing screens
- Fixed drag & drop function not working
- Fixed the issue where the window occasionally doesn't close when exiting the game.

## 3.2.0

- Massively improved performance of blocking GLFW calls
- Added an API for ensuring thread safety when using native functions
- Re-added support for some popular versions. The currently supported versions are: `1.20.1~1.20.6`, `1.21.1`, and `1.21.4~1.21.7`.
- Fixed compatibility with Frostbyte's Improved Inventory mod
- Added standard cursor cache to improve performance

## 3.1.2

- Fixed macOS crash (hopefully; tester wanted)
- Added an optimization for the FPS limiter. The enhanced FPS limiter provides more stable FPS and ensures thread safety.
- Improved input latency
- More GLFW threading fixes

## 3.1.1

- Improved mod compatibility
- Added mouse button cache and monitor cache to improve performance
- Fixed glfwGetKey cache (was causing Shift+clicking to be unable to move items in inventories)

## 3.1.0

- Implemented GLFW state caching. This allowed Ixeris to achieve full thread safety while maintaining great performance.
- Fixed handling for null callbacks
- Added missing threading check for monitor callbacks
- (1.20.1) Fixed Minecraft#gameThread field not set

## 3.0.0

- Fixed compatibility with ModernUI mod
- Fixed GLFWErrorCallback description
- Migrated development toolchain to stonecutter on modstitch. While no difference is instantly visible by the user, this makes the development process more streamlined and helps reduce the number of issues caused by dealing with different Minecraft versions
- Dropped support for Minecraft versions that are considered obsolete. The currently supported versions are: `1.20.1`, `1.21.1`, `1.21.5`, `1.21.6`, `1.21.7`

## 2.0.3

- Correctly fixed camera flickering when closing menus

## 2.0.2

- Fixed crash when setting window icon
- Fixed camera flickering when closing menus in some cases
- 1.21.6 is now supported

## 2.0.1

- Massively improved the background CPU usage of the event polling thread

## 2.0.0

Initial release for 1.21.5+