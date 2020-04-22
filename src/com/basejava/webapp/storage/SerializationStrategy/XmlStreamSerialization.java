package com.basejava.webapp.storage.SerializationStrategy;

import com.basejava.webapp.model.*;
import com.basejava.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerialization implements SerializationStrategy {
    private final XmlParser xmlParser;

    public XmlStreamSerialization() {
        xmlParser = new XmlParser(
                Resume.class, Organization.class, OrganizationSection.class, ListSection.class,
                Link.class, Organization.Period.class, TextSection.class
        );
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
        }
    }
}
