package com.runescape.client.util;

import com.runescape.client.io.Stream;

public final class TextInput {

    public static String constructInput(int i, Stream stream) {
        int count = 0;
        int k = -1;
        
        for (int l = 0; l < i; l++) {
            int i1 = stream.readUByte();
            int characterId = i1 >> 4 & 0xf;
            
            if (k == -1) {
                if (characterId < 13) {
                    characters[count++] = validChars[characterId];
                } else {
                    k = characterId;
                }
            } else {
                characters[count++] = validChars[((k << 4) + characterId) - 195];
                k = -1;
            }
            characterId = i1 & 0xf;
            
            if (k == -1) {
                if (characterId < 13) {
                    characters[count++] = validChars[characterId];
                } else {
                    k = characterId;
                }
            } else {
                characters[count++] = validChars[((k << 4) + characterId) - 195];
                k = -1;
            }
        }
        boolean flag1 = true;
        
        for (int k1 = 0; k1 < count; k1++) {
            char c = characters[k1];
            
            if (flag1 && c >= 'a' && c <= 'z') {
                characters[k1] += '\uFFE0';
                flag1 = false;
            }
            
            if (c == '.' || c == '!' || c == '?') {
                flag1 = true;
            }
        }
        return new String(characters, 0, count);
    }

    public static void method526(String s, Stream stream) {
        if (s.length() > 80) {
            s = s.substring(0, 80);
        }
        s = s.toLowerCase();
        int i = -1;
        
        for (int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            int k = 0;
            
            for (int l = 0; l < validChars.length; l++) {
                if (c != validChars[l]) {
                    continue;
                }
                k = l;
                break;
            }

            if (k > 12) {
                k += 195;
            }
            
            if (i == -1) {
                if (k < 13) {
                    i = k;
                } else {
                    stream.writeByte(k);
                }
            } else if (k < 13) {
                stream.writeByte((i << 4) + k);
                i = -1;
            } else {
                stream.writeByte((i << 4) + (k >> 4));
                i = k & 0xf;
            }
        }
        
        if (i != -1) {
            stream.writeByte(i << 4);
        }
    }

    public static String processText(String s) {
        stream.currentOffset = 0;
        method526(s, stream);
        int streamOffset = stream.currentOffset;
        stream.currentOffset = 0;
        String s1 = constructInput(streamOffset, stream);
        return s1;
    }

    private static final char[] characters = new char[100];
    private static final Stream stream = new Stream(new byte[100]);
    private static final char[] validChars = {
        ' ', 'e', 't', 'a', 'o', 'i', 'h', 'n', 's', 'r',
        'd', 'l', 'u', 'm', 'w', 'c', 'y', 'f', 'g', 'p',
        'b', 'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2',
        '3', '4', '5', '6', '7', '8', '9', ' ', '!', '?',
        '.', ',', ':', ';', '(', ')', '-', '&', '*', '\\',
        '\'', '@', '#', '+', '=', '\243', '$', '%', '"', '[',
        ']'
    };
}