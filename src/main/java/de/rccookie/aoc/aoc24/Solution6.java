package de.rccookie.aoc.aoc24;

import java.util.Arrays;

public class Solution6 extends FastSolution {

    /**
     * Lookup for movement in x direction for each direction (0=up, 1=left, 2=down, 3=right).
     */
    private static final int[] DIRS_X = { 0, -1, 0, 1 };
    /**
     * Lookup for movement in y direction for each direction (0=up, 1=left, 2=down, 3=right).
     */
    private static final int[] DIRS_Y = { -1, 0, 1, 0 };

    @Override
    public Object task1() {

        // Part 1 is pretty much exactly the same as the first step of part 2. The only difference is
        // that we set visited fields to 'X' instead of the direction, because this means we can test
        // for equality with 'X', rather than an inequality. We also increment a counter we do so, to
        // count the number of fields visited.

        // In part 1 we actually need to make a copy of the input, since we modify it, which would break
        // re-running the task for benchmarking purposes. I guess you could also just set all the Xs
        // back to '.' afterward, which might be faster.
        char[] chars = this.chars.clone();

        int len = chars.length;
        int w = indexOf('\n', 0), w1 = w+1;

        int pos = indexOf('^', 0);
        // Mark the starting position with 'X', such that we won't miscount in case we loop back over it
        chars[pos] = 'X';

        int dir = 0;
        // Counter for the number of visited cells. Start with 1 for the starting position.
        int count = 1;
        pathLoop: while(true) {

            switch(dir) {
                case 0:
                    pos -= w1;
                    if(pos < 0)
                        break pathLoop;
                    if(chars[pos] == '#') {
                        pos += w1;
                        dir = 3;
                        continue;
                    }
                    break;
                case 1:
                    --pos;
                    if(pos == -1 || chars[pos] == '\n')
                        break pathLoop;
                    if(chars[pos] == '#') {
                        ++pos;
                        dir = 0;
                        continue;
                    }
                    break;
                case 2:
                    pos += w1;
                    if(pos >= len)
                        break pathLoop;
                    if(chars[pos] == '#') {
                        pos -= w1;
                        dir = 1;
                        continue;
                    }
                    break;
                case 3:
                    ++pos;
                    if(pos == len || chars[pos] == '\n')
                        break pathLoop;
                    if(chars[pos] == '#') {
                        --pos;
                        dir = 2;
                        continue;
                    }
                    break;
                default:
                    throw new AssertionError();
            }
            if(chars[pos] != 'X') {
                chars[pos] = 'X';
                count++;
            }
        }

        return count;
    }


