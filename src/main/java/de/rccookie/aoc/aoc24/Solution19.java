package de.rccookie.aoc.aoc24;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.json.Json;

public class Solution19 extends FastSolution {

    private String[] availableStr;
    private String[] patternsStr;
    private int[] available;
    private int[] availableMask;
    private int[] availableLengths;

    @Override
    public void load() {
        String[] parts = input.split("\n\n");
        availableStr = parts[0].split("\\s*,\\s*");
        patternsStr = parts[1].split("\n");

        available = new int[availableStr.length];
        availableMask = new int[available.length];
        availableLengths = new int[available.length];

        for(int i=0; i<availableStr.length; i++) {
            String s = availableStr[i];
            int len = s.length();

            int a = 0;
            int m = 0;
            for(int j=0; j<len; j++) {
                a |= encode((byte) s.charAt(j)) << 3 * j;
                m |= 0b111 << 3 * j;
            }

            available[i] = a;
            availableMask[i] = m;
            availableLengths[i] = len;
        }
    }

    private static int encode(byte c) {
        return switch(c) {
            case 'b' -> 1;
            case 'g' -> 2;
            case 'r' -> 3;
            case 'u' -> 4;
            case 'w' -> 5;
            default -> throw new AssertionError(Json.escape((char) c));
        };
    }

    @Override
    public Object task1() {
        return count(patternsStr, p -> combinations(p) != 0);
    }

    @Override
    public Object task2() {
        return sum(patternsStr, this::combinations);
    }

    private long combinations(String pattern) {
        int len = pattern.length();

        long[] aligned = new long[(len * 3 + 63) / 64];
        long[] offset = new long[aligned.length + 1];

        for(int i=0; i<len; i++) {
            long c = encode((byte) pattern.charAt(i));

            int start = 3*i;
            aligned[start >>> 6] |= c << (start & 63);

            start += 32;
            offset[start >>> 6] |= c << (start & 63);
        }



        long[] combinations = new long[len+1];
        combinations[0] = 1;

        for(int i=0; i<len; i++) {
            if(combinations[i] == 0) continue;

            int i3 = 3 * i;
            int i3mod64 = i3 & 63;

            long patternSection;
            if(i3mod64 >= 32)
                patternSection = offset[(i3 + 32) >>> 6] >> (i3mod64 ^ 32);
            else patternSection = aligned[i3 >>> 6] >>> i3mod64;

            for(int j=0; j<available.length; j++)
                if((patternSection & availableMask[j]) == available[j])
                    combinations[i + availableLengths[j]] += combinations[i];
        }

        return combinations[len];
    }

//    private static String bin(long x) {
//        String str = Long.toBinaryString(x);
//        str = "0".repeat((str.length() + 2) / 3 * 3 - str.length()) + str;
//        StringBuilder s = new StringBuilder();
//        for(int i=0; i<str.length(); i+=3)
//            s.append(str, i, i+3).append('|');
//        return s.substring(0, s.length() - 1);
//    }

//    private static final class Trie {
//        final int[] next = new int[5];
//        boolean isWord = false;
//    }
}
