package de.rccookie.aoc.aoc24.clean;

import de.rccookie.aoc.aoc24.FastSolution;

public class Solution7 extends FastSolution {

    @Override
    public Object task1() {
        return sum(l -> {
            long[] nums = parseLongs(l);

            long[] lowerBounds = new long[nums.length];
            long maxIncrement = 1;
            for(int i=nums.length-1; i>=2; i--) {
                maxIncrement *= nums[i];
                lowerBounds[i] = (nums[0]-1) / maxIncrement;
            }

            return computablePlusMinus(nums[0], nums[1], nums, lowerBounds, 2) ? nums[0] : 0;
        });
    }

    @Override
    public Object task2() {
        return sum(l -> {
            long[] nums = parseLongs(l);

            int[] magnitudes = new int[nums.length];
            for(int i=2; i<magnitudes.length; i++) {
                magnitudes[i] = 10;
                while(magnitudes[i] <= nums[i])
                    magnitudes[i] *= 10;
            }

            long[] lowerBounds = new long[nums.length];
            long maxIncrement = 1;
            for(int i=nums.length-1; i>=2; i--) {
                maxIncrement *= magnitudes[i];
                lowerBounds[i] = nums[0] / maxIncrement;
            }

            return computablePlusMinusConcat(nums[0], nums[1], nums, magnitudes, lowerBounds, 2) ? nums[0] : 0;
        });
    }

    private boolean computablePlusMinus(long target, long current, long[] nums, long[] lowerBounds, int nextIndex) {
        if(current == target)
            return true;
        if(current > target || nextIndex >= nums.length || current < lowerBounds[nextIndex])
            return false;
        long x = nums[nextIndex];
        return computablePlusMinus(target, current + x, nums, lowerBounds, nextIndex + 1) ||
                computablePlusMinus(target, current * x, nums, lowerBounds, nextIndex + 1);
    }

    private boolean computablePlusMinusConcat(long target, long current, long[] nums, int[] magnitudes, long[] lowerBounds, int nextIndex) {
        if(current == target)
            return true;
        if(current > target || nextIndex >= nums.length || current < lowerBounds[nextIndex])
            return false;
        long x = nums[nextIndex];
        return computablePlusMinusConcat(target, current + x, nums, magnitudes, lowerBounds, nextIndex + 1) ||
                computablePlusMinusConcat(target, current * x, nums, magnitudes, lowerBounds, nextIndex + 1) ||
                computablePlusMinusConcat(target, current * magnitudes[nextIndex] + x, nums, magnitudes, lowerBounds, nextIndex + 1);
    }
}
