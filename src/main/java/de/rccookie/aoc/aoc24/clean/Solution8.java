package de.rccookie.aoc.aoc24.clean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.rccookie.aoc.aoc24.FastSolution;
import de.rccookie.math.int2;

public class Solution8 extends FastSolution {
    @Override
    public Object task1() {

        Set<int2> antiNodes = new HashSet<>();
        for(List<int2> locations : antennas().values()) {
            for(int2 a : locations) for(int2 b : locations) {
                if(a != b) {
                    int2 ab = int2.between(a,b);
                    addIfInBounds(a.subed(ab), antiNodes);
                    addIfInBounds(b.added(ab), antiNodes);
                }
            }
        }

        return antiNodes.size();
    }

    @Override
    public Object task2() {

        Set<int2> antiNodes = new HashSet<>();
        for(List<int2> locations : antennas().values()) {
            for(int2 a : locations) for(int2 b : locations) {
                if(a != b) {
                    int2 ab = int2.between(a,b);
                    addAllIfInBounds(a, ab.negated(), antiNodes);
                    addAllIfInBounds(b, ab, antiNodes);
                }
            }
        }

        return antiNodes.size();
    }

    private Map<Character, List<int2>> antennas() {
        Map<Character, List<int2>> antennas = new HashMap<>();
        for(int2 pos : size)
            if(charAt(pos) != '.')
                antennas.computeIfAbsent(charAt(pos), $ -> new ArrayList<>()).add(pos);
        return antennas;
    }

    private void addIfInBounds(int2 pos, Set<int2> to) {
        if(pos.geq(int2.zero) && pos.less(size))
            to.add(pos);
    }

    private void addAllIfInBounds(int2 pos, int2 offset, Set<int2> to) {
        for(; pos.geq(int2.zero) && pos.less(size); pos = pos.added(offset))
            to.add(pos);
    }
}
