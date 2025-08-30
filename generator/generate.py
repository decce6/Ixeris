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
        left = callback.index('(')
        right = callback.index(')')
        callback_name = callback[:left]
        callback_params = []
        for strparam in callback[(left+1):right].split(','):
            strparam = strparam.strip()
            j = strparam.index(' ')
            callback_params.append(Param(strparam[0:j], strparam[(j+1):]))
        callbacks.append(Callback(callback_name, callback_params))

def replace_template(template : str, callback : Callback):
    result = template.replace(template_name, callback.name)
    result = result.replace(lowercase_first_letter(template_name), lowercase_first_letter(callback.name))
    result = result.replace(template_callback.params_str(), callback.params_str())
    result = result.replace(template_callback.params_str_no_type(), callback.params_str_no_type())
    result = patch_class(result, not callback.has_window())
    return result

init()
input_dir = '../core/src/main/java/me/decce/ixeris/core/glfw/callback_dispatcher'
input_mixin_dir = '../src/main/java/me/decce/ixeris/mixins/callback_dispatcher'
mixin_filename = 'GLFWMixin.java'
output_dir = './generated/'
if not os.path.isdir(output_dir):
    os.makedir(output_dir)
template_name = 'Char'
template_callback = [x for x in callbacks if x.name == template_name][0]
template = open(os.path.join(input_dir, filename_of(template_name)), "r").read()
mixin_head = open(os.path.join(input_mixin_dir, mixin_filename), 'r').read()
mark = '// GENERATED CODE BELOW'
mixin_head = mixin_head[:(mixin_head.index(mark))]
mixin_template = mixin_head[(mixin_head.index('{') + 1):].rstrip()
mixin_class = mixin_head + mark + '\n'
for callback in callbacks:
    if callback.name == template_name:
        continue
    result = replace_template(template, callback)
    open(os.path.join(output_dir, filename_of(callback.name)), 'w').write(result)
    mixin_class += replace_template(mixin_template, callback) + '\n'
open(os.path.join(output_dir, mixin_filename), 'w').write(mixin_class + '}')