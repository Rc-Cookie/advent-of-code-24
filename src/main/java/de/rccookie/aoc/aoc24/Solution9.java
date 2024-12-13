package de.rccookie.aoc.aoc24;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.aoc.aoc24.util.IntHeap;

public class Solution9 extends FastSolution {
    @Override
    public Object task1() {

        int len = 0;
        int endId = (chars.length-1) >>> 1;
        int remainingEnd = chars[endId << 1] - '0';

        long checksum = 0;

        int size, j;
        for(int startId=0; startId<endId; startId++) {
            size = chars[startId << 1] - '0';

            // Sum(j=0->size) (startId * (len + j))
            checksum += startId * ((size * (((long) len << 1) + (size-1))) >>> 1);
            len += size;

            size = chars[(startId<<1)+1] - '0';
            for(j=0; j<size; j++) {
                checksum += (long) len++ * endId;
                remainingEnd--;
                while(remainingEnd <= 0)
                    remainingEnd = chars[--endId << 1] - '0';
            }
        }
        for(; remainingEnd > 0; remainingEnd--)
            checksum += (long) len++ * endId;

        return checksum;
    }

    @Override
    public Object task2() {

        final char[] chars = this.chars;
        final int len = chars.length - 1;
        final int gapCount = len >>> 1, fileCount = gapCount + 1;

        int i, size;
        IntHeap h;


        final int[] gapCounts = new int[10];
        for(i=1; i<len; i+=2)
            gapCounts[chars[i] - '0']++;

        IntHeap[] gaps = new IntHeap[9];
        for(i=0; i<9; ++i)
            (gaps[i] = new IntHeap(gapCounts[i+1] + 1)).data[0] = Integer.MAX_VALUE;


        final int[] fileStarts = new int[fileCount];

        i = 0;
        for(int fsIndex=0, i2; i<gapCount; ++i) {
            i2 = i << 1;
            fsIndex += chars[i2] - '0';
            size = chars[i2+1] - '0';
            if(size != 0) {
                h = gaps[size - 1];
                h.data[h.size++] = fsIndex;
            }
            fsIndex += size;
            fileStarts[i+1] = fsIndex;
        }

        final int[] firstGap = new int[10];
        final int[] firstGapSize = new int[9];
        firstGap[9] = Integer.MAX_VALUE;
        updateFirstGaps(firstGap, firstGapSize, gaps, 8);

        long checksum = 0;

        int file = fileCount - 1;
        for(int fileStart, gap, gapSizeM1, sizeM1; firstGap[0] < (fileStart = fileStarts[file]); --file) {

            size = chars[file << 1] - '0';
            sizeM1 = size - 1;

            if(firstGap[sizeM1] < fileStart) {
                gapSizeM1 = firstGapSize[sizeM1];
                h = gaps[gapSizeM1];

                gap = h.dequeue();
                h.data[h.size] = Integer.MAX_VALUE;
                // Sum(j=0->size) (file * (gap + j))
                checksum += file * ((size * (((long) gap << 1) + sizeM1)) >>> 1);

                if(gapSizeM1 != sizeM1)
                    gaps[gapSizeM1 - size].enqueue(gap + size);

                updateFirstGaps(firstGap, firstGapSize, gaps, gapSizeM1);
            }
            else {
                // Sum(j=0->size) (file * (fileStart + j))
                checksum += file * ((size * (((long) fileStart << 1) + sizeM1)) >>> 1);
            }
        }

        for(; file >= 0; --file) {
            size = chars[file << 1] - '0';
            // Sum(j=0->size) (file * (fileStarts[file] + j))
            checksum += file * ((size * (((long) fileStarts[file] << 1) + size - 1)) >>> 1);
        }

        return checksum;
    }


    private static void updateFirstGaps(final int[] firstGap, final int[] firstGapSize, final IntHeap[] gaps, int i) {
        for(int cur, bigger; i>=0; --i) {
            cur = gaps[i].data[0];
            bigger = firstGap[i+1];
            if(cur <= bigger) {
                firstGap[i] = cur;
                firstGapSize[i] = i;
            }
            else {
                firstGap[i] = bigger;
                firstGapSize[i] = firstGapSize[i+1];
            }
        }
    }
}
