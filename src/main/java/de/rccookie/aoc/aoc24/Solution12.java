package de.rccookie.aoc.aoc24;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.aoc.aoc24.util.IntArrayDeque;

public class Solution12 extends FastSolution {

    @Override
    public Object task1() {

        char[] chars = this.chars;
        int w = eol(0), w1 = w + 1;
        int len = (chars.length + 1) / w1 * w1 - 1;

        long total = 0;

        long[] dejavu = new long[(chars.length + 63) / 64];
        IntArrayDeque todo = new IntArrayDeque();

        for(int i=0; i<len; i++) {
            char c;
            if((c = chars[i]) == '\n' || gsbi(dejavu, i))
                continue;

            int size = 0, border = 0;

            todo.enqueue(i);
            while(!todo.isEmpty()) {
                int p = todo.dequeue();
                ++size;

                int adj;
                if((adj = p-1) < 0 || chars[adj] != c)
                    border++;
                else if(!gsbi(dejavu, adj))
                    todo.enqueue(adj);
                if((adj = p+1) >= len || chars[adj] != c)
                    border++;
                else if(!gsbi(dejavu, adj))
                    todo.enqueue(adj);
                if((adj = p-w1) < 0 || chars[adj] != c)
                    border++;
                else if(!gsbi(dejavu, adj))
                    todo.enqueue(adj);
                if((adj = p+w1) >= len || chars[adj] != c)
                    border++;
                else if(!gsbi(dejavu, adj))
                    todo.enqueue(adj);
            }

            total += (long) size * border;
        }

        return total;
    }

    @Override
    public Object task2() {

        char[] chars = this.chars;
        int w = eol(0), w1 = w + 1, w2 = w1 + 1;
        int len = (chars.length + 1) / w1 * w1 - 1;

        long total = 0;

        long[] dejavu = new long[(chars.length + 63) / 64];
        IntArrayDeque todo = new IntArrayDeque();

        for(int i=0; i<len; i++) {
            char c;
            if((c = chars[i]) == '\n' || gsbi(dejavu, i))
                continue;

            int size = 0, corners = 0;

            todo.enqueue(i);
            while(todo.head != todo.tail) {
                int p = todo.dequeue();
                ++size;

                boolean nl = false, nr = false, nt = false, nb = false;

                int adj;
                if((adj = p-1) < 0 || chars[adj] != c)
                    nl = true;
                else if(!gsbi(dejavu, adj))
                    todo.enqueue(adj);
                if((adj = p+1) >= len || chars[adj] != c)
                    nr = true;
                else if(!gsbi(dejavu, adj))
                    todo.enqueue(adj);
                if((adj = p-w1) < 0 || chars[adj] != c)
                    nt = true;
                else if(!gsbi(dejavu, adj))
                    todo.enqueue(adj);
                if((adj = p+w1) >= len || chars[adj] != c)
                    nb = true;
                else if(!gsbi(dejavu, adj))
                    todo.enqueue(adj);

                if(nl == nt && (nl || chars[p-w2] != c)) corners++;
                if(nr == nt && (nr || chars[p-w] != c)) corners++;
                if(nl == nb && (nl || chars[p+w] != c)) corners++;
                if(nr == nb && (nr || chars[p+w2] != c)) corners++;
            }

            total += (long) size * corners;
        }

        return total;
    }
}
