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
Duration (average of 100 runs): 22.08136ms
┌────────╥─────────┬──────────┐
│        ║  Task 1 │   Task 2 │
╞════════╬═════════╪══════════╡
│  Day 1 ║ 0.272ms │  0.135ms │
├────────╫─────────┼──────────┤
│  Day 2 ║ 0.065ms │  0.135ms │
├────────╫─────────┼──────────┤
│  Day 3 ║ 0.042ms │  0.015ms │
├────────╫─────────┼──────────┤
│  Day 4 ║ 0.225ms │  0.017ms │
├────────╫─────────┼──────────┤
│  Day 5 ║ 0.046ms │  0.068ms │
├────────╫─────────┼──────────┤
│  Day 6 ║ 0.037ms │ 13.539ms │
├────────╫─────────┼──────────┤
│  Day 7 ║ 0.273ms │  5.738ms │
├────────╫─────────┼──────────┤
│  Day 8 ║ 0.028ms │  0.043ms │
├────────╫─────────┼──────────┤
│  Day 9 ║ 0.055ms │  0.389ms │
├────────╫─────────┼──────────┤
│ Day 10 ║ 0.146ms │  0.087ms │
├────────╫─────────┼──────────┤
│ Day 11 ║ 0.013ms │  0.011ms │
├────────╫─────────┼──────────┤
│ Day 12 ║ 0.315ms │  0.360ms │
├────────╫─────────┼──────────┤
│ Day 13 ║ 0.013ms │  0.014ms │
└────────╨─────────┴──────────┘
```
