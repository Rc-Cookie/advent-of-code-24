package de.rccookie.aoc.aoc24.clean;

import java.util.function.ToLongBiFunction;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.graph.Graph;
import de.rccookie.graph.Graphs;
import de.rccookie.math.int2;

public class Solution12 extends FastSolution {

    @Override
    public Object task1() {
        return fencePrice((c,v) -> {
            int border = 0;
            if(!c.contains(v.added( 1, 0))) border++;
            if(!c.contains(v.added(-1, 0))) border++;
            if(!c.contains(v.added( 0, 1))) border++;
            if(!c.contains(v.added( 0,-1))) border++;
            return border;
        });
    }

    @Override
    public Object task2() {
        return fencePrice((c,v) -> {
            boolean tl = c.contains(v.added(-1,-1));
            boolean t =  c.contains(v.added( 0,-1));
            boolean tr = c.contains(v.added( 1,-1));
            boolean l =  c.contains(v.added(-1, 0));
            boolean r =  c.contains(v.added( 1, 0));
            boolean bl = c.contains(v.added(-1, 1));
            boolean b =  c.contains(v.added( 0, 1));
            boolean br = c.contains(v.added( 1, 1));

            int corners = 0;
            if((!l && !t) || (l && t && !tl)) corners++;
            if((!r && !t) || (r && t && !tr)) corners++;
            if((!l && !b) || (l && b && !bl)) corners++;
            if((!r && !b) || (r && b && !br)) corners++;
            return corners;
        });
    }

    private long fencePrice(ToLongBiFunction<Graph<int2,?>, int2> tileBorderCost) {
        Graph<int2,?> g = grid.graphFromChars(false, (a, b) -> a == b);
        return sum(Graphs.components(g), c -> sum(c, p -> tileBorderCost.applyAsLong(c,p)) * c.size());
    }
}
