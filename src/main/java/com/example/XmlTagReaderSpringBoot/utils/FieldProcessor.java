package com.example.XmlTagReaderSpringBoot.utils;

import javax.xml.stream.XMLStreamReader;
import java.util.Deque;
import java.util.Map;

@FunctionalInterface
public interface FieldProcessor {

    void process(XMLStreamReader reader, Map<String, String> values, Deque<String> pathStack) throws Exception;

}
