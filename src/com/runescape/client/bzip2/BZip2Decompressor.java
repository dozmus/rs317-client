package com.runescape.client.bzip2;

public final class BZip2Decompressor {

    private static final BZip2FileEntry ENTRY = new BZip2FileEntry();

    public static int getStreamAvailableOut(byte strm_out[], int strm_avail_out,
            byte strm_in[], int strm_avail_in, int strm_next_in) {
        synchronized (ENTRY) {
            ENTRY.strm_in = strm_in;
            ENTRY.strm_next_in = strm_next_in;
            ENTRY.strm_out = strm_out;
            ENTRY.strm_next_out = 0;
            ENTRY.strm_avail_in = strm_avail_in;
            ENTRY.strm_avail_out = strm_avail_out;
            ENTRY.bsLive = 0;
            ENTRY.bsBuff = 0;
            ENTRY.strm_total_in_lo32 = 0;
            ENTRY.strm_total_in_hi32 = 0;
            ENTRY.strm_total_out_lo32 = 0;
            ENTRY.strm_total_out_hi32 = 0;
            ENTRY.currBlockNo = 0;
            method227(ENTRY);
            strm_avail_out -= ENTRY.strm_avail_out;
            return strm_avail_out;
        }
    }

    private static void method226(BZip2FileEntry entry) {
        byte state_out_ch = entry.state_out_ch;
        int state_out_len = entry.state_out_len;
        int nblock_used = entry.nblock_used;
        int k0 = entry.k0;
        int tt[] = BZip2FileEntry.tt;
        int tPos = entry.tPos;
        byte strm_out[] = entry.strm_out;
        int strm_next_out = entry.strm_next_out;
        int strm_avail_out = entry.strm_avail_out;
        int strm_avail_out_dup = strm_avail_out;
        int save_nblock_pp = entry.save_nblock + 1;
        
        label0:
        do {
            if (state_out_len > 0) {
                do {
                    if (strm_avail_out == 0) {
                        break label0;
                    }
                    
                    if (state_out_len == 1) {
                        break;
                    }
                    strm_out[strm_next_out] = state_out_ch;
                    state_out_len--;
                    strm_next_out++;
                    strm_avail_out--;
                } while (true);
                
                if (strm_avail_out == 0) {
                    state_out_len = 1;
                    break;
                }
                strm_out[strm_next_out] = state_out_ch;
                strm_next_out++;
                strm_avail_out--;
            }
            boolean flag = true;
            
            while (flag) {
                flag = false;
                
                if (nblock_used == save_nblock_pp) {
                    state_out_len = 0;
                    break label0;
                }
                state_out_ch = (byte) k0;
                tPos = tt[tPos];
                byte byte0 = (byte) (tPos & 0xff);
                tPos >>= 8;
                nblock_used++;
                
                if (byte0 != k0) {
                    k0 = byte0;
                    
                    if (strm_avail_out == 0) {
                        state_out_len = 1;
                    } else {
                        strm_out[strm_next_out] = state_out_ch;
                        strm_next_out++;
                        strm_avail_out--;
                        flag = true;
                        continue;
                    }
                    break label0;
                }
                
                if (nblock_used != save_nblock_pp) {
                    continue;
                }
                
                if (strm_avail_out == 0) {
                    state_out_len = 1;
                    break label0;
                }
                strm_out[strm_next_out] = state_out_ch;
                strm_next_out++;
                strm_avail_out--;
                flag = true;
            }
            state_out_len = 2;
            tPos = tt[tPos];
            byte byte1 = (byte) (tPos & 0xff);
            tPos >>= 8;
            
            if (++nblock_used != save_nblock_pp) {
                if (byte1 != k0) {
                    k0 = byte1;
                } else {
                    state_out_len = 3;
                    tPos = tt[tPos];
                    byte byte2 = (byte) (tPos & 0xff);
                    tPos >>= 8;
                    
                    if (++nblock_used != save_nblock_pp) {
                        if (byte2 != k0) {
                            k0 = byte2;
                        } else {
                            tPos = tt[tPos];
                            byte byte3 = (byte) (tPos & 0xff);
                            tPos >>= 8;
                            nblock_used++;
                            state_out_len = (byte3 & 0xff) + 4;
                            tPos = tt[tPos];
                            k0 = (byte) (tPos & 0xff);
                            tPos >>= 8;
                            nblock_used++;
                        }
                    }
                }
            }
        } while (true);
        int i2 = entry.strm_total_out_lo32;
        entry.strm_total_out_lo32 += strm_avail_out_dup - strm_avail_out;
        
        if (entry.strm_total_out_lo32 < i2) {
            entry.strm_total_out_hi32++;
        }
        entry.state_out_ch = state_out_ch;
        entry.state_out_len = state_out_len;
        entry.nblock_used = nblock_used;
        entry.k0 = k0;
        BZip2FileEntry.tt = tt;
        entry.tPos = tPos;
        entry.strm_out = strm_out;
        entry.strm_next_out = strm_next_out;
        entry.strm_avail_out = strm_avail_out;
    }

