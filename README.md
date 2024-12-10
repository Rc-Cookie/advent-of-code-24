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
Duration (average of 100 runs): 21.210959ms
┌───────╥─────────┬──────────┐
│       ║  Task 1 │   Task 2 │
╞═══════╬═════════╪══════════╡
│ Day 1 ║ 0.272ms │  0.114ms │
├───────╫─────────┼──────────┤
│ Day 2 ║ 0.091ms │  0.193ms │
├───────╫─────────┼──────────┤
│ Day 3 ║ 0.023ms │  0.016ms │
├───────╫─────────┼──────────┤
│ Day 4 ║ 0.260ms │  0.017ms │
├───────╫─────────┼──────────┤
│ Day 5 ║ 0.062ms │  0.069ms │
├───────╫─────────┼──────────┤
│ Day 6 ║ 0.052ms │ 13.418ms │
├───────╫─────────┼──────────┤
│ Day 7 ║ 0.281ms │  5.805ms │
├───────╫─────────┼──────────┤
│ Day 8 ║ 0.030ms │  0.038ms │
├───────╫─────────┼──────────┤
│ Day 9 ║ 0.029ms │  0.442ms │
└───────╨─────────┴──────────┘
```
