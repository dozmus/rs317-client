package com.runescape.client.io;

import com.runescape.client.ISAACCipher;
import com.runescape.client.NodeList;
import com.runescape.client.NodeSub;
import java.math.BigInteger;
import com.runescape.client.signlink.Signlink;

/**
 * The Data Stream class. Each function is 'pre' and post-fixed according
 * to their function.
 * 
 * @author Pure_
 */
public final class Stream extends NodeSub {

    private static final int[] BITMASKS = {
        0, 1, 3, 7, 15, 31, 63, 127, 255, 511,
        1023, 2047, 4095, 8191, 16383, 32767, 65535, 0x1ffff, 0x3ffff, 0x7ffff,
        0xfffff, 0x1fffff, 0x3fffff, 0x7fffff, 0xffffff, 0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff, 0x1fffffff,
        0x3fffffff, 0x7fffffff, -1
    };
    private static int availableNodes;
    private static final NodeList nodeList = new NodeList();

    public static Stream fetch() {
        synchronized (nodeList) {
            Stream stream = null;

            if (availableNodes > 0) {
                availableNodes--;
                stream = (Stream) nodeList.popHead();
            }
            
            if (stream != null) {
                stream.currentOffset = 0;
                return stream;
            }
        }
        Stream stream2 = new Stream();
        stream2.currentOffset = 0;
        stream2.buffer = new byte[5000];
        return stream2;
    }
    
    public byte[] buffer;
    public int currentOffset;
    public int bitPosition;
    public ISAACCipher encryption;

    private Stream() {
    }

    public Stream(byte buffer[]) {
        this.buffer = buffer;
        currentOffset = 0;
    }

    public void initBitAccess() {
        bitPosition = currentOffset * 8;
    }

    public void finishBitAccess() {
        currentOffset = (bitPosition + 7) / 8;
    }
    
    public void generateKeys() {
        int size = currentOffset;
        currentOffset = 0;
        byte buf[] = new byte[size];
        readBytes(size, 0, buf);
        BigInteger biginteger2 = new BigInteger(buf);
        BigInteger biginteger3 = biginteger2;
        byte abyte1[] = biginteger3.toByteArray();
        currentOffset = 0;
        writeByte(abyte1.length);
        writeBytes(abyte1, abyte1.length, 0);
    }

    public void writePacketHeaderEnc(int opcode) {
        buffer[currentOffset++] = (byte) (opcode + encryption.getNextKey());
    }

    public void writeByte(int value) {
        buffer[currentOffset++] = (byte) value;
    }

    public void writeByteC(int value) {
        buffer[currentOffset++] = (byte) (-value);
    }

    public void writeByteS(int value) {
        buffer[currentOffset++] = (byte) (128 - value);
    }

    public void writeShort(int value) {
        buffer[currentOffset++] = (byte) (value >> 8);
        buffer[currentOffset++] = (byte) value;
    }

    public void writeShortLE(int value) {
        buffer[currentOffset++] = (byte) value;
        buffer[currentOffset++] = (byte) (value >> 8);
    }

    public void writeShortA(int value) {
        buffer[currentOffset++] = (byte) (value >> 8);
        buffer[currentOffset++] = (byte) (value + 128);
    }

    public void writeShortLEA(int value) {
        buffer[currentOffset++] = (byte) (value + 128);
        buffer[currentOffset++] = (byte) (value >> 8);
    }

    public void writeTriByte(int value) {
        buffer[currentOffset++] = (byte) (value >> 16);
        buffer[currentOffset++] = (byte) (value >> 8);
        buffer[currentOffset++] = (byte) value;
    }

    public void writeInt(int value) {
        buffer[currentOffset++] = (byte) (value >> 24);
        buffer[currentOffset++] = (byte) (value >> 16);
        buffer[currentOffset++] = (byte) (value >> 8);
        buffer[currentOffset++] = (byte) value;
    }

