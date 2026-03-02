package me.decce.ixeris.core.input.win32;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public class KeyCodeTranslatorWin32 {
    public static final short[] keycodes;
    public static final short[] scancodes;

    static {
        // https://github.com/glfw/glfw/blob/7c9ea952338b792cb2f70d7c663e498725073797/src/win32_init.c#L171

        keycodes = new short[512];
        scancodes = new short[GLFW_KEY_LAST + 1];
        Arrays.fill(keycodes, (short) -1);
        Arrays.fill(keycodes, (short) -1);
        keycodes[0x00B] = GLFW_KEY_0;
        keycodes[0x002] = GLFW_KEY_1;
        keycodes[0x003] = GLFW_KEY_2;
        keycodes[0x004] = GLFW_KEY_3;
        keycodes[0x005] = GLFW_KEY_4;
        keycodes[0x006] = GLFW_KEY_5;
        keycodes[0x007] = GLFW_KEY_6;
        keycodes[0x008] = GLFW_KEY_7;
        keycodes[0x009] = GLFW_KEY_8;
        keycodes[0x00A] = GLFW_KEY_9;
        keycodes[0x01E] = GLFW_KEY_A;
        keycodes[0x030] = GLFW_KEY_B;
        keycodes[0x02E] = GLFW_KEY_C;
        keycodes[0x020] = GLFW_KEY_D;
        keycodes[0x012] = GLFW_KEY_E;
        keycodes[0x021] = GLFW_KEY_F;
        keycodes[0x022] = GLFW_KEY_G;
        keycodes[0x023] = GLFW_KEY_H;
        keycodes[0x017] = GLFW_KEY_I;
        keycodes[0x024] = GLFW_KEY_J;
        keycodes[0x025] = GLFW_KEY_K;
        keycodes[0x026] = GLFW_KEY_L;
        keycodes[0x032] = GLFW_KEY_M;
        keycodes[0x031] = GLFW_KEY_N;
        keycodes[0x018] = GLFW_KEY_O;
        keycodes[0x019] = GLFW_KEY_P;
        keycodes[0x010] = GLFW_KEY_Q;
        keycodes[0x013] = GLFW_KEY_R;
        keycodes[0x01F] = GLFW_KEY_S;
        keycodes[0x014] = GLFW_KEY_T;
        keycodes[0x016] = GLFW_KEY_U;
        keycodes[0x02F] = GLFW_KEY_V;
        keycodes[0x011] = GLFW_KEY_W;
        keycodes[0x02D] = GLFW_KEY_X;
        keycodes[0x015] = GLFW_KEY_Y;
        keycodes[0x02C] = GLFW_KEY_Z;

        keycodes[0x028] = GLFW_KEY_APOSTROPHE;
        keycodes[0x02B] = GLFW_KEY_BACKSLASH;
        keycodes[0x033] = GLFW_KEY_COMMA;
        keycodes[0x00D] = GLFW_KEY_EQUAL;
        keycodes[0x029] = GLFW_KEY_GRAVE_ACCENT;
        keycodes[0x01A] = GLFW_KEY_LEFT_BRACKET;
        keycodes[0x00C] = GLFW_KEY_MINUS;
        keycodes[0x034] = GLFW_KEY_PERIOD;
        keycodes[0x01B] = GLFW_KEY_RIGHT_BRACKET;
        keycodes[0x027] = GLFW_KEY_SEMICOLON;
        keycodes[0x035] = GLFW_KEY_SLASH;
        keycodes[0x056] = GLFW_KEY_WORLD_2;

        keycodes[0x00E] = GLFW_KEY_BACKSPACE;
        keycodes[0x153] = GLFW_KEY_DELETE;
        keycodes[0x14F] = GLFW_KEY_END;
        keycodes[0x01C] = GLFW_KEY_ENTER;
        keycodes[0x001] = GLFW_KEY_ESCAPE;
        keycodes[0x147] = GLFW_KEY_HOME;
        keycodes[0x152] = GLFW_KEY_INSERT;
        keycodes[0x15D] = GLFW_KEY_MENU;
        keycodes[0x151] = GLFW_KEY_PAGE_DOWN;
        keycodes[0x149] = GLFW_KEY_PAGE_UP;
        keycodes[0x045] = GLFW_KEY_PAUSE;
        keycodes[0x039] = GLFW_KEY_SPACE;
        keycodes[0x00F] = GLFW_KEY_TAB;
        keycodes[0x03A] = GLFW_KEY_CAPS_LOCK;
        keycodes[0x145] = GLFW_KEY_NUM_LOCK;
        keycodes[0x046] = GLFW_KEY_SCROLL_LOCK;
        keycodes[0x03B] = GLFW_KEY_F1;
        keycodes[0x03C] = GLFW_KEY_F2;
        keycodes[0x03D] = GLFW_KEY_F3;
        keycodes[0x03E] = GLFW_KEY_F4;
        keycodes[0x03F] = GLFW_KEY_F5;
        keycodes[0x040] = GLFW_KEY_F6;
        keycodes[0x041] = GLFW_KEY_F7;
        keycodes[0x042] = GLFW_KEY_F8;
        keycodes[0x043] = GLFW_KEY_F9;
        keycodes[0x044] = GLFW_KEY_F10;
        keycodes[0x057] = GLFW_KEY_F11;
        keycodes[0x058] = GLFW_KEY_F12;
        keycodes[0x064] = GLFW_KEY_F13;
        keycodes[0x065] = GLFW_KEY_F14;
        keycodes[0x066] = GLFW_KEY_F15;
        keycodes[0x067] = GLFW_KEY_F16;
        keycodes[0x068] = GLFW_KEY_F17;
        keycodes[0x069] = GLFW_KEY_F18;
        keycodes[0x06A] = GLFW_KEY_F19;
        keycodes[0x06B] = GLFW_KEY_F20;
        keycodes[0x06C] = GLFW_KEY_F21;
        keycodes[0x06D] = GLFW_KEY_F22;
        keycodes[0x06E] = GLFW_KEY_F23;
        keycodes[0x076] = GLFW_KEY_F24;
        keycodes[0x038] = GLFW_KEY_LEFT_ALT;
        keycodes[0x01D] = GLFW_KEY_LEFT_CONTROL;
        keycodes[0x02A] = GLFW_KEY_LEFT_SHIFT;
        keycodes[0x15B] = GLFW_KEY_LEFT_SUPER;
        keycodes[0x137] = GLFW_KEY_PRINT_SCREEN;
        keycodes[0x138] = GLFW_KEY_RIGHT_ALT;
        keycodes[0x11D] = GLFW_KEY_RIGHT_CONTROL;
        keycodes[0x036] = GLFW_KEY_RIGHT_SHIFT;
        keycodes[0x15C] = GLFW_KEY_RIGHT_SUPER;
        keycodes[0x150] = GLFW_KEY_DOWN;
        keycodes[0x14B] = GLFW_KEY_LEFT;
        keycodes[0x14D] = GLFW_KEY_RIGHT;
        keycodes[0x148] = GLFW_KEY_UP;

        keycodes[0x052] = GLFW_KEY_KP_0;
        keycodes[0x04F] = GLFW_KEY_KP_1;
        keycodes[0x050] = GLFW_KEY_KP_2;
        keycodes[0x051] = GLFW_KEY_KP_3;
        keycodes[0x04B] = GLFW_KEY_KP_4;
        keycodes[0x04C] = GLFW_KEY_KP_5;
        keycodes[0x04D] = GLFW_KEY_KP_6;
        keycodes[0x047] = GLFW_KEY_KP_7;
        keycodes[0x048] = GLFW_KEY_KP_8;
        keycodes[0x049] = GLFW_KEY_KP_9;
        keycodes[0x04E] = GLFW_KEY_KP_ADD;
        keycodes[0x053] = GLFW_KEY_KP_DECIMAL;
        keycodes[0x135] = GLFW_KEY_KP_DIVIDE;
        keycodes[0x11C] = GLFW_KEY_KP_ENTER;
        keycodes[0x059] = GLFW_KEY_KP_EQUAL;
        keycodes[0x037] = GLFW_KEY_KP_MULTIPLY;
        keycodes[0x04A] = GLFW_KEY_KP_SUBTRACT;

        for (short scancode = 0; scancode < 512; scancode++)
        {
            if (keycodes[scancode] > 0)
                scancodes[keycodes[scancode]] = scancode;
        }
    }
}
