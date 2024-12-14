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
Duration (average of 100 runs): 15.647935ms
┌────────╥─────────┬─────────┐
│        ║  Task 1 │  Task 2 │
╞════════╬═════════╪═════════╡
│  Day 1 ║ 0.238ms │ 0.071ms │
├────────╫─────────┼─────────┤
│  Day 2 ║ 0.044ms │ 0.127ms │
├────────╫─────────┼─────────┤
│  Day 3 ║ 0.022ms │ 0.017ms │
├────────╫─────────┼─────────┤
│  Day 4 ║ 0.164ms │ 0.015ms │
├────────╫─────────┼─────────┤
│  Day 5 ║ 0.030ms │ 0.051ms │
├────────╫─────────┼─────────┤
│  Day 6 ║ 0.026ms │ 8.161ms │
├────────╫─────────┼─────────┤
│  Day 7 ║ 0.230ms │ 4.991ms │
├────────╫─────────┼─────────┤
│  Day 8 ║ 0.025ms │ 0.030ms │
├────────╫─────────┼─────────┤
│  Day 9 ║ 0.044ms │ 0.345ms │
├────────╫─────────┼─────────┤
│ Day 10 ║ 0.152ms │ 0.076ms │
├────────╫─────────┼─────────┤
│ Day 11 ║ 0.010ms │ 0.012ms │
├────────╫─────────┼─────────┤
│ Day 12 ║ 0.275ms │ 0.314ms │
├────────╫─────────┼─────────┤
│ Day 13 ║ 0.015ms │ 0.015ms │
├────────╫─────────┼─────────┤
│ Day 14 ║ 0.009ms │ 0.137ms │
└────────╨─────────┴─────────┘
```
