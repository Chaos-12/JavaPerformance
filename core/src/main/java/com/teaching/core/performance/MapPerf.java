package com.teaching.core.performance;

import java.util.*;

public class MapPerf {

    public static Map<String, List<Integer>> populateMapWithFilter(Map<String, List<Integer>> map,
                                                                   Collection<String> whiteList,
                                                                   String[] data) {
        for(int index=0; index < data.length; index++) {
            String input = data[index];
            if (whiteList.contains(input)) {
                if (map.containsKey(input)) {
                    map.get(input).add(index);
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(index);
                    map.put(input, list);
                }
            }
        }
        return map;
    }

    public static <E extends Enum<E>> Map<E, List<Integer>> populateMapWithEnum(Map<E, List<Integer>> map, E[] data) {
        for(int index=0; index < data.length; index++) {
            E input = data[index];
            if (Objects.nonNull(input)) {
                if (map.containsKey(input)) {
                    map.get(input).add(index);
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(index);
                    map.put(input, list);
                }
            }
        }
        return map;
    }
}
