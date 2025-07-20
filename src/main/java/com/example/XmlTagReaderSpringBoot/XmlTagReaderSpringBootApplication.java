package com.example.XmlTagReaderSpringBoot;

import com.example.XmlTagReaderSpringBoot.config.ExtractConfig;
import com.example.XmlTagReaderSpringBoot.service.XmlPlaceholderExtractor;
import com.example.XmlTagReaderSpringBoot.utils.FileReaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.nio.file.Files;
import java.nio.file.Paths;

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
		System.out.println(extractor.extractValues(xml));
	}
}
