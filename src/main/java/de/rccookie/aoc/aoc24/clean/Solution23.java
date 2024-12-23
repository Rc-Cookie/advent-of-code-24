package de.rccookie.aoc.aoc24.clean;

import java.util.Arrays;
import java.util.stream.Collectors;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.aoc.aoc24.util.IntArrayList;
import de.rccookie.aoc.aoc24.util.IntSet;
import de.rccookie.graph.SimpleIntMatrixGraph;

public class Solution23 extends FastSolution {

    private static final int T = 't' - 'a';
    private static final int LIMIT = encode("zz") + 1;

    private SimpleIntMatrixGraph graph;

    @Override
    public void load() {
        graph = new SimpleIntMatrixGraph(LIMIT);
        for(String l : lines) {
            int m = encode(l.substring(0,2)), n = encode(l.substring(3,5));
            graph.connect(m,n);
            graph.connect(n,m);
        }
    }

    @Override
    public Object task1() {
        int count = 0;
        for(int i=encode("ta"); i<encode("ua"); i++) {
            if(!graph.contains(i)) continue;
            for(int j : graph.adj(i)) {
                if(!graph.connected(i,j) || (j>>5 == T && j<=i)) continue;
                for(int k : graph.adj(j))
                    if(j < k && !(k>>5 == T && k<=i) && graph.connected(j,k) && graph.connected(k,i))
                        count++;
            }
        }
        return count;
    }

    @Override
    public Object task2() {
        IntArrayList clique = findMaxClique(new IntArrayList(), new IntSet(LIMIT), -1);
        return Arrays.stream(clique.toArray()).mapToObj(Solution23::decode).collect(Collectors.joining(","));
    }

    private IntArrayList findMaxClique(IntArrayList current, IntSet visited, int prev) {
        IntArrayList max = current;
        outer: for(int i=prev+1; i<LIMIT; i++) {
            if(visited.is(i) || !graph.contains(i)) continue;
            for(int j : current)
                if(!graph.connected(i,j))
                    continue outer;

            current.push(i);
            IntArrayList maxInner = findMaxClique(current, visited, i);
            current.pop();

            if(max.size < maxInner.size) {
                visited.set(i);
                max = maxInner;
            }
        }
        return max == current ? max.clone() : max;
    }

    private static int encode(String str) {
        return (str.charAt(0) - 'a' << 5) | (str.charAt(1) - 'a');
    }

    private static String decode(int x) {
        return "" + (char) ((x >> 5) + 'a') + (char) ((x & 31) + 'a');
    }
}
