# Simple, brute-force script that takes Mixins and translates them for use by the ClassTransform library
import os
import re

translations = {
    "me.decce.ixeris.core.mixins" : "me.decce.ixeris.forge.transformers",
    "import org.spongepowered.asm.mixin.Unique;": "",
    "org.spongepowered.asm.mixin.Mixin":"net.lenni0451.classtransform.annotations.CTransformer",
    "org.spongepowered.asm.mixin.injection.At":"net.lenni0451.classtransform.annotations.CTarget",
    "org.spongepowered.asm.mixin.injection.Inject":"net.lenni0451.classtransform.annotations.injection.CInject",
    "org.spongepowered.asm.mixin.injection.Redirect":"net.lenni0451.classtransform.annotations.injection.CRedirect",
    "org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable":"net.lenni0451.classtransform.InjectionCallback",
    "org.spongepowered.asm.mixin.injection.callback.CallbackInfo":"net.lenni0451.classtransform.InjectionCallback",
    "@Mixin" : "@CTransformer",
    ", remap = false" : "",
    "@Inject" : "@CInline @CInject",
    "// ~@CInline" : "@CInline",
    "@Redirect" : "@CInline @CRedirect",
    "at = @At" : "target = @CTarget",
    ".cancel()" : ".setCancelled(true)",
    "@Unique": "",
    "CallbackInfo" : "InjectionCallback",
    "Mixin {": "Transformer {"
}

translations_regex = {
    "CallbackInfoReturnable<(.+)>":"InjectionCallback",
    r",\s*priority\s*=\s*\d+":"",
    r",\s*require\s*=\s*\d+":"",
    r",\s*expect\s*=\s*\d+":""
}

to_import = [
    "net.lenni0451.classtransform.annotations.CInline",
    "static me.decce.ixeris.core.util.LambdaHelper.*"
]

def add_import(mixin: str):
    return mixin.replace("@CTransformer", '\n'.join(map(lambda x : "import " + x + ";", to_import))+"\n\n@CTransformer")

def nuke_lambdas(mixin : str, class_name : str) -> str:
    for method, maker in (("run", "makeRunnable"), ("runNow", "makeRunnable"), ("query", "makeSupplier")):
        needle = method + "(() -> "
        while (needle in mixin):
            start = mixin.index(needle)
            i = start + len(method)
            if ("glfw" in mixin):
                j = mixin.index("glfw", i + 1)
            else:
                j = mixin.index("SDL", i + 1) + len(class_name) + 1
            k = mixin.index("(", j)
            l = mixin.index(")",k)
            fun = mixin[j:k]
            params0 = mixin[(k+1):l]
            if (params0 != ""):
                params0 = ", " + params0
            mixin = mixin.replace(mixin[start:l], f"{method}({maker}({class_name}::{fun}{params0}", 1)
    return mixin
def make_forge_only(mixin : str) -> str:
    return "//? if forge { \n" + mixin + "\n//? }"

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
    i1 = mixin.rfind("/")
    i2 = mixin.index("Mixin")
    class_name = mixin[(i1+1):i2]
    translated = make_forge_only(add_import(nuke_lambdas(translated, class_name)))
    p = os.path.join("./generated/transformers", os.path.relpath(mixin.replace("Mixin", "Transformer"), mixins_dir))
    if not os.path.exists(os.path.dirname(p)):
        os.makedirs(os.path.dirname(p))
    open(p, 'w').write(translated)