    private static void method227(BZip2FileEntry entry) {
        int k8 = 0;
        int ai[] = null;
        int ai1[] = null;
        int ai2[] = null;
        entry.blockSize100k = 1;
        
        if (BZip2FileEntry.tt == null) {
            BZip2FileEntry.tt = new int[entry.blockSize100k * 0x186a0];
        }
        boolean flag19 = true;
        
        while (flag19) {
            byte byte0 = readByte(entry);
            
            if (byte0 == 23) {
                return;
            }
            byte0 = readByte(entry);
            byte0 = readByte(entry);
            byte0 = readByte(entry);
            byte0 = readByte(entry);
            byte0 = readByte(entry);
            entry.currBlockNo++;
            byte0 = readByte(entry);
            byte0 = readByte(entry);
            byte0 = readByte(entry);
            byte0 = readByte(entry);
            byte0 = readBit(entry);
            entry.blockRandomised = byte0 != 0;
            
            if (entry.blockRandomised) {
                System.out.println("(BZip2) PANIC! RANDOMISED BLOCK!");
            }
            entry.origPtr = 0;
            byte0 = readByte(entry);
            entry.origPtr = entry.origPtr << 8 | byte0 & 0xff;
            byte0 = readByte(entry);
            entry.origPtr = entry.origPtr << 8 | byte0 & 0xff;
            byte0 = readByte(entry);
            entry.origPtr = entry.origPtr << 8 | byte0 & 0xff;
            
            for (int j = 0; j < 16; j++) {
                byte byte1 = readBit(entry);
                entry.inUse16[j] = byte1 == 1;
            }

            for (int k = 0; k < 256; k++) {
                entry.inUse[k] = false;
            }

            for (int l = 0; l < 16; l++) {
                if (entry.inUse16[l]) {
                    for (int i3 = 0; i3 < 16; i3++) {
                        byte byte2 = readBit(entry);
                        
                        if (byte2 == 1) {
                            entry.inUse[l * 16 + i3] = true;
                        }
                    }
                }
            }
            method231(entry);
            int i4 = entry.nInUse + 2;
            int j4 = readBits(3, entry);
            int k4 = readBits(15, entry);
            
            for (int i1 = 0; i1 < k4; i1++) {
                int j3 = 0;
                
                do {
                    byte byte3 = readBit(entry);
                    
                    if (byte3 == 0) {
                        break;
                    }
                    j3++;
                } while (true);
                entry.selectorMtf[i1] = (byte) j3;
            }
            byte abyte0[] = new byte[6];
            
            for (byte byte16 = 0; byte16 < j4; byte16++) {
                abyte0[byte16] = byte16;
            }

            for (int j1 = 0; j1 < k4; j1++) {
                byte byte17 = entry.selectorMtf[j1];
                byte byte15 = abyte0[byte17];
                
                for (; byte17 > 0; byte17--) {
                    abyte0[byte17] = abyte0[byte17 - 1];
                }
                abyte0[0] = byte15;
                entry.selector[j1] = byte15;
            }

            for (int k3 = 0; k3 < j4; k3++) {
                int l6 = readBits(5, entry);
                
                for (int k1 = 0; k1 < i4; k1++) {
                    do {
                        byte byte4 = readBit(entry);
                        
                        if (byte4 == 0) {
                            break;
                        }
                        byte4 = readBit(entry);
                        
                        if (byte4 == 0) {
                            l6++;
                        } else {
                            l6--;
                        }
                    } while (true);
                    entry.len[k3][k1] = (byte) l6;
                }
            }

            for (int l3 = 0; l3 < j4; l3++) {
                byte byte8 = 32;
                int i = 0;
                
                for (int l1 = 0; l1 < i4; l1++) {
                    if (entry.len[l3][l1] > i) {
                        i = entry.len[l3][l1];
                    }
                    
                    if (entry.len[l3][l1] < byte8) {
                        byte8 = entry.len[l3][l1];
                    }
                }
                method232(entry.limitt[l3], entry.base[l3], entry.perm[l3],
                        entry.len[l3], byte8, i, i4);
                entry.minLens[l3] = byte8;
            }
            int l4 = entry.nInUse + 1;
            int l5 = 0x186a0 * entry.blockSize100k;
            int i5 = -1;
            int j5 = 0;
            
            for (int i2 = 0; i2 <= 255; i2++) {
                entry.unzftab[i2] = 0;
            }
            int j9 = 4095;
            
            for (int l8 = 15; l8 >= 0; l8--) {
                for (int i9 = 15; i9 >= 0; i9--) {
                    entry.mtfa[j9] = (byte) (l8 * 16 + i9);
                    j9--;
                }
                entry.mtfbase[l8] = j9 + 1;
            }
            int i6 = 0;
            
            if (j5 == 0) {
                i5++;
                j5 = 50;
                byte byte12 = entry.selector[i5];
                k8 = entry.minLens[byte12];
                ai = entry.limitt[byte12];
                ai2 = entry.perm[byte12];
                ai1 = entry.base[byte12];
            }
            j5--;
            int i7 = k8;
            int l7;
            byte byte9;
            
            for (l7 = readBits(i7, entry); l7 > ai[i7]; l7 = l7 << 1 | byte9) {
                i7++;
                byte9 = readBit(entry);
            }

            for (int k5 = ai2[l7 - ai1[i7]]; k5 != l4;) {
                if (k5 == 0 || k5 == 1) {
                    int j6 = -1;
                    int k6 = 1;
                    
                    do {
                        if (k5 == 0) {
                            j6 += k6;
                        } else if (k5 == 1) {
                            j6 += 2 * k6;
                        }
                        k6 *= 2;
                        
                        if (j5 == 0) {
                            i5++;
                            j5 = 50;
                            byte byte13 = entry.selector[i5];
                            k8 = entry.minLens[byte13];
                            ai = entry.limitt[byte13];
                            ai2 = entry.perm[byte13];
                            ai1 = entry.base[byte13];
                        }
                        j5--;
                        int j7 = k8;
                        int i8;
                        byte byte10;
                        
                        for (i8 = readBits(j7, entry); i8 > ai[j7]; i8 = i8 << 1 | byte10) {
                            j7++;
                            byte10 = readBit(entry);
                        }
                        k5 = ai2[i8 - ai1[j7]];
                    } while (k5 == 0 || k5 == 1);
                    
                    j6++;
                    byte byte5 = entry.seqToUnseq[entry.mtfa[entry.mtfbase[0]] & 0xff];
                    entry.unzftab[byte5 & 0xff] += j6;
                    
                    for (; j6 > 0; j6--) {
                        BZip2FileEntry.tt[i6] = byte5 & 0xff;
                        i6++;
                    }
                } else {
                    int j11 = k5 - 1;
                    byte byte6;
                    
                    if (j11 < 16) {
                        int j10 = entry.mtfbase[0];
                        byte6 = entry.mtfa[j10 + j11];
                        
                        for (; j11 > 3; j11 -= 4) {
                            int k11 = j10 + j11;
                            entry.mtfa[k11] = entry.mtfa[k11 - 1];
                            entry.mtfa[k11 - 1] = entry.mtfa[k11 - 2];
                            entry.mtfa[k11 - 2] = entry.mtfa[k11 - 3];
                            entry.mtfa[k11 - 3] = entry.mtfa[k11 - 4];
                        }

                        for (; j11 > 0; j11--) {
                            entry.mtfa[j10 + j11] = entry.mtfa[(j10 + j11) - 1];
                        }
                        entry.mtfa[j10] = byte6;
                    } else {
                        int l10 = j11 / 16;
                        int i11 = j11 % 16;
                        int k10 = entry.mtfbase[l10] + i11;
                        byte6 = entry.mtfa[k10];
                        
                        for (; k10 > entry.mtfbase[l10]; k10--) {
                            entry.mtfa[k10] = entry.mtfa[k10 - 1];
                        }
                        entry.mtfbase[l10]++;
                        
                        for (; l10 > 0; l10--) {
                            entry.mtfbase[l10]--;
                            entry.mtfa[entry.mtfbase[l10]] = entry.mtfa[(entry.mtfbase[l10 - 1] + 16) - 1];
                        }
                        entry.mtfbase[0]--;
                        entry.mtfa[entry.mtfbase[0]] = byte6;
                        
                        if (entry.mtfbase[0] == 0) {
                            int i10 = 4095;
                            
                            for (int k9 = 15; k9 >= 0; k9--) {
                                for (int l9 = 15; l9 >= 0; l9--) {
                                    entry.mtfa[i10] = entry.mtfa[entry.mtfbase[k9] + l9];
                                    i10--;
                                }
                                entry.mtfbase[k9] = i10 + 1;
                            }
                        }
                    }
                    entry.unzftab[entry.seqToUnseq[byte6 & 0xff] & 0xff]++;
                    BZip2FileEntry.tt[i6] = entry.seqToUnseq[byte6 & 0xff] & 0xff;
                    i6++;
                    
                    if (j5 == 0) {
                        i5++;
                        j5 = 50;
                        byte byte14 = entry.selector[i5];
                        k8 = entry.minLens[byte14];
                        ai = entry.limitt[byte14];
                        ai2 = entry.perm[byte14];
                        ai1 = entry.base[byte14];
                    }
                    j5--;
                    int k7 = k8;
                    int j8;
                    byte byte11;
                    
                    for (j8 = readBits(k7, entry); j8 > ai[k7]; j8 = j8 << 1 | byte11) {
                        k7++;
                        byte11 = readBit(entry);
                    }
                    k5 = ai2[j8 - ai1[k7]];
                }
            }
            entry.state_out_len = 0;
            entry.state_out_ch = 0;
            entry.cftab[0] = 0;
            
            for (int j2 = 1; j2 <= 256; j2++) {
                entry.cftab[j2] = entry.unzftab[j2 - 1];
            }

            for (int k2 = 1; k2 <= 256; k2++) {
                entry.cftab[k2] += entry.cftab[k2 - 1];
            }

            for (int l2 = 0; l2 < i6; l2++) {
                byte byte7 = (byte) (BZip2FileEntry.tt[l2] & 0xff);
                BZip2FileEntry.tt[entry.cftab[byte7 & 0xff]] |= l2 << 8;
                entry.cftab[byte7 & 0xff]++;
            }
            entry.tPos = BZip2FileEntry.tt[entry.origPtr] >> 8;
            entry.nblock_used = 0;
            entry.tPos = BZip2FileEntry.tt[entry.tPos];
            entry.k0 = (byte) (entry.tPos & 0xff);
            entry.tPos >>= 8;
            entry.nblock_used++;
            entry.save_nblock = i6;
            method226(entry);
            flag19 = entry.nblock_used == entry.save_nblock + 1 && entry.state_out_len == 0;
        }
    }

