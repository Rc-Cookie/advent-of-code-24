package de.rccookie.aoc.aoc24.clean;

import de.rccookie.aoc.Grid;
import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.math.constInt2;
import de.rccookie.math.float2;
import de.rccookie.math.int2;
import de.rccookie.util.MappingIterator;

public class Solution14 extends FastSolution {

    public static final int WIDTH = 101, HEIGHT = 103;

    @Override
    public Object task1() {
        int hw = WIDTH / 2, hh = HEIGHT / 2;
        long tl = 0, tr = 0, bl = 0, br = 0;
        Grid grid = new Grid(WIDTH, HEIGHT, '.');
        for(int2 pos : positionsAfter(100)) {
            if(grid.charAt(pos) == '.')
                grid.set(pos, '1');
            else grid.set(pos, (char) (grid.charAt(pos) + 1));
            if(pos.x() < hw) {
                if(pos.y() < hh) tl++;
                else if(pos.y() > hh) bl++;
            }
            else if(pos.x() > hw) {
                if(pos.y() < hh) tr++;
                else if(pos.y() > hh) br++;
            }
        }
        System.out.println(grid);
        return tl * tr * bl * br;
    }

    @Override
    public Object task2() {
        int2 min = new int2(0);
        float2 minVariance = float2.inf();

        for(int i=0; i<Math.max(WIDTH, HEIGHT); i++) {
            float2 avg = new float2(0), avg2 = new float2(0);
            for(int2 p : positionsAfter(i)) {
                avg.add(p);
                avg2.add(p.scale(p));
            }
            avg.div(linesArr.length);
            avg2.div(linesArr.length);
            float2 variance = avg2.subed(avg.scaled(avg)).abs();
            if(variance.x() < minVariance.x())
                min.setX(i);
            if(variance.y() < minVariance.y())
                min.setY(i);
            minVariance.setMin(variance);
        }

        int2 hw = new int2(WIDTH, HEIGHT).yx();

//        Grid g = new Grid(hw.yx(), '▉');
//        positionsAfter(Math.floorMod(extendedEuclideanAlgorithm(hw).scale(hw).scale(min).sum(), hw.product())).forEach(g::mark);
//        System.out.println(g);

        return Math.floorMod(extendedEuclideanAlgorithm(hw).scale(hw).scale(min).sum(), hw.product());
    }

    private Iterable<int2> positionsAfter(int seconds) {
        return () -> new MappingIterator<>(lines, line -> {
            int[] nums = parseInts(line);
            return new int2(
                Math.floorMod(nums[0] + seconds * nums[2], WIDTH),
                Math.floorMod(nums[1] + seconds * nums[3], HEIGHT)
            );
        });
    }

    public static int2 extendedEuclideanAlgorithm(constInt2 ab) {
        int oldR = ab.x(), r = ab.y();
        int oldS = 1, s = 0;
        int oldT = 0, t = 1;

        while(r != 0) {
            int q = oldR / r;

            int tmp = r;
            r = oldR - q * r;
            oldR = tmp;

            tmp = s;
            s = oldS - q * s;
            oldS = tmp;

            tmp = t;
            t = oldT - q * t;
            oldT = tmp;
        }

        return new int2(oldS,oldT);
    }
}
