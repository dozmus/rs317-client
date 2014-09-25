package com.runescape.client;

import com.runescape.client.signlink.Signlink;

final class StringHelper {

    private static final char[] VALID_CHARACTERS = {
        '_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
        't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2',
        '3', '4', '5', '6', '7', '8', '9'
    };

    public static long longForName(String s) {
        long value = 0L;

        for (int i = 0; i < s.length() && i < 12; i++) {
            char c = s.charAt(i);
            value *= 37L;

            if (c >= 'A' && c <= 'Z') {
                value += (1 + c) - 65;
            } else if (c >= 'a' && c <= 'z') {
                value += (1 + c) - 97;
            } else if (c >= '0' && c <= '9') {
                value += (27 + c) - 48;
            }
        }

        for (; value % 37L == 0L && value != 0L; value /= 37L);
        return value;
    }

    public static String nameForLong(long l) {
        try {
            if (l <= 0L || l >= 0x5b5b57f8a98a5dd1L) {
                return "invalid_name";
            }
            
            if (l % 37L == 0L) {
                return "invalid_name";
            }
            int i = 0;
            char ac[] = new char[12];
            
            while (l != 0L) {
                long l1 = l;
                l /= 37L;
                ac[11 - i++] = VALID_CHARACTERS[(int) (l1 - l * 37L)];
            }
            return new String(ac, 12 - i, i);
        } catch (RuntimeException ex) {
            Signlink.printError("81570, " + l + ", " + (byte) -99 + ", " + ex.toString());
        }
        throw new RuntimeException();
    }

    public static long method585(String s) {
        s = s.toUpperCase();
        long hash = 0L;

        for (int i = 0; i < s.length(); i++) {
            hash = (hash * 61L + (long) s.charAt(i)) - 32L;
            hash = hash + (hash >> 56) & 0xffffffffffffffL;
        }
        return hash;
    }

    public static String decompressIpAddress(int ipAddress) {
        return (ipAddress >> 24 & 0xff) + "."
                + (ipAddress >> 16 & 0xff) + "."
                + (ipAddress >> 8 & 0xff) + "."
                + (ipAddress & 0xff);
    }

    public static String fixName(String s) {
        if (s.length() > 0) {
            char ac[] = s.toCharArray();

            for (int j = 0; j < ac.length; j++) {
                if (ac[j] == '_') {
                    ac[j] = ' ';

                    if (j + 1 < ac.length && ac[j + 1] >= 'a' && ac[j + 1] <= 'z') {
                        ac[j + 1] = (char) ((ac[j + 1] + 65) - 97);
                    }
                }
            }

            if (ac[0] >= 'a' && ac[0] <= 'z') {
                ac[0] = (char) ((ac[0] + 65) - 97);
            }
            return new String(ac);
        } else {
            return s;
        }
    }

    public static String hidePassword(String password) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < password.length(); i++) {
            sb.append("*");
        }
        return sb.toString();
    }
}
