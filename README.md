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
Duration (average of 100 runs): 29.236773ms
┌────────╥─────────┬──────────┐
│        ║  Task 1 │   Task 2 │
╞════════╬═════════╪══════════╡
│  Day 1 ║ 0.371ms │  0.204ms │
├────────╫─────────┼──────────┤
│  Day 2 ║ 0.139ms │  0.237ms │
├────────╫─────────┼──────────┤
│  Day 3 ║ 0.032ms │  0.021ms │
├────────╫─────────┼──────────┤
│  Day 4 ║ 0.330ms │  0.023ms │
├────────╫─────────┼──────────┤
│  Day 5 ║ 0.085ms │  0.133ms │
├────────╫─────────┼──────────┤
│  Day 6 ║ 0.061ms │ 18.699ms │
├────────╫─────────┼──────────┤
│  Day 7 ║ 0.449ms │  7.372ms │
├────────╫─────────┼──────────┤
│  Day 8 ║ 0.040ms │  0.049ms │
├────────╫─────────┼──────────┤
│  Day 9 ║ 0.076ms │  0.561ms │
├────────╫─────────┼──────────┤
│ Day 10 ║ 0.204ms │  0.119ms │
├────────╫─────────┼──────────┤
│ Day 11 ║ 0.015ms │  0.016ms │
└────────╨─────────┴──────────┘
```
