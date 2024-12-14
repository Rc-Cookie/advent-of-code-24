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
Duration (average of 100 runs): 21.352875ms
┌────────╥─────────┬──────────┐
│        ║  Task 1 │   Task 2 │
╞════════╬═════════╪══════════╡
│  Day 1 ║ 0.291ms │  0.136ms │
├────────╫─────────┼──────────┤
│  Day 2 ║ 0.055ms │  0.135ms │
├────────╫─────────┼──────────┤
│  Day 3 ║ 0.028ms │  0.017ms │
├────────╫─────────┼──────────┤
│  Day 4 ║ 0.180ms │  0.026ms │
├────────╫─────────┼──────────┤
│  Day 5 ║ 0.055ms │  0.090ms │
├────────╫─────────┼──────────┤
│  Day 6 ║ 0.021ms │ 12.727ms │
├────────╫─────────┼──────────┤
│  Day 7 ║ 0.253ms │  5.716ms │
├────────╫─────────┼──────────┤
│  Day 8 ║ 0.028ms │  0.026ms │
├────────╫─────────┼──────────┤
│  Day 9 ║ 0.029ms │  0.392ms │
├────────╫─────────┼──────────┤
│ Day 10 ║ 0.140ms │  0.088ms │
├────────╫─────────┼──────────┤
│ Day 11 ║ 0.007ms │  0.008ms │
├────────╫─────────┼──────────┤
│ Day 12 ║ 0.316ms │  0.366ms │
├────────╫─────────┼──────────┤
│ Day 13 ║ 0.016ms │  0.024ms │
├────────╫─────────┼──────────┤
│ Day 14 ║ 0.010ms │  0.175ms │
└────────╨─────────┴──────────┘
```
