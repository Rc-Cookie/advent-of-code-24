package de.rccookie.aoc.aoc24.clean;

import java.util.HashMap;

import de.rccookie.aoc.aoc24.util.FastSolution;

public class Solution21 extends FastSolution {

    private static final String[][] PATHS = new String[128][128];
    static {
        PATHS['A']['A'] = "";
        PATHS['A']['^'] = "<";
        PATHS['A']['v'] = "<v";
        PATHS['A']['<'] = "v<<";
        PATHS['A']['>'] = "v";
        PATHS['A']['0'] = "<";
        PATHS['A']['1'] = "^<<";
        PATHS['A']['2'] = "<^";
        PATHS['A']['3'] = "^";
        PATHS['A']['4'] = "^^<<";
        PATHS['A']['5'] = "<^^";
        PATHS['A']['6'] = "^^";
        PATHS['A']['7'] = "^^^<<";
        PATHS['A']['8'] = "<^^^";
        PATHS['A']['9'] = "^^^";

        PATHS['^']['A'] = ">";
        PATHS['^']['^'] = "";
        PATHS['^']['v'] = "v";
        PATHS['^']['<'] = "v<";
        PATHS['^']['>'] = "v>";

        PATHS['v']['A'] = "^>";
        PATHS['v']['^'] = "^";
        PATHS['v']['v'] = "";
        PATHS['v']['<'] = "<";
        PATHS['v']['>'] = ">";

        PATHS['<']['A'] = ">>^";
        PATHS['<']['^'] = ">^";
        PATHS['<']['v'] = ">";
        PATHS['<']['<'] = "";
        PATHS['<']['>'] = ">>";

        PATHS['>']['A'] = "^";
        PATHS['>']['^'] = "<^";
        PATHS['>']['v'] = "<";
        PATHS['>']['<'] = "<<";
        PATHS['>']['>'] = "";

        PATHS['0']['A'] = ">";
        PATHS['0']['0'] = "";
        PATHS['0']['1'] = "^<";
        PATHS['0']['2'] = "^";
        PATHS['0']['3'] = "^>";
        PATHS['0']['4'] = "^^<";
        PATHS['0']['5'] = "^^";
        PATHS['0']['6'] = "^^>";
        PATHS['0']['7'] = "^^^<";
        PATHS['0']['8'] = "^^^";
        PATHS['0']['9'] = "^^^>";

        PATHS['1']['A'] = ">>v";
        PATHS['1']['0'] = ">v";
        PATHS['1']['1'] = "";
        PATHS['1']['2'] = ">";
        PATHS['1']['3'] = ">>";
        PATHS['1']['4'] = "^";
        PATHS['1']['5'] = "^>";
        PATHS['1']['6'] = "^>>";
        PATHS['1']['7'] = "^^";
        PATHS['1']['8'] = "^^>";
        PATHS['1']['9'] = "^^>>";

        PATHS['2']['A'] = ">v";
        PATHS['2']['0'] = "v";
        PATHS['2']['1'] = "<";
        PATHS['2']['2'] = "";
        PATHS['2']['3'] = ">";
        PATHS['2']['4'] = "<^";
        PATHS['2']['5'] = "^";
        PATHS['2']['6'] = "^>";
        PATHS['2']['7'] = "<^^";
        PATHS['2']['8'] = "^^";
        PATHS['2']['9'] = "^^>";

        PATHS['3']['A'] = "v";
        PATHS['3']['0'] = "<v";
        PATHS['3']['1'] = "<<";
        PATHS['3']['2'] = "<";
        PATHS['3']['3'] = "";
        PATHS['3']['4'] = "<<^";
        PATHS['3']['5'] = "<^";
        PATHS['3']['6'] = "^";
        PATHS['3']['7'] = "<<^^";
        PATHS['3']['8'] = "<^^";
        PATHS['3']['9'] = "^^";

        PATHS['4']['A'] = ">>vv";
        PATHS['4']['0'] = ">vv";
        PATHS['4']['1'] = "v";
        PATHS['4']['2'] = "v>";
        PATHS['4']['3'] = "v>>";
        PATHS['4']['4'] = "";
        PATHS['4']['5'] = ">";
        PATHS['4']['6'] = ">>";
        PATHS['4']['7'] = "^";
        PATHS['4']['8'] = "^>";
        PATHS['4']['9'] = "^>>";

        PATHS['5']['A'] = "vv>";
        PATHS['5']['0'] = "vv";
        PATHS['5']['1'] = "<v";
        PATHS['5']['2'] = "v";
        PATHS['5']['3'] = "v>";
        PATHS['5']['4'] = "<";
        PATHS['5']['5'] = "";
        PATHS['5']['6'] = ">";
        PATHS['5']['7'] = "<^";
        PATHS['5']['8'] = "^";
        PATHS['5']['9'] = "^>";

        PATHS['6']['A'] = "vv";
        PATHS['6']['0'] = "<vv";
        PATHS['6']['1'] = "<<v";
        PATHS['6']['2'] = "<v";
        PATHS['6']['3'] = "v";
        PATHS['6']['4'] = "<<";
        PATHS['6']['5'] = "<";
        PATHS['6']['6'] = "";
        PATHS['6']['7'] = "<<^";
        PATHS['6']['8'] = "<^";
        PATHS['6']['9'] = "^";

        PATHS['7']['A'] = ">>vvv";
        PATHS['7']['0'] = ">vvv";
        PATHS['7']['1'] = "vv";
        PATHS['7']['2'] = "vv>";
        PATHS['7']['3'] = "vv>>";
        PATHS['7']['4'] = "v";
        PATHS['7']['5'] = "v>";
        PATHS['7']['6'] = "v>>";
        PATHS['7']['7'] = "";
        PATHS['7']['8'] = ">";
        PATHS['7']['9'] = ">>";

        PATHS['8']['A'] = "vvv>";
        PATHS['8']['0'] = "vvv";
        PATHS['8']['1'] = "<vv";
        PATHS['8']['2'] = "vv";
        PATHS['8']['3'] = "vv>";
        PATHS['8']['4'] = "<v";
        PATHS['8']['5'] = "v";
        PATHS['8']['6'] = "v>";
        PATHS['8']['7'] = "<";
        PATHS['8']['8'] = "";
        PATHS['8']['9'] = ">";

        PATHS['9']['A'] = "vvv";
        PATHS['9']['0'] = "<vvv";
        PATHS['9']['1'] = "<<vv";
        PATHS['9']['2'] = "<vv";
        PATHS['9']['3'] = "vv";
        PATHS['9']['4'] = "<<v";
        PATHS['9']['5'] = "<v";
        PATHS['9']['6'] = "v";
        PATHS['9']['7'] = "<<";
        PATHS['9']['8'] = "<";
        PATHS['9']['9'] = "";
    }

    private HashMap<String, Long> cache;

    @Override
    public Object task1() {
        cache = new HashMap<>();
        return sum(code -> numericPart(code) * proxyLength(code, 3));
    }

    @Override
    public Object task2() {
        cache = new HashMap<>();
        return sum(code -> numericPart(code) * proxyLength(code, 26));
    }

    private long proxyLength(String sequence, int proxyCount) {
        if(proxyCount == 0)
            return sequence.length();

        String key = sequence + proxyCount;
        Long cached = cache.get(key);
        if(cached != null)
            return cached;

        char prev = 'A';
        long len = 0;
        for(char c : sequence.toCharArray())
            len += proxyLength(PATHS[prev][prev = c] + 'A', proxyCount - 1);
        cache.put(key, len);
        return len;
    }

    private static long numericPart(String sequence) {
        return Long.parseLong(sequence.replace("A", ""));
    }
}