    public void writeIntLE(int value) {
        buffer[currentOffset++] = (byte) value;
        buffer[currentOffset++] = (byte) (value >> 8);
        buffer[currentOffset++] = (byte) (value >> 16);
        buffer[currentOffset++] = (byte) (value >> 24);
    }

    public void writeLong(long value) {
        try {
            buffer[currentOffset++] = (byte) (int) (value >> 56);
            buffer[currentOffset++] = (byte) (int) (value >> 48);
            buffer[currentOffset++] = (byte) (int) (value >> 40);
            buffer[currentOffset++] = (byte) (int) (value >> 32);
            buffer[currentOffset++] = (byte) (int) (value >> 24);
            buffer[currentOffset++] = (byte) (int) (value >> 16);
            buffer[currentOffset++] = (byte) (int) (value >> 8);
            buffer[currentOffset++] = (byte) (int) value;
        } catch (RuntimeException e) {
            Signlink.printError("WriteLong exception, val: " + value + ", error: " + e.toString());
            throw new RuntimeException();
        }
    }

    public void writeByteXXX(int i) {
        buffer[currentOffset - i - 1] = (byte) i;
    }

    public int readUByte() {
        return buffer[currentOffset++] & 0xff;
    }

    public byte readByte() {
        return buffer[currentOffset++];
    }

    public int readUByteA() {
        return buffer[currentOffset++] - 128 & 0xff;
    }

    public int readUByteC() {
        return -buffer[currentOffset++] & 0xff;
    }

    public int readUByteS() {
        return 128 - buffer[currentOffset++] & 0xff;
    }

    public byte readByteC() {
        return (byte) (-buffer[currentOffset++]);
    }

    public byte readByteS() {
        return (byte) (128 - buffer[currentOffset++]);
    }

    public int readUShort() {
        currentOffset += 2;
        return ((buffer[currentOffset - 2] & 0xff) << 8)
                + (buffer[currentOffset - 1] & 0xff);
    }

    public int readShort() {
        currentOffset += 2;
        int i = ((buffer[currentOffset - 2] & 0xff) << 8)
                + (buffer[currentOffset - 1] & 0xff);
        
        if (i > 32767) {
            i -= 0x10000;
        }
        return i;
    }

    public int readUShortLE() {
        currentOffset += 2;
        return ((buffer[currentOffset - 1] & 0xff) << 8)
                + (buffer[currentOffset - 2] & 0xff);
    }

    public int readUShortA() {
        currentOffset += 2;
        return ((buffer[currentOffset - 2] & 0xff) << 8)
                + (buffer[currentOffset - 1] - 128 & 0xff);
    }

    public int readUShortLEA() {
        currentOffset += 2;
        return ((buffer[currentOffset - 1] & 0xff) << 8)
                + (buffer[currentOffset - 2] - 128 & 0xff);
    }

    public int readShortLE() {
        currentOffset += 2;
        int j = ((buffer[currentOffset - 1] & 0xff) << 8)
                + (buffer[currentOffset - 2] & 0xff);
        
        if (j > 32767) {
            j -= 0x10000;
        }
        return j;
    }

    public int readShortLEA() {
        currentOffset += 2;
        int j = ((buffer[currentOffset - 1] & 0xff) << 8)
                + (buffer[currentOffset - 2] - 128 & 0xff);
        
        if (j > 32767) {
            j -= 0x10000;
        }
        return j;
    }

    public int readUTriByte() {
        currentOffset += 3;
        return ((buffer[currentOffset - 3] & 0xff) << 16)
                + ((buffer[currentOffset - 2] & 0xff) << 8)
                + (buffer[currentOffset - 1] & 0xff);
    }

    public int readUInt() {
        currentOffset += 4;
        return ((buffer[currentOffset - 4] & 0xff) << 24)
                + ((buffer[currentOffset - 3] & 0xff) << 16)
                + ((buffer[currentOffset - 2] & 0xff) << 8)
                + (buffer[currentOffset - 1] & 0xff);
    }

