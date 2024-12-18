package de.rccookie.aoc.aoc24.util;

import java.util.Arrays;

import de.rccookie.aoc.Solution;

public abstract class FastSolution extends Solution {

    protected static final int[] CHAR_VALUE = new int[128];
    private static final boolean[] IS_HEX = new boolean[128];
    static {
        for(int i=0; i<10; i++) {
            CHAR_VALUE['0' + i] = i;
            IS_HEX['0'+i] = true;
        }
        for(int i=0; i<26; i++) {
            CHAR_VALUE['a' + i] = CHAR_VALUE['A' + i] = 10 + i;
            IS_HEX['a'+i] = IS_HEX['A'+i] = i < 6;
        }
    }

    public final int eol(int from) {
        return indexOf('\n', from, chars.length);
    }

    public final int indexOf(char c, int from) {
        return indexOf(c, from, chars.length);
    }

    public final int indexOf(char c, int from, int to) {
        while(chars[from] != c) {
            from++;
            if(from == to)
                return -1;
        }
        return from;
    }

    public final int count(char c, int from, int to) {
        int count = 0;
        for(; from<to; from++)
            if(chars[from] == c)
                count++;
        return count;
    }

    public final int parseUInt(int from, int to) {
        int x = 0;
        for(; from<to; from++)
            x = 10 * x + chars[from] - '0';
        return x;
    }

    public final int parseUInt(int from) {
        int x = 0;
        do x = 10 * x + chars[from++] - '0';
        while(from != chars.length && chars[from] >= '0' && chars[from] <= '9');
        return x;
    }

    public final int parseUIntHex(int from, int to) {
        int x = 0;
        for(; from<to; from++)
            x = x << 4 | CHAR_VALUE[chars[from]];
        return x;
    }

    public final int parseUIntHex(int from) {
        int x = 0;
        do x = x << 4 | chars[from++] - '0';
        while(from != chars.length && chars[from] < IS_HEX.length && IS_HEX[chars[from]]);
        return x;
    }

    public final long parseULong(int from, int to) {
        long x = 0;
        for(; from<to; from++)
            x = 10 * x + chars[from] - '0';
        return x;
    }

    public final long parseULong(int from) {
        long x = 0;
        do x = 10 * x + chars[from++] - '0';
        while(from != chars.length && chars[from] >= '0' && chars[from] <= '9');
        return x;
    }

    public final long parseULongHex(int from, int to) {
        long x = 0;
        for(; from<to; from++)
            x = x << 4 | CHAR_VALUE[chars[from]];
        return x;
    }

    public final long parseULongHex(int from) {
        long x = 0;
        do x = x << 4 | chars[from++] - '0';
        while(from != chars.length && chars[from] < IS_HEX.length && IS_HEX[chars[from]]);
        return x;
    }



    public static boolean gbi(long[] data, int i) {
        return (data[i >>> 6] & (1L << (i & 63))) != 0;
    }

    public static void sbi(long[] data, int i) {
        data[i >>> 6] |= 1L << (i & 63);
    }

    public static boolean gsbi(long[] data, int i) {
        return data[i >>> 6] == (data[i >>> 6] |= 1L << (i & 63));
    }

    public static void cbi(long[] data, int i) {
        data[i >>> 6] &= ~(1L << (i & 63));
    }

    public static void sbi(long[] data, int i, int val) {
        data[i >>> 6] |= (long) val << (i & 63);
        data[i >>> 6] &= ~((1L^val) << (i & 63));
    }

    public static boolean gbi(long[] data, int x, int y, int width) {
        int i = x + width * y;
        return (data[i >>> 6] & 1L << (i & 63)) != 0;
    }

    public static boolean gbi(long[] data, int x, int y, int width, int step, int off) {
        int i = (x + width * y) * step + off;
        return (data[i >>> 6] & 1L << (i & 63)) != 0;
    }

    public static void sbi(long[] data, int x, int y, int width) {
        int i = x + width * y;
        data[i >>> 6] |= 1L << (i & 63);
    }

