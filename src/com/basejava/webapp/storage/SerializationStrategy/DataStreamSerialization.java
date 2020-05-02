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

            collectionReader(dataInputStream, inputData ->
                    resume.addContact(ContactType.valueOf(inputData.readUTF()), inputData.readUTF()));

            collectionReader(dataInputStream, dataInputStream1 -> {
                SectionType sectionType = SectionType.valueOf(dataInputStream1.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new TextSection(dataInputStream1.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(sectionType, new ListSection(listReader(dataInputStream1, DataInput::readUTF)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.addSection(sectionType, organizationSectionReader(dataInputStream1, dataInputStream2 ->
                                new Organization(dataInputStream2.readUTF(), linkReader(dataInputStream2),
                                        listReader(dataInputStream2, dataInputStream3 -> new Organization.Period(
                                                dataInputStream3.readInt(),
                                                Month.valueOf(dataInputStream3.readUTF()),
                                                dataInputStream3.readInt(),
                                                Month.valueOf(dataInputStream3.readUTF()),
                                                dataInputStream3.readUTF(),
                                                dataInputStream3.readUTF()
                                        )))));
                        break;
                }
            });
            return resume;
        }
    }

    private void collectionReader(DataInputStream dataInputStream, Reader reader) throws IOException {
        int collectionSize = dataInputStream.readInt();
        for (int i = 0; i < collectionSize; i++) {
            reader.read(dataInputStream);
        }
    }

    private <T> List<T> listReader(DataInputStream dataInputStream, SectionReader<T> sectionReader) throws IOException {
        List<T> list = new ArrayList<>();
        int listSize = dataInputStream.readInt();
        for (int i = 0; i < listSize; i++) {
            list.add(sectionReader.read(dataInputStream));
        }
        return list;
    }

    private OrganizationSection organizationSectionReader(DataInputStream dataInputStream, SectionReader<Organization> organizationSectionReader) throws IOException {
        OrganizationSection organizationSection = new OrganizationSection();
        int quantityOfOrganization = dataInputStream.readInt();
        for (int i = 0; i < quantityOfOrganization; i++) {
            organizationSection.addOrganization(organizationSectionReader.read(dataInputStream));
        }
        return organizationSection;
    }

    private void linkWriter(Link link, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(link.getName());
        if (link.getUrl() == null) {
            dataOutputStream.writeUTF("");
        } else dataOutputStream.writeUTF(link.getUrl());
    }

    private void dateWriter(LocalDate date, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(date.getYear());
        dataOutputStream.writeUTF(String.valueOf(date.getMonth()));
    }

    private Link linkReader(DataInputStream dataInputStream) throws IOException {
        return new Link(dataInputStream.readUTF(), dataInputStream.readUTF());
    }

    private <T> void collectionWriter(DataOutputStream dataOutputStream, Collection<T> collection, Writer<T> writer) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }

    @FunctionalInterface
    private interface Reader {
        void read(DataInputStream dataInputStream) throws IOException;
    }

    @FunctionalInterface
    private interface SectionReader<T> {
        T read(DataInputStream dataInputStream) throws IOException;
    }

    @FunctionalInterface
    private interface Writer<T> {
        void write(T item) throws IOException;
    }
}
