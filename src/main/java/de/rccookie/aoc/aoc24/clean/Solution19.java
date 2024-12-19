package de.rccookie.aoc.aoc24.clean;

import java.util.HashMap;
import java.util.Map;

import de.rccookie.aoc.aoc24.util.FastSolution;

public class Solution19 extends FastSolution {

    private String[] available;
    private String[] patterns;
    private Map<String, Boolean> possibleCache;
    private Map<String, Long> combinationsCache;

    @Override
    public void load() {
        String[] parts = input.split("\n\n");
        available = parts[0].split("\\s*,\\s*");
        patterns = parts[1].split("\n");
    }

    @Override
    public Object task1() {
        possibleCache = new HashMap<>();
        return count(patterns, this::isPossible);
    }

    @Override
    public Object task2() {
        combinationsCache = new HashMap<>();
        return sum(patterns, this::countCombinations);
    }

    private boolean isPossible(String pattern) {
        if(pattern.isEmpty())
            return true;
        Boolean cached = possibleCache.get(pattern);
        if(cached != null)
            return cached;
        for(String a : available) {
            if(pattern.startsWith(a) && isPossible(pattern.substring(a.length()))) {
                possibleCache.put(pattern, true);
                return true;
            }
        }
        possibleCache.put(pattern, false);
        return false;
    }

    private long countCombinations(String pattern) {
        if(pattern.isEmpty())
            return 1;
        Long cached = combinationsCache.get(pattern);
        if(cached != null)
            return cached;

        long combinations = sum(available, a -> pattern.startsWith(a) ? countCombinations(pattern.substring(a.length())) : 0);
        combinationsCache.put(pattern, combinations);
        return combinations;
    }
}
