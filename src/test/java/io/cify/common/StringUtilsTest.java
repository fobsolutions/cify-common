package io.cify.common;

import io.cify.common.util.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StringUtilsTest {
    @Test
    public void testListToString() {
        List<String> list = new ArrayList<>();

        assertEquals("[]", StringUtils.toString(list));

        list.add("One");
        list.add("Two");
        list.add("Three");

        assertEquals("[One, Two, Three]", StringUtils.toString(list));
        assertEquals("null", StringUtils.toString((List<Object>) null));
    }

    @Test
    public void testMapToString() {
        Map<String, String> map = new HashMap<>();

        assertEquals("[]", StringUtils.toString(map));

        map.put("Key_1", "One");
        map.put("Key_2", "Two");
        map.put("Key_3", "Three");

        assertEquals("[Key_1 : One, Key_2 : Two, Key_3 : Three]", StringUtils.toString(map));
        assertEquals("null", StringUtils.toString((Map<Object, Object>) null));
    }
}
