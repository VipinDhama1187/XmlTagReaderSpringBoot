package com.example.XmlTagReaderSpringBoot.service;

import com.example.XmlTagReaderSpringBoot.config.ExtractConfig;
import com.example.XmlTagReaderSpringBoot.utils.FieldProcessor;
import com.example.XmlTagReaderSpringBoot.utils.FieldProcessors;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.util.*;

@Service
public class XmlPlaceholderExtractor {
    private final ExtractConfig config;
    private final Map<String, FieldProcessor> processorMap = new HashMap<>();

    public XmlPlaceholderExtractor(ExtractConfig config) {
        this.config = config;
    }

    @PostConstruct
    public void init() {
        // Simple tag-to-key fields
        config.commonFields().forEach((tag, key) ->
                processorMap.put(tag, FieldProcessors.defaultProcessor(key))
        );

        // Dynamically register all path-based fields
        config.pathFields().forEach((tagName, pathList) -> {
            List<Map.Entry<String, String>> mappings = pathList.stream()
                    .map(p -> Map.entry(p.path(), p.key()))
                    .toList();

            processorMap.put(tagName, FieldProcessors.pathBasedProcessor(mappings));
        });
    }

    public Map<String, String> extractValues(String xmlContent) throws Exception {
        Map<String, String> values = new HashMap<>();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true); // ✅ Important
        XMLStreamReader reader = factory.createXMLStreamReader(new StringReader(xmlContent));
        Deque<String> path = new ArrayDeque<>();

        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                String tag = reader.getLocalName();
                System.out.println("START_ELEMENT: " + tag);

                path.push(tag);

                FieldProcessor processor = processorMap.get(tag);
                if (processor != null) {
                    processor.process(reader, values, path);
                }
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                path.pop();
            }
        }

        reader.close();
        return values;
    }
}
