package de.rccookie.aoc.aoc24;

import java.util.Arrays;
import java.util.PrimitiveIterator;
import java.util.function.IntConsumer;

import de.rccookie.util.ListStream;
import org.jetbrains.annotations.NotNull;

public final class IntArrayDeque implements Iterable<Integer> {
    //      tail     head
    //        v        v
    // [., ., 1, 2, 3, ., .]
    public int[] data;
    public int mod;
    public int head = 0, tail = 0;

    public IntArrayDeque(int initialCapacity) {
        mod = 1;
        while(mod < initialCapacity)
            mod = mod << 1 | 1;
        data = new int[mod+1];
    }

    public IntArrayDeque() {
        data = new int[8];
        mod = 7;
    }

    @Override
    public String toString() {
        return ListStream.of(this).toString();
    }

    public int size() {
        return (tail + head + mod + 1) & mod;
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public void growIfNeeded() {
        if((head + 1 & mod) == tail)
            grow();
    }

    public void grow() {
        mod = mod << 1 | 1;
        if(head < tail) {
            int[] arr = new int[mod+1];
            System.arraycopy(data, tail, arr, tail, data.length - tail);
            System.arraycopy(data, 0, arr, data.length, head);
            head += data.length;
            data = arr;
        }
        else data = Arrays.copyOf(data, mod + 1);
    }

    public void enqueue(int x) {
        growIfNeeded();
        data[head] = x;
        head = head + 1 & mod;
    }

    public void push(int x) {
        growIfNeeded();
        data[head] = x;
        head = head + 1 & mod;
    }

    public void prepend(int x) {
        growIfNeeded();
        data[tail = tail - 1 & mod] = x;
    }

    public int peekDequeue() {
        return data[tail];
    }

    public int peekPop() {
        return data[head-1 & mod];
    }

    public int dequeue() {
        int x = data[tail];
        tail = tail + 1 & mod;
        return x;
    }

    public int pop() {
        return data[head = head-1 & mod];
    }

    public int get(int index) {
        return data[tail + index & mod];
    }

    public void clear() {
        head = tail;
    }

    @NotNull
    @Override
    public PrimitiveIterator.OfInt iterator() {
        return new PrimitiveIterator.OfInt() {
            int i = tail;
            @Override
            public int nextInt() {
                int x = data[i];
                i = i+1 & mod;
                return x;
            }

            @Override
            public boolean hasNext() {
                return i != head;
            }
        };
    }

    public void forEachInt(IntConsumer action) {
        for(int i=tail; i!=head; i=i+1&mod)
            action.accept(data[i]);
    }
}
