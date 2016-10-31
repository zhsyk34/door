package com.dnk.smart.door.kit;

public class ValidateKit {

    public static boolean empty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean notEmpty(String str) {
        return !empty(str);
    }

    public static boolean invalid(Long key) {
        return key == null || key <= 0;
    }

    public static boolean valid(Long key) {
        return !invalid(key);
    }
}
