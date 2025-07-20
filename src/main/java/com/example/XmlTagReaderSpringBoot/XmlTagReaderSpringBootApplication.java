package com.example.XmlTagReaderSpringBoot;

import com.example.XmlTagReaderSpringBoot.config.ExtractConfig;
import com.example.XmlTagReaderSpringBoot.service.XmlPlaceholderExtractor;
import com.example.XmlTagReaderSpringBoot.utils.FileReaderUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@SpringBootApplication
@EnableConfigurationProperties(ExtractConfig.class)
public class XmlTagReaderSpringBootApplication implements CommandLineRunner {

	@Autowired
	private XmlPlaceholderExtractor extractor;

	public static void main(String[] args) {
		SpringApplication.run(XmlTagReaderSpringBootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String xml = FileReaderUtils.readFileAsString("pacs4.xml");
		Map<String, String> extractValues = extractor.extractValues(xml);
		System.out.println(extractValues);

		String template = FileReaderUtils.readFileAsString("pacs2_from_pacs4.xml");
		String pacs2 =  StringSubstitutor.replace(template, extractValues, "${", "}");

		System.out.println("Pacs2 xml after replacement : "+ pacs2);
	}
}
