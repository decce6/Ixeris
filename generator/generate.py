"""
Simple script for generating repeated code

It does:

- read me.decce.ixeris.core.glfw.callback_dispatcher.CharCallbackDispatcher as template
- generate other callback dispatchers and store them in ./generated
- read me.decce.ixeris.core.mixins.callback_dispatcher.GLFWMixin and take the injections for the char callback as template for all other callbacks
- generate the mixin and store the result in ./generated
"""

import os
from dataclasses import dataclass
from callback_patcher import patch_class


@dataclass
class Callback:
    name: str
    params: list
    version: str
    def has_version(self):
        return self.version != None
    def has_window(self):
        return self.params[0].name == 'window'
    def params_str(self):
        ret = []
        for param in self.params:
            ret.append(param.type + " " + param.name)
        return ", ".join(ret)
    def params_str_no_type(self):
        ret = []
        for param in self.params:
            ret.append(param.name)
        return ", ".join(ret)


@dataclass
class Param:
    type: str
    name: str

callbacks = []

def lowercase_first_letter(string: str):
    return string[0].lower() + string[1:]

def filename_of(callback_name: str):
    return callback_name + 'CallbackDispatcher.java'

def init():
    input_file = './meta/callback_list.txt'
    callbacks_list = open(input_file, "r").readlines()
    for callback in callbacks_list:
        iequal = callback.find('=')
        version = ""
        if (iequal != -1):
            version = callback[(iequal+1):].strip()
            callback = callback[:iequal]
        left = callback.index('(')
        right = callback.index(')')
        callback_name = callback[:left]
        callback_params = []
        for strparam in callback[(left+1):right].split(','):
            strparam = strparam.strip()
            j = strparam.index(' ')
            callback_params.append(Param(strparam[0:j], strparam[(j+1):]))
        callbacks.append(Callback(callback_name, callback_params, None if iequal == -1 else version))

def replace_template(template : str, callback : Callback):
    result = template.replace(template_name, callback.name)
    result = result.replace(lowercase_first_letter(template_name), lowercase_first_letter(callback.name))
    result = result.replace(template_callback.params_str(), callback.params_str())
    result = result.replace(template_callback.params_str_no_type(), callback.params_str_no_type())
    result = patch_class(result, not callback.has_window())
    if (callback.version != None):
        result = result.replace("package me.decce.ixeris.core.glfw.callback_dispatcher", "package me.decce.ixeris.core.glfw.callback_dispatcher._" + callback.version)
        result = result.replace("CommonCallbacks", "CommonCallbacks_" + callback.version)
    return result

init()
input_dir = '../core/src/main/java/me/decce/ixeris/core/glfw/callback_dispatcher'
input_mixin_dir = '../core/src/main/java/me/decce/ixeris/core/mixins/callback_dispatcher'
mixin_filename = 'GLFWMixin.java'
output_dir = './generated/'
if not os.path.isdir(output_dir):
    os.makedir(output_dir)
template_name = 'Char'
template_callback = [x for x in callbacks if x.name == template_name][0]
template = open(os.path.join(input_dir, filename_of(template_name)), "r").read()
original_mixin_file = open(os.path.join(input_mixin_dir, mixin_filename), 'r').read()
mark_class_start = 'public class GLFWMixin {'
mark_generated_start = '// GENERATED CODE BELOW'
mixin_head = original_mixin_file[:(original_mixin_file.index(mark_generated_start))]
mixin_head_no_original_method = original_mixin_file[:(original_mixin_file.index(mark_class_start) + len(mark_class_start))] + '\n\n    ' + mark_generated_start + '\n'
mixin_template = mixin_head[(mixin_head.index('{') + 1):].rstrip()
mixin_head = mixin_head + mark_generated_start + '\n'
mixin_bodies = {}
for callback in callbacks:
    if callback.name == template_name:
        continue
    result = replace_template(template, callback)
    write_path = os.path.join(output_dir, "callback_dispatcher")
    if (callback.has_version()):
        write_path = os.path.join(write_path, '_' + callback.version)
    os.makedirs(write_path, exist_ok=True)
    open(os.path.join(write_path, filename_of(callback.name)), 'w').write(result)
    mixin_bodies[callback.version] = mixin_bodies.get(callback.version, "") + replace_template(mixin_template, callback) + '\n'
for mixin_version in mixin_bodies:
    if (mixin_version == None):
        write_path = os.path.join(output_dir, "mixins/callback_dispatcher")
        os.makedirs(write_path, exist_ok=True)
        open(os.path.join(write_path, mixin_filename), 'w').write(mixin_head + mixin_bodies[mixin_version] + '}')
    else:
        generated_class = mixin_head_no_original_method + mixin_bodies[mixin_version] + '}'
        generated_class = generated_class.replace("import me.decce.ixeris.core.glfw.callback_dispatcher", "import me.decce.ixeris.core.glfw.callback_dispatcher._" + mixin_version)
        write_path = os.path.join(output_dir, "mixins/callback_dispatcher_" + mixin_version)
        os.makedirs(write_path, exist_ok=True)
        open(os.path.join(write_path, mixin_filename), 'w').write(generated_class)