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
                        collectionWriter(dataOutputStream, ((OrganizationSection) section).getOrganizations().entrySet(), organization -> {
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
            int contactSize = dataInputStream.readInt();
            for (int i = 0; i < contactSize; i++) {
                resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
            }
            int quantityOfSections = dataInputStream.readInt();
            for (int i = 0; i < quantityOfSections; i++) {
                SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new TextSection(dataInputStream.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(sectionType, new ListSection(listReader(dataInputStream)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.addSection(sectionType, organizationSectionReader(dataInputStream));
                        break;
                }
            }
            return resume;
        }
    }

    private void linkWriter(Link link, DataOutputStream dataOutputStream) throws IOException {
        if (link == null) {
            dataOutputStream.writeBoolean(false);
            return;
        } else {
            dataOutputStream.writeBoolean(true);
        }
        dataOutputStream.writeUTF(link.getName());
        dataOutputStream.writeUTF(link.getUrl());
    }

    private void dateWriter(LocalDate date, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(date.getYear());
        dataOutputStream.writeUTF(String.valueOf(date.getMonth()));
    }

    private List<String> listReader(DataInputStream dataInputStream) throws IOException {
        List<String> list = new ArrayList<>();
        int listSize = dataInputStream.readInt();
        for (int i = 0; i < listSize; i++) {
            list.add(dataInputStream.readUTF());
        }
        return list;
    }

    private OrganizationSection organizationSectionReader(DataInputStream dataInputStream) throws IOException {
        OrganizationSection organizations = new OrganizationSection();
        int organizationsSize = dataInputStream.readInt();
        for (int i = 0; i < organizationsSize; i++) {
            organizations.addOrganization(organizationReader(dataInputStream));
        }
        return organizations;
    }

    private Organization organizationReader(DataInputStream dataInputStream) throws IOException {
        Organization organization = new Organization(dataInputStream.readUTF(), linkReader(dataInputStream));
        organization.addPeriods(periodReader(dataInputStream));
        return organization;
    }

    private List<Organization.Period> periodReader(DataInputStream dataInputStream) throws IOException {
        int periodSize = dataInputStream.readInt();
        List<Organization.Period> periods = new ArrayList<>();
        for (int i = 0; i < periodSize; i++) {
            periods.add(new Organization.Period(dataInputStream.readInt(), Month.valueOf(dataInputStream.readUTF()),
                    dataInputStream.readInt(), Month.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF(), dataInputStream.readUTF()));
        }
        return periods;
    }

    private Link linkReader(DataInputStream dataInputStream) throws IOException {
        boolean isLinkExist = dataInputStream.readBoolean();
        if (isLinkExist) {
            return new Link(dataInputStream.readUTF(), dataInputStream.readUTF());
        }
        return null;
    }

    private <T> void collectionWriter(DataOutputStream dataOutputStream, Collection<T> collection, Writer<T> writer) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }

    @FunctionalInterface
    private interface Writer<T> {
        void write(T item) throws IOException;
    }
}
