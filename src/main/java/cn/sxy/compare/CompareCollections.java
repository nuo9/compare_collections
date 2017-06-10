package cn.sxy.compare;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Math.min;

// PK是对象的主键， T是对象
public class CompareCollections<PK, T> {

    private Map<PK, T> before;
    private Map<PK, T> after;

    private CompareCollections() {
    }

    public static <PK, T> CompareCollections<PK, T> create(Map<PK, T> before, Map<PK, T> after) {
        CompareCollections<PK, T> c = new CompareCollections<>();
        c.before = before;
        c.after = after;
        return c;
    }

    public static <T> CompareCollections<T, T> create(Collection<T> before, Collection<T> after)
            throws IllegalStateException {
        CompareCollections<T, T> c = new CompareCollections<>();
        c.before = before.stream().collect(Collectors.toMap(e -> e, e -> e));
        c.after = after.stream().collect(Collectors.toMap(e -> e, e -> e));
        return c;
    }

    public static <PK, T> CompareCollections<PK, T> create(Collection<T> before, Collection<T> after, Function<T, PK> getPK)
            throws IllegalStateException {
        CompareCollections<PK, T> c = new CompareCollections<>();
        c.before = before.stream().collect(Collectors.toMap(getPK, e -> e));
        c.after = after.stream().collect(Collectors.toMap(getPK, e -> e));
        return c;
    }

    public HashMap<PK, T> getAddMap() {
        HashMap<PK, T> addMap = new HashMap<>(after.size());
        after.forEach((pk, t) -> {
            if (!before.containsKey(pk)) {
                addMap.put(pk, t);
            }
        });
        return addMap;
    }

    public HashMap<PK, T> getSubMap() {
        HashMap<PK, T> subMap = new HashMap<>(before.size());
        before.forEach((pk, v) -> {
            if (!after.containsKey(pk)) {
                subMap.put(pk, v);
            }
        });
        return subMap;
    }

    public HashMap<PK, Pair<T>> getUpdateMap() {
        HashMap<PK, Pair<T>> updateMap = new HashMap<>(min(before.size(), after.size()));
        after.forEach((pk, afterV) -> {
            T beforeV = before.get(pk);
            if (beforeV != null) {
                if (!beforeV.equals(afterV)) {
                    Pair<T> pair = new Pair<>(beforeV, afterV);
                    updateMap.put(pk, pair);
                }
            }
        });
        return updateMap;
    }

    public Map<PK, T> getBefore() {
        return before;
    }

    public Map<PK, T> getAfter() {
        return after;
    }

}

