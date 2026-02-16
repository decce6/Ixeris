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
 * struct RAWMOUSE {
 *     USHORT usFlags;
 *     union {
 *         ULONG ulButtons;
 *         struct {
 *             USHORT usButtonFlags;
 *             USHORT usButtonData;
 *         };
 *     };
 *     ULONG ulRawButtons;
 *     LONG lLastX;
 *     LONG lLastY;
 *     ULONG ulExtraInformation;
 * }</code></pre>
 */
public class RAWMOUSE extends Struct<RAWMOUSE> implements NativeResource {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        USFLAGS,
        ULBUTTONS,
        USBUTTONFLAGS,
        USBUTTONDATA,
        ULRAWBUTTONS,
        LLASTX,
        LLASTY,
        ULEXTRAINFORMATION;

    static {
        Layout layout = __struct(
            __member(2),
            __union(
                __member(4),
                __struct(
                    __member(2),
                    __member(2)
                )
            ),
            __member(4),
            __member(4),
            __member(4),
            __member(4)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        USFLAGS = layout.offsetof(0);
        ULBUTTONS = layout.offsetof(2);
        USBUTTONFLAGS = layout.offsetof(4);
        USBUTTONDATA = layout.offsetof(5);
        ULRAWBUTTONS = layout.offsetof(6);
        LLASTX = layout.offsetof(7);
        LLASTY = layout.offsetof(8);
        ULEXTRAINFORMATION = layout.offsetof(9);
    }

    protected RAWMOUSE(long address, @Nullable ByteBuffer container) {
        super(address, container);
    }

    @Override
    protected RAWMOUSE create(long address, @Nullable ByteBuffer container) {
        return new RAWMOUSE(address, container);
    }

    /**
     * Creates a {@code RAWMOUSE} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public RAWMOUSE(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** @return the value of the {@code usFlags} field. */
    @NativeType("USHORT")
    public short usFlags() { return nusFlags(address()); }
    /** @return the value of the {@code ulButtons} field. */
    @NativeType("ULONG")
    public int ulButtons() { return nulButtons(address()); }
    /** @return the value of the {@code usButtonFlags} field. */
    @NativeType("USHORT")
    public short usButtonFlags() { return nusButtonFlags(address()); }
    /** @return the value of the {@code usButtonData} field. */
    @NativeType("USHORT")
    public short usButtonData() { return nusButtonData(address()); }
    /** @return the value of the {@code ulRawButtons} field. */
    @NativeType("ULONG")
    public int ulRawButtons() { return nulRawButtons(address()); }
    /** @return the value of the {@code lLastX} field. */
    @NativeType("LONG")
    public int lLastX() { return nlLastX(address()); }
    /** @return the value of the {@code lLastY} field. */
    @NativeType("LONG")
    public int lLastY() { return nlLastY(address()); }
    /** @return the value of the {@code ulExtraInformation} field. */
    @NativeType("ULONG")
    public int ulExtraInformation() { return nulExtraInformation(address()); }

    /** Sets the specified value to the {@code usFlags} field. */
    public RAWMOUSE usFlags(@NativeType("USHORT") short value) { nusFlags(address(), value); return this; }
    /** Sets the specified value to the {@code ulButtons} field. */
    public RAWMOUSE ulButtons(@NativeType("ULONG") int value) { nulButtons(address(), value); return this; }
    /** Sets the specified value to the {@code usButtonFlags} field. */
    public RAWMOUSE usButtonFlags(@NativeType("USHORT") short value) { nusButtonFlags(address(), value); return this; }
    /** Sets the specified value to the {@code usButtonData} field. */
    public RAWMOUSE usButtonData(@NativeType("USHORT") short value) { nusButtonData(address(), value); return this; }
    /** Sets the specified value to the {@code ulRawButtons} field. */
    public RAWMOUSE ulRawButtons(@NativeType("ULONG") int value) { nulRawButtons(address(), value); return this; }
    /** Sets the specified value to the {@code lLastX} field. */
    public RAWMOUSE lLastX(@NativeType("LONG") int value) { nlLastX(address(), value); return this; }
    /** Sets the specified value to the {@code lLastY} field. */
    public RAWMOUSE lLastY(@NativeType("LONG") int value) { nlLastY(address(), value); return this; }
    /** Sets the specified value to the {@code ulExtraInformation} field. */
    public RAWMOUSE ulExtraInformation(@NativeType("ULONG") int value) { nulExtraInformation(address(), value); return this; }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public RAWMOUSE set(RAWMOUSE src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    // -----------------------------------

    /** Returns a new {@code RAWMOUSE} instance allocated with {@link MemoryUtil#memAlloc memAlloc}. The instance must be explicitly freed. */
    public static RAWMOUSE malloc() {
        return new RAWMOUSE(nmemAllocChecked(SIZEOF), null);
    }

    /** Returns a new {@code RAWMOUSE} instance allocated with {@link MemoryUtil#memCalloc memCalloc}. The instance must be explicitly freed. */
    public static RAWMOUSE calloc() {
        return new RAWMOUSE(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code RAWMOUSE} instance allocated with {@link BufferUtils}. */
    public static RAWMOUSE create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new RAWMOUSE(memAddress(container), container);
    }

    /** Returns a new {@code RAWMOUSE} instance for the specified memory address. */
    public static RAWMOUSE create(long address) {
        return new RAWMOUSE(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static @Nullable RAWMOUSE createSafe(long address) {
        return address == NULL ? null : new RAWMOUSE(address, null);
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
     * Returns a new {@code RAWMOUSE} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static RAWMOUSE malloc(MemoryStack stack) {
        return new RAWMOUSE(stack.nmalloc(ALIGNOF, SIZEOF), null);
    }

    /**
     * Returns a new {@code RAWMOUSE} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static RAWMOUSE calloc(MemoryStack stack) {
        return new RAWMOUSE(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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

    /** Unsafe version of {@link #usFlags}. */
    public static short nusFlags(long struct) { return memGetShort(struct + RAWMOUSE.USFLAGS); }
    /** Unsafe version of {@link #ulButtons}. */
    public static int nulButtons(long struct) { return memGetInt(struct + RAWMOUSE.ULBUTTONS); }
    /** Unsafe version of {@link #usButtonFlags}. */
    public static short nusButtonFlags(long struct) { return memGetShort(struct + RAWMOUSE.USBUTTONFLAGS); }
    /** Unsafe version of {@link #usButtonData}. */
    public static short nusButtonData(long struct) { return memGetShort(struct + RAWMOUSE.USBUTTONDATA); }
    /** Unsafe version of {@link #ulRawButtons}. */
    public static int nulRawButtons(long struct) { return memGetInt(struct + RAWMOUSE.ULRAWBUTTONS); }
    /** Unsafe version of {@link #lLastX}. */
    public static int nlLastX(long struct) { return memGetInt(struct + RAWMOUSE.LLASTX); }
    /** Unsafe version of {@link #lLastY}. */
    public static int nlLastY(long struct) { return memGetInt(struct + RAWMOUSE.LLASTY); }
    /** Unsafe version of {@link #ulExtraInformation}. */
    public static int nulExtraInformation(long struct) { return memGetInt(struct + RAWMOUSE.ULEXTRAINFORMATION); }

    /** Unsafe version of {@link #usFlags(short) usFlags}. */
    public static void nusFlags(long struct, short value) { memPutShort(struct + RAWMOUSE.USFLAGS, value); }
    /** Unsafe version of {@link #ulButtons(int) ulButtons}. */
    public static void nulButtons(long struct, int value) { memPutInt(struct + RAWMOUSE.ULBUTTONS, value); }
    /** Unsafe version of {@link #usButtonFlags(short) usButtonFlags}. */
    public static void nusButtonFlags(long struct, short value) { memPutShort(struct + RAWMOUSE.USBUTTONFLAGS, value); }
    /** Unsafe version of {@link #usButtonData(short) usButtonData}. */
    public static void nusButtonData(long struct, short value) { memPutShort(struct + RAWMOUSE.USBUTTONDATA, value); }
    /** Unsafe version of {@link #ulRawButtons(int) ulRawButtons}. */
    public static void nulRawButtons(long struct, int value) { memPutInt(struct + RAWMOUSE.ULRAWBUTTONS, value); }
    /** Unsafe version of {@link #lLastX(int) lLastX}. */
    public static void nlLastX(long struct, int value) { memPutInt(struct + RAWMOUSE.LLASTX, value); }
    /** Unsafe version of {@link #lLastY(int) lLastY}. */
    public static void nlLastY(long struct, int value) { memPutInt(struct + RAWMOUSE.LLASTY, value); }
    /** Unsafe version of {@link #ulExtraInformation(int) ulExtraInformation}. */
    public static void nulExtraInformation(long struct, int value) { memPutInt(struct + RAWMOUSE.ULEXTRAINFORMATION, value); }

    // -----------------------------------

    /** An array of {@link RAWMOUSE} structs. */
    public static class Buffer extends StructBuffer<RAWMOUSE, Buffer> implements NativeResource {

        private static final RAWMOUSE ELEMENT_FACTORY = RAWMOUSE.create(-1L);

        /**
         * Creates a new {@code RAWMOUSE.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link RAWMOUSE#SIZEOF}, and its mark will be undefined.</p>
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
        protected RAWMOUSE getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** @return the value of the {@code usFlags} field. */
        @NativeType("USHORT")
        public short usFlags() { return RAWMOUSE.nusFlags(address()); }
        /** @return the value of the {@code ulButtons} field. */
        @NativeType("ULONG")
        public int ulButtons() { return RAWMOUSE.nulButtons(address()); }
        /** @return the value of the {@code usButtonFlags} field. */
        @NativeType("USHORT")
        public short usButtonFlags() { return RAWMOUSE.nusButtonFlags(address()); }
        /** @return the value of the {@code usButtonData} field. */
        @NativeType("USHORT")
        public short usButtonData() { return RAWMOUSE.nusButtonData(address()); }
        /** @return the value of the {@code ulRawButtons} field. */
        @NativeType("ULONG")
        public int ulRawButtons() { return RAWMOUSE.nulRawButtons(address()); }
        /** @return the value of the {@code lLastX} field. */
        @NativeType("LONG")
        public int lLastX() { return RAWMOUSE.nlLastX(address()); }
        /** @return the value of the {@code lLastY} field. */
        @NativeType("LONG")
        public int lLastY() { return RAWMOUSE.nlLastY(address()); }
        /** @return the value of the {@code ulExtraInformation} field. */
        @NativeType("ULONG")
        public int ulExtraInformation() { return RAWMOUSE.nulExtraInformation(address()); }

        /** Sets the specified value to the {@code usFlags} field. */
        public Buffer usFlags(@NativeType("USHORT") short value) { RAWMOUSE.nusFlags(address(), value); return this; }
        /** Sets the specified value to the {@code ulButtons} field. */
        public Buffer ulButtons(@NativeType("ULONG") int value) { RAWMOUSE.nulButtons(address(), value); return this; }
        /** Sets the specified value to the {@code usButtonFlags} field. */
        public Buffer usButtonFlags(@NativeType("USHORT") short value) { RAWMOUSE.nusButtonFlags(address(), value); return this; }
        /** Sets the specified value to the {@code usButtonData} field. */
        public Buffer usButtonData(@NativeType("USHORT") short value) { RAWMOUSE.nusButtonData(address(), value); return this; }
        /** Sets the specified value to the {@code ulRawButtons} field. */
        public Buffer ulRawButtons(@NativeType("ULONG") int value) { RAWMOUSE.nulRawButtons(address(), value); return this; }
        /** Sets the specified value to the {@code lLastX} field. */
        public Buffer lLastX(@NativeType("LONG") int value) { RAWMOUSE.nlLastX(address(), value); return this; }
        /** Sets the specified value to the {@code lLastY} field. */
        public Buffer lLastY(@NativeType("LONG") int value) { RAWMOUSE.nlLastY(address(), value); return this; }
        /** Sets the specified value to the {@code ulExtraInformation} field. */
        public Buffer ulExtraInformation(@NativeType("ULONG") int value) { RAWMOUSE.nulExtraInformation(address(), value); return this; }

    }

}