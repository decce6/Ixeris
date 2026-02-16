/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 * MACHINE GENERATED FILE, DO NOT EDIT
 */
package me.decce.ixeris.core.win32;

import javax.annotation.*;

import java.nio.*;

import org.lwjgl.*;
import org.lwjgl.system.*;

import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.MemoryStack.*;

/**
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct RAWINPUT {
 *     {@link RAWINPUTHEADER RAWINPUTHEADER} header;
 *     union {
 *         {@link RAWMOUSE RAWMOUSE} mouse;
 *         {@link RAWKEYBOARD RAWKEYBOARD} keyboard;
 *         {@link RAWHID RAWHID} hid;
 *     };
 * }</code></pre>
 */
public class RAWINPUT extends Struct<RAWINPUT> implements NativeResource {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        HEADER,
        MOUSE,
        KEYBOARD,
        HID;

    static {
        Layout layout = __struct(
            __member(RAWINPUTHEADER.SIZEOF, RAWINPUTHEADER.ALIGNOF),
            __union(
                __member(RAWMOUSE.SIZEOF, RAWMOUSE.ALIGNOF),
                __member(RAWKEYBOARD.SIZEOF, RAWKEYBOARD.ALIGNOF),
                __member(RAWHID.SIZEOF, RAWHID.ALIGNOF)
            )
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        HEADER = layout.offsetof(0);
        MOUSE = layout.offsetof(2);
        KEYBOARD = layout.offsetof(3);
        HID = layout.offsetof(4);
    }

    protected RAWINPUT(long address, @Nullable ByteBuffer container) {
        super(address, container);
    }

    @Override
    protected RAWINPUT create(long address, @Nullable ByteBuffer container) {
        return new RAWINPUT(address, container);
    }

    /**
     * Creates a {@code RAWINPUT} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public RAWINPUT(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** @return a {@link RAWINPUTHEADER} view of the {@code header} field. */
    public RAWINPUTHEADER header() { return nheader(address()); }
    /** @return a {@link RAWMOUSE} view of the {@code mouse} field. */
    public RAWMOUSE mouse() { return nmouse(address()); }
    /** @return a {@link RAWKEYBOARD} view of the {@code keyboard} field. */
    public RAWKEYBOARD keyboard() { return nkeyboard(address()); }
    /** @return a {@link RAWHID} view of the {@code hid} field. */
    public RAWHID hid() { return nhid(address()); }

    /** Copies the specified {@link RAWINPUTHEADER} to the {@code header} field. */
    public RAWINPUT header(RAWINPUTHEADER value) { nheader(address(), value); return this; }
    /** Passes the {@code header} field to the specified {@link java.util.function.Consumer Consumer}. */
    public RAWINPUT header(java.util.function.Consumer<RAWINPUTHEADER> consumer) { consumer.accept(header()); return this; }
    /** Copies the specified {@link RAWMOUSE} to the {@code mouse} field. */
    public RAWINPUT mouse(RAWMOUSE value) { nmouse(address(), value); return this; }
    /** Passes the {@code mouse} field to the specified {@link java.util.function.Consumer Consumer}. */
    public RAWINPUT mouse(java.util.function.Consumer<RAWMOUSE> consumer) { consumer.accept(mouse()); return this; }
    /** Copies the specified {@link RAWKEYBOARD} to the {@code keyboard} field. */
    public RAWINPUT keyboard(RAWKEYBOARD value) { nkeyboard(address(), value); return this; }
    /** Passes the {@code keyboard} field to the specified {@link java.util.function.Consumer Consumer}. */
    public RAWINPUT keyboard(java.util.function.Consumer<RAWKEYBOARD> consumer) { consumer.accept(keyboard()); return this; }
    /** Copies the specified {@link RAWHID} to the {@code hid} field. */
    public RAWINPUT hid(RAWHID value) { nhid(address(), value); return this; }
    /** Passes the {@code hid} field to the specified {@link java.util.function.Consumer Consumer}. */
    public RAWINPUT hid(java.util.function.Consumer<RAWHID> consumer) { consumer.accept(hid()); return this; }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public RAWINPUT set(RAWINPUT src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    // -----------------------------------

    /** Returns a new {@code RAWINPUT} instance allocated with {@link MemoryUtil#memAlloc memAlloc}. The instance must be explicitly freed. */
    public static RAWINPUT malloc() {
        return new RAWINPUT(nmemAllocChecked(SIZEOF), null);
    }

    /** Returns a new {@code RAWINPUT} instance allocated with {@link MemoryUtil#memCalloc memCalloc}. The instance must be explicitly freed. */
    public static RAWINPUT calloc() {
        return new RAWINPUT(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code RAWINPUT} instance allocated with {@link BufferUtils}. */
    public static RAWINPUT create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new RAWINPUT(memAddress(container), container);
    }

    /** Returns a new {@code RAWINPUT} instance for the specified memory address. */
    public static RAWINPUT create(long address) {
        return new RAWINPUT(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static @Nullable RAWINPUT createSafe(long address) {
        return address == NULL ? null : new RAWINPUT(address, null);
    }

    /**
     * Returns a new {@link Buffer} instance allocated with {@link MemoryUtil#memAlloc memAlloc}. The instance must be explicitly freed.
     *
     * @param capacity the buffer capacity
     */
    public static Buffer malloc(int capacity) {
        return new Buffer(nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
    }

    /**
     * Returns a new {@link Buffer} instance allocated with {@link MemoryUtil#memCalloc memCalloc}. The instance must be explicitly freed.
     *
     * @param capacity the buffer capacity
     */
    public static Buffer calloc(int capacity) {
        return new Buffer(nmemCallocChecked(capacity, SIZEOF), capacity);
    }

    /**
     * Returns a new {@link Buffer} instance allocated with {@link BufferUtils}.
     *
     * @param capacity the buffer capacity
     */
    public static Buffer create(int capacity) {
        ByteBuffer container = __create(capacity, SIZEOF);
        return new Buffer(memAddress(container), container, -1, 0, capacity, capacity);
    }

    /**
     * Create a {@link Buffer} instance at the specified memory.
     *
     * @param address  the memory address
     * @param capacity the buffer capacity
     */
    public static Buffer create(long address, int capacity) {
        return new Buffer(address, capacity);
    }

    /** Like {@link #create(long, int) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static @Nullable Buffer createSafe(long address, int capacity) {
        return address == NULL ? null : new Buffer(address, capacity);
    }

    /**
     * Returns a new {@code RAWINPUT} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static RAWINPUT malloc(MemoryStack stack) {
        return new RAWINPUT(stack.nmalloc(ALIGNOF, SIZEOF), null);
    }

    /**
     * Returns a new {@code RAWINPUT} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static RAWINPUT calloc(MemoryStack stack) {
        return new RAWINPUT(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
    }

    /**
     * Returns a new {@link Buffer} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack    the stack from which to allocate
     * @param capacity the buffer capacity
     */
    public static Buffer malloc(int capacity, MemoryStack stack) {
        return new Buffer(stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
    }

    /**
     * Returns a new {@link Buffer} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack    the stack from which to allocate
     * @param capacity the buffer capacity
     */
    public static Buffer calloc(int capacity, MemoryStack stack) {
        return new Buffer(stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
    }

    // -----------------------------------

    /** Unsafe version of {@link #header}. */
    public static RAWINPUTHEADER nheader(long struct) { return RAWINPUTHEADER.create(struct + RAWINPUT.HEADER); }
    /** Unsafe version of {@link #mouse}. */
    public static RAWMOUSE nmouse(long struct) { return RAWMOUSE.create(struct + RAWINPUT.MOUSE); }
    /** Unsafe version of {@link #keyboard}. */
    public static RAWKEYBOARD nkeyboard(long struct) { return RAWKEYBOARD.create(struct + RAWINPUT.KEYBOARD); }
    /** Unsafe version of {@link #hid}. */
    public static RAWHID nhid(long struct) { return RAWHID.create(struct + RAWINPUT.HID); }

    /** Unsafe version of {@link #header(RAWINPUTHEADER) header}. */
    public static void nheader(long struct, RAWINPUTHEADER value) { memCopy(value.address(), struct + RAWINPUT.HEADER, RAWINPUTHEADER.SIZEOF); }
    /** Unsafe version of {@link #mouse(RAWMOUSE) mouse}. */
    public static void nmouse(long struct, RAWMOUSE value) { memCopy(value.address(), struct + RAWINPUT.MOUSE, RAWMOUSE.SIZEOF); }
    /** Unsafe version of {@link #keyboard(RAWKEYBOARD) keyboard}. */
    public static void nkeyboard(long struct, RAWKEYBOARD value) { memCopy(value.address(), struct + RAWINPUT.KEYBOARD, RAWKEYBOARD.SIZEOF); }
    /** Unsafe version of {@link #hid(RAWHID) hid}. */
    public static void nhid(long struct, RAWHID value) { memCopy(value.address(), struct + RAWINPUT.HID, RAWHID.SIZEOF); }

    /**
     * Validates pointer members that should not be {@code NULL}.
     *
     * @param struct the struct to validate
     */
    public static void validate(long struct) {
        RAWINPUTHEADER.validate(struct + RAWINPUT.HEADER);
    }

    // -----------------------------------

    /** An array of {@link RAWINPUT} structs. */
    public static class Buffer extends StructBuffer<RAWINPUT, Buffer> implements NativeResource {

        private static final RAWINPUT ELEMENT_FACTORY = RAWINPUT.create(-1L);

        /**
         * Creates a new {@code RAWINPUT.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link RAWINPUT#SIZEOF}, and its mark will be undefined.</p>
         *
         * <p>The created buffer instance holds a strong reference to the container object.</p>
         */
        public Buffer(ByteBuffer container) {
            super(container, container.remaining() / SIZEOF);
        }

        public Buffer(long address, int cap) {
            super(address, null, -1, 0, cap, cap);
        }

        Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
            super(address, container, mark, pos, lim, cap);
        }

        @Override
        protected Buffer self() {
            return this;
        }

        @Override
        protected Buffer create(long address, @Nullable ByteBuffer container, int mark, int position, int limit, int capacity) {
            return new Buffer(address, container, mark, position, limit, capacity);
        }

        @Override
        protected RAWINPUT getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** @return a {@link RAWINPUTHEADER} view of the {@code header} field. */
        public RAWINPUTHEADER header() { return RAWINPUT.nheader(address()); }
        /** @return a {@link RAWMOUSE} view of the {@code mouse} field. */
        public RAWMOUSE mouse() { return RAWINPUT.nmouse(address()); }
        /** @return a {@link RAWKEYBOARD} view of the {@code keyboard} field. */
        public RAWKEYBOARD keyboard() { return RAWINPUT.nkeyboard(address()); }
        /** @return a {@link RAWHID} view of the {@code hid} field. */
        public RAWHID hid() { return RAWINPUT.nhid(address()); }

        /** Copies the specified {@link RAWINPUTHEADER} to the {@code header} field. */
        public Buffer header(RAWINPUTHEADER value) { RAWINPUT.nheader(address(), value); return this; }
        /** Passes the {@code header} field to the specified {@link java.util.function.Consumer Consumer}. */
        public Buffer header(java.util.function.Consumer<RAWINPUTHEADER> consumer) { consumer.accept(header()); return this; }
        /** Copies the specified {@link RAWMOUSE} to the {@code mouse} field. */
        public Buffer mouse(RAWMOUSE value) { RAWINPUT.nmouse(address(), value); return this; }
        /** Passes the {@code mouse} field to the specified {@link java.util.function.Consumer Consumer}. */
        public Buffer mouse(java.util.function.Consumer<RAWMOUSE> consumer) { consumer.accept(mouse()); return this; }
        /** Copies the specified {@link RAWKEYBOARD} to the {@code keyboard} field. */
        public Buffer keyboard(RAWKEYBOARD value) { RAWINPUT.nkeyboard(address(), value); return this; }
        /** Passes the {@code keyboard} field to the specified {@link java.util.function.Consumer Consumer}. */
        public Buffer keyboard(java.util.function.Consumer<RAWKEYBOARD> consumer) { consumer.accept(keyboard()); return this; }
        /** Copies the specified {@link RAWHID} to the {@code hid} field. */
        public Buffer hid(RAWHID value) { RAWINPUT.nhid(address(), value); return this; }
        /** Passes the {@code hid} field to the specified {@link java.util.function.Consumer Consumer}. */
        public Buffer hid(java.util.function.Consumer<RAWHID> consumer) { consumer.accept(hid()); return this; }

    }

}