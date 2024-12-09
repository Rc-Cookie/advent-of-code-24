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

    private int loadFS(int[] fs) {
        int len = 0;
        for(int i=0, id=0; i<chars.length; i++, id++) {
            int size = chars[i] - '0';
            for(int j=0; j<size; j++)
                fs[len++] = id;
            if(++i == chars.length)
                break;
            size = chars[i] - '0';
            for(int j=0; j<size; j++)
                fs[len++] = -1;
        }
        return len;
    }

    private int[] loadFS() {
        int total = 0;
        int len = chars.length - 1;
        for(int i=0; i<len; i++)
            total += chars[i];
        int[] fs = new int[total - len * '0'];

        int fsIndex = 0;
        for(int i=0, id=0; i<len; i++, id++) {
            int size = chars[i] - '0';
            for(int j=0; j<size; j++)
                fs[fsIndex++] = id;
            size = chars[++i] - '0';
            for(int j=0; j<size; j++)
                fs[fsIndex++] = -1;
        }
        return fs;
    }

    @Override
    public Object task2() {

//        int[] fs = loadFS();
//        int len = fs.length;


        int[] gapCursors = new int[10];
        for(int i=1; i<chars.length-1; i+=2)
            gapCursors[chars[i] - '0']++;

//        int[] gapStarts = new int[(chars.length-1)/2];
//        byte[] gapSizes = new byte[gapStarts.length];
        int[] fileStarts = new int[(chars.length+1)/2];

        IntHeap[] sortedGapStarts = new IntHeap[9];
        for(int i=0; i<9; i++)
            sortedGapStarts[i] = new IntHeap(gapCursors[i+1] + 1);

        for(int i=0, fsIndex=0; i<(chars.length-1)/2; i++) {
            fsIndex += chars[2*i] - '0';
            int size = chars[2*i+1] - '0';
            if(size != 0) {
                IntHeap h = sortedGapStarts[size - 1];
                h.data[h.size++] = fsIndex;
            }
            fsIndex += size;
//            gapStarts[i] = fsIndex;
//            gapSizes[i] = (byte) (chars[2*i+1] - '0');
//            fsIndex += gapSizes[i];
            fileStarts[i+1] = fsIndex;
        }

//        fileStarts[fileStarts.length-1] = gapStarts[gapStarts.length-1] + gapSizes[gapSizes.length-1];

//        int firstGap = 0;
//        while(gapSizes[firstGap] == 0)
//            firstGap++;
        int firstGap = Integer.MAX_VALUE;
        for(int i=0; i<sortedGapStarts.length; i++)
            if(!sortedGapStarts[i].isEmpty() && firstGap > sortedGapStarts[i].peek())
                firstGap = sortedGapStarts[i].peek();

//        fs = new int[fs.length];
//        Arrays.fill(fs, -1);

        long checksum = 0;
        int file = (chars.length - 1) / 2;
        for(; firstGap < fileStarts[file]; file--) {
            byte size = (byte) (chars[2*file] - '0');
            int gap = Integer.MAX_VALUE;
            int gapSizeM1 = 0;

            for(int s=size-1; s<9; s++)
                if(!sortedGapStarts[s].isEmpty() && sortedGapStarts[s].peek() < gap)
                    gap = sortedGapStarts[gapSizeM1 = s].peek();

            if(gap != Integer.MAX_VALUE && gap < fileStarts[file]) {
                int gapStart = sortedGapStarts[gapSizeM1].dequeue();
                for(int j=0; j<size; j++) {
                    checksum += (long) file * (gapStart + j);
//                    fs[gapStart + j] = file;
                }

                if(gapSizeM1 != size - 1)
                    sortedGapStarts[gapSizeM1 - size].enqueue(gapStart + size);

                if(gapStart == firstGap) {
                    firstGap = Integer.MAX_VALUE;
                    for(int i=0; i<sortedGapStarts.length; i++)
                        if(!sortedGapStarts[i].isEmpty() && firstGap > sortedGapStarts[i].peek())
                            firstGap = sortedGapStarts[i].peek();
                }
            }

//            for(int i=firstGap; i<file; i++) {
//                if(gapSizes[i] >= size) {
//                    gap = i;
//                    break;
//                }
//            }
//
//            if(gap != Integer.MAX_VALUE) {
//                for(int j=0; j<size; j++) {
//                    checksum += (long) file * (gapStarts[gap] + j);
////                    fs[(gaps[gap] & 0xFFFF) + j] = file;
//                }
//                gapStarts[gap] += size;
//                gapSizes[gap] -= size;
//                while(gapSizes[firstGap] == 0)
//                    firstGap++;
//            }
            else {
                for (int j = 0; j < size; j++) {
                    checksum += (long) file * (fileStarts[file] + j);
//                    fs[fileStarts[file] + j] = file;
                }
            }

//            int firstGap1 = Integer.MAX_VALUE;
//            for(int i=0; i<sortedGapStarts.length; i++)
//                if(!sortedGapStarts[i].isEmpty() && firstGap1 > sortedGapStarts[i].peek())
//                    firstGap1 = sortedGapStarts[i].peek();
//            if(firstGap != firstGap1)
//                throw new AssertionError();
        }

        for(; file >= 0; file--) {
            byte size = (byte) (chars[2*file] - '0');
            for(int j=0; j<size; j++) {
                checksum += (long) file * (fileStarts[file] + j);
//                fs[fileStarts[file] + j] = file;
            }
        }

        // 6707707088978
        // 6421724645083
//        System.out.println("|"+Arrays.stream(fs).limit(len).mapToObj(i -> i < 0 ? "____" : " ".repeat(4-(""+i).length()) + i).collect(Collectors.joining("|"))+"|");

        return checksum;
    }
}
