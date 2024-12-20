package de.rccookie.aoc.aoc24;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.aoc.aoc24.util.IntArrayList;
import de.rccookie.json.Json;

public class Solution15 extends FastSolution {

    private static final int LEFT = -1, RIGHT = 1;
    private int up, down;

    @Override
    public Object task1() {
        int len = chars.length;

        int width = eol(0);
        down = width + 1;
        up = -down;
        int[] directions = new int[128];
        directions['^'] = up;
        directions['v'] = down;
        directions['<'] = LEFT;
        directions['>'] = RIGHT;

        int instr = 0;
        while(chars[instr] != '\n')
            instr += down;
        ++instr;

        int robot = indexOf((byte) '@', 0);
        chars[robot] = '.';

        char c;
        for(; instr < len; instr++) {
            if((c = chars[instr]) == '\n') continue;
            int dir = directions[c];

            int boxCount = 0;
            while((c = chars[robot + (boxCount+1) * dir]) == 'O')
                boxCount++;
            if(c == '#') continue;

            robot += dir;
            if(boxCount != 0) {
                chars[robot] = '.';
                chars[robot + boxCount * dir] = 'O';
            }
        }

        int total = 0;
        for(int y=down; chars[y] != '\n'; y+=down)
            for(int x=1; x<width-1; x++)
                if(chars[x+y] == 'O')
                    total += x + 100 * (y/down);
        return total;
    }


    private byte[] grid;

    @Override
    public Object task2() {

        int len = chars.length;

        int width = eol(0);
        down = (width << 1) + 1;
        up = -down;
        int[] directions = new int[128];
        directions['^'] = up;
        directions['v'] = down;
        directions['<'] = LEFT;
        directions['>'] = RIGHT;

        int instr = 0;
        while(chars[instr] != '\n')
            instr += width + 1;
        ++instr;

        int robot = 0;
        int height = instr / (width+1);
        grid = new byte[((width<<1)+1) * height];
        for(int i=0, j=0; i<instr-1; ++i, ++j) {
            switch(chars[i]) {
                case '.' -> {
                    grid[j] = '.';
                    grid[++j] = '.';
                }
                case '#' -> {
                    grid[j] = '#';
                    grid[++j] = '#';
                }
                case 'O' -> {
                    grid[j] = '[';
                    grid[++j] = ']';
                }
                case '@' -> {
                    grid[robot = j] = '.';
                    grid[++j] = '.';
                }
                case '\n' -> grid[j] = '\n';
            }
        }
        assert robot != 0;

        long[] dejavu = new long[(grid.length + 63) >>> 6];
        IntArrayList movedBoxes = new IntArrayList();

//        grid[robot] = '@';
//        StringBuilder str = new StringBuilder();
//        for(byte b : grid) str.append((char) b);
//        System.out.println(str);
//        grid[robot] = '.';

        char c;
        byte b;
        for(; instr < len; instr++) {
            if((c = chars[instr]) == '\n') continue;
            int dir = directions[c];

//            grid[robot] = (byte) c;
//            str = new StringBuilder();
//            for(byte b : grid) str.append(b == c ? Console.colored(c, Attribute.RED_TEXT(), Attribute.BOLD()) : (char) b + "");
//            System.out.println(str);
//            grid[robot] = '.';

            switch(c) {
                case '<' -> {
                    int boxCount2 = 0;
                    while((b = grid[robot - boxCount2 - 1]) == ']')
                        boxCount2 += 2;
                    if(b == '#') continue;

                    --robot;
                    grid[robot] = '.';
                    for(boxCount2 -= 2; boxCount2>=0; boxCount2 -= 2) {
                        grid[robot - boxCount2 - 2] = '[';
                        grid[robot - boxCount2 - 1] = ']';
                    }
                }
                case '>' -> {
                    int boxCount2 = 0;
                    while((b = grid[robot + boxCount2 + 1]) == '[')
                        boxCount2 += 2;
                    if(b == '#') continue;

                    ++robot;
                    grid[robot] = '.';
                    for(boxCount2 -= 2; boxCount2>=0; boxCount2 -= 2) {
                        grid[robot + boxCount2 + 1] = '[';
                        grid[robot + boxCount2 + 2] = ']';
                    }
                }
                default -> {
                    movedBoxes.size = 0;
                    if(!findMovedBoxes(robot + dir, dir, dejavu, movedBoxes)) {
                        for(int i=0; i<movedBoxes.size; i++)
                            dejavu[movedBoxes.data[i] >>> 6] = 0;
                        continue;
                    }

                    robot += dir;
                    for(int i=0; i<movedBoxes.size; i++) {
                        int box = movedBoxes.data[i];
                        grid[box] = '.';
                        grid[box + RIGHT] = '.';
                        dejavu[box >>> 6] = 0;
                    }
                    for(int i=0; i<movedBoxes.size; i++) {
                        grid[movedBoxes.data[i] + dir] = '[';
                        grid[movedBoxes.data[i] + dir + RIGHT] = ']';
                    }
                }
            }
        }

        int total = 0;
        int wM2 = (width << 1) - 2;
        for(int y=down; y<grid.length; y+=down) {
            for(int x=2; x<wM2; x++) {
                if(grid[x+y] == '[') {
                    total += x + 100 * (y/down);
                    ++x;
                }
            }
        }
        return total;
    }

    private boolean findMovedBoxes(int pos, int dir, long[] dejavu, IntArrayList boxesOut) {
        return switch(grid[pos]) {
            case '#' -> false;
            case '.' -> true;
            case '[' -> {
                if(gsbi(dejavu, pos))
                    yield true;
                boxesOut.add(pos);
                yield findMovedBoxes(pos + dir, dir, dejavu, boxesOut) && findMovedBoxes(pos + dir + RIGHT, dir, dejavu, boxesOut);
            }
            case ']' -> {
                --pos;
                if(gsbi(dejavu, pos))
                    yield true;
                boxesOut.add(pos);
                yield findMovedBoxes(pos + dir, dir, dejavu, boxesOut) && findMovedBoxes(pos + dir + RIGHT, dir, dejavu, boxesOut);
            }
            default -> throw new AssertionError(Json.escape((char) grid[pos]));
        };
    }
}