    /**
     * Read a Middle-Endian Small Int.
     * Order: B2 A1 D4 C3 (where A1 is the smallest byte and D4 is the biggest byte)
     * @return 
     */
    public int readIntMES() {
        currentOffset += 4;
        return ((buffer[currentOffset - 2] & 0xff) << 24)
                + ((buffer[currentOffset - 1] & 0xff) << 16)
                + ((buffer[currentOffset - 4] & 0xff) << 8)
                + (buffer[currentOffset - 3] & 0xff);
    }

    /**
     * Read a Middle-Endian Big Int.
     * Order: C3 D4 A1 B2 (where A1 is the smallest byte and D4 is the biggest byte)
     * 
     * @return 
     */
    public int readIntMEB() {
        currentOffset += 4;
        return ((buffer[currentOffset - 3] & 0xff) << 24)
                + ((buffer[currentOffset - 4] & 0xff) << 16)
                + ((buffer[currentOffset - 1] & 0xff) << 8)
                + (buffer[currentOffset - 2] & 0xff);
    }

    public long readULong() {
        long val1 = (long) readUInt() & 0xffffffffL;
        long val2 = (long) readUInt() & 0xffffffffL;
        return (val1 << 32) + val2;
    }

    /**
     * Also known as 'SpaceSaverB'.
     * 
     * @return value
     */
    public int readSmart() {
        int i = buffer[currentOffset] & 0xff;
        
        if (i < 128) {
            return readUByte() - 64;
        } else {
            return readUShort() - 49152;
        }
    }

    /**
     * Also known as 'SpaceSaverA'.
     * 
     * @return value
     */
    public int readSmarts() {
        int i = buffer[currentOffset] & 0xff;
        
        if (i < 128) {
            return readUByte();
        } else {
            return readUShort() - 32768;
        }
    }

    public void method441(int i, byte buf[], int j) {
        for (int k = (i + j) - 1; k >= i; k--) {
            buffer[currentOffset++] = (byte) (buf[k] + 128);
        }
    }

    public void method442(int i, int j, byte buf[]) {
        for (int k = (j + i) - 1; k >= j; k--) {
            buf[k] = buffer[currentOffset++];
        }
    }

    public void writeString(String str) {
        System.arraycopy(str.getBytes(), 0, buffer, currentOffset, str.length());
        currentOffset += str.length();
        buffer[currentOffset++] = 10;
    }

    public String readString() {
        int i = currentOffset;
        while (buffer[currentOffset++] != 10) ;
        return new String(buffer, i, currentOffset - i - 1);
    }

    public byte[] readBytes() {
        int i = currentOffset;
        while (buffer[currentOffset++] != 10) ;
        byte abyte0[] = new byte[currentOffset - i - 1];
        System.arraycopy(buffer, i, abyte0, i - i, currentOffset - 1 - i);
        return abyte0;
    }

    public void readBytes(int length, int startIndex, byte buf[]) {
        for (int i = startIndex; i < startIndex + length; i++) {
            buf[i] = buffer[currentOffset++];
        }
    }

    public int readBits(int i) {
        int k = bitPosition >> 3;
        int l = 8 - (bitPosition & 7);
        int i1 = 0;
        bitPosition += i;
        
        for (; i > l; l = 8) {
            i1 += (buffer[k++] & BITMASKS[l]) << i - l;
            i -= l;
        }
        if (i == l) {
            i1 += buffer[k] & BITMASKS[l];
        } else {
            i1 += buffer[k] >> l - i & BITMASKS[i];
        }
        return i1;
    }

    public void writeBytes(byte buf[], int length, int startIndex) {
        for (int i = startIndex; i < startIndex + length; i++) {
            buffer[currentOffset++] = buf[i];
        }
    }
}
