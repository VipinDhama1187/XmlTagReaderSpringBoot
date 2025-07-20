package com.example.XmlTagReaderSpringBoot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "iso.extract")
public record ExtractConfig(
        Map<String, String> commonFields,
        Map<String, List<FieldPathConfig>> pathFields,
        Map<String, TargetFieldConfig> targets,
        Map<String, String> defaultValues
) {
    public record FieldPathConfig(String path, String key) {}
    public record TargetFieldConfig(Map<String, String> overrideFields) {}
}