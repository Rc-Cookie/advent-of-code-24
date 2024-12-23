package de.rccookie.aoc.aoc24.util;

import java.util.Arrays;

public final class IntTrie {

    public final int limit;
    public final int nodeSize;
    public int nodeCount;
    public int[] data;

    public IntTrie(int limit, int capacity) {
        this.limit = limit;
        nodeSize = limit + 1;
        data = new int[nodeSize * capacity];
        nodeCount = 1;
    }

    public IntTrie(int limit) {
        this(limit, 8);
    }

    public int add(int... word) {
        return increment(afterOrGenerate(root(), word));
    }

    public int root() {
        return 0;
    }

    public int after(int node, int ch) {
        return data[node + ch];
    }

    public int after(int node, int... word) {
        for(int c : word)
            if((node = data[node + c]) == 0)
                return 0;
        return node;
    }

    public int afterOrGenerate(int node, int ch) {
        int o = node + ch;
        int n = data[o];
        if(n != 0)
            return n;
        n = nodeCount++ * nodeSize;
        if(n >= data.length)
            data = Arrays.copyOf(data, data.length * 2);
        data[o] = n;
        return n;
    }

    public int afterOrGenerate(int node, int... word) {
        for(int c : word) {
            int pos = node + c;
            if(data[pos] != 0)
                node = data[pos];
            else if((data[pos] = node = nodeCount++ * nodeSize) >= data.length)
                data = Arrays.copyOf(data, data.length * 2);
        }
        return node;
    }

    public int count(int node) {
        return data[node + limit];
    }

    public int count(int node, int ch) {
        node = after(node, ch);
        return node != 0 ? data[node + limit] : 0;
    }

    public int count(int node, int... word) {
        node = after(node, word);
        return node != 0 || word.length != 0 ? data[node + limit] : 0;
    }

    public int increment(int node) {
        return ++data[node + limit];
    }

    public void setCount(int node, int count) {
        data[node + limit] = count;
    }
}
