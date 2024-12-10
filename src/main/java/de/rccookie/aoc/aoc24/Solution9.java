package de.rccookie.aoc.aoc24;

public class Solution9 extends FastSolution {
    @Override
    public Object task1() {

        int len = 0;
        int endId = (chars.length-1) / 2;
        int remainingEnd = chars[2*endId] - '0';

        long checksum = 0;

        int size, j;
        for(int startId=0; startId<endId; startId++) {
            size = chars[2*startId] - '0';
            for(j=0; j<size; j++)
                checksum += (long) len++ * startId;
            size = chars[2*startId+1] - '0';
            for(j=0; j<size; j++) {
                checksum += (long) len++ * endId;
                remainingEnd--;
                while(remainingEnd <= 0)
                    remainingEnd = chars[2 * --endId] - '0';
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
        final int gapCount = len / 2, fileCount = gapCount + 1;

        int i, fsIndex, size;
        IntHeap h;


        final int[] gapCounts = new int[10];
        for(i=1; i<len; i+=2)
            gapCounts[chars[i] - '0']++;

        IntHeap[] gaps = new IntHeap[9];
        for(i=0; i<9; i++)
            gaps[i] = new IntHeap(gapCounts[i+1] + 1);


        final int[] fileStarts = new int[fileCount];

        for(i=0, fsIndex=0; i<gapCount; i++) {
            int i2 = i << 1;
            fsIndex += chars[i2] - '0';
            size = chars[i2+1] - '0';
            if(size != 0) {
                h = gaps[size - 1];
                h.data[h.size++] = fsIndex;
            }
            fsIndex += size;
            fileStarts[i+1] = fsIndex;
        }

        int firstGap = Integer.MAX_VALUE;
        for(i=0; i<9; i++) {
            h = gaps[i];
            if(h.size != 0 && firstGap > h.data[0])
                firstGap = h.data[0];
        }

        long checksum = 0;

        int file = fileCount - 1;
        for(int fileStart, gap, gapSizeM1 = 0, s; firstGap < (fileStart = fileStarts[file]); file--) {
            size = chars[file << 1] - '0';
            gap = Integer.MAX_VALUE;

            for(s=size-1; s<9; s++) {
                h = gaps[s];
                if(h.size != 0 && h.data[0] < gap) {
                    gap = h.data[0];
                    gapSizeM1 = s;
                }
            }

            if(gap < fileStart) {
                gaps[gapSizeM1].dequeue();
                // Sum(j=0->size) (file * (gap + j))
                checksum += file * ((size * (((long) gap << 1) + size - 1)) >>> 1);

                if(gapSizeM1 != size - 1)
                    gaps[gapSizeM1 - size].enqueue(gap + size);

                if(gap == firstGap) {
                    firstGap = Integer.MAX_VALUE;
                    for(i=0; i<9; i++) {
                        h = gaps[i];
                        if(h.size != 0 && firstGap > h.data[0])
                            firstGap = h.data[0];
                    }
                }
            }
            else {
                // Sum(j=0->size) (file * (fileStart + j))
                checksum += file * ((size * (((long) fileStart << 1) + size - 1)) >>> 1);
            }
        }

        for(; file >= 0; file--) {
            size = chars[file << 1] - '0';
            // Sum(j=0->size) (file * (fileStarts[file] + j))
            checksum += file * ((size * (((long) fileStarts[file] << 1) + size - 1)) >>> 1);
        }

        return checksum;
    }
}
