//? if forge {
/*/^
Auto-translated from Mixin. See the generator directory in project root.
^/

// Note: this line is not recognized in the core package, but it is when translated and put into the main src directory
//? if >=1.19 {
package me.decce.ixeris.forge.transformers.glfw_threading_330;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWAllocator;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.InjectionCallback;

// This mixins houses functions that are added GLFW 3.3.4 (LWJGL 3.3.0) and do not exist in older versions of Minecraft
import net.lenni0451.classtransform.annotations.CInline;
import static me.decce.ixeris.core.util.LambdaHelper.*;

@CTransformer(value = GLFW.class)
public class GLFWTransformer {
    @CInline @CInject(method = "glfwInitAllocator", target = @CTarget("HEAD"), cancellable = true)
    private static void ixeris$glfwInitAllocator(GLFWAllocator allocator, InjectionCallback ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.setCancelled(true);
            MainThreadDispatcher.runNow(makeRunnable(GLFW::glfwInitAllocator, allocator));
        }
    }
}
//?}
*///?}