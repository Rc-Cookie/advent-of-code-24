package de.rccookie.aoc.aoc24.util;

import java.util.Arrays;

public final class LookupIntHeap {

    public final int[] data;
    public final int[] cost;
    public final int[] lookup;
    public int size = 0;

    public LookupIntHeap(int range) {
        if(range <= 0)
            throw new IllegalArgumentException("Range must be > 0");
        data = new int[range];
        cost = new int[range];
        lookup = new int[range];
        Arrays.fill(lookup, -1);
    }

    @Override
    public String toString() {
        if(size == 0)
            return "[]";
        if(size == 1)
            return "["+data[0]+"]";
        if(size == 2)
            return "["+data[0]+", "+data[1]+"]";
        return "IntHeap[min: "+data[0]+", size: "+size+", contents: "+Arrays.toString(Arrays.copyOf(data, size))+"]";
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void appendGreatest(int x) {
        assert x >= 0 && x < data.length && Arrays.stream(data, 0, size).allMatch(y -> cost[y] < cost[x]);

        final int s = size;
        lookup[x] = s;
        data[s] = x;
        size = s + 1;
    }

    public void enqueue(int x) {
        assert x >= 0 && x < lookup.length && !contains(x);

        final int s = size;
        rise(s, x, data, cost, lookup);
        size = s + 1;
    }

    public int peek() {
        assert size > 0;
        return data[0];
    }

    public int dequeue() {
        assert size > 0;

        final int[] d,l;
        final int x, s, move;
        x = (d = data)[0];
        (l = lookup)[x] = -1;
        move = d[s = --size];
        sink(0, move, d, cost, l, s);
        return x;
    }

    public boolean contains(int x) {
        assert x >= 0 && x < lookup.length;
        return lookup[x] >= 0;
    }

    public void updateIncreased(int x) {
        assert contains(x);
        final int[] l = lookup;
        sink(l[x], x, data, cost, l, size);
    }

    public void updateDecreased(int x) {
        assert contains(x);
        final int[] l = lookup;
        rise(l[x], x, data, cost, l);
    }

    private static void rise(int i, int x, int[] data, int[] cost, int[] lookup) {
        int c = cost[x];
        while(i > 0) {
            int parent = (i-1) >>> 1;
            int p = data[parent];
            if(c >= cost[p]) break;
            data[i] = p;
            lookup[p] = i;
            i = parent;
        }
        data[i] = x;
        lookup[x] = i;
    }

    private static void sink(int i, int x, int[] data, int[] cost, int[] lookup, int size) {
        int h = size >>> 1;
        int xc = cost[x];
        while(i < h) {
            int child = (i << 1) + 1;
            int right = child + 1;
            int c = data[child];
            int cc = cost[c];
            if(right < size && cc > cost[data[right]])
                cc = cost[c = data[child = right]];
            if(xc <= cc)
                break;
            data[i] = c;
            lookup[c] = i;
            i = child;
        }
        data[i] = x;
        lookup[x] = i;
    }
}
