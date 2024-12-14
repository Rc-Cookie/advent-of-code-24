package de.rccookie.aoc.aoc24.clean;

import java.util.function.ToLongFunction;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.math.int2;

public class Solution12 extends FastSolution {

    @Override
    public Object task1() {
        return fencePrice((v) -> {
            int border = 0;
            char ch = grid.charAt(v);
            if(ch != grid.charAt(v.added( 1, 0))) border++;
            if(ch != grid.charAt(v.added(-1, 0))) border++;
            if(ch != grid.charAt(v.added( 0, 1))) border++;
            if(ch != grid.charAt(v.added( 0,-1))) border++;
            return border;
        });
    }

    @Override
    public Object task2() {
        return fencePrice((v) -> {

            char ch = grid.charAt(v);
            boolean tl = ch == grid.charAt(v.added(-1,-1));
            boolean t =  ch == grid.charAt(v.added( 0,-1));
            boolean tr = ch == grid.charAt(v.added( 1,-1));
            boolean l =  ch == grid.charAt(v.added(-1, 0));
            boolean r =  ch == grid.charAt(v.added( 1, 0));
            boolean bl = ch == grid.charAt(v.added(-1, 1));
            boolean b =  ch == grid.charAt(v.added( 0, 1));
            boolean br = ch == grid.charAt(v.added( 1, 1));

            int corners = 0;
            if((!l && !t) || (l && t && !tl)) corners++;
            if((!r && !t) || (r && t && !tr)) corners++;
            if((!l && !b) || (l && b && !bl)) corners++;
            if((!r && !b) || (r && b && !br)) corners++;
            return corners;
        });
    }

    private long fencePrice(ToLongFunction<int2> tileBorderCost) {
        return grid.sum(p -> grid.marked(p) ? 0 : sum(grid.floodFill(p).peek(grid::mark), tileBorderCost) * grid.floodFill(p).count());
    }
}
