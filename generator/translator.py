# Simple, brute-force script that takes Mixins and translates them for use by the ClassTransform library
import os
import re

translations = {
    "me.decce.ixeris.core.mixins" : "me.decce.ixeris.forge.core.transformers",
    "import org.spongepowered.asm.mixin.Unique;": "",
    "org.spongepowered.asm.mixin.Mixin":"net.lenni0451.classtransform.annotations.CTransformer",
    "org.spongepowered.asm.mixin.injection.At":"net.lenni0451.classtransform.annotations.CTarget",
    "org.spongepowered.asm.mixin.injection.Inject":"net.lenni0451.classtransform.annotations.injection.CInject",
    "org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable":"net.lenni0451.classtransform.InjectionCallback",
    "org.spongepowered.asm.mixin.injection.callback.CallbackInfo":"net.lenni0451.classtransform.InjectionCallback",
    "@Mixin" : "@CTransformer",
    ", remap = false" : "",
    "@Inject" : "@CInline @CInject",
    "at = @At" : "target = @CTarget",
    ".cancel()" : ".setCancelled(true)",
    "@Unique": "",
    "CallbackInfo" : "InjectionCallback",
    "GLFWMixin" : "GLFWTransformer"
}

translations_regex = {
    "CallbackInfoReturnable<(.+)>":"InjectionCallback"
}

to_import = [
    "net.lenni0451.classtransform.annotations.CInline",
    "static me.decce.ixeris.core.util.LambdaHelper.*"
]

def add_import(mixin: str):
    return mixin.replace("@CTransformer", '\n'.join(map(lambda x : "import " + x + ";", to_import))+"\n\n@CTransformer")

def nuke_lambdas(mixin : str) -> str:
    while ("run(() -> " in mixin):
        i = mixin.index("run(() -> ") + 3
        j = mixin.index("glfw", i + 1)
        k = mixin.index("(", j)
        l = mixin.index(")",k)
        fun = mixin[j:k]
        params0 = mixin[(k+1):l]
        if (params0 != ""):
            params0 = ", " + params0
        mixin = mixin.replace(mixin[i:(l)], f"(makeRunnable(GLFW::{fun}{params0}")
    while ("runNow(() -> " in mixin):
        i = mixin.index("runNow(() -> ") + 6
        j = mixin.index("glfw", i + 1)
        k = mixin.index("(", j)
        l = mixin.index(")",k)
        fun = mixin[j:k]
        params0 = mixin[(k+1):l]
        if (params0 != ""):
            params0 = ", " + params0
        mixin = mixin.replace(mixin[i:(l)], f"(makeRunnable(GLFW::{fun}{params0}")
    while ("query(() -> " in mixin):
        i = mixin.index("query(() -> ") + 5
        j = mixin.index("glfw", i + 1)
        k = mixin.index("(", j)
        l = mixin.index(")",k)
        fun = mixin[j:k]
        params0 = mixin[(k+1):l]
        if (params0 != ""):
            params0 = ", " + params0
        mixin = mixin.replace(mixin[i:(l)], f"(makeSupplier(GLFW::{fun}{params0}")
    return mixin


def translate(mixin : str) -> str:
    for t in translations_regex.items():
        mixin = re.sub(t[0], t[1], mixin)
    for t in translations.items():
        mixin = mixin.replace(t[0], t[1])
    mixin = "/*\nAuto-translated from Mixin. See the generator directory in project root.\n*/\n\n"+mixin
    return mixin

mixins_dir = "../core/src/main/java/me/decce/ixeris/core/mixins"
mixins = []
output_dir = "./generated/transformers"
if not os.path.exists(output_dir):
    os.makedirs(output_dir)
transformers = []
for r, d, f in os.walk(mixins_dir):
    for file in f:
        if file.endswith(".java"):
            mixins.append(r + "/" + file)

for mixin in mixins:
    translated = translate(open(mixin, 'r').read())
    translated = add_import(nuke_lambdas(translated))
    p = os.path.join("./generated/transformers", os.path.relpath(mixin.replace("Mixin", "Transformer"), mixins_dir))
    if not os.path.exists(os.path.dirname(p)):
        os.makedirs(os.path.dirname(p))
    open(p, 'w').write(translated)