package com.teaching.core.helpers;

public class LogHelper {

    private LogHelper(){ }

    public static void log(String message, Object... arguments) {
        System.out.printf((message) + "%n", arguments);
    }

}
