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
 * struct RAWKEYBOARD {
 *     USHORT MakeCode;
 *     USHORT Flags;
 *     USHORT Reserved;
 *     USHORT VKey;
 *     UINT Message;
 *     ULONG ExtraInformation;
 * }</code></pre>
 */
public class RAWKEYBOARD extends Struct<RAWKEYBOARD> implements NativeResource {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        MAKECODE,
        FLAGS,
        RESERVED,
        VKEY,
        MESSAGE,
        EXTRAINFORMATION;

    static {
        Layout layout = __struct(
            __member(2),
            __member(2),
            __member(2),
            __member(2),
            __member(4),
            __member(4)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        MAKECODE = layout.offsetof(0);
        FLAGS = layout.offsetof(1);
        RESERVED = layout.offsetof(2);
        VKEY = layout.offsetof(3);
        MESSAGE = layout.offsetof(4);
        EXTRAINFORMATION = layout.offsetof(5);
    }

    protected RAWKEYBOARD(long address, @Nullable ByteBuffer container) {
        super(address, container);
    }

    @Override
    protected RAWKEYBOARD create(long address, @Nullable ByteBuffer container) {
        return new RAWKEYBOARD(address, container);
    }

    /**
     * Creates a {@code RAWKEYBOARD} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public RAWKEYBOARD(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** @return the value of the {@code MakeCode} field. */
    @NativeType("USHORT")
    public short MakeCode() { return nMakeCode(address()); }
    /** @return the value of the {@code Flags} field. */
    @NativeType("USHORT")
    public short Flags() { return nFlags(address()); }
    /** @return the value of the {@code Reserved} field. */
    @NativeType("USHORT")
    public short Reserved() { return nReserved(address()); }
    /** @return the value of the {@code VKey} field. */
    @NativeType("USHORT")
    public short VKey() { return nVKey(address()); }
    /** @return the value of the {@code Message} field. */
    @NativeType("UINT")
    public int Message() { return nMessage(address()); }
    /** @return the value of the {@code ExtraInformation} field. */
    @NativeType("ULONG")
    public int ExtraInformation() { return nExtraInformation(address()); }

    /** Sets the specified value to the {@code MakeCode} field. */
    public RAWKEYBOARD MakeCode(@NativeType("USHORT") short value) { nMakeCode(address(), value); return this; }
    /** Sets the specified value to the {@code Flags} field. */
    public RAWKEYBOARD Flags(@NativeType("USHORT") short value) { nFlags(address(), value); return this; }
    /** Sets the specified value to the {@code Reserved} field. */
    public RAWKEYBOARD Reserved(@NativeType("USHORT") short value) { nReserved(address(), value); return this; }
    /** Sets the specified value to the {@code VKey} field. */
    public RAWKEYBOARD VKey(@NativeType("USHORT") short value) { nVKey(address(), value); return this; }
    /** Sets the specified value to the {@code Message} field. */
    public RAWKEYBOARD Message(@NativeType("UINT") int value) { nMessage(address(), value); return this; }
    /** Sets the specified value to the {@code ExtraInformation} field. */
    public RAWKEYBOARD ExtraInformation(@NativeType("ULONG") int value) { nExtraInformation(address(), value); return this; }

    /** Initializes this struct with the specified values. */
    public RAWKEYBOARD set(
        short MakeCode,
        short Flags,
        short Reserved,
        short VKey,
        int Message,
        int ExtraInformation
    ) {
        MakeCode(MakeCode);
        Flags(Flags);
        Reserved(Reserved);
        VKey(VKey);
        Message(Message);
        ExtraInformation(ExtraInformation);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public RAWKEYBOARD set(RAWKEYBOARD src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    // -----------------------------------

    /** Returns a new {@code RAWKEYBOARD} instance allocated with {@link MemoryUtil#memAlloc memAlloc}. The instance must be explicitly freed. */
    public static RAWKEYBOARD malloc() {
        return new RAWKEYBOARD(nmemAllocChecked(SIZEOF), null);
    }

    /** Returns a new {@code RAWKEYBOARD} instance allocated with {@link MemoryUtil#memCalloc memCalloc}. The instance must be explicitly freed. */
    public static RAWKEYBOARD calloc() {
        return new RAWKEYBOARD(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code RAWKEYBOARD} instance allocated with {@link BufferUtils}. */
    public static RAWKEYBOARD create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new RAWKEYBOARD(memAddress(container), container);
    }

    /** Returns a new {@code RAWKEYBOARD} instance for the specified memory address. */
    public static RAWKEYBOARD create(long address) {
        return new RAWKEYBOARD(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static @Nullable RAWKEYBOARD createSafe(long address) {
        return address == NULL ? null : new RAWKEYBOARD(address, null);
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
     * Returns a new {@code RAWKEYBOARD} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static RAWKEYBOARD malloc(MemoryStack stack) {
        return new RAWKEYBOARD(stack.nmalloc(ALIGNOF, SIZEOF), null);
    }

    /**
     * Returns a new {@code RAWKEYBOARD} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static RAWKEYBOARD calloc(MemoryStack stack) {
        return new RAWKEYBOARD(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
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

    /** Unsafe version of {@link #MakeCode}. */
    public static short nMakeCode(long struct) { return memGetShort(struct + RAWKEYBOARD.MAKECODE); }
    /** Unsafe version of {@link #Flags}. */
    public static short nFlags(long struct) { return memGetShort(struct + RAWKEYBOARD.FLAGS); }
    /** Unsafe version of {@link #Reserved}. */
    public static short nReserved(long struct) { return memGetShort(struct + RAWKEYBOARD.RESERVED); }
    /** Unsafe version of {@link #VKey}. */
    public static short nVKey(long struct) { return memGetShort(struct + RAWKEYBOARD.VKEY); }
    /** Unsafe version of {@link #Message}. */
    public static int nMessage(long struct) { return memGetInt(struct + RAWKEYBOARD.MESSAGE); }
    /** Unsafe version of {@link #ExtraInformation}. */
    public static int nExtraInformation(long struct) { return memGetInt(struct + RAWKEYBOARD.EXTRAINFORMATION); }

    /** Unsafe version of {@link #MakeCode(short) MakeCode}. */
    public static void nMakeCode(long struct, short value) { memPutShort(struct + RAWKEYBOARD.MAKECODE, value); }
    /** Unsafe version of {@link #Flags(short) Flags}. */
    public static void nFlags(long struct, short value) { memPutShort(struct + RAWKEYBOARD.FLAGS, value); }
    /** Unsafe version of {@link #Reserved(short) Reserved}. */
    public static void nReserved(long struct, short value) { memPutShort(struct + RAWKEYBOARD.RESERVED, value); }
    /** Unsafe version of {@link #VKey(short) VKey}. */
    public static void nVKey(long struct, short value) { memPutShort(struct + RAWKEYBOARD.VKEY, value); }
    /** Unsafe version of {@link #Message(int) Message}. */
    public static void nMessage(long struct, int value) { memPutInt(struct + RAWKEYBOARD.MESSAGE, value); }
    /** Unsafe version of {@link #ExtraInformation(int) ExtraInformation}. */
    public static void nExtraInformation(long struct, int value) { memPutInt(struct + RAWKEYBOARD.EXTRAINFORMATION, value); }

    // -----------------------------------

    /** An array of {@link RAWKEYBOARD} structs. */
    public static class Buffer extends StructBuffer<RAWKEYBOARD, Buffer> implements NativeResource {

        private static final RAWKEYBOARD ELEMENT_FACTORY = RAWKEYBOARD.create(-1L);

        /**
         * Creates a new {@code RAWKEYBOARD.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link RAWKEYBOARD#SIZEOF}, and its mark will be undefined.</p>
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
        protected RAWKEYBOARD getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** @return the value of the {@code MakeCode} field. */
        @NativeType("USHORT")
        public short MakeCode() { return RAWKEYBOARD.nMakeCode(address()); }
        /** @return the value of the {@code Flags} field. */
        @NativeType("USHORT")
        public short Flags() { return RAWKEYBOARD.nFlags(address()); }
        /** @return the value of the {@code Reserved} field. */
        @NativeType("USHORT")
        public short Reserved() { return RAWKEYBOARD.nReserved(address()); }
        /** @return the value of the {@code VKey} field. */
        @NativeType("USHORT")
        public short VKey() { return RAWKEYBOARD.nVKey(address()); }
        /** @return the value of the {@code Message} field. */
        @NativeType("UINT")
        public int Message() { return RAWKEYBOARD.nMessage(address()); }
        /** @return the value of the {@code ExtraInformation} field. */
        @NativeType("ULONG")
        public int ExtraInformation() { return RAWKEYBOARD.nExtraInformation(address()); }

        /** Sets the specified value to the {@code MakeCode} field. */
        public Buffer MakeCode(@NativeType("USHORT") short value) { RAWKEYBOARD.nMakeCode(address(), value); return this; }
        /** Sets the specified value to the {@code Flags} field. */
        public Buffer Flags(@NativeType("USHORT") short value) { RAWKEYBOARD.nFlags(address(), value); return this; }
        /** Sets the specified value to the {@code Reserved} field. */
        public Buffer Reserved(@NativeType("USHORT") short value) { RAWKEYBOARD.nReserved(address(), value); return this; }
        /** Sets the specified value to the {@code VKey} field. */
        public Buffer VKey(@NativeType("USHORT") short value) { RAWKEYBOARD.nVKey(address(), value); return this; }
        /** Sets the specified value to the {@code Message} field. */
        public Buffer Message(@NativeType("UINT") int value) { RAWKEYBOARD.nMessage(address(), value); return this; }
        /** Sets the specified value to the {@code ExtraInformation} field. */
        public Buffer ExtraInformation(@NativeType("ULONG") int value) { RAWKEYBOARD.nExtraInformation(address(), value); return this; }

    }

}