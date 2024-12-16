package de.rccookie.aoc.aoc24.clean;

import de.rccookie.aoc.Grid;
import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.math.constInt2;
import de.rccookie.math.int2;

public class Solution15 extends FastSolution {
    @Override
    public Object task1() {
        String[] parts = input.split("\n\n");
        Grid grid = new Grid(parts[0]);

        int2 robot = grid.findAndSet('@', '.');

        for(char instr : parts[1].replace("\n", "").toCharArray()) {
            constInt2 dir = directionVec(instr);
            if(grid.push(robot, dir, c -> c == 'O') != -1)
                robot.add(dir);
        }

        int2 gps = new int2(1, 100);
        return grid.sum(p -> grid.charAtRaw(p) == 'O' ? p.dot(gps) : 0);
    }


    @Override
    public Object task2() {
        String[] parts = input.split("\n\n");
        grid = new Grid(parts[0].replace("#", "##").replace(".", "..").replace("O", "[]").replace("@", "@."));

        int2 robot = grid.findAndSet('@', '.');

        for(char instr : parts[1].replace("\n", "").toCharArray()) {
            constInt2 dir = directionVec(instr);
            if(grid.pushShape(robot, dir, "[]") != -1)
                robot.add(dir);
        }

        int2 gps = new int2(1, 100);
        return grid.sum(p -> grid.charAtRaw(p) == '[' ? p.dot(gps) : 0);
    }
}