    @Override
    public Object task2() {

        // Cache the length, memory locality
        int len = chars.length;
        // Width of the grid, and width of a line (which is the width of the grid + '\n')
        int w = indexOf('\n', 0), w1 = w+1;

        // Find the start position
        int start = indexOf('^', 0);
        // Cursor for keeping track of the current position. We don't use explicit x and y
        // coordinates but rather use the index in the raw string directly. To read a value
        // from the array, we otherwise always had to recompute the index, this way we avoid
        // this extra effort. Moving left and right corresponds to decrementing /
        // incrementing the cursor, and moving up and down by decrementing / incrementing by
        // the length of a line (=w1).
        int pos = start;


        // The current movement direction: 0=up, 1=left, 2=down, 3=right
        int dir = 0;

        // First evaluate the regular (unobstructed) path of the guard (identical to part 1).
        // We then only need to consider obstacles which are positioned on the regular path.
        pathLoop: while(true) {

            // In each iteration:
            //  - Move cursor to the next cell in the current movement direction
            //  - Bounds check (running out of the area -> done)
            //  - Check for obstacle; if there is one:
            //    - Move back one cell
            //    - Rotate 90° degrees clockwise
            //
            // You could also write this out as a combined operation depending on 'dir', but
            // using a switch statement is faster:
            //  - We only need to do a bounds check in the direction that we just moved
            //  - Either DIRS_X[dir] or DIRS_Y[dir] is always 0, which means that we make
            //    an unnecessary addition (and possibly multiplication)
            //  - Switch statement has very low overhead
            switch(dir) {
                // Currently moving up
                case 0 -> {
                    // Moving up by 1 = move up 1 line. Each line consists of w1 characters,
                    // thus decrement pos by w1
                    pos -= w1;
                    // Bounds check - the only thing that can go wrong is that we run out of
                    // the top, i.e. into negative indices.
                    if(pos < 0)
                        // Reached the outside -> done following the path
                        break pathLoop;
                    // Check for obstacle at moved position
                    if(chars[pos] == '#') {
                        // Move back to previous position
                        pos += w1;
                        // We were facing up -> rotate right -> now facing right = 3
                        dir = 3;
                        // Continue is needed to not unnecessarily repeat the check whether
                        // this cell is not yet marked - it definitely already is
                        continue;
                    }
                }
                // Currently moving left
                case 1 -> {
                    // Moving left = decrement pos by 1
                    --pos;
                    // We have to differentiate between running out of the first (-> negative index)
                    // and running out of a subsequent line (-> wrapped around to EOL of previous line).
                    // A padding with '\n' in front and back would be quite convenient, but copying the
                    // whole buffer just for that is not worth it.
                    if(pos == -1 || chars[pos] == '\n')
                        break pathLoop;
                    if(chars[pos] == '#') {
                        ++pos;
                        dir = 0;
                        continue;
                    }
                }
                // Currently moving down
                case 2 -> {
                    pos += w1;
                    if(pos >= len)
                        break pathLoop;
                    if(chars[pos] == '#') {
                        pos -= w1;
                        dir = 1;
                        continue;
                    }
                }
                // Currently moving right
                case 3 -> {
                    ++pos;
                    if(pos == len || chars[pos] == '\n')
                        break pathLoop;
                    if(chars[pos] == '#') {
                        --pos;
                        dir = 2;
                        continue;
                    }
                }
                default -> throw new AssertionError();
            }
            // If we just moved, we get here and check whether we are visiting this cell
            // for the first time. If we are moved here for the first time, we mark the
            // cell as part of the default path. We set it to the current direction, this
            // allows us to later start simulating the path right from the point where
            // the obstacle is hit, skipping the part before which is identical to the
            // default path. Since all initial characters are >3, we can also use this to
            // distinguish visited cells and don't need to allocate new memory. Later in
            // the algorithm the input string will actually be restored automatically.
            if(chars[pos] > 3)
                chars[pos] = (char) dir;
        }

        // Even if the default path loops back over the initial position, we don't want
        // to try putting an obstacle there (since it's not allowed).
        chars[start] = '^';


        // To detect when we found a loop we essentially use a 3D boolean for x-coordinate
        // of the cell, the y-coordinate of the cell and the direction in which we visited
        // it. X and y coordinates are already naturally flattened into one array by the
        // nature of using the string index as cursor. Then the directions are also flattened
        // into a 1D array, where 4 subsequent entries are the 4 directions of the same cell.
        // Flattening makes the code faster because of memory locality, and because in Java
        // arrays are objects, so having many small objects takes up more memory. Also, reading
        // a cell in a 3D array consists of 3 dereferences, whereas a flattened 1D array only
        // requires one.
        //
        // Finally, we replace the boolean array with a long array, of which each bit represents
        // one entry. Booleans take up a whole byte in Java, so using longs reduces the required
        // memory by ~87%. Long arrays are from testing faster than longer byte arrays.
        //
        // We actually have some unused gaps in the array from the newlines in the input string.
        // We could calculate a more efficient index to reduce the array size slightly, but
        // that defeats the whole purpose of using the index in the string as cursor ("pos") to
        // avoid having to do additional arithmetic for an array lookup. Testing shows that this
        // slight overallocation is faster than using explicit x and y (which by nature don't
        // have this problem with newlines).
        long[] dejavu = new long[(len*4+63)/64];

        // Counting variable for found loops
        int count = 0;
        int i;

        // Iterate over all cells. If we find one which is part of the default path, we simulate
        // putting an obstacle there and test whether the remaining path leads to a loop. Flattening
        // The loop from explicit x and y is slightly slower than just skipping newline entries.
        for(int o=0; o<len; o++) {

            // Check whether this cell is part of the default path, which would be indicated by
            // the string containing the direction (0, 1, 2 or 3) from which we first got there.
            // This also skips the newlines.
            if(chars[o] > 3) continue;

            // We don't need to start from the beginning again, but rather directly before the
            // obstacle that we just placed - the path there will not be affected. We may have to
            // trace at least some part of this part prefix if it is part of the loop, but if it is
            // not, we have saved some computation time (and not really wasted any in the other case).

            // Initialize position and direction to the position of the obstacle and the direction
            // in which we travelled there on the default path.
            pos = o;
            dir = chars[pos];
            // Move one cell back (currently we were on the obstacle) (this can't be out of bounds)
            pos -= DIRS_X[dir] + w1 * DIRS_Y[dir];
            // Rotate 90° to the right (because we just hit that obstacle)
            dir = dir - 1 & 3;

            // Temporarily mark this cell as an obstacle for the duration of the simulation
            chars[o] = '#';
            // Reset the buffer for registering loops
            Arrays.fill(dejavu, 0);

            // Now walk along the path until
            //  - we find a cell which we have already visited in the same direction (-> loop)
            //  - we run out of bounds (-> no loop)
            pathLoop: while(true) {

                // Calculate the index of the relevant bit in the dejavu array (pos<<2 = pos*4).
                i = (pos << 2) + dir;
                // Read that bit from the array. If it is set, we found a loop -> increment counter
                // and stop simulation
                if(gbi(dejavu, i)) {
                    count++;
                    break;
                }
                // We haven't been there yet - now we have been, thus set that bit in the dejavu array
                sbi(dejavu, i);

                // Move one cell or one 90° rotation based; identical to the movement earlier. If we
                // run out of bounds, cancel the simulation without incrementing the counter.
                switch(dir) {
                    case 0 -> {
                        pos -= w1;
                        if(pos < 0)
                            break pathLoop;
                        if(chars[pos] == '#') {
                            pos += w1;
                            dir = 3;
                        }
                    }
                    case 1 -> {
                        --pos;
                        if(pos == -1 || chars[pos] == '\n')
                            break pathLoop;
                        if(chars[pos] == '#') {
                            ++pos;
                            dir = 0;
                        }
                    }
                    case 2 -> {
                        pos += w1;
                        if(pos >= len)
                            break pathLoop;
                        if(chars[pos] == '#') {
                            pos -= w1;
                            dir = 1;
                        }
                    }
                    case 3 -> {
                        ++pos;
                        if(pos == len || chars[pos] == '\n')
                            break pathLoop;
                        if(chars[pos] == '#') {
                            --pos;
                            dir = 2;
                        }
                    }
                    default -> throw new AssertionError();
                }
            }

            // After the simulation, remove the additional obstacle. This actually automatically
            // restores the string to its initial form.
            chars[o] = '.';
        }

        // Once all cells have been tested, return the accumulated count
        return count;
    }
}
