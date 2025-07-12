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