package de.rccookie.aoc.aoc24.clean;

import java.util.ArrayList;
import java.util.List;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.math.intN;

public class Solution25 extends FastSolution {
    @Override
    public Object task1() {
        List<int[]> keys = new ArrayList<>();
        List<int[]> locks = new ArrayList<>();

        for(String s : input.split("\n\n")) {
            int[] obj = new int[5];
            for(int i=0; i<5; i++)
                for(int j=1; j<=5; j++)
                    if(s.charAt(j * 6 + i) == '#')
                        obj[i]++;
            if(s.startsWith("#"))
                locks.add(obj);
            else keys.add(obj);
        }

        intN vec5 = new intN(5,5);

        return sum(keys, k -> count(locks, l -> intN.ref(k).added(intN.ref(l)).leq(vec5)));
    }

    @Override
    public Object task2() {
        return "";
    }
}
