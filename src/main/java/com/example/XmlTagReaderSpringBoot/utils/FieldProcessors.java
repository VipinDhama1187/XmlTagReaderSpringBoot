package com.example.XmlTagReaderSpringBoot.utils;

import java.util.*;

public class FieldProcessors {

    public static FieldProcessor defaultProcessor(String key) {
        return (reader, values, path) -> values.put(key, reader.getElementText());
    }

    public static FieldProcessor pathBasedProcessor(List<Map.Entry<String, String>> pathToKey) {
        return (reader, values, path) -> {
            String fullPath = getCurrentPath(path);
            for (Map.Entry<String, String> entry : pathToKey) {
                if (fullPath.contains(entry.getKey())) {
                    values.put(entry.getValue(), reader.getElementText());
                    return;
                }
            }
            // Optional fallback
            values.put("generic_" + reader.getLocalName(), reader.getElementText());
        };
    }

    private static String getCurrentPath(Deque<String> path) {
        List<String> list = new ArrayList<>(path);
        Collections.reverse(list); // from root > ... > current tag
        return String.join(">", list);
    }
}
