# Advent of Code 2024

My solutions to (some of) the [Advent of Code](https://adventofcode.com) puzzles of 2024.

I am using my [Advent-of-Code-Runner](https://github.com/Rc-Cookie/advent-of-code-runner) library
which automatically downloads input, submits output and generally simplifies development with some
commonly used helper functions. Feel free to check it out yourself if you are using Java!

My main solutions are (mostly) optimized for performance.
Thus, they avoid using things like `String.split()` or creating substrings at all, resulting in some 'interesting' looking parsing methods.
Additionally, I make use of some bit shift magic for some puzzles.
All of this doesn't really improve readability and also increases size, thus I have started another 'clean' solution, which has its own config file and solution classes.
They are generally a lot easier to read and use similar algorithms, but are quite a bit slower than the code spagetti.

## Performance

Some performance stats of the optimized solutions, which have been obtained using `--all --warmup 100 --repeat 100` on my AMD Ryzen 9 5950x:

```
Duration (average of 100 runs): 15.437151ms
┌────────╥─────────┬─────────┐
│        ║  Task 1 │  Task 2 │
╞════════╬═════════╪═════════╡
│  Day 1 ║ 0.200ms │ 0.065ms │
├────────╫─────────┼─────────┤
│  Day 2 ║ 0.043ms │ 0.115ms │
├────────╫─────────┼─────────┤
│  Day 3 ║ 0.019ms │ 0.015ms │
├────────╫─────────┼─────────┤
│  Day 4 ║ 0.163ms │ 0.017ms │
├────────╫─────────┼─────────┤
│  Day 5 ║ 0.016ms │ 0.076ms │
├────────╫─────────┼─────────┤
│  Day 6 ║ 0.025ms │ 7.995ms │
├────────╫─────────┼─────────┤
│  Day 7 ║ 0.224ms │ 4.792ms │
├────────╫─────────┼─────────┤
│  Day 8 ║ 0.021ms │ 0.026ms │
├────────╫─────────┼─────────┤
│  Day 9 ║ 0.043ms │ 0.333ms │
├────────╫─────────┼─────────┤
│ Day 10 ║ 0.147ms │ 0.075ms │
├────────╫─────────┼─────────┤
│ Day 11 ║ 0.006ms │ 0.005ms │
├────────╫─────────┼─────────┤
│ Day 12 ║ 0.268ms │ 0.310ms │
├────────╫─────────┼─────────┤
│ Day 13 ║ 0.012ms │ 0.005ms │
├────────╫─────────┼─────────┤
│ Day 14 ║ 0.008ms │ 0.138ms │
├────────╫─────────┼─────────┤
│ Day 15 ║ 0.054ms │ 0.220ms │
└────────╨─────────┴─────────┘
```
