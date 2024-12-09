package de.rccookie.aoc.aoc24;

import java.util.Arrays;

public final class IntHeap {

    public int[] data;
    public int size = 0;

    public IntHeap(int initialCapacity) {
        if(initialCapacity <= 0)
            throw new IllegalArgumentException("Capacity must be > 0");
        data = new int[initialCapacity];
    }

    public IntHeap() {
        data = new int[8];
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
        int[] d;
        final int s;
        if((d = data).length <= (s = size))
            data = d = Arrays.copyOf(d, 2 * s);
        d[s] = x;
        size = s + 1;
    }

    public void enqueue(int x) {
        int[] d;
        final int s;
        if((d = data).length <= (s = size))
            data = d = Arrays.copyOf(d, 2 * s);
        rise(s, x, d);
        size = s + 1;
    }

    public int peek() {
        return data[0];
    }

    public int dequeue() {
        final int[] d;
        final int x, s, move;
        x = (d = data)[0];
        move = d[s = --size];
        sink(0, move, d, s);
        return x;
    }

    public static void enqueue(int[] queue, int size, int x) {
        rise(size, x, queue);
    }

    public static int dequeue(int[] queue, int size) {
        final int x = queue[0];
        sink(0, queue[--size], queue, size);
        return x;
    }

    private static void rise(int i, int x, int[] data) {
        while(i > 0) {
            int parent = (i-1) >>> 1;
            int p = data[parent];
            if(x >= p) break;
            data[i] = p;
            i = parent;
        }
        data[i] = x;
    }

    private static void sink(int i, int x, int[] data, int size) {
        int h = size >>> 1;
        while(i < h) {
            int child = (i << 1) + 1;
            int right = child + 1;
            int c = data[child];
            if(right < size && c > data[right])
                c = data[child = right];
            if(x <= c)
                break;
            data[i] = c;
            i = child;
        }
        data[i] = x;
    }
}
