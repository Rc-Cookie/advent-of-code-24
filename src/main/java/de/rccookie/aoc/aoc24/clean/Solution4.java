package de.rccookie.aoc.aoc24.clean;

import de.rccookie.aoc.aoc24.FastSolution;
import de.rccookie.math.constInt2;
import de.rccookie.math.int2;

public class Solution4 extends FastSolution {

    public static final constInt2 BOT_RIGHT = int2.one;
    public static final constInt2 BOT_LEFT = new int2(-1,1);

    @Override
    public Object task1() {
        int count = 0;
        for(int2 pos : new int2(charTable[0].length, charTable.length)) {
            for(int2 step : new int2(3, 3)) {
                try {
                    if(check("XMAS", pos, step.subed(1)))
                        count++;
                } catch(IndexOutOfBoundsException ignored) { }
            }
        }
        return count;
    }

    @Override
    public Object task2() {
        int count = 0;
        for(int2 pos : new int2(charTable[0].length - 2, charTable.length - 2)) {
            if(
                    (check("MAS", pos, BOT_RIGHT) || check("SAM", pos, BOT_RIGHT)) &&
                    (check("MAS", pos.added(2, 0), BOT_LEFT) || check("SAM", pos.added(2, 0), BOT_LEFT))
            )
                count++;
        }
        return count;
    }

    private boolean check(String word, constInt2 start, constInt2 step) {
        for(int i=0; i<word.length(); i++)
            if(charTable[start.y() + i * step.y()][start.x() + i * step.x()] != word.charAt(i))
                return false;
        return true;
    }
}
