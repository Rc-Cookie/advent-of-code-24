package de.rccookie.aoc.aoc24.clean;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.util.Console;
import de.rccookie.util.LongWrapper;
import de.rccookie.util.Parallel;

public class Solution22 extends FastSolution {

    @Override
    public Object task1() {

        int[] nums = lines.mapToInt(Integer::parseInt).toArray();

        long sum = 0;
        int stop = linesArr.length & ~1;

        for(int i=0; i<stop; i+=2) {
            long x = (long) nums[i] << 32 | nums[i+1];
            for(int j=0; j<2000; j++)
                x = next(x);
            sum += (x & 0xFFFFFF) + (x >> 32);
        }
        if(stop != linesArr.length) {
            int x = nums[stop];
            for(int j=0; j<2000; j++)
                x = next(x);
            sum += x;
        }
        return sum;
    }

    @Override
    public Object task2() {
        int[] nums = lines.mapToInt(Integer::parseInt).toArray();

//        long max = 0;
//        for(int a=-9; a<=9; a++) {
        LongWrapper max = new LongWrapper(0);
        Parallel.For(-9, 10, a -> {
//            Console.debug(a);
            for(int b=Math.max(-9,-9-a), stopB=Math.min(9,9-a); b<=stopB; b++) {
                for(int c=Math.max(-9,-9-a-b), stopC=Math.min(9,9-a-b); c<=stopC; c++) {
                    for(int d=Math.max(0,-9-a-b-c), stopD=Math.min(9,9-a-b-c); d<=stopD; d++) {
                        long count = simulate(a, b, c, d, nums);
                        if(max.value < count) {
                            synchronized(max) {
                                if(max.value < count) {
                                    max.value = count;
                                    Console.log(a, b, c, d, "->", count);
                                }
                            }
                        }
                    }
                }
            }
        });
        return max;
    }

    /*

    x                 abcdefghijklmnopqrstuvwx
    x << 6            ghijklmnopqrstuvwx

    x                 abcdefghijklmnopqrstuvwx
    x << 6 & 0x7FF                 tuvwx
    x << 12           mnopqrstuvwx
    x >> 5                 abcdefghijklmnopqrs
    x << 1 & 0x7FFFF       ghijklmnopqrstuvwx
    x << 11           lmnopqrstuvwx
    x << 17           rstuvwx

    x >> 5                 abcdefghijklmnopqrs
    x << 11           lmnopqrstuvwx

     */

    private static int next(int x) {
        int x6l = x << 6 & 0xFFFFFF;
        int xx6l = x ^ x6l;
        return (
                x
                ^ (x << 12)
                ^ (x6l & 0x7FF)
                ^ (xx6l << 11)
                ^ (xx6l >>> 5)
        ) & 0xFFFFFF;

//        x ^= x << 6 & 0xFFFFFF;
//        x ^= x >>> 5 & 0xFFFFFF;
//        x ^= x << 11 & 0xFFFFFF;
//        return x;
    }

    private static long next(long x) {

        long x6l = x << 6;
        long xx6l = x ^ x6l;
        return (
                x
                ^ ((x & 0xFFF_00000FFFL) << 12)
                ^ (x6l & 0x7FF_000007FFL)
                ^ ((xx6l & 0x1FFF_00001FFFL) << 11)
                ^ ((xx6l & 0xFFFFE0_00FFFFE0L) >>> 5)
        );

//        x ^= (x & 0x03FFFF_0003FFFFL) << 6;
//        x ^= (x & 0xFFFFE0_00FFFFE0L) >>> 5;
//        x ^= (x & 0x001FFF_00001FFFL) << 11;
//        return x;
    }

    private static int push(int seq, int prev, int cur) {
        return seq << 8 | (cur - prev & 0xFF);
    }

    private static long simulate(int a, int b, int c, int d, int[] nums) {
        int mask = a << 24 | (b & 0xFF) << 16 | (c & 0xFF) << 8 | (d & 0xFF);
        long sum = 0;
        for(int x : nums) {
            int x0 = x % 10;
            int seq = push(0, x0, x0 = (x = next(x)) % 10);
            seq = push(seq, x0, x0 = (x = next(x)) % 10);
            seq = push(seq, x0, x0 = (x = next(x)) % 10);
            for(int i=0; i<1997; i++) {
                seq = push(seq, x0, x0 = (x = next(x)) % 10);
                if(seq == mask) {
                    sum += x0;
                    break;
                }
            }
        }
        return sum;
    }

//    private static long simulate(int a, int b, int c, int d, int[][] allDiffs) {
//        int mask = a << 24 | (b & 0xFF) << 16 | (c & 0xFF) << 8 | (d & 0xFF);
//        long sum = 0;
//        for(int[] diffs : allDiffs) {
//            int x0 = x % 10;
//            int seq = push(0, x0, x0 = (x = next(x)) % 10);
//            seq = push(seq, x0, x0 = (x = next(x)) % 10);
//            seq = push(seq, x0, x0 = (x = next(x)) % 10);
//            for(int i=0; i<1997; i++) {
//                seq = push(seq, x0, x0 = (x = next(x)) % 10);
//                if(seq == mask) {
//                    sum += x0;
//                    break;
//                }
//            }
//        }
//        return sum;
//    }
//
//    private static int[][] simulateDiffs(int[] nums) {
//        int[][] diffs = new int[nums.length][];
//        for(int i=0; i<nums.length; i++)
//            diffs[i] = simulateDiffs(nums[i]);
//        return diffs;
//    }
//
//    private static int[] simulateDiffs(int x) {
//        int x0 = x % 10;
//        int seq = push(0, x0, x0 = (x = next(x)) % 10);
//        seq = push(seq, x0, x0 = (x = next(x)) % 10);
//        seq = push(seq, x0, x0 = (x = next(x)) % 10);
//
//        int[] diffs = new int[1997];
//        for(int i=0; i<1997; i++)
//            diffs[i] = seq = push(seq, x0, x0 = (x = next(x)) % 10);
//
//        return diffs;
//    }
//
//    public static void main(String[] args) {
//        Console.log(simulate(-1, -1, 0, 2, new int[] { 123 }));
//    }
}
