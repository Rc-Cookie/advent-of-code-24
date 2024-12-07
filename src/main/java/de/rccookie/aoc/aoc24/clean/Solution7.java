package de.rccookie.aoc.aoc24.clean;

import de.rccookie.aoc.aoc24.FastSolution;

public class Solution7 extends FastSolution {

    @Override
    public Object task1() {
        return sum(l -> {
            long[] nums = parseLongs(l);
            return computablePlusMinus(nums[0], nums[1], nums, 2) ? nums[0] : 0;
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
            return computablePlusMinusConcat(nums[0], nums[1], nums, magnitudes, 2) ? nums[0] : 0;
        });
    }

    private boolean computablePlusMinus(long target, long current, long[] nums, int nextIndex) {
        if(current == target)
            return true;
        if(current > target || nextIndex >= nums.length)
            return false;
        long x = nums[nextIndex];
        return computablePlusMinus(target, current + x, nums, nextIndex + 1) ||
                computablePlusMinus(target, current * x, nums, nextIndex + 1);
    }

    private boolean computablePlusMinusConcat(long target, long current, long[] nums, int[] magnitudes, int nextIndex) {
        if(current == target)
            return true;
        if(current > target || nextIndex >= nums.length)
            return false;
        long x = nums[nextIndex];
        return computablePlusMinusConcat(target, current + x, nums, magnitudes, nextIndex + 1) ||
                computablePlusMinusConcat(target, current * x, nums, magnitudes, nextIndex + 1) ||
                computablePlusMinusConcat(target, current * magnitudes[nextIndex] + x, nums, magnitudes, nextIndex + 1);
    }
}
