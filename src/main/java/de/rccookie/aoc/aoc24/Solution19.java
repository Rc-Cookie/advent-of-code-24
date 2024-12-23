package de.rccookie.aoc.aoc24;

import java.util.Arrays;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.aoc.aoc24.util.IntTrie;

public class Solution19 extends FastSolution {

    // Inspired by https://github.com/maneatingape/advent-of-code-rust/blob/main/src/year2024/day19.rs

    private static final int[] ENCODING = new int[128];
    static {
        ENCODING['b'] = 1;
        ENCODING['g'] = 2;
        ENCODING['r'] = 3;
        ENCODING['u'] = 4;
        ENCODING['w'] = 5;
    }

    private IntTrie trie;
    private int patternsStart;

    @Override
    public void load() {
        trie = new IntTrie(6);
        int pos = 0;
        byte c;
        do {
            int n = trie.afterOrGenerate(trie.root(), ENCODING[bytes[pos]]);
            while((c = bytes[++pos]) >= 'a')
                n = trie.afterOrGenerate(n, ENCODING[c]);
            trie.increment(n);
            pos += 2;
        } while(c != '\n');

        patternsStart = pos;
    }

    @Override
    public Object task1() {
        return countOrSumCombinations(false);
    }

    @Override
    public Object task2() {
        return countOrSumCombinations(true);
    }

    private long countOrSumCombinations(boolean sum) {
        long total = 0;
        long[] combinations = new long[65];
        int[] encoded = new int[64];

        int pos = patternsStart;
        while(pos < bytes.length) {
            int end = eol(pos);

            long c = combinations(pos, end, combinations, encoded);
            if(sum) total += c;
            else if(c != 0) ++total;

            pos = end + 1;
        }

        return total;
    }

    private long combinations(int from, int to, long[] combinations, int[] encoded) {
        int len = to - from;

        Arrays.fill(combinations, 1, to - from + 1, 0);
        combinations[0] = 1;

        for(int i=0; i<len; i++)
            encoded[i] = ENCODING[bytes[i + from]];

        for(int i=0; i<len; i++) {
            if(combinations[i] == 0) continue;

            int t = trie.root();
            for(int j=i; j<len; j++) {
                if((t = trie.after(t, encoded[j])) == 0) break;
                combinations[j + 1] += trie.count(t) * combinations[i];
            }
        }

        return combinations[len];
    }
}
