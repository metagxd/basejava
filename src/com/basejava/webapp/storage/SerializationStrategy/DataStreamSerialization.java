package com.basejava.webapp.storage.SerializationStrategy;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerialization implements SerializationStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());

            collectionWriter(dataOutputStream, resume.getContacts().entrySet(), item -> {
                dataOutputStream.writeUTF(item.getKey().name());
                dataOutputStream.writeUTF(item.getValue());
            });

            collectionWriter(dataOutputStream, resume.getSections().entrySet(), item -> {
                SectionType sectionType = item.getKey();
                Section section = item.getValue();
                dataOutputStream.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dataOutputStream.writeUTF(section.toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        collectionWriter(dataOutputStream, ((ListSection) section).getItems(), dataOutputStream::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        collectionWriter(dataOutputStream, ((OrganizationSection) section).getOrganizations().entrySet(),
                                organization -> {
                                    dataOutputStream.writeUTF(organization.getValue().getTitle());
                                    linkWriter(organization.getValue().getHomePage(), dataOutputStream);
                                    collectionWriter(dataOutputStream, organization.getValue().getPeriods(), period -> {
                                        dateWriter(period.getStartTime(), dataOutputStream);
                                        dateWriter(period.getEndTime(), dataOutputStream);
                                        dataOutputStream.writeUTF(period.getPosition());
                                        if (period.getDescription() == null) {
                                            dataOutputStream.writeUTF("");
                                        } else {
                                            dataOutputStream.writeUTF(period.getDescription());
                                        }
                                    });
                                });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            Resume resume = new Resume(dataInputStream.readUTF(), dataInputStream.readUTF());

            collectionReader(dataInputStream, () ->
                    resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF()));

            collectionReader(dataInputStream, () -> {
                SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new TextSection(dataInputStream.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(sectionType, new ListSection(listReader(dataInputStream, dataInputStream::readUTF)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        OrganizationSection organizationSection = new OrganizationSection();
                        organizationSection.addListOfOrganization(listReader(dataInputStream, () ->
                                new Organization(dataInputStream.readUTF(),
                                        linkReader(dataInputStream),
                                        listReader(dataInputStream, () ->
                                                new Organization.Period(
                                                        dataInputStream.readInt(),
                                                        Month.valueOf(dataInputStream.readUTF()),
                                                        dataInputStream.readInt(),
                                                        Month.valueOf(dataInputStream.readUTF()),
                                                        dataInputStream.readUTF(),
                                                        dataInputStream.readUTF()
                                                )))));
                        resume.addSection(sectionType, organizationSection);
                        break;
                }
            });
            return resume;
        }
    }

    private <T> void collectionWriter(DataOutputStream dataOutputStream, Collection<T> collection, Writer<T> writer) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }

    private void collectionReader(DataInputStream dataInputStream, Reader reader) throws IOException {
        int collectionSize = dataInputStream.readInt();
        for (int i = 0; i < collectionSize; i++) {
            reader.read();
        }
    }

    private <T> List<T> listReader(DataInputStream dataInputStream, SectionReader<T> sectionReader) throws IOException {
        List<T> list = new ArrayList<>();
        int listSize = dataInputStream.readInt();
        for (int i = 0; i < listSize; i++) {
            list.add(sectionReader.read());
        }
        return list;
    }

    private void linkWriter(Link link, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(link.getName());
        if (link.getUrl() == null) {
            dataOutputStream.writeUTF("");
        } else dataOutputStream.writeUTF(link.getUrl());
    }

    private Link linkReader(DataInputStream dataInputStream) throws IOException {
        return new Link(dataInputStream.readUTF(), dataInputStream.readUTF());
    }

    private void dateWriter(LocalDate date, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(date.getYear());
        dataOutputStream.writeUTF(String.valueOf(date.getMonth()));
    }

    @FunctionalInterface
    private interface Writer<T> {
        void write(T item) throws IOException;
    }

    @FunctionalInterface
    private interface Reader {
        void read() throws IOException;

    }

    @FunctionalInterface
    private interface SectionReader<T> {
        T read() throws IOException;

    }
}
