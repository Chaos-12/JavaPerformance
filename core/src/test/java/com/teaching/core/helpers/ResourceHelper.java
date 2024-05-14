package com.teaching.core.helpers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ResourceHelper {

    private ResourceHelper(){ }

    public static InputStream getResourceAsInputStream(String directory){
        return ResourceHelper.class.getResourceAsStream(directory);
    }

    public static String getResourceAsString(String directory) {
        InputStream resourceAsStream = getResourceAsInputStream(directory);
        return new BufferedReader(new InputStreamReader(resourceAsStream))
                .lines()
                .parallel()
                .collect(Collectors.joining(System.getProperty("line.separator")));
    }

    public static String[] getResourceAsArray(String directory) {
        return StringHelper.splitByWords(getResourceAsString(directory));
    }
}
