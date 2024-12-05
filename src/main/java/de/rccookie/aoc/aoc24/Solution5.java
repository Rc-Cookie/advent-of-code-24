package de.rccookie.aoc.aoc24;

public class Solution5 extends FastSolution {

    private final long[] rules = new long[(128*128+63)/64];
    private final byte[] order = new byte[32];
    private int pos;

    @Override
    public Object task1() {
        loadRules();

        int sum = 0, len, i, r;

        ordersLoop: for(; pos < chars.length - 1; pos += len * 3) {

            len = pos + 2;
            while(chars[len] != '\n') len++;
            len = (len - pos + 1) / 3;

            order[0] = (byte) ((chars[pos] - '0') * 10 + chars[pos+1] - '0');
            for(i=1; i<len; i++) {
                order[i] = (byte) ((chars[pos + 3*i] - '0') * 10 + chars[pos + 3*i + 1] - '0');
                r = order[i]-10 + (order[i-1] << 7) - 1280;
                if((rules[r >> 6] & 1L << (r & 63)) != 0)
                    continue ordersLoop;
            }
            sum += order[len/2];
        }
        return sum;
    }

    @Override
    public Object task2() {

        loadRules();

        int sum = 0, len, i, j, r;
        boolean sorted;
        byte x;

        for(; pos < chars.length - 1; pos += len * 3) {

            len = pos + 2;
            while(chars[len] != '\n') len++;
            len = (len - pos + 1) / 3;

            order[0] = (byte) ((chars[pos] - '0') * 10 + chars[pos+1] - '0');
            sorted = true;
            insertLoop: for(i=1; i<len; i++) {
                x = (byte) ((chars[pos + 3*i] - '0') * 10 + chars[pos + 3*i + 1] - '0');
                r = x-10 + (order[i-1] << 7) - 1280;
                if((rules[r >> 6] & 1L << (r & 63)) == 0)
                    order[i] = x;
                else {
                    sorted = false;
                    for(j=i-1; j>0; j--) {
                        r = x-10 + (order[j-1] << 7) - 1280;
                        if((rules[r >> 6] & 1L << (r & 63)) == 0) {
                            System.arraycopy(order, j, order, j+1, i-j);
                            order[j] = x;
                            continue insertLoop;
                        }
                    }
                    System.arraycopy(order, 0, order, 1, i);
                    order[0] = x;
                }
            }
            if(!sorted)
                sum += order[len/2];
        }
        return sum;
    }

    private void loadRules() {
        int i;
        for(pos = 0; chars[pos] != '\n'; pos += 6) {
            i = (chars[pos]-'0')*10 + chars[pos+1]-('0'+10) + (((chars[pos+3]-'0')*10 + chars[pos+4]-('0'+10)) << 7);
            rules[i >> 6] |= 1L << (i & 63);
        }
        pos++;
    }
}
