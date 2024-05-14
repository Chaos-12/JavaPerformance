package com.teaching.core.helpers;

import org.apache.commons.lang3.StringUtils;

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
}
