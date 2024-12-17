package de.rccookie.aoc.aoc24.clean;

import java.util.Arrays;
import java.util.stream.Collectors;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.json.Json;

public class Solution17 extends FastSolution {

    private int[] program;
    private long[] registers;

    @Override
    public void load() {
        String[] parts = input.split("\n\n");
        registers = parseLongs(parts[0]);
        program = parseInts(parts[1]);
    }

    @Override
    public Object task1() {
        return execute(registers[0]);
    }

    @Override
    public Object task2() {
        return findInput(Arrays.stream(program).mapToObj(i->i+"").collect(Collectors.joining(",")), 0);
    }


    private long findInput(String targetOutput, long input) {
        long min = Long.MAX_VALUE;
        for(int i=0; i<8; i++) {
            long in = input << 3 | i;
            String out = execute(in);
            if(targetOutput.equals(out))
                return in;
            if(!targetOutput.endsWith(out) || in == 0)
                continue;
            min = Math.min(min, findInput(targetOutput, in));
        }
        return min;
    }

    private String execute(long input) {
        StringBuilder out = new StringBuilder();

        long A = input, B = 0, C = 0;

        for(int pc = 0; pc < program.length; pc += 2) {
            int arg = program[pc+1];
            long comboArg = switch(arg) {
                case 0,1,2,3 -> arg;
                case 4 -> A;
                case 5 -> B;
                case 6 -> C;
                default -> throw new AssertionError("Illegal combo operand "+ Json.escape(arg));
            };

            switch(program[pc]) {
                case 0 -> { // adv = A division
                    A >>>= comboArg;
                }
                case 1 -> { // bxl = B xor literalS
                    B ^= arg;
                }
                case 2 -> { // bst = B set
                    B = comboArg & 7;
                }
                case 3 -> { // jnz = Jump if not zero
                    if(A != 0)
                        pc = arg - 2;
                }
                case 4 -> { // bxc = B xor C
                    B ^= C;
                }
                case 5 -> { // out = Output
                    out.append(comboArg & 7).append(',');
                }
                case 6 -> { // bdv = Division to B
                    B = A >>> comboArg;
                }
                case 7 -> { // bdv = Division to C
                    C = A >>> comboArg;
                }
                default -> throw new AssertionError("Illegal instruction "+ Json.escape(program[pc]));
            }
        }

        return out.substring(0, Math.max(0, out.length() - 1));
    }
}
