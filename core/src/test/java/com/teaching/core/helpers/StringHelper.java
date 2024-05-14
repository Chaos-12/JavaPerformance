package com.teaching.core.helpers;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

public class StringHelper {

    private static final String INVALID_CHARS = "([.,:\\-'!?])";

    public static String[] splitByWords(String input){
        return StringUtils.normalizeSpace(deleteSpecialCharacters(input))
                .toLowerCase()
                .split(" ");
    }

    private static String deleteSpecialCharacters(String input){
        return input.replaceAll(INVALID_CHARS, "");
    }

    public static <E extends Enum<E>> Set<String> fromEnumtoSet(Class<E> enumClass) {
        return EnumUtils.getEnumList(enumClass)
                .stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
