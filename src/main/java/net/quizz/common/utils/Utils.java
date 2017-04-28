package net.quizz.common.utils;

import java.util.Collection;

/**
 * @author lutfun
 * @since 4/28/17
 */
public class Utils {

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
