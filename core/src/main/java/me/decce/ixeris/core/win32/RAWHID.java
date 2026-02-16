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
 * struct RAWHID {
 *     DWORD dwSizeHid;
 *     DWORD dwCount;
 *     BYTE bRawData[1];
 * }</code></pre>
 */
public class RAWHID extends Struct<RAWHID> implements NativeResource {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        DWSIZEHID,
        DWCOUNT,
        BRAWDATA;

    static {
        Layout layout = __struct(
            __member(4),
            __member(4),
            __array(1, 1)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        DWSIZEHID = layout.offsetof(0);
        DWCOUNT = layout.offsetof(1);
        BRAWDATA = layout.offsetof(2);
    }

    protected RAWHID(long address, @Nullable ByteBuffer container) {
        super(address, container);
    }

    @Override
    protected RAWHID create(long address, @Nullable ByteBuffer container) {
        return new RAWHID(address, container);
    }

    /**
     * Creates a {@code RAWHID} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public RAWHID(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** @return the value of the {@code dwSizeHid} field. */
    @NativeType("DWORD")
    public int dwSizeHid() { return ndwSizeHid(address()); }
    /** @return the value of the {@code dwCount} field. */
    @NativeType("DWORD")
    public int dwCount() { return ndwCount(address()); }
    /** @return a {@link ByteBuffer} view of the {@code bRawData} field. */
    @NativeType("BYTE[1]")
    public ByteBuffer bRawData() { return nbRawData(address()); }
    /** @return the value at the specified index of the {@code bRawData} field. */
    @NativeType("BYTE")
    public byte bRawData(int index) { return nbRawData(address(), index); }

    /** Sets the specified value to the {@code dwSizeHid} field. */
    public RAWHID dwSizeHid(@NativeType("DWORD") int value) { ndwSizeHid(address(), value); return this; }
    /** Sets the specified value to the {@code dwCount} field. */
    public RAWHID dwCount(@NativeType("DWORD") int value) { ndwCount(address(), value); return this; }
    /** Copies the specified {@link ByteBuffer} to the {@code bRawData} field. */
    public RAWHID bRawData(@NativeType("BYTE[1]") ByteBuffer value) { nbRawData(address(), value); return this; }
    /** Sets the specified value at the specified index of the {@code bRawData} field. */
    public RAWHID bRawData(int index, @NativeType("BYTE") byte value) { nbRawData(address(), index, value); return this; }

    /** Initializes this struct with the specified values. */
    public RAWHID set(
        int dwSizeHid,
        int dwCount,
        ByteBuffer bRawData
    ) {
        dwSizeHid(dwSizeHid);
        dwCount(dwCount);
        bRawData(bRawData);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public RAWHID set(RAWHID src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    // -----------------------------------

    /** Returns a new {@code RAWHID} instance allocated with {@link MemoryUtil#memAlloc memAlloc}. The instance must be explicitly freed. */
    public static RAWHID malloc() {
        return new RAWHID(nmemAllocChecked(SIZEOF), null);
    }

    /** Returns a new {@code RAWHID} instance allocated with {@link MemoryUtil#memCalloc memCalloc}. The instance must be explicitly freed. */
    public static RAWHID calloc() {
        return new RAWHID(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code RAWHID} instance allocated with {@link BufferUtils}. */
    public static RAWHID create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new RAWHID(memAddress(container), container);
    }

    /** Returns a new {@code RAWHID} instance for the specified memory address. */
    public static RAWHID create(long address) {
        return new RAWHID(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static @Nullable RAWHID createSafe(long address) {
        return address == NULL ? null : new RAWHID(address, null);
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
     * Returns a new {@code RAWHID} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static RAWHID malloc(MemoryStack stack) {
        return new RAWHID(stack.nmalloc(ALIGNOF, SIZEOF), null);
    }

    /**
     * Returns a new {@code RAWHID} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static RAWHID calloc(MemoryStack stack) {
        return new RAWHID(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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

    /** Unsafe version of {@link #dwSizeHid}. */
    public static int ndwSizeHid(long struct) { return memGetInt(struct + RAWHID.DWSIZEHID); }
    /** Unsafe version of {@link #dwCount}. */
    public static int ndwCount(long struct) { return memGetInt(struct + RAWHID.DWCOUNT); }
    /** Unsafe version of {@link #bRawData}. */
    public static ByteBuffer nbRawData(long struct) { return memByteBuffer(struct + RAWHID.BRAWDATA, 1); }
    /** Unsafe version of {@link #bRawData(int) bRawData}. */
    public static byte nbRawData(long struct, int index) {
        return memGetByte(struct + RAWHID.BRAWDATA + check(index, 1) * 1);
    }

    /** Unsafe version of {@link #dwSizeHid(int) dwSizeHid}. */
    public static void ndwSizeHid(long struct, int value) { memPutInt(struct + RAWHID.DWSIZEHID, value); }
    /** Unsafe version of {@link #dwCount(int) dwCount}. */
    public static void ndwCount(long struct, int value) { memPutInt(struct + RAWHID.DWCOUNT, value); }
    /** Unsafe version of {@link #bRawData(ByteBuffer) bRawData}. */
    public static void nbRawData(long struct, ByteBuffer value) {
        if (CHECKS) { checkGT(value, 1); }
        memCopy(memAddress(value), struct + RAWHID.BRAWDATA, value.remaining() * 1);
    }
    /** Unsafe version of {@link #bRawData(int, byte) bRawData}. */
    public static void nbRawData(long struct, int index, byte value) {
        memPutByte(struct + RAWHID.BRAWDATA + check(index, 1) * 1, value);
    }

    // -----------------------------------

    /** An array of {@link RAWHID} structs. */
    public static class Buffer extends StructBuffer<RAWHID, Buffer> implements NativeResource {

        private static final RAWHID ELEMENT_FACTORY = RAWHID.create(-1L);

        /**
         * Creates a new {@code RAWHID.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link RAWHID#SIZEOF}, and its mark will be undefined.</p>
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
        protected RAWHID getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** @return the value of the {@code dwSizeHid} field. */
        @NativeType("DWORD")
        public int dwSizeHid() { return RAWHID.ndwSizeHid(address()); }
        /** @return the value of the {@code dwCount} field. */
        @NativeType("DWORD")
        public int dwCount() { return RAWHID.ndwCount(address()); }
        /** @return a {@link ByteBuffer} view of the {@code bRawData} field. */
        @NativeType("BYTE[1]")
        public ByteBuffer bRawData() { return RAWHID.nbRawData(address()); }
        /** @return the value at the specified index of the {@code bRawData} field. */
        @NativeType("BYTE")
        public byte bRawData(int index) { return RAWHID.nbRawData(address(), index); }

        /** Sets the specified value to the {@code dwSizeHid} field. */
        public Buffer dwSizeHid(@NativeType("DWORD") int value) { RAWHID.ndwSizeHid(address(), value); return this; }
        /** Sets the specified value to the {@code dwCount} field. */
        public Buffer dwCount(@NativeType("DWORD") int value) { RAWHID.ndwCount(address(), value); return this; }
        /** Copies the specified {@link ByteBuffer} to the {@code bRawData} field. */
        public Buffer bRawData(@NativeType("BYTE[1]") ByteBuffer value) { RAWHID.nbRawData(address(), value); return this; }
        /** Sets the specified value at the specified index of the {@code bRawData} field. */
        public Buffer bRawData(int index, @NativeType("BYTE") byte value) { RAWHID.nbRawData(address(), index, value); return this; }

    }

}