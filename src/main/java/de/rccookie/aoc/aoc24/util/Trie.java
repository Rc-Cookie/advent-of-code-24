package de.rccookie.aoc.aoc24.util;

import java.util.HashMap;
import java.util.Map;

public class Trie {

    public int count;
    public final Map<Character, Trie> next = new HashMap<>();

    public Trie() { }



    public int add(CharSequence str) {
        return add(str, 0, str.length());
    }

    public int add(CharSequence str, int fromIndex, int toIndex) {
        return afterOrGenerate(str, fromIndex, toIndex).count++;
    }

    public int add(char... str) {
        return add(str, 0, str.length);
    }

    public int add(char[] str, int fromIndex, int toIndex) {
        return afterOrGenerate(str, fromIndex, toIndex).count++;
    }


    public Trie afterOrGenerate(char c) {
        return next.computeIfAbsent(c, $ -> new Trie());
    }

    public Trie afterOrGenerate(CharSequence prefix) {
        return afterOrGenerate(prefix, 0, prefix.length());
    }

    public Trie afterOrGenerate(CharSequence prefix, int fromIndex, int toIndex) {
        if(fromIndex == toIndex)
            return this;
        return afterOrGenerate(prefix.charAt(fromIndex)).afterOrGenerate(prefix, fromIndex + 1, toIndex);
    }

    public Trie afterOrGenerate(char... prefix) {
        return afterOrGenerate(prefix, 0, prefix.length);
    }

    public Trie afterOrGenerate(char[] prefix, int fromIndex, int toIndex) {
        if(fromIndex == toIndex)
            return this;
        return afterOrGenerate(prefix[fromIndex]).afterOrGenerate(prefix, fromIndex + 1, toIndex);
    }


    public Trie after(char c) {
        return next.get(c);
    }

    public Trie after(CharSequence prefix) {
        return after(prefix, 0, prefix.length());
    }

    public Trie after(CharSequence prefix, int fromIndex, int toIndex) {
        if(fromIndex == toIndex)
            return this;
        Trie next = after(prefix.charAt(fromIndex));
        return next != null ? next.after(prefix, fromIndex + 1, toIndex) : null;
    }

    public Trie after(char... prefix) {
        return after(prefix, 0, prefix.length);
    }

    public Trie after(char[] prefix, int fromIndex, int toIndex) {
        if(fromIndex == toIndex)
            return this;
        Trie next = after(prefix[fromIndex]);
        return next != null ? next.after(prefix, fromIndex + 1, toIndex) : null;
    }


    public int getCount(CharSequence str) {
        return getCount(str, 0, str.length());
    }

    public int getCount(CharSequence str, int fromIndex, int toIndex) {
        Trie t = after(str, fromIndex, toIndex);
        return t != null ? t.count : 0;
    }

    public int getCount(char... str) {
        return getCount(str, 0, str.length);
    }

    public int getCount(char[] str, int fromIndex, int toIndex) {
        Trie t = after(str, fromIndex, toIndex);
        return t != null ? t.count : 0;
    }

    public boolean contains(char... str) {
        return getCount(str) != 0;
    }

    public boolean contains(char[] str, int fromIndex, int toIndex) {
        return getCount(str, fromIndex, toIndex) != 0;
    }

    public boolean contains(CharSequence str) {
        return getCount(str) != 0;
    }

    public boolean contains(CharSequence str, int fromIndex, int toIndex) {
        return getCount(str, fromIndex, toIndex) != 0;
    }
}