    private static void method231(BZip2FileEntry entry) {
        entry.nInUse = 0;
        
        for (int i = 0; i < 256; i++) {
            if (entry.inUse[i]) {
                entry.seqToUnseq[entry.nInUse] = (byte) i;
                entry.nInUse++;
            }
        }
    }

    private static void method232(int ai[], int ai1[], int ai2[],
            byte abyte0[], int i, int j, int k) {
        int l = 0;
        
        for (int i1 = i; i1 <= j; i1++) {
            for (int l2 = 0; l2 < k; l2++) {
                if (abyte0[l2] == i1) {
                    ai2[l] = l2;
                    l++;
                }
            }
        }

        for (int j1 = 0; j1 < 23; j1++) {
            ai1[j1] = 0;
        }

        for (int k1 = 0; k1 < k; k1++) {
            ai1[abyte0[k1] + 1]++;
        }

        for (int l1 = 1; l1 < 23; l1++) {
            ai1[l1] += ai1[l1 - 1];
        }

        for (int i2 = 0; i2 < 23; i2++) {
            ai[i2] = 0;
        }
        int i3 = 0;
        
        for (int j2 = i; j2 <= j; j2++) {
            i3 += ai1[j2 + 1] - ai1[j2];
            ai[j2] = i3 - 1;
            i3 <<= 1;
        }

        for (int k2 = i + 1; k2 <= j; k2++) {
            ai1[k2] = (ai[k2 - 1] + 1 << 1) - ai1[k2];
        }
    }

    private static byte readByte(BZip2FileEntry entry) {
        return (byte) readBits(8, entry);
    }

    private static byte readBit(BZip2FileEntry entry) {
        return (byte) readBits(1, entry);
    }

    private static int readBits(int bits, BZip2FileEntry entry) {
        int value;
        
        do {
            if (entry.bsLive >= bits) {
                int val = entry.bsBuff >> entry.bsLive - bits & (1 << bits) - 1;
                entry.bsLive -= bits;
                value = val;
                break;
            }
            entry.bsBuff = entry.bsBuff << 8 | entry.strm_in[entry.strm_next_in] & 0xff;
            entry.bsLive += 8;
            entry.strm_next_in++;
            entry.strm_avail_in--;
            entry.strm_total_in_lo32++;
            
            if (entry.strm_total_in_lo32 == 0) {
                entry.strm_total_in_hi32++;
            }
        } while (true);
        return value;
    }
}