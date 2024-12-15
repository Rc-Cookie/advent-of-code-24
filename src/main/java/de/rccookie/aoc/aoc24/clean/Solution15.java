package de.rccookie.aoc.aoc24.clean;

import java.util.HashSet;
import java.util.Set;

import de.rccookie.aoc.Grid;
import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.math.int2;

public class Solution15 extends FastSolution {
    @Override
    public Object task1() {
        String[] parts = input.split("\n\n");
        Grid grid = new Grid(parts[0]);
        char[] instructions = parts[1].replace("\n", "").toCharArray();

        int2 robot = grid.find('@');
        grid.set(robot, '.');

        for(char instr : instructions) {
            int2 dir = direction(instr);

            int boxCount = 0;
            while(grid.charAt(dir.scaled(boxCount+1).add(robot)) == 'O')
                boxCount++;
            if(grid.charAt(dir.scaled(boxCount+1).add(robot)) == '#') continue;

            robot.add(dir);
            if(boxCount != 0) {
                grid.set(robot, '.');
                grid.set(dir.scaled(boxCount).add(robot), 'O');
            }
        }

        int2 gps = new int2(1, 100);
        return grid.sum(p -> grid.charAtRaw(p) == 'O' ? p.dot(gps) : 0);
    }


    @Override
    public Object task2() {
        String[] parts = input.split("\n\n");
        grid = new Grid(parts[0].replace("#", "##").replace(".", "..").replace("O", "[]").replace("@", "@."));
        char[] instructions = parts[1].replace("\n", "").toCharArray();

        int2 robot = grid.find('@');
        grid.set(robot, '.');

        for(char instr : instructions) {
            int2 dir = direction(instr);

//            grid.set(robot, instr);
//            grid.mark(robot);
//            System.out.println(grid);
//            grid.unmark(robot);
//            grid.set(robot, '.');

            Set<int2> movedBoxes = new HashSet<>();
            if(!findMovedBoxes(robot.added(dir), dir, movedBoxes))
                continue;

            robot.add(dir);
            for(int2 box : movedBoxes) {
                grid.set(box, '.');
                grid.set(box.added(1,0), '.');
            }
            for(int2 box : movedBoxes) {
                grid.set(box.added(dir), '[');
                grid.set(box.added(dir).add(1,0), ']');
            }
        }

        int2 gps = new int2(1, 100);
        return grid.sum(p -> grid.charAtRaw(p) == '[' ? p.dot(gps) : 0);
    }

    private boolean findMovedBoxes(int2 pos, int2 dir, Set<int2> boxesOut) {
        return switch(grid.charAtRaw(pos)) {
            case '#' -> false;
            case '.' -> true;
            case '[' -> !boxesOut.add(pos) || (findMovedBoxes(pos.added(dir), dir, boxesOut) && findMovedBoxes(pos.added(dir).add(1,0), dir, boxesOut));
            case ']' -> !boxesOut.add(pos.subed(1,0)) || (findMovedBoxes(pos.added(dir).subed(1,0), dir, boxesOut) && findMovedBoxes(pos.added(dir), dir, boxesOut));
            default -> throw new AssertionError(grid.charAtRaw(pos));
        };
    }

    private static int2 direction(char c) {
        return switch(c) {
            case '<' -> new int2(-1, 0);
            case '>' -> new int2( 1, 0);
            case '^' -> new int2( 0,-1);
            case 'v' -> new int2( 0, 1);
            default -> throw new AssertionError(c);
        };
    }
}
