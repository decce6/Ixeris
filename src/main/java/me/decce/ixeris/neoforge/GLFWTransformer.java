package me.decce.ixeris.neoforge;

import me.decce.ixeris.Ixeris;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.injection.CInject;
import org.lwjgl.glfw.GLFW;

@CTransformer(GLFW.class)
public class GLFWTransformer {
    @CInject(method = "glfwInit", target = @CTarget("TAIL"))
    private static void ixeris$glfwInit() {
        System.out.println("GLFW Init"); // TODO remove
        Ixeris.glfwInitialized = true; // <---- Ixeris class is not in the boot layer
    }
}
