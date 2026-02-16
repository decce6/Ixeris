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
 * struct RAWINPUTHEADER {
 *     DWORD dwType;
 *     DWORD dwSize;
 *     HANDLE hDevice;
 *     WPARAM wParam;
 * }</code></pre>
 */
public class RAWINPUTHEADER extends Struct<RAWINPUTHEADER> implements NativeResource {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        DWTYPE,
        DWSIZE,
        HDEVICE,
        WPARAM;

    static {
        Layout layout = __struct(
            __member(4),
            __member(4),
            __member(POINTER_SIZE),
            __member(POINTER_SIZE)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        DWTYPE = layout.offsetof(0);
        DWSIZE = layout.offsetof(1);
        HDEVICE = layout.offsetof(2);
        WPARAM = layout.offsetof(3);
    }

    protected RAWINPUTHEADER(long address, @Nullable ByteBuffer container) {
        super(address, container);
    }

    @Override
    protected RAWINPUTHEADER create(long address, @Nullable ByteBuffer container) {
        return new RAWINPUTHEADER(address, container);
    }

    /**
     * Creates a {@code RAWINPUTHEADER} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public RAWINPUTHEADER(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** @return the value of the {@code dwType} field. */
    @NativeType("DWORD")
    public int dwType() { return ndwType(address()); }
    /** @return the value of the {@code dwSize} field. */
    @NativeType("DWORD")
    public int dwSize() { return ndwSize(address()); }
    /** @return the value of the {@code hDevice} field. */
    @NativeType("HANDLE")
    public long hDevice() { return nhDevice(address()); }
    /** @return the value of the {@code wParam} field. */
    @NativeType("WPARAM")
    public long wParam() { return nwParam(address()); }

    /** Sets the specified value to the {@code dwType} field. */
    public RAWINPUTHEADER dwType(@NativeType("DWORD") int value) { ndwType(address(), value); return this; }
    /** Sets the specified value to the {@code dwSize} field. */
    public RAWINPUTHEADER dwSize(@NativeType("DWORD") int value) { ndwSize(address(), value); return this; }
    /** Sets the specified value to the {@code hDevice} field. */
    public RAWINPUTHEADER hDevice(@NativeType("HANDLE") long value) { nhDevice(address(), value); return this; }
    /** Sets the specified value to the {@code wParam} field. */
    public RAWINPUTHEADER wParam(@NativeType("WPARAM") long value) { nwParam(address(), value); return this; }

    /** Initializes this struct with the specified values. */
    public RAWINPUTHEADER set(
        int dwType,
        int dwSize,
        long hDevice,
        long wParam
    ) {
        dwType(dwType);
        dwSize(dwSize);
        hDevice(hDevice);
        wParam(wParam);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public RAWINPUTHEADER set(RAWINPUTHEADER src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    // -----------------------------------

    /** Returns a new {@code RAWINPUTHEADER} instance allocated with {@link MemoryUtil#memAlloc memAlloc}. The instance must be explicitly freed. */
    public static RAWINPUTHEADER malloc() {
        return new RAWINPUTHEADER(nmemAllocChecked(SIZEOF), null);
    }

    /** Returns a new {@code RAWINPUTHEADER} instance allocated with {@link MemoryUtil#memCalloc memCalloc}. The instance must be explicitly freed. */
    public static RAWINPUTHEADER calloc() {
        return new RAWINPUTHEADER(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code RAWINPUTHEADER} instance allocated with {@link BufferUtils}. */
    public static RAWINPUTHEADER create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new RAWINPUTHEADER(memAddress(container), container);
    }

    /** Returns a new {@code RAWINPUTHEADER} instance for the specified memory address. */
    public static RAWINPUTHEADER create(long address) {
        return new RAWINPUTHEADER(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static @Nullable RAWINPUTHEADER createSafe(long address) {
        return address == NULL ? null : new RAWINPUTHEADER(address, null);
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
     * Returns a new {@code RAWINPUTHEADER} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static RAWINPUTHEADER malloc(MemoryStack stack) {
        return new RAWINPUTHEADER(stack.nmalloc(ALIGNOF, SIZEOF), null);
    }

    /**
     * Returns a new {@code RAWINPUTHEADER} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static RAWINPUTHEADER calloc(MemoryStack stack) {
        return new RAWINPUTHEADER(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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

    /** Unsafe version of {@link #dwType}. */
    public static int ndwType(long struct) { return memGetInt(struct + RAWINPUTHEADER.DWTYPE); }
    /** Unsafe version of {@link #dwSize}. */
    public static int ndwSize(long struct) { return memGetInt(struct + RAWINPUTHEADER.DWSIZE); }
    /** Unsafe version of {@link #hDevice}. */
    public static long nhDevice(long struct) { return memGetAddress(struct + RAWINPUTHEADER.HDEVICE); }
    /** Unsafe version of {@link #wParam}. */
    public static long nwParam(long struct) { return memGetAddress(struct + RAWINPUTHEADER.WPARAM); }

    /** Unsafe version of {@link #dwType(int) dwType}. */
    public static void ndwType(long struct, int value) { memPutInt(struct + RAWINPUTHEADER.DWTYPE, value); }
    /** Unsafe version of {@link #dwSize(int) dwSize}. */
    public static void ndwSize(long struct, int value) { memPutInt(struct + RAWINPUTHEADER.DWSIZE, value); }
    /** Unsafe version of {@link #hDevice(long) hDevice}. */
    public static void nhDevice(long struct, long value) { memPutAddress(struct + RAWINPUTHEADER.HDEVICE, check(value)); }
    /** Unsafe version of {@link #wParam(long) wParam}. */
    public static void nwParam(long struct, long value) { memPutAddress(struct + RAWINPUTHEADER.WPARAM, value); }

    /**
     * Validates pointer members that should not be {@code NULL}.
     *
     * @param struct the struct to validate
     */
    public static void validate(long struct) {
        check(memGetAddress(struct + RAWINPUTHEADER.HDEVICE));
    }

    // -----------------------------------

    /** An array of {@link RAWINPUTHEADER} structs. */
    public static class Buffer extends StructBuffer<RAWINPUTHEADER, Buffer> implements NativeResource {

        private static final RAWINPUTHEADER ELEMENT_FACTORY = RAWINPUTHEADER.create(-1L);

        /**
         * Creates a new {@code RAWINPUTHEADER.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link RAWINPUTHEADER#SIZEOF}, and its mark will be undefined.</p>
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
        protected RAWINPUTHEADER getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** @return the value of the {@code dwType} field. */
        @NativeType("DWORD")
        public int dwType() { return RAWINPUTHEADER.ndwType(address()); }
        /** @return the value of the {@code dwSize} field. */
        @NativeType("DWORD")
        public int dwSize() { return RAWINPUTHEADER.ndwSize(address()); }
        /** @return the value of the {@code hDevice} field. */
        @NativeType("HANDLE")
        public long hDevice() { return RAWINPUTHEADER.nhDevice(address()); }
        /** @return the value of the {@code wParam} field. */
        @NativeType("WPARAM")
        public long wParam() { return RAWINPUTHEADER.nwParam(address()); }

        /** Sets the specified value to the {@code dwType} field. */
        public Buffer dwType(@NativeType("DWORD") int value) { RAWINPUTHEADER.ndwType(address(), value); return this; }
        /** Sets the specified value to the {@code dwSize} field. */
        public Buffer dwSize(@NativeType("DWORD") int value) { RAWINPUTHEADER.ndwSize(address(), value); return this; }
        /** Sets the specified value to the {@code hDevice} field. */
        public Buffer hDevice(@NativeType("HANDLE") long value) { RAWINPUTHEADER.nhDevice(address(), value); return this; }
        /** Sets the specified value to the {@code wParam} field. */
        public Buffer wParam(@NativeType("WPARAM") long value) { RAWINPUTHEADER.nwParam(address(), value); return this; }

    }

}