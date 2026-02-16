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

import static org.lwjgl.system.Checks.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.MemoryStack.*;

/**
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct RAWINPUTDEVICE {
 *     USHORT usUsagePage;
 *     USHORT usUsage;
 *     DWORD dwFlags;
 *     HWND hwndTarget;
 * }</code></pre>
 */
public class RAWINPUTDEVICE extends Struct<RAWINPUTDEVICE> implements NativeResource {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        USUSAGEPAGE,
        USUSAGE,
        DWFLAGS,
        HWNDTARGET;

    static {
        Layout layout = __struct(
            __member(2),
            __member(2),
            __member(4),
            __member(POINTER_SIZE)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        USUSAGEPAGE = layout.offsetof(0);
        USUSAGE = layout.offsetof(1);
        DWFLAGS = layout.offsetof(2);
        HWNDTARGET = layout.offsetof(3);
    }

    protected RAWINPUTDEVICE(long address, @Nullable ByteBuffer container) {
        super(address, container);
    }

    @Override
    protected RAWINPUTDEVICE create(long address, @Nullable ByteBuffer container) {
        return new RAWINPUTDEVICE(address, container);
    }

    /**
     * Creates a {@code RAWINPUTDEVICE} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public RAWINPUTDEVICE(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** @return the value of the {@code usUsagePage} field. */
    @NativeType("USHORT")
    public short usUsagePage() { return nusUsagePage(address()); }
    /** @return the value of the {@code usUsage} field. */
    @NativeType("USHORT")
    public short usUsage() { return nusUsage(address()); }
    /** @return the value of the {@code dwFlags} field. */
    @NativeType("DWORD")
    public int dwFlags() { return ndwFlags(address()); }
    /** @return the value of the {@code hwndTarget} field. */
    @NativeType("HWND")
    public long hwndTarget() { return nhwndTarget(address()); }

    /** Sets the specified value to the {@code usUsagePage} field. */
    public RAWINPUTDEVICE usUsagePage(@NativeType("USHORT") short value) { nusUsagePage(address(), value); return this; }
    /** Sets the specified value to the {@code usUsage} field. */
    public RAWINPUTDEVICE usUsage(@NativeType("USHORT") short value) { nusUsage(address(), value); return this; }
    /** Sets the specified value to the {@code dwFlags} field. */
    public RAWINPUTDEVICE dwFlags(@NativeType("DWORD") int value) { ndwFlags(address(), value); return this; }
    /** Sets the specified value to the {@code hwndTarget} field. */
    public RAWINPUTDEVICE hwndTarget(@NativeType("HWND") long value) { nhwndTarget(address(), value); return this; }

    /** Initializes this struct with the specified values. */
    public RAWINPUTDEVICE set(
        short usUsagePage,
        short usUsage,
        int dwFlags,
        long hwndTarget
    ) {
        usUsagePage(usUsagePage);
        usUsage(usUsage);
        dwFlags(dwFlags);
        hwndTarget(hwndTarget);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public RAWINPUTDEVICE set(RAWINPUTDEVICE src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    // -----------------------------------

    /** Returns a new {@code RAWINPUTDEVICE} instance allocated with {@link MemoryUtil#memAlloc memAlloc}. The instance must be explicitly freed. */
    public static RAWINPUTDEVICE malloc() {
        return new RAWINPUTDEVICE(nmemAllocChecked(SIZEOF), null);
    }

    /** Returns a new {@code RAWINPUTDEVICE} instance allocated with {@link MemoryUtil#memCalloc memCalloc}. The instance must be explicitly freed. */
    public static RAWINPUTDEVICE calloc() {
        return new RAWINPUTDEVICE(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code RAWINPUTDEVICE} instance allocated with {@link BufferUtils}. */
    public static RAWINPUTDEVICE create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new RAWINPUTDEVICE(memAddress(container), container);
    }

    /** Returns a new {@code RAWINPUTDEVICE} instance for the specified memory address. */
    public static RAWINPUTDEVICE create(long address) {
        return new RAWINPUTDEVICE(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static @Nullable RAWINPUTDEVICE createSafe(long address) {
        return address == NULL ? null : new RAWINPUTDEVICE(address, null);
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
     * Returns a new {@code RAWINPUTDEVICE} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static RAWINPUTDEVICE malloc(MemoryStack stack) {
        return new RAWINPUTDEVICE(stack.nmalloc(ALIGNOF, SIZEOF), null);
    }

    /**
     * Returns a new {@code RAWINPUTDEVICE} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static RAWINPUTDEVICE calloc(MemoryStack stack) {
        return new RAWINPUTDEVICE(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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

    /** Unsafe version of {@link #usUsagePage}. */
    public static short nusUsagePage(long struct) { return memGetShort(struct + RAWINPUTDEVICE.USUSAGEPAGE); }
    /** Unsafe version of {@link #usUsage}. */
    public static short nusUsage(long struct) { return memGetShort(struct + RAWINPUTDEVICE.USUSAGE); }
    /** Unsafe version of {@link #dwFlags}. */
    public static int ndwFlags(long struct) { return memGetInt(struct + RAWINPUTDEVICE.DWFLAGS); }
    /** Unsafe version of {@link #hwndTarget}. */
    public static long nhwndTarget(long struct) { return memGetAddress(struct + RAWINPUTDEVICE.HWNDTARGET); }

    /** Unsafe version of {@link #usUsagePage(short) usUsagePage}. */
    public static void nusUsagePage(long struct, short value) { memPutShort(struct + RAWINPUTDEVICE.USUSAGEPAGE, value); }
    /** Unsafe version of {@link #usUsage(short) usUsage}. */
    public static void nusUsage(long struct, short value) { memPutShort(struct + RAWINPUTDEVICE.USUSAGE, value); }
    /** Unsafe version of {@link #dwFlags(int) dwFlags}. */
    public static void ndwFlags(long struct, int value) { memPutInt(struct + RAWINPUTDEVICE.DWFLAGS, value); }
    /** Unsafe version of {@link #hwndTarget(long) hwndTarget}. */
    public static void nhwndTarget(long struct, long value) { memPutAddress(struct + RAWINPUTDEVICE.HWNDTARGET, value); }

    /**
     * Validates pointer members that should not be {@code NULL}.
     *
     * @param struct the struct to validate
     */
    public static void validate(long struct) {
        check(memGetAddress(struct + RAWINPUTDEVICE.HWNDTARGET));
    }

    // -----------------------------------

    /** An array of {@link RAWINPUTDEVICE} structs. */
    public static class Buffer extends StructBuffer<RAWINPUTDEVICE, Buffer> implements NativeResource {

        private static final RAWINPUTDEVICE ELEMENT_FACTORY = RAWINPUTDEVICE.create(-1L);

        /**
         * Creates a new {@code RAWINPUTDEVICE.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link RAWINPUTDEVICE#SIZEOF}, and its mark will be undefined.</p>
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
        protected RAWINPUTDEVICE getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** @return the value of the {@code usUsagePage} field. */
        @NativeType("USHORT")
        public short usUsagePage() { return RAWINPUTDEVICE.nusUsagePage(address()); }
        /** @return the value of the {@code usUsage} field. */
        @NativeType("USHORT")
        public short usUsage() { return RAWINPUTDEVICE.nusUsage(address()); }
        /** @return the value of the {@code dwFlags} field. */
        @NativeType("DWORD")
        public int dwFlags() { return RAWINPUTDEVICE.ndwFlags(address()); }
        /** @return the value of the {@code hwndTarget} field. */
        @NativeType("HWND")
        public long hwndTarget() { return RAWINPUTDEVICE.nhwndTarget(address()); }

        /** Sets the specified value to the {@code usUsagePage} field. */
        public Buffer usUsagePage(@NativeType("USHORT") short value) { RAWINPUTDEVICE.nusUsagePage(address(), value); return this; }
        /** Sets the specified value to the {@code usUsage} field. */
        public Buffer usUsage(@NativeType("USHORT") short value) { RAWINPUTDEVICE.nusUsage(address(), value); return this; }
        /** Sets the specified value to the {@code dwFlags} field. */
        public Buffer dwFlags(@NativeType("DWORD") int value) { RAWINPUTDEVICE.ndwFlags(address(), value); return this; }
        /** Sets the specified value to the {@code hwndTarget} field. */
        public Buffer hwndTarget(@NativeType("HWND") long value) { RAWINPUTDEVICE.nhwndTarget(address(), value); return this; }

    }

}