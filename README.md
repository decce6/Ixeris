# Ixeris

## Overview

Ixeris is a mod that optimizes event polling to improve client performance.

You might have noticed a visible drop of the FPS when you move your mouse. Part of the FPS drop is because the game does have additional jobs to do when you turn the camera, like calculating the visibility of chunks. However, because of the inefficiency in the native code that polls events and the JNI upcall overhead, some of the CPU time, otherwize can be utilized for rendering, are unnecessarily spent on event polling. This is most noticeable on Windows, especially when your mouse has a high polling rate.

Ixeris resolves the issue mainly through two measures:

- **Threaded Event Polling**. Instead of performing rendering and event polling on the same thread, Ixeris performs event polling on the *main thread* and kicks rendering to a separate *render thread*.
- **Buffered Raw Input** (Windows-only). Switches the method used for input polling from the inefficient `GetRawInputData` (one call for each input event) to `GetRawInputBuffer` which allows reading raw input messages in batches (one call for all events). Additionally, performing this work in Java code has allowed us to eliminate the JNI upcall overhead.

## Benchmarks

These tests are done after the world has fully loaded and the framerate has stabilized. The mouse has a polling rate of 1000Hz. ```F3+Esc``` is pressed to make sure the framerate change when moving the mouse is the result of event polling, not any other calculation.

The "Idle FPS" column shows the FPS when not moving the mouse. The next two columns show the FPS when moving the mouse quickly over the game window, without Ixeris and with Ixeris, respectively. The last column compares the FPS when Ixeris is and is not installed.

|                 | Idle FPS | Without Ixeris | With Ixeris | Improvement |
|-----------------|----------|----------------|-------------|-------------|
| Windows         | 233 FPS  | 133 FPS        | 165 FPS     | 1.24x       |
| Linux (X11)     | 358 FPS  | 320 FPS        | 355 FPS     | 1.11x       |
| Linux (Wayland) | 364 FPS  | 289 FPS        | 298 FPS     | 1.03x       |

## Technical Details

### Thread Satefy

In its current state Ixeris should not break thread safety. Callbacks registered with ```glfwSet*Callback``` are executed on the render thread. Calls to GLFW functions that are required to be called on the main thread, if made on other threads, are dispatched to the main thread. These calls may immediately return if they can be safely delayed, or otherwise may block the caller until the call is finished.

As of version 3.1.0, the requirements of thread safety in the GLFW documentation are strictly obeyed.

### Glossary

- The **main thread** is the thread that the game is started on. Most GLFW functions are required to be called on this thread, and it is responsible for event polling.
- The **render thread** does everything the game normally does, except event polling.

These two terms are synonymous in vanilla Minecraft.
