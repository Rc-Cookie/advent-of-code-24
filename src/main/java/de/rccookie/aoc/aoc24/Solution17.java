package de.rccookie.aoc.aoc24;

import de.rccookie.aoc.aoc24.util.FastSolution;

public class Solution17 extends FastSolution {

    private int x,y;
    private int[] program;

    @Override
    public Object task1() {
        int pos = 12;
        int A = chars[pos] - '0';
        char c;
        while((c = chars[++pos]) >= '0' && c <= '9')
            A = 10 * A + c - '0';

        pos += 45;
        x = chars[pos] - '0';

        pos += 2;
        for(;; pos+=4) {
            if(chars[pos] == '1') {
                y = chars[pos + 2] - '0';
                break;
            }
        }

//        long program = execute(A);
//
//        String str = "";
//        for(int i=0; i<16; i++)
//            str = (program >> 3*i & 7) + "," + str;
//        Console.log(str);


        StringBuilder out = new StringBuilder(20);

        int axx;
        do out.append((axx = (A & 0b111) ^ x) ^ y ^ (A >> axx & 0b111)).append(',');
        while((A >>>= 3) != 0);

        out.setLength(out.length() - 1);
        return out;
    }

    @Override
    public Object task2() {
        int pos = indexOf('\n', 0) + 39;

        x = chars[pos + 6] - '0';

        for(int pos1 = pos + 8; ; pos1 += 4) {
            if(chars[pos1] == '1') {
                y = chars[pos1 + 2] - '0';
                break;
            }
        }

        long program = 0;
        for(int len = chars.length; pos < len; pos+=2)
            program = program << 3 | chars[pos] - '0';

        return findInput(program, 0, 0);
    }


    private long findInput(long targetOutput, long input, int it) {
        long min = Long.MAX_VALUE;
        for(int i=0; i<8; i++) {
            long in = input << 3 | i;
            long out = execute(in);
            if(targetOutput == out)
                return in;
            if(targetOutput << 61 - 3*it != out << 61 - 3*it || in == 0)
                continue;
            min = Math.min(min, findInput(targetOutput, in, it + 1));
        }
        return min;
    }

    private long execute(long input) {
        long out = 0;

        long axx;
        do out = out << 3 | ((axx = (input & 0b111) ^ x) ^ y ^ (input >> axx & 0b111));
        while((input >>>= 3) != 0);

        return out;
    }
}
