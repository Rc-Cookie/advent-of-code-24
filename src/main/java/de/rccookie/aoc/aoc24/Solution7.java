package de.rccookie.aoc.aoc24;

import de.rccookie.aoc.aoc24.util.FastSolution;

public class Solution7 extends FastSolution {

    private long target;
    private final int[] nums = new int[50];
    private final long[] lowerBounds = new long[nums.length];
    private final long[] magnitudes = new long[nums.length];
    private int numCount, multiplicationFeasibleCount;

    @Override
    public Object task1() {

        long total = 0, maxIncrement, remainingTarget;
        int first, i, pos = 0;

        while(pos < chars.length - 1) {
            target = chars[pos++] - '0';
            while(chars[pos] != ':')
                target = 10 * target + chars[pos++] - '0';

            pos += 2;
            first = chars[pos++] - '0';
            while(chars[pos] >= '0' && chars[pos] <= '9')
                first = 10 * first + chars[pos++] - '0';

            numCount = count(' ', pos, eol(pos));

            for(i=0; i<numCount; i++) {
                pos++;
                nums[i] = chars[pos++] - '0';
                while (chars[pos] >= '0' && chars[pos] <= '9')
                    nums[i] = 10 * nums[i] + chars[pos++] - '0';
            }

            pos++;

            maxIncrement = 1;
            for(i=numCount-1; i>=0; i--) {
                maxIncrement *= nums[i];
                lowerBounds[i] = (target-1) / maxIncrement;
            }

            // This is only possible for part 1 because in part 2 we cannot assume addition as the only alternative
            multiplicationFeasibleCount = numCount;
            remainingTarget = target;
            while(multiplicationFeasibleCount > 0 && remainingTarget % nums[multiplicationFeasibleCount-1] != 0)
                remainingTarget -= nums[--multiplicationFeasibleCount];

            if(computablePlusMultiply(first, 0))
                total += target;
        }

        return total;
    }

    @Override
    public Object task2() {

        long total = 0, maxIncrement;
        int first, i, pos = 0;

        while(pos < chars.length - 1) {
            target = chars[pos++] - '0';
            while(chars[pos] != ':')
                target = 10 * target + chars[pos++] - '0';

            pos += 2;
            first = chars[pos++] - '0';
            while(chars[pos] >= '0' && chars[pos] <= '9')
                first = 10 * first + chars[pos++] - '0';

            numCount = count(' ', pos, eol(pos));

            for(i=0; i<numCount; i++) {
                pos++;
                nums[i] = chars[pos++] - '0';
                magnitudes[i] = 10;
                while(chars[pos] >= '0' && chars[pos] <= '9') {
                    nums[i] = 10 * nums[i] + chars[pos++] - '0';
                    magnitudes[i] *= 10;
                }
            }

            pos++;

            maxIncrement = 1;
            for(i=numCount-1; i>=0; i--) {
                maxIncrement *= magnitudes[i];
                lowerBounds[i] = target / maxIncrement;
            }

            if(computablePlusMultiplyConcat(first, 0))
                total += target;
        }

        return total;
    }

    private boolean computablePlusMultiply(long current, int nextIndex) {
        if(current == target)
            return true;
        if(current > target || nextIndex >= numCount || current < lowerBounds[nextIndex])
            return false;
        long x = nums[nextIndex];
        return computablePlusMultiply(current + x, nextIndex + 1) ||
                (nextIndex < multiplicationFeasibleCount && computablePlusMultiply(current * x, nextIndex + 1));
    }

    private boolean computablePlusMultiplyConcat(long current, int nextIndex) {
        if(current == target)
            return true;
        if(current > target || nextIndex >= numCount || current < lowerBounds[nextIndex])
            return false;
        long x = nums[nextIndex];
        return computablePlusMultiplyConcat(current + x, nextIndex + 1) ||
                computablePlusMultiplyConcat(current * x, nextIndex + 1) ||
                computablePlusMultiplyConcat(current * magnitudes[nextIndex] + x, nextIndex + 1);
    }
}