    public static boolean gsbi(long[] data, int x, int y, int width) {
        int i = x + width * y;
        return data[i >>> 6] == (data[i >>> 6] |= 1L << (i & 63));
    }

    public static void sbi(long[] data, int x, int y, int width, int step, int off) {
        int i = (x + width * y) * step + off;
        data[i >>> 6] |= 1L << (i & 63);
    }

    public static void cbi(long[] data, int x, int y, int width) {
        int i = x + width * y;
        data[i >>> 6] &= ~(1L << (i & 63));
    }

    public static void cbi(long[] data, int x, int y, int width, int step, int off) {
        int i = (x + width * y) * step + off;
        data[i >>> 6] &= ~(1L << (i & 63));
    }


    public static String renderGrid(long[] data, int width, int height) {
        StringBuilder str = new StringBuilder((width+1) * height);

        int maxW = ((width-1)+"").length();
        int maxH = ((height-1)+"").length();

        for(int y=maxW; y>0; y--) {
            str.append(" ".repeat(maxH+2));
            for(int x=0; x<width; x++) {
                String s = x+"";
                str.append(s.length() < y ? ' ' : s.charAt(s.length() - y));
            }
            str.append('\n');
        }

        try {
            for(int y=0; y<height; y++) {
                String s = y+"";
                str.append(" ".repeat(maxH - s.length())).append(s).append("  ");
                for(int x=0; x<width; x++)
                    str.append(gbi(data, x, y, width) ? '#' : '.');
                str.append('\n');
            }

            return str.substring(0, Math.max(0, str.length() - 1));
        } catch(ArrayIndexOutOfBoundsException e) {
            return str.toString();
        }
    }



    /**
     * Parses a list of integers with arbitrary delimiters into an array. Leading and trailing
     * "stuff" will be ignored.
     *
     * @param str The string to parse the list from
     * @return The list of integers
     */
    public static int[] parseInts(String str) {
        IntArrayList ints = new IntArrayList();
        int i = 0, x, len = str.length();
        char c;
        boolean minus;
        outer: while(true) {
            do if(i >= len) break outer;
            while(((c = str.charAt(i++)) < '0' || c > '9') && c != '-');

            //noinspection AssignmentUsedAsCondition
            if(minus = c == '-') {
                if(i >= len) break;
                if((c = str.charAt(i++)) < '0' || c > '9') continue;
            }

            x = c - '0';
            if(i < len) {
                while((c = str.charAt(i)) >= '0' && c <= '9') {
                    x = 10 * x + c - '0';
                    if(++i >= len)
                        break;
                }
            }
            ints.add(minus ? -x : x);
        }
        if(ints.data.length == ints.size)
            return ints.data;
        return Arrays.copyOf(ints.data, ints.size);
    }

    /**
     * Parses a list of integers with arbitrary delimiters into an array using 64-bit signed
     * integers. Leading and trailing "stuff" will be ignored.
     *
     * @param str The string to parse the list from
     * @return The list of longs
     */
    public static long[] parseLongs(String str) {
        LongArrayList longs = new LongArrayList();
        int i = 0, len = str.length();
        long x;
        char c;
        boolean minus;
        outer: while(true) {
            do if(i >= len) break outer;
            while(((c = str.charAt(i++)) < '0' || c > '9') && c != '-');

            //noinspection AssignmentUsedAsCondition
            if(minus = c == '-') {
                if(i >= len) break;
                if((c = str.charAt(i++)) < '0' || c > '9') continue;
            }

            x = c - '0';
            if(i < len) {
                while((c = str.charAt(i)) >= '0' && c <= '9') {
                    x = 10 * x + c - '0';
                    if(++i >= len)
                        break;
                }
            }
            longs.add(minus ? -x : x);
        }
        if(longs.data.length == longs.size)
            return longs.data;
        return Arrays.copyOf(longs.data, longs.size);
    }
}
