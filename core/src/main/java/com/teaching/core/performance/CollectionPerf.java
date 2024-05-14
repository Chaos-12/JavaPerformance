package com.teaching.core.performance;

import java.util.Arrays;
import java.util.Collection;

public class CollectionPerf {

    public static <T> Collection<T> populateOneByOne(Collection<T> collection, T[] data) {
        for (T item : data) {
            collection.add(item);
        }
        return collection;
    }

    public static <T> Collection<T> populateAllSameTime(Collection<T> collection, T[] data) {
        collection.addAll(Arrays.asList(data));
        return collection;
    }
}
