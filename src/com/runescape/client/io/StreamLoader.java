package com.runescape.client.io;

import com.runescape.client.bzip2.BZip2Decompressor;

public final class StreamLoader {

    private final byte[] strm_in;
    private final int dataSize;
    private final int[] anIntArray728;
    private final int[] anIntArray729;
    private final int[] anIntArray730;
    private final int[] anIntArray731;
    private final boolean aBoolean732;

    public StreamLoader(byte buf[]) {
        Stream stream = new Stream(buf);
        int i = stream.readUTriByte();
        int j = stream.readUTriByte();
        
        if (j != i) {
            byte abyte1[] = new byte[i];
            BZip2Decompressor.getStreamAvailableOut(abyte1, i, buf, j, 6);
            strm_in = abyte1;
            stream = new Stream(strm_in);
            aBoolean732 = true;
        } else {
            strm_in = buf;
            aBoolean732 = false;
        }
        dataSize = stream.readUShort();
        anIntArray728 = new int[dataSize];
        anIntArray729 = new int[dataSize];
        anIntArray730 = new int[dataSize];
        anIntArray731 = new int[dataSize];
        int k = stream.currentOffset + dataSize * 10;
        
        for (int l = 0; l < dataSize; l++) {
            anIntArray728[l] = stream.readUInt();
            anIntArray729[l] = stream.readUTriByte();
            anIntArray730[l] = stream.readUTriByte();
            anIntArray731[l] = k;
            k += anIntArray730[l];
        }
    }

    public byte[] getDataForName(String s) {
        byte strm_out[] = null;
        int nameHash = 0;
        s = s.toUpperCase();
        
        for (int j = 0; j < s.length(); j++) {
            nameHash = (nameHash * 61 + s.charAt(j)) - 32;
        }

        for (int k = 0; k < dataSize; k++) {
            if (anIntArray728[k] == nameHash) {
                if (strm_out == null) {
                    strm_out = new byte[anIntArray729[k]];
                }
                
                if (!aBoolean732) {
                    BZip2Decompressor.getStreamAvailableOut(strm_out, anIntArray729[k], strm_in, anIntArray730[k], anIntArray731[k]);
                } else {
                    System.arraycopy(strm_in, anIntArray731[k], strm_out, 0, anIntArray729[k]);
                }
                return strm_out;
            }
        }
        return null;
    }
}
