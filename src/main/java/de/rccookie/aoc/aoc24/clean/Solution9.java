package de.rccookie.aoc.aoc24.clean;

import de.rccookie.aoc.aoc24.FastSolution;

public class Solution9 extends FastSolution {
    @Override
    public Object task1() {
        int[] fs = new int[9 * chars.length];
        int len = loadFS(fs);

        long checksum = 0;
        for(int i=0; i<len; i++) {
            if(fs[i] == -1) {
                fs[i] = fs[--len];
                while (fs[len - 1] == -1) len--;
            }
            checksum += (long) i * fs[i];
        }

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

    @Override
    public Object task2() {
        int[] fs = new int[9 * chars.length];
        int len = loadFS(fs);

        int firstFree = 0;
        while(fs[firstFree] != -1) firstFree++;

        for(int i=len-1; i>firstFree; i--) {
            int id = fs[i];
            if(id < 0) continue;

            int size = 0;
            for(int j=i; fs[j] == id; j--)
                size++;

            int found = 0;
            for(int j=firstFree; j<i; j++) {
                if(fs[j] >= 0)
                    found = 0;
                else if(++found == size) {
                    for(int k=0; k<size; k++) {
                        fs[j-size+1+k] = id;
                        fs[i-k] = -1;
                    }
                    if(j == firstFree + size - 1) {
                        firstFree += size;
                        while(fs[firstFree] >= 0 && firstFree < i) firstFree++;
                    }
                    break;
                }
            }

            i -= size - 1;
        }

        long checksum = 0;
        for(int i=0; i<len; i++)
            checksum += (long) i * Math.max(0, fs[i]);

        return checksum;
    }
}
