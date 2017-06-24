package cn.sxy.compare;

import java.util.Arrays;
import java.util.List;

public class Pair<T> {

    private final T before;
    private final T after;

    public Pair(T before, T after) {
        this.before = before;
        this.after = after;
    }

    public T getBefore() {
        return before;
    }

    public T getAfter() {
        return after;
    }

    public List<T> toList() {
        return Arrays.asList(before, after);
    }

}
