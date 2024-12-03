package de.rccookie.aoc.aoc24.clean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.rccookie.aoc.aoc24.FastSolution;

public class Solution3 extends FastSolution {

    @Override
    public Object task1() {
        Pattern p = Pattern.compile("mul\\((\\d+),(\\d+)\\)", Pattern.MULTILINE);
        Matcher m = p.matcher(input);
        long total = 0;
        while(m.find())
            total += Long.parseLong(m.group(1)) * Long.parseLong(m.group(2));
        return total;
    }

    @Override
    public Object task2() {
        Pattern p = Pattern.compile("do\\(\\)|don't\\(\\)|mul\\((\\d+),(\\d+)\\)", Pattern.MULTILINE);
        Matcher m = p.matcher(input);
        long total = 0;
        long factor = 1;
        while(m.find()) {
            switch(m.group()) {
                case "do()" -> factor = 1;
                case "don't()" -> factor = 0;
                default -> total += factor * Long.parseLong(m.group(1)) * Long.parseLong(m.group(2));
            }
        }
        return total;
    }
}
