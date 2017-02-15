package cn.sxy.compare;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Math.min;

// PK是对象的主键， T是对象
public class CompareCollections<PK, T> {

    private HashMap<PK, T> before;
    private HashMap<PK, T> after;

    private CompareCollections() {
    }

    public static <PK, T> CompareCollections<PK, T> create(Map<PK, T> before, Map<PK, T> after) {
        CompareCollections<PK, T> c = new CompareCollections<>();
        c.before = new HashMap<>(before);
        c.after = new HashMap<>(after);
        return c;
    }

    public static <T> CompareCollections<T, T> create(Collection<T> before, Collection<T> after)
            throws IllegalStateException {
        CompareCollections<T, T> c = new CompareCollections<>();
        c.before = (HashMap<T, T>) before.stream().collect(Collectors.toMap(e -> e, e -> e));
        c.after = (HashMap<T, T>) after.stream().collect(Collectors.toMap(e -> e, e -> e));
        return c;
    }

    public static <PK, T> CompareCollections<PK, T> create(Collection<T> before, Collection<T> after, Function<T, PK> getPK)
            throws IllegalStateException {
        CompareCollections<PK, T> c = new CompareCollections<>();
        c.before = (HashMap<PK, T>) before.stream().collect(Collectors.toMap(getPK, e -> e));
        c.after = (HashMap<PK, T>) after.stream().collect(Collectors.toMap(getPK, e -> e));
        return c;
    }

    public HashMap<PK, T> getAddMap() {
        Map<PK, T> addMap = new HashMap<>(after.size());
        after.forEach((pk, t) -> {
            if (!before.containsKey(pk)) {
                addMap.put(pk, t);
            }
        });
        return new HashMap<>(addMap);
    }

    public HashMap<PK, T> getSubMap() {
        Map<PK, T> subMap = new HashMap<>(before.size());
        before.forEach((pk, v) -> {
            if (!after.containsKey(pk)) {
                subMap.put(pk, v);
            }
        });
        return new HashMap<>(subMap);
    }

    // List<T>[0] 是 before， List<T>[1] 是 after
    public HashMap<PK, List<T>> getUpdateMap() {
        Map<PK, List<T>> updateMap = new HashMap<>(min(before.size(), after.size()));
        after.forEach((pk, afterV) -> {
            T beforeV = before.get(pk);
            if (beforeV != null) {
                if (!beforeV.equals(afterV)) {
                    ArrayList<T> list = new ArrayList<>(2);
                    list.add(beforeV);
                    list.add(afterV);

                    updateMap.put(pk, list);
                }
            }
        });
        return new HashMap<>(updateMap);
    }

    public Map<PK, T> getBefore() {
        return before;
    }

    public Map<PK, T> getAfter() {
        return after;
    }

}

