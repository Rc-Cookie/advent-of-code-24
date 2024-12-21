package de.rccookie.aoc.aoc24;

import de.rccookie.aoc.aoc24.util.FastSolution;

@SuppressWarnings({"SpellCheckingInspection", "CommentedOutCode"})
public class Solution21 extends FastSolution {

    private static final int[] NUMERIC_PATHS = { 36, 2140, 268, 2132, 16988, 2124, 16980, 135772, 16972, 135764, 276, 0, 0, 0, 0, 0, 2180, 36, 276, 2196, 268, 2132, 17044, 2124, 16980, 135828, 17540, 0, 0, 0, 0, 0, 260, 284, 36, 276, 2252, 268, 2132, 17996, 2124, 16980, 2068, 0, 0, 0, 0, 0, 2244, 2268, 284, 36, 18124, 2252, 268, 144972, 17996, 2124, 260, 0, 0, 0, 0, 0, 17412, 260, 2068, 16532, 36, 276, 2196, 268, 2132, 17044, 140292, 0, 0, 0, 0, 0, 2052, 2244, 260, 2068, 284, 36, 276, 2252, 268, 2132, 16404, 0, 0, 0, 0, 0, 17924, 18116, 2244, 260, 2268, 284, 36, 18124, 2252, 268, 2052, 0, 0, 0, 0, 0, 139268, 2052, 16404, 131220, 260, 2068, 16532, 36, 276, 2196, 1122308, 0, 0, 0, 0, 0, 16388, 17924, 2052, 16404, 2244, 260, 2068, 284, 36, 276, 131092, 0, 0, 0, 0, 0, 143364, 144900, 17924, 2052, 18116, 2244, 260, 2268, 284, 36, 16388, 0, 0, 0, 0, 0, 284, 17116, 2140, 268, 135900, 17996, 2124, 1086172, 143948, 16972, 36 };
    private static final int[] DIRECTIONAL_PATHS = { 36, 260, 2076, 2068, 276, 0, 0, 0, 268, 36, 284, 276, 2132, 0, 0, 0, 2188, 276, 36, 2196, 17548, 0, 0, 0, 2252, 284, 2268, 36, 268, 0, 0, 0, 284, 2244, 16604, 260, 36 };
    private static final int[] DIRECTIONAL_LENGTHS = { 1, 2, 3, 3, 2, 0, 0, 0, 2, 1, 2, 2, 3, 0, 0, 0, 3, 2, 1, 3, 4, 0, 0, 0, 3, 2, 3, 1, 2, 0, 0, 0, 2, 3, 4, 2, 1 };

//     Code to generate this data:
//
//    private static final int[] NUMERIC_PATHS = new int[10 << 4 | 11];
//    private static final int[] DIRECTIONAL_PATHS = new int[4 << 3 | 5];
//    private static final byte[] DIRECTIONAL_LENGTHS = new byte[DIRECTIONAL_PATHS.length];
//    static {
//        DIRECTIONAL_PATHS[encodeDir('A', 'A')] = encodeDir("AA");
//        DIRECTIONAL_LENGTHS[encodeDir('A', 'A')] = 1;
//        DIRECTIONAL_PATHS[encodeDir('A', '^')] = encodeDir("A<A");
//        DIRECTIONAL_LENGTHS[encodeDir('A', '^')] = 2;
//        DIRECTIONAL_PATHS[encodeDir('A', 'v')] = encodeDir("A<vA");
//        DIRECTIONAL_LENGTHS[encodeDir('A', 'v')] = 3;
//        DIRECTIONAL_PATHS[encodeDir('A', '<')] = encodeDir("Av<<A");
//        DIRECTIONAL_LENGTHS[encodeDir('A', '<')] = 4;
//        DIRECTIONAL_PATHS[encodeDir('A', '>')] = encodeDir("AvA");
//        DIRECTIONAL_LENGTHS[encodeDir('A', '>')] = 2;
//
//        DIRECTIONAL_PATHS[encodeDir('^', 'A')] = encodeDir("A>A");
//        DIRECTIONAL_LENGTHS[encodeDir('^', 'A')] = 2;
//        DIRECTIONAL_PATHS[encodeDir('^', '^')] = encodeDir("AA");
//        DIRECTIONAL_LENGTHS[encodeDir('^', '^')] = 1;
//        DIRECTIONAL_PATHS[encodeDir('^', 'v')] = encodeDir("AvA");
//        DIRECTIONAL_LENGTHS[encodeDir('^', 'v')] = 2;
//        DIRECTIONAL_PATHS[encodeDir('^', '<')] = encodeDir("Av<A");
//        DIRECTIONAL_LENGTHS[encodeDir('^', '<')] = 3;
//        DIRECTIONAL_PATHS[encodeDir('^', '>')] = encodeDir("Av>A");
//        DIRECTIONAL_LENGTHS[encodeDir('^', '>')] = 3;
//
//        DIRECTIONAL_PATHS[encodeDir('v', 'A')] = encodeDir("A^>A");
//        DIRECTIONAL_LENGTHS[encodeDir('v', 'A')] = 3;
//        DIRECTIONAL_PATHS[encodeDir('v', '^')] = encodeDir("A^A");
//        DIRECTIONAL_LENGTHS[encodeDir('v', '^')] = 2;
//        DIRECTIONAL_PATHS[encodeDir('v', 'v')] = encodeDir("AA");
//        DIRECTIONAL_LENGTHS[encodeDir('v', 'v')] = 1;
//        DIRECTIONAL_PATHS[encodeDir('v', '<')] = encodeDir("A<A");
//        DIRECTIONAL_LENGTHS[encodeDir('v', '<')] = 2;
//        DIRECTIONAL_PATHS[encodeDir('v', '>')] = encodeDir("A>A");
//        DIRECTIONAL_LENGTHS[encodeDir('v', '>')] = 2;
//
//        DIRECTIONAL_PATHS[encodeDir('<', 'A')] = encodeDir("A>>^A");
//        DIRECTIONAL_LENGTHS[encodeDir('<', 'A')] = 4;
//        DIRECTIONAL_PATHS[encodeDir('<', '^')] = encodeDir("A>^A");
//        DIRECTIONAL_LENGTHS[encodeDir('<', '^')] = 3;
//        DIRECTIONAL_PATHS[encodeDir('<', 'v')] = encodeDir("A>A");
//        DIRECTIONAL_LENGTHS[encodeDir('<', 'v')] = 2;
//        DIRECTIONAL_PATHS[encodeDir('<', '<')] = encodeDir("AA");
//        DIRECTIONAL_LENGTHS[encodeDir('<', '<')] = 1;
//        DIRECTIONAL_PATHS[encodeDir('<', '>')] = encodeDir("A>>A");
//        DIRECTIONAL_LENGTHS[encodeDir('<', '>')] = 3;
//
//        DIRECTIONAL_PATHS[encodeDir('>', 'A')] = encodeDir("A^A");
//        DIRECTIONAL_LENGTHS[encodeDir('>', 'A')] = 2;
//        DIRECTIONAL_PATHS[encodeDir('>', '^')] = encodeDir("A<^A");
//        DIRECTIONAL_LENGTHS[encodeDir('>', '^')] = 3;
//        DIRECTIONAL_PATHS[encodeDir('>', 'v')] = encodeDir("A<A");
//        DIRECTIONAL_LENGTHS[encodeDir('>', 'v')] = 2;
//        DIRECTIONAL_PATHS[encodeDir('>', '<')] = encodeDir("A<<A");
//        DIRECTIONAL_LENGTHS[encodeDir('>', '<')] = 3;
//        DIRECTIONAL_PATHS[encodeDir('>', '>')] = encodeDir("AA");
//        DIRECTIONAL_LENGTHS[encodeDir('>', '>')] = 1;
//
//        NUMERIC_PATHS[encodeNum('A', 'A')] = encodeDir("AA");
//        NUMERIC_PATHS[encodeNum('A', '0')] = encodeDir("A<A");
//        NUMERIC_PATHS[encodeNum('A', '1')] = encodeDir("A^<<A");
//        NUMERIC_PATHS[encodeNum('A', '2')] = encodeDir("A<^A");
//        NUMERIC_PATHS[encodeNum('A', '3')] = encodeDir("A^A");
//        NUMERIC_PATHS[encodeNum('A', '4')] = encodeDir("A^^<<A");
//        NUMERIC_PATHS[encodeNum('A', '5')] = encodeDir("A<^^A");
//        NUMERIC_PATHS[encodeNum('A', '6')] = encodeDir("A^^A");
//        NUMERIC_PATHS[encodeNum('A', '7')] = encodeDir("A^^^<<A");
//        NUMERIC_PATHS[encodeNum('A', '8')] = encodeDir("A<^^^A");
//        NUMERIC_PATHS[encodeNum('A', '9')] = encodeDir("A^^^A");
//
//        NUMERIC_PATHS[encodeNum('0', 'A')] = encodeDir("A>A");
//        NUMERIC_PATHS[encodeNum('0', '0')] = encodeDir("AA");
//        NUMERIC_PATHS[encodeNum('0', '1')] = encodeDir("A^<A");
//        NUMERIC_PATHS[encodeNum('0', '2')] = encodeDir("A^A");
//        NUMERIC_PATHS[encodeNum('0', '3')] = encodeDir("A^>A");
//        NUMERIC_PATHS[encodeNum('0', '4')] = encodeDir("A^^<A");
//        NUMERIC_PATHS[encodeNum('0', '5')] = encodeDir("A^^A");
//        NUMERIC_PATHS[encodeNum('0', '6')] = encodeDir("A^^>A");
//        NUMERIC_PATHS[encodeNum('0', '7')] = encodeDir("A^^^<A");
//        NUMERIC_PATHS[encodeNum('0', '8')] = encodeDir("A^^^A");
//        NUMERIC_PATHS[encodeNum('0', '9')] = encodeDir("A^^^>A");
//
//        NUMERIC_PATHS[encodeNum('1', 'A')] = encodeDir("A>>vA");
//        NUMERIC_PATHS[encodeNum('1', '0')] = encodeDir("A>vA");
//        NUMERIC_PATHS[encodeNum('1', '1')] = encodeDir("AA");
//        NUMERIC_PATHS[encodeNum('1', '2')] = encodeDir("A>A");
//        NUMERIC_PATHS[encodeNum('1', '3')] = encodeDir("A>>A");
//        NUMERIC_PATHS[encodeNum('1', '4')] = encodeDir("A^A");
//        NUMERIC_PATHS[encodeNum('1', '5')] = encodeDir("A^>A");
//        NUMERIC_PATHS[encodeNum('1', '6')] = encodeDir("A^>>A");
//        NUMERIC_PATHS[encodeNum('1', '7')] = encodeDir("A^^A");
//        NUMERIC_PATHS[encodeNum('1', '8')] = encodeDir("A^^>A");
//        NUMERIC_PATHS[encodeNum('1', '9')] = encodeDir("A^^>>A");
//
//        NUMERIC_PATHS[encodeNum('2', 'A')] = encodeDir("A>vA");
//        NUMERIC_PATHS[encodeNum('2', '0')] = encodeDir("AvA");
//        NUMERIC_PATHS[encodeNum('2', '1')] = encodeDir("A<A");
//        NUMERIC_PATHS[encodeNum('2', '2')] = encodeDir("AA");
//        NUMERIC_PATHS[encodeNum('2', '3')] = encodeDir("A>A");
//        NUMERIC_PATHS[encodeNum('2', '4')] = encodeDir("A<^A");
//        NUMERIC_PATHS[encodeNum('2', '5')] = encodeDir("A^A");
//        NUMERIC_PATHS[encodeNum('2', '6')] = encodeDir("A^>A");
//        NUMERIC_PATHS[encodeNum('2', '7')] = encodeDir("A<^^A");
//        NUMERIC_PATHS[encodeNum('2', '8')] = encodeDir("A^^A");
//        NUMERIC_PATHS[encodeNum('2', '9')] = encodeDir("A^^>A");
//
//        NUMERIC_PATHS[encodeNum('3', 'A')] = encodeDir("AvA");
//        NUMERIC_PATHS[encodeNum('3', '0')] = encodeDir("A<vA");
//        NUMERIC_PATHS[encodeNum('3', '1')] = encodeDir("A<<A");
//        NUMERIC_PATHS[encodeNum('3', '2')] = encodeDir("A<A");
//        NUMERIC_PATHS[encodeNum('3', '3')] = encodeDir("AA");
//        NUMERIC_PATHS[encodeNum('3', '4')] = encodeDir("A<<^A");
//        NUMERIC_PATHS[encodeNum('3', '5')] = encodeDir("A<^A");
//        NUMERIC_PATHS[encodeNum('3', '6')] = encodeDir("A^A");
//        NUMERIC_PATHS[encodeNum('3', '7')] = encodeDir("A<<^^A");
//        NUMERIC_PATHS[encodeNum('3', '8')] = encodeDir("A<^^A");
//        NUMERIC_PATHS[encodeNum('3', '9')] = encodeDir("A^^A");
//
//        NUMERIC_PATHS[encodeNum('4', 'A')] = encodeDir("A>>vvA");
//        NUMERIC_PATHS[encodeNum('4', '0')] = encodeDir("A>vvA");
//        NUMERIC_PATHS[encodeNum('4', '1')] = encodeDir("AvA");
//        NUMERIC_PATHS[encodeNum('4', '2')] = encodeDir("Av>A");
//        NUMERIC_PATHS[encodeNum('4', '3')] = encodeDir("Av>>A");
//        NUMERIC_PATHS[encodeNum('4', '4')] = encodeDir("AA");
//        NUMERIC_PATHS[encodeNum('4', '5')] = encodeDir("A>A");
//        NUMERIC_PATHS[encodeNum('4', '6')] = encodeDir("A>>A");
//        NUMERIC_PATHS[encodeNum('4', '7')] = encodeDir("A^A");
//        NUMERIC_PATHS[encodeNum('4', '8')] = encodeDir("A^>A");
//        NUMERIC_PATHS[encodeNum('4', '9')] = encodeDir("A^>>A");
//
//        NUMERIC_PATHS[encodeNum('5', 'A')] = encodeDir("Avv>A");
//        NUMERIC_PATHS[encodeNum('5', '0')] = encodeDir("AvvA");
//        NUMERIC_PATHS[encodeNum('5', '1')] = encodeDir("A<vA");
//        NUMERIC_PATHS[encodeNum('5', '2')] = encodeDir("AvA");
//        NUMERIC_PATHS[encodeNum('5', '3')] = encodeDir("Av>A");
//        NUMERIC_PATHS[encodeNum('5', '4')] = encodeDir("A<A");
//        NUMERIC_PATHS[encodeNum('5', '5')] = encodeDir("AA");
//        NUMERIC_PATHS[encodeNum('5', '6')] = encodeDir("A>A");
//        NUMERIC_PATHS[encodeNum('5', '7')] = encodeDir("A<^A");
//        NUMERIC_PATHS[encodeNum('5', '8')] = encodeDir("A^A");
//        NUMERIC_PATHS[encodeNum('5', '9')] = encodeDir("A^>A");
//
//        NUMERIC_PATHS[encodeNum('6', 'A')] = encodeDir("AvvA");
//        NUMERIC_PATHS[encodeNum('6', '0')] = encodeDir("A<vvA");
//        NUMERIC_PATHS[encodeNum('6', '1')] = encodeDir("A<<vA");
//        NUMERIC_PATHS[encodeNum('6', '2')] = encodeDir("A<vA");
//        NUMERIC_PATHS[encodeNum('6', '3')] = encodeDir("AvA");
//        NUMERIC_PATHS[encodeNum('6', '4')] = encodeDir("A<<A");
//        NUMERIC_PATHS[encodeNum('6', '5')] = encodeDir("A<A");
//        NUMERIC_PATHS[encodeNum('6', '6')] = encodeDir("AA");
//        NUMERIC_PATHS[encodeNum('6', '7')] = encodeDir("A<<^A");
//        NUMERIC_PATHS[encodeNum('6', '8')] = encodeDir("A<^A");
//        NUMERIC_PATHS[encodeNum('6', '9')] = encodeDir("A^A");
//
//        NUMERIC_PATHS[encodeNum('7', 'A')] = encodeDir("A>>vvvA");
//        NUMERIC_PATHS[encodeNum('7', '0')] = encodeDir("A>vvvA");
//        NUMERIC_PATHS[encodeNum('7', '1')] = encodeDir("AvvA");
//        NUMERIC_PATHS[encodeNum('7', '2')] = encodeDir("Avv>A");
//        NUMERIC_PATHS[encodeNum('7', '3')] = encodeDir("Avv>>A");
//        NUMERIC_PATHS[encodeNum('7', '4')] = encodeDir("AvA");
//        NUMERIC_PATHS[encodeNum('7', '5')] = encodeDir("Av>A");
//        NUMERIC_PATHS[encodeNum('7', '6')] = encodeDir("Av>>A");
//        NUMERIC_PATHS[encodeNum('7', '7')] = encodeDir("AA");
//        NUMERIC_PATHS[encodeNum('7', '8')] = encodeDir("A>A");
//        NUMERIC_PATHS[encodeNum('7', '9')] = encodeDir("A>>A");
//
//        NUMERIC_PATHS[encodeNum('8', 'A')] = encodeDir("Avvv>A");
//        NUMERIC_PATHS[encodeNum('8', '0')] = encodeDir("AvvvA");
//        NUMERIC_PATHS[encodeNum('8', '1')] = encodeDir("A<vvA");
//        NUMERIC_PATHS[encodeNum('8', '2')] = encodeDir("AvvA");
//        NUMERIC_PATHS[encodeNum('8', '3')] = encodeDir("Avv>A");
//        NUMERIC_PATHS[encodeNum('8', '4')] = encodeDir("A<vA");
//        NUMERIC_PATHS[encodeNum('8', '5')] = encodeDir("AvA");
//        NUMERIC_PATHS[encodeNum('8', '6')] = encodeDir("Av>A");
//        NUMERIC_PATHS[encodeNum('8', '7')] = encodeDir("A<A");
//        NUMERIC_PATHS[encodeNum('8', '8')] = encodeDir("AA");
//        NUMERIC_PATHS[encodeNum('8', '9')] = encodeDir("A>A");
//
//        NUMERIC_PATHS[encodeNum('9', 'A')] = encodeDir("AvvvA");
//        NUMERIC_PATHS[encodeNum('9', '0')] = encodeDir("A<vvvA");
//        NUMERIC_PATHS[encodeNum('9', '1')] = encodeDir("A<<vvA");
//        NUMERIC_PATHS[encodeNum('9', '2')] = encodeDir("A<vvA");
//        NUMERIC_PATHS[encodeNum('9', '3')] = encodeDir("AvvA");
//        NUMERIC_PATHS[encodeNum('9', '4')] = encodeDir("A<<vA");
//        NUMERIC_PATHS[encodeNum('9', '5')] = encodeDir("A<vA");
//        NUMERIC_PATHS[encodeNum('9', '6')] = encodeDir("AvA");
//        NUMERIC_PATHS[encodeNum('9', '7')] = encodeDir("A<<A");
//        NUMERIC_PATHS[encodeNum('9', '8')] = encodeDir("A<A");
//        NUMERIC_PATHS[encodeNum('9', '9')] = encodeDir("AA");
//    }
//
//    private static int encodeDir(String sequence) {
//        int x = 0;
//        for(int i=0; i<sequence.length(); ++i)
//            x |= encodeDir(sequence.charAt(i)) << 3*i;
//        return x;
//    }
//
//    private static int encodeDir(char a, char b) {
//        return encodeDir(b) << 3 | encodeDir(a);
//    }
//
//    private static int encodeDir(char c) {
//        return encodeDir((byte) c);
//    }
//
//    private static int encodeDir(byte c) {
//        return switch(c) {
//            case '^' -> 0;
//            case 'v' -> 1;
//            case '<' -> 2;
//            case '>' -> 3;
//            case 'A' -> 4;
//            default -> throw new AssertionError(Json.escape(c)+" is not a valid directional input");
//        };
//    }
//
//    private static int encodeNum(char a, char b) {
//        return encodeNum(b) << 4 | encodeNum(a);
//    }
//
//    private static int encodeNum(char c) {
//        return encodeNum((byte) c);
//    }

