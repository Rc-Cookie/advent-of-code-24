package de.rccookie.aoc.aoc24;

public class Solution7 extends FastSolution {

    private long target;
    private final int[] nums = new int[50];
    private final long[] lowerBounds = new long[nums.length];
    private final long[] magnitudes = new long[nums.length];
    private int numCount;

    @Override
    public Object task1() {

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

            numCount = count(' ', pos, indexOf('\n', pos));

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

            if(computablePlusMinus(first, 0))
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

            numCount = count(' ', pos, indexOf('\n', pos));

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
                lowerBounds[i] = (target) / maxIncrement;
            }

            if(computablePlusMinusConcat(first, 0))
                total += target;
        }

        return total;
    }

    private boolean computablePlusMinus(long current, int nextIndex) {
        if(current == target)
            return true;
        if(current > target || nextIndex >= numCount || current < lowerBounds[nextIndex])
            return false;
        long x = nums[nextIndex];
        return computablePlusMinus(current + x, nextIndex + 1) ||
                computablePlusMinus(current * x, nextIndex + 1);
    }

    private boolean computablePlusMinusConcat(long current, int nextIndex) {
        if(current == target)
            return true;
        if(current > target || nextIndex >= numCount || current < lowerBounds[nextIndex])
            return false;
        long x = nums[nextIndex];
        return computablePlusMinusConcat(current + x, nextIndex + 1) ||
                computablePlusMinusConcat(current * x, nextIndex + 1) ||
                computablePlusMinusConcat(current * magnitudes[nextIndex] + x, nextIndex + 1);
    }
}