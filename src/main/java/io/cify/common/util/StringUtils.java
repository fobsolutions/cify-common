package io.cify.common.util;

import java.util.List;
import java.util.Map;

public class StringUtils {
    public static <T> String toString(List<T> list) {
        if (list == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder("[");
        for (T object : list) {
            sb.append(object.toString());
            sb.append(", ");
        }
        if (!list.isEmpty()) {
            sb.delete(sb.length()-2, sb.length());
        }
        sb.append("]");
        return sb.toString();
    }

    public static <K, V> String toString(Map<K, V> map) {
        if (map == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder("[");
        for (Map.Entry<K, V> entry : map.entrySet()) {
            sb.append(entry.getKey().toString());
            sb.append(" : ");
            sb.append(entry.getValue().toString());
            sb.append(", ");
        }
        if (!map.isEmpty()) {
            sb.delete(sb.length()-2, sb.length());
        }
        sb.append("]");
        return sb.toString();
    }
}
