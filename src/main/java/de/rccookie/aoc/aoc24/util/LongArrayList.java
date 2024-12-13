package de.rccookie.aoc.aoc24.util;

import java.util.Arrays;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;

import org.jetbrains.annotations.NotNull;

public final class LongArrayList implements Iterable<Long> {

    public long[] data;
    public int size = 0;

    public LongArrayList(int initialCapacity) {
        data = new long[initialCapacity];
    }

    public LongArrayList() {
        this(8);
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOfRange(data, 0, size));
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public long get(int index) {
        if(index >= size)
            throw new IndexOutOfBoundsException(index);
        return data[index];
    }

    public void add(long x) {
        if(data.length == size)
            data = Arrays.copyOf(data, 2 * size);
        data[size++] = x;
    }

    public void add(int index, long x) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException(index);
        if(data.length == size)
            data = Arrays.copyOf(data, 2 * size);
        if(index == size)
            data[size++] = x;
        else {
            System.arraycopy(data, index, data, size, size - index);
            data[index] = x;
            size++;
        }
    }

    public void remove(int index) {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException(index);
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        size--;
    }

    public void clear() {
        size = 0;
    }

    @NotNull
    @Override
    public PrimitiveIterator.OfLong iterator() {
        return new PrimitiveIterator.OfLong() {
            int i = 0;
            @Override
            public long nextLong() {
                return data[i++];
            }

            @Override
            public boolean hasNext() {
                return i < size;
            }
        };
    }

    public void forEachLong(LongConsumer action) {
        for(int i=0; i<size; i++)
            action.accept(data[i]);
    }

    @Override
    public Spliterator.OfLong spliterator() {
        return Spliterators.spliterator(data, 0, size, Spliterator.NONNULL | Spliterator.ORDERED | Spliterator.CONCURRENT);
    }
}
