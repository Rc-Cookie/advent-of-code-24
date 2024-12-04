package de.rccookie.aoc.aoc24;

public class Solution4 extends FastSolution {

    private int width, height, tl, tr, bl, br;

    private void init() {
        width = indexOf('\n', 0);
        height = (chars.length + 1) / (width + 1);
        tl = -width - 2;
        tr = -width;
        bl = width;
        br = width + 2;
    }

    @Override
    public Object task1() {
        init();

        int count = 0;
        for(int x=-1; x<=1; x++) for(int y=-1; y<=1; y++)
            if(x != 0 || y != 0)
                count += checkDirection(x,y);
        return count;
    }

    private int checkDirection(int stepX, int stepY) {
        int mr = stepX > 0 ? 3 : 0;
        int mb = stepY > 0 ? 3 : 0;
        int mt = stepY < 0 ? 3 : 0;
        int step = stepX + (width+1) * stepY;
        int count = 0;
        for(int x = stepX<0?3:0; x < width - mr; x++) {
            for(int y = mt; y < height - mb; y++) {
                if(checkXMAS(x, y, step))
                    count++;
            }
        }
        return count;
    }

    @Override
    public Object task2() {
        init();

        int count = 0;
        for(int x=0; x<width-2; x++) for(int y=0; y<height-2; y++)
            if((checkMAS(x, y, br) || checkMAS(x+2, y+2, tl)) && (checkMAS(x+2, y, bl) || checkMAS(x, y+2, tr)))
                count++;
        return count;
    }

    private boolean checkXMAS(int x, int y, int step) {
        int start = x + (width+1) * y;
        return chars[start] == 'X' && chars[start + step] == 'M' && chars[start + 2*step] == 'A' && chars[start + 3*step] == 'S';
    }

    private boolean checkMAS(int x, int y, int step) {
        int start = x + (width+1) * y;
        return chars[start] == 'M' && chars[start + step] == 'A' && chars[start + 2*step] == 'S';
    }
}
