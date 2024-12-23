package de.rccookie.aoc.aoc24.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.Set;
import java.util.stream.Collectors;

import de.rccookie.util.Cloneable;
import org.jetbrains.annotations.NotNull;

public class IntSet implements Iterable<Integer>, Cloneable<IntSet> {

    public final long[] data;

    public IntSet(int limit) {
        data = new long[(limit + 63) / 64];
    }

    public IntSet(long[] data) {
        assert data != null;
        this.data = data;
    }

    @Override
    public @NotNull IntSet clone() {
        return new IntSet(data.clone());
    }

    @Override
    public String toString() {
        return Arrays.stream(data).mapToObj(l -> {
            String s = Long.toBinaryString(l);
            return " ".repeat(64 - s.length()) + s;
        }).collect(Collectors.joining());
    }

    public boolean is(int x) {
        assert x >= 0 && x >>> 6 < data.length;
        return (data[x >>> 6] >>> (x & 63) & 1) != 0;
    }

    public int is01(int x) {
        assert x >= 0 && x >>> 6 < data.length;
        return (int) (data[x >>> 6] >>> (x & 63)) & 1;
    }

    public void set(int x) {
        assert x >= 0 && x >>> 6 < data.length;
        data[x >>> 6] |= 1L << (x & 63);
    }

    public boolean getAndSet(int x) {
        assert x >= 0 && x >>> 6 < data.length;
        int i = x >>> 6;
        long old = data[i];
        return old != (data[i] = old | 1L << (x & 63));
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    public void and(IntSet x) {
        long[] d = data, xd = x.data;
        int len = Math.min(data.length, x.data.length);
        for(int i=0; i<len; i++)
            d[i] &= xd[i];
        Arrays.fill(d, len, d.length, 0);
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    public void or(IntSet x) {
        long[] d = data, xd = x.data;
        int len = Math.min(data.length, x.data.length);
        for(int i=0; i<len; i++)
            d[i] |= xd[i];
    }

    public void clear(int x) {
        assert x >= 0 && x >>> 6 < data.length;
        data[x >>> 6] &= ~(1L << (x & 63));
    }

    public boolean getAndClear(int x) {
        assert x >= 0 && x >>> 6 < data.length;
        int i = x >>> 6;
        long old = data[i];
        return old != (data[i] = old & ~(1L << (x & 63)));
    }

    public void set(int x, boolean value) {
        if(value) set(x);
        else clear(x);
    }

    public void maybeSet(int x, long value01) {
        assert x >= 0 && x >>> 6 < data.length && (value01 == 0 || value01 == 1);
        data[x >>> 6] |= value01 << (x & 63);
    }

    public boolean getAndMaybeSet(int x, long value01) {
        assert x >= 0 && x >>> 6 < data.length && (value01 == 0 || value01 == 1);
        int i = x >>> 6;
        long old = data[i];
        return old != (data[i] = old | value01 << (x & 63));
    }

    public void maybeClear(int x, long value01) {
        assert x >= 0 && x >>> 6 < data.length && (value01 == 0 || value01 == 1);
        data[x >>> 6] &= ~(value01 << (x & 63));
    }

    public boolean getAndMaybeClear(int x, long value01) {
        assert x >= 0 && x >>> 6 < data.length && (value01 == 0 || value01 == 1);
        int i = x >>> 6;
        long old = data[i];
        return old != (data[i] = old & ~(value01 << (x & 63)));
    }


    public void clear() {
        Arrays.fill(data, 0);
    }


    @NotNull
    @Override
    public PrimitiveIterator.OfInt iterator() {
        return new PrimitiveIterator.OfInt() {
            int next = 0;
            @Override
            public int nextInt() {
                assert hasNext();
                return next++;
            }

            @Override
            public boolean hasNext() {
                if(next >= data.length << 6)
                    return false;
                if(is(next))
                    return true;

                int i = next >>> 6;
                while(i < data.length && data[i] == 0) {
                    ++i;
                    next &= ~63;
                }
                if(i >= data.length << 6)
                    return false;

                long x = data[i];
                next &= 63;
                long m = 1;
                while((x & m) == 0) {
                    if(++next >= 64) {
                        next = data.length << 6;
                        return false;
                    }
                    m <<= 1;
                }

                next |= i << 6;
                return true;
            }

            @Override
            public void remove() {
                clear(next - 1);
            }
        };
    }


    public Set<Integer> asSet() {
        return new AsSet();
    }


    private class AsSet implements Set<Integer> {

        @Override
        public int size() {
            int size = 0;
            for(long l : data)
                size += Long.bitCount(l);
            return size;
        }

        @Override
        public boolean isEmpty() {
            for(long l : data)
                if(l != 0)
                    return false;
            return true;
        }

        @Override
        public boolean contains(Object o) {
            return is(((Number) o).intValue());
        }

        @NotNull
        @Override
        public Iterator<Integer> iterator() {
            return IntSet.this.iterator();
        }

        @NotNull
        @Override
        public Object[] toArray() {
            Integer[] arr = new Integer[size()];
            if(arr.length == 0)
                return arr;

            int index = 0;
            for(int i=0; i<data.length<<6; i++)
                if(is(i))
                    arr[index++] = i;
            return arr;
        }

        @SuppressWarnings("unchecked")
        @NotNull
        @Override
        public <T> T[] toArray(@NotNull T[] a) {
            int size = size();
            if(size == 0)
                return a;
            if(a.length < size)
                a = Arrays.copyOf(a, size);

            int index = 0;
            for(int i=0; i<data.length<<6; i++)
                if(is(i))
                    a[index++] = (T) (Integer) i;
            return a;
        }

        @Override
        public boolean add(Integer x) {
            return !getAndSet(x);
        }

        @Override
        public boolean remove(Object o) {
            return getAndClear(((Number) o).intValue());
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
            for(int x : c)
                any |= add(x);
            return any;
        }

        @Override
        public boolean retainAll(@NotNull Collection<?> c) {
            boolean any = false;
            for(Iterator<Integer> it = iterator(); it.hasNext(); ) {
                if(!c.contains(it.next())) {
                    it.remove();
                    any = true;
                }
            }
            return any;
        }

        @Override
        public boolean removeAll(@NotNull Collection<?> c) {
            boolean any = false;
            for(Object x : c)
                any |= remove(x);
            return any;
        }

        @Override
        public void clear() {
            IntSet.this.clear();
        }
    }
}
