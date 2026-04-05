# Ixeris

## Overview

Ixeris is a mod that optimizes event polling to improve client performance.

You might have noticed a visible drop of the FPS when you move your mouse. Part of the FPS drop is because the game does have additional jobs to do when you turn the camera, like calculating the visibility of chunks. However, because of the inefficiency in the native code that polls events and the JNI upcall overhead, some of the CPU time, otherwize can be utilized for rendering, are unnecessarily spent on event polling. This is most noticeable on Windows, especially when your mouse has a high polling rate.

Ixeris resolves the issue mainly through two measures:

- **Threaded Event Polling**. Instead of performing rendering and event polling on the same thread, Ixeris performs event polling on the *main thread* and kicks rendering to a separate *render thread*.
- **Buffered Raw Input** (Windows-only). Switches the method used for input polling from the inefficient `GetRawInputData` (one call for each input event) to `GetRawInputBuffer` which allows reading raw input messages in batches (one call for all events). Additionally, performing this work in Java code has allowed us to eliminate the JNI upcall overhead.

## Benchmarks

These tests are done after the world has fully loaded and the framerate has stabilized.

### Test 1: In Game

This test compares the performance when the mouse is grabbed, i.e. cursor invisible. This test is performed in a superflat world with no entities.

This test is done on Windows, where Ixeris uses buffered raw input by default to improve performance. The "Ixeris, Non-buffered" column shows the FPS with that option disabled.

| Polling Rate | Without Ixeris | Ixeris, Non-buffered | Ixeris, Buffered |
|--------------|----------------|----------------------|------------------|
| 8000 Hz      | 12 FPS         | 83 FPS (6.9x)        | 121 FPS (10.1x)  |
| 2000 Hz      | 76 FPS         | 114 FPS (1.50x)      | 135 FPS (1.78x)  |
| 500 Hz       | 134 FPS        | 145 FPS (1.08x)      | 151 FPS (1.13x)  |

### Test 2: In Menus

This test compares the performance when the mouse is not grabbed, i.e. cursor visible, as in F3+Esc pause screen. In this case, raw input is never used as the game needs to know the *actual* cursor position, not the raw relative movement. The polling rate is 1000Hz.

The "Idle FPS" column shows the FPS when not moving the mouse, and the next two columns show the FPS when moving the mouse quickly over the game window, without Ixeris and with Ixeris, respectively. Note that this test was performed at the initial release of Ixeris, and many improvements have been made since then.

|                 | Idle FPS | Without Ixeris | With Ixeris     |
|-----------------|----------|----------------|-----------------|
| Windows         | 233 FPS  | 133 FPS        | 165 FPS (1.24x) |
| Linux (X11)     | 358 FPS  | 320 FPS        | 355 FPS (1.11x) |
| Linux (Wayland) | 364 FPS  | 289 FPS        | 298 FPS (1.03x) |

## Technical Details

### Thread Satefy

In its current state Ixeris should not break thread safety. Callbacks registered with ```glfwSet*Callback``` are executed on the render thread. Calls to GLFW functions that are required to be called on the main thread, if made on other threads, are dispatched to the main thread. These calls may immediately return if they can be safely delayed, or otherwise may block the caller until the call is finished.

As of version 3.1.0, the requirements of thread safety in the GLFW documentation are strictly obeyed.

### GLFW State Caching

Most GLFW functions are required to be called from the main thread. However, many mods may call them from the render thread. To avoid performance degradation introduced by thread communications, Ixeris caches frequently used GLFW states for fast access from any thread, without having to route the call to the main thread. The caches are safe and do not introduce extra lag.

### Enhanced FPS Limiter

The vanilla FPS limiter (prior to 26.1) is flawed, using `glfwWaitEventsTimeout` to sleep. This function, however, cannot be called from the render thread and thus does not work optimally with Ixeris.

Ixeris rewrites the FPS limiter in a hybrid way, sleeping precisely and starting spin waiting when the wait time is very low.

### Glossary

- The **main thread** is the thread that the game is started on. Most GLFW functions are required to be called on this thread, and it is responsible for event polling.
- The **render thread** does everything the game normally does, except event polling.

These two terms are synonymous in vanilla Minecraft.