    @Override
    public Object task1() {
        return sumComplexities(1);
    }

    @Override
    public Object task2() {
        return sumComplexities(24);
    }

    private long sumComplexities(int proxyCountM2) {
        long[] cache = new long[proxyCountM2 << 6 | 4 << 3 | 5];
        return complexity( 0, proxyCountM2, cache)
             + complexity( 5, proxyCountM2, cache)
             + complexity(10, proxyCountM2, cache)
             + complexity(15, proxyCountM2, cache)
             + complexity(20, proxyCountM2, cache);
    }

    private long complexity(int i, int proxyCountM2, long[] cache) {
        byte a = bytes[i], b = bytes[i+1], c = bytes[i+2];
        int ia = encodeNum(a), ib = encodeNum(b), ic = encodeNum(c);

        int numeric = 100 * a + 10 * b + c - 5328;
        long length = numericProxyLength(NUMERIC_PATHS[ia << 4 | 10], proxyCountM2, cache)
                    + numericProxyLength(NUMERIC_PATHS[ib << 4 | ia], proxyCountM2, cache)
                    + numericProxyLength(NUMERIC_PATHS[ic << 4 | ib], proxyCountM2, cache)
                    + numericProxyLength(NUMERIC_PATHS[10 << 4 | ic], proxyCountM2, cache);

        return numeric * length;
    }

    private static long numericProxyLength(int sequence, int proxyCountM1, long[] cache) {
        long len = 0;
        for(int s = sequence; s > 7; s >>>= 3)
            len += directionalProxyLength(s & 63, proxyCountM1, cache);
        return len;
    }

    private static long directionalProxyLength(int index, int proxyCount, long[] cache) {
        if(proxyCount == 0)
            return DIRECTIONAL_LENGTHS[index];

        long len = cache[proxyCount << 6 | index];
        if(len != 0)
            return len;

        --proxyCount;
        for(int s = DIRECTIONAL_PATHS[index]; s > 7; s >>>= 3)
            len += directionalProxyLength(s & 63, proxyCount, cache);

        cache[(proxyCount+1) << 6 | index] = len;
        return len;
    }

    private static int encodeNum(byte c) {
        //noinspection SwitchStatementWithTooFewBranches
        return switch(c) {
            case 'A' -> 10;
            default -> c - '0';
        };
    }
}
