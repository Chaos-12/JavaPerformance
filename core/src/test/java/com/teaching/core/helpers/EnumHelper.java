package com.teaching.core.helpers;

import org.apache.commons.lang3.EnumUtils;

import java.lang.reflect.Array;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumHelper {

    public static <E extends Enum<E>> Set<String> fromEnumtoSet(Class<E> enumClass) {
        return EnumUtils.getEnumList(enumClass)
                .stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    public static <E extends Enum<E>> E[] fromStringToEnum(Class<E> enumClass, String[] data) {
        E[] result = (E[]) Array.newInstance(enumClass, data.length);
        for (int index=0; index < data.length; index++) {
            result[index] = EnumUtils.getEnum(enumClass, data[index]);
        }
        return result;
    }
}
