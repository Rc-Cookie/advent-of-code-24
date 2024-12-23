package de.rccookie.aoc.aoc24;

import java.util.Arrays;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.json.Json;

public class Solution19 extends FastSolution {

    private String[] availableStr;
    private String[] patternsStr;
    private int[] available;
    private int[] availableMask;
    private long[][] patternsAligned, patternsOffset;
    private final boolean[] cached = new boolean[64];
    private final boolean[] possibleCache = new boolean[64];
    private final long[] combinationsCache = new long[64];

    @Override
    public void load() {
        String[] parts = input.split("\n\n");
        availableStr = parts[0].split("\\s*,\\s*");
        patternsStr = parts[1].split("\n");

        available = new int[availableStr.length];
        for(int i=0; i<availableStr.length; i++) {
            String s = availableStr[i];
        }
        available = Arrays.stream(availableStr).mapToInt(s -> {
            int a = 0;
            for(int j=0; j<s.length(); j++)
                a |= encode((byte) s.charAt(j)) << 3 * j;
            return a;
        }).toArray();
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
        return count(patternsStr, pattern -> {
            Arrays.fill(cached, false);
            return isPossible(pattern, 0);
        });
    }

    @Override
    public Object task2() {
        return sum(patternsStr, pattern -> {

            long[] aligned = new long[(pattern.length() * 3 + 63) / 64];
            long[] offset = new long[aligned.length + 1];

            for(int i=0; i<pattern.length(); i++) {
                long c = encode((byte) pattern.charAt(i));
                int start = 3*i, end = start + 2;
                int startChunk = start / 64, endChunk = end / 64;

                aligned[startChunk] |= c << (start & 63);
                if(startChunk != endChunk)
                    aligned[endChunk] |= c >> 64 - (start & 63);
            }

            for(int i=0; i<pattern.length(); i++) {
                long c = encode((byte) pattern.charAt(i));
                int start = 3*i + 32, end = start + 2;
                int startChunk = start / 64, endChunk = end / 64;

                offset[startChunk] |= c << (start & 63);
                if(startChunk != endChunk)
                    offset[endChunk] |= c >> 64 - (start & 63);
            }



            int len = pattern.length();
            long[] combinations = new long[len+1];
            combinations[0] = 1;

            for(int i=1; i<=len; i++)
                for(String a : availableStr)
                    if(pattern.startsWith(a, len - i))
                        combinations[i] += combinations[i - a.length()];

            return combinations[len];

//            Arrays.fill(cached, false);
//            return countCombinations(pattern, 0);
        });
    }

    int test = 0;
    private boolean isPossible(String pattern, int offset) {
        if(offset == pattern.length())
            return true;
        if(cached[offset])
            return possibleCache[offset];
        for(String a : availableStr)
            if(pattern.startsWith(a, offset) && isPossible(pattern, offset + a.length()))
                return possibleCache[offset] = cached[offset] = true;
        cached[offset] = true;
        return possibleCache[offset] = false;
    }

    private long countCombinations(String pattern, int offset) {
        if(offset == pattern.length())
            return 1;
        if(cached[offset])
            return combinationsCache[offset];

        long combinations = sum(availableStr, a -> pattern.startsWith(a, offset) ? countCombinations(pattern, offset + a.length()) : 0);
        cached[offset] = true;
        combinationsCache[offset] = combinations;
        return combinations;
    }
}
