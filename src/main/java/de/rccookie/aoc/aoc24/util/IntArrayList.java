package de.rccookie.aoc.aoc24.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

import de.rccookie.util.Cloneable;
import de.rccookie.util.RandomAccessListIterator;
import de.rccookie.util.RandomAccessSubList;
import de.rccookie.util.Utils;
import org.jetbrains.annotations.NotNull;

public final class IntArrayList implements Iterable<Integer>, Cloneable<IntArrayList> {

    public int[] data;
    public int size = 0;

    public IntArrayList(int initialCapacity) {
        data = new int[initialCapacity];
    }

    public IntArrayList() {
        this(8);
    }

    @SuppressWarnings("unused")
    private IntArrayList(Void flag) { }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOfRange(data, 0, size));
    }

    @Override
    public @NotNull IntArrayList clone() {
        IntArrayList copy = new IntArrayList(null);
        copy.data = toArray();
        copy.size = size;
        return copy;
    }

    public int[] toArray() {
        return Arrays.copyOf(data, size);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int get(int index) {
        return data[index];
    }

    public void add(int x) {
        if(data.length == size)
            data = Arrays.copyOf(data, 2 * size);
        data[size++] = x;
    }

    public void push(int x) {
        if(data.length == size)
            data = Arrays.copyOf(data, 2 * size);
        data[size++] = x;
    }

    public void add(int index, int x) {
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
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        size--;
    }

    public int removeLast() {
        return data[--size];
    }

    public int pop() {
        return data[--size];
    }

    public void clear() {
        size = 0;
    }

    public int indexOf(int x) {
        int[] d = data;
        int s = size;
        for(int i=0; i<s; ++i)
            if(d[i] == x)
                return i;
        return -1;
    }

    public int lastIndexOf(int x) {
        int[] d = data;
        int s = size;
        for(int i=s-1; i>=0; --i)
            if(d[i] == x)
                return i;
        return -1;
    }

    @NotNull
    @Override
    public PrimitiveIterator.OfInt iterator() {
        return new PrimitiveIterator.OfInt() {
            int i = 0;
            @Override
            public int nextInt() {
                return data[i++];
            }

            @Override
            public boolean hasNext() {
                return i < size;
            }
        };
    }

    public void forEachInt(IntConsumer action) {
        for(int i=0; i<size; i++)
            action.accept(data[i]);
    }

    @Override
    public Spliterator.OfInt spliterator() {
        return Spliterators.spliterator(data, 0, size, Spliterator.NONNULL | Spliterator.ORDERED | Spliterator.CONCURRENT);
    }

    public List<Integer> asList() {
        return new AsList();
    }


    private class AsList implements List<Integer> {

        private AsList() { }

        @Override
        public String toString() {
            return Utils.toString(this);
        }

        @Override
        public boolean equals(Object obj) {
            return Utils.equals(this, obj);
        }

        @Override
        public int hashCode() {
            return Utils.hashCode(this);
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public boolean contains(Object o) {
            return o instanceof Number && IntArrayList.this.indexOf(((Number) o).intValue()) != -1;
        }

        @NotNull
        @Override
        public PrimitiveIterator.OfInt iterator() {
            return IntArrayList.this.iterator();
        }

        @NotNull
        @Override
        public Object[] toArray() {
            Integer[] arr = new Integer[size];
            for(int i=0; i<size; i++)
                arr[i] = data[i];
            return arr;
        }

        @SuppressWarnings("unchecked")
        @NotNull
        @Override
        public <T> T[] toArray(@NotNull T[] a) {
            if(a.length < size)
                a = Arrays.copyOf(a, size);
            for(int i=0; i<size; i++)
                a[i] = (T) (Integer) data[i];
            return a;
        }

        @Override
        public boolean add(Integer integer) {
            IntArrayList.this.add(integer);
            return true;
        }

        @Override
        public boolean remove(Object o) {
            int index = indexOf(o);
            if(index < 0)
                return false;
            IntArrayList.this.remove(index);
            return true;
        }

        @Override
        public boolean containsAll(@NotNull Collection<?> c) {
            for(Object o : c)
                if(!contains(o))
                    return false;
            return true;
        }

        @Override
        public boolean addAll(@NotNull Collection<? extends Integer> c) {
            boolean any = false;
            for(Integer o : c) {
                IntArrayList.this.add(o);
                any = true;
            }
            return any;
        }

        @Override
        public boolean addAll(int index, @NotNull Collection<? extends Integer> c) {
            int cSize = c.size();
            if(cSize == 0)
                return false;

            int required = size + cSize;
            if(data.length < required)
                data = Arrays.copyOf(data, Math.max(data.length << 1, required));

            System.arraycopy(data, index, data, size, size - index);
            for(Integer o : c)
                data[index++] = o;
            return true;
        }

        @Override
        public boolean removeAll(@NotNull Collection<?> c) {
            boolean any = false;
            for(Object o : c)
                any |= remove(o);
            return any;
        }

        @Override
        public boolean retainAll(@NotNull Collection<?> c) {
            int[] d = data;
            int s = size, newSize = 0;
            for(int i=0; i<s; i++) {
                int x = d[i];
                if(c.contains(x))
                    d[newSize++] = x;
            }
            return size == (size = newSize);
        }

        @Override
        public void clear() {
            IntArrayList.this.clear();
        }

        @Override
        public Integer get(int index) {
            return IntArrayList.this.get(index);
        }

        @Override
        public Integer set(int index, Integer element) {
            int old = data[index];
            data[index] = element;
            return old;
        }

        @Override
        public void add(int index, Integer element) {
            IntArrayList.this.add(index, element);
        }

        @Override
        public Integer remove(int index) {
            int old = data[index];
            IntArrayList.this.remove(index);
            return old;
        }

        @Override
        public int indexOf(Object o) {
            return o instanceof Number ? IntArrayList.this.indexOf(((Number) o).intValue()) : -1;
        }

        @Override
        public int lastIndexOf(Object o) {
            return o instanceof Number ? IntArrayList.this.lastIndexOf(((Number) o).intValue()) : -1;
        }

        @NotNull
        @Override
        public ListIterator<Integer> listIterator() {
            return new RandomAccessListIterator<>(this);
        }

        @NotNull
        @Override
        public ListIterator<Integer> listIterator(int index) {
            return new RandomAccessListIterator<>(this, index);
        }

        @NotNull
        @Override
        public List<Integer> subList(int fromIndex, int toIndex) {
            return RandomAccessSubList.ofRange(this, fromIndex, toIndex);
        }
    }
}
