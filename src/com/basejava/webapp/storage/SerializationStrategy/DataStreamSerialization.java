package com.basejava.webapp.storage.SerializationStrategy;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class DataStreamSerialization implements SerializationStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
            dataOutputStream.writeInt(resume.getContacts().size());
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                dataOutputStream.writeUTF(entry.getKey().name());
                dataOutputStream.writeUTF(entry.getValue());
            }
            for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
                SectionType sectionType = entry.getKey();
                dataOutputStream.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dataOutputStream.writeUTF(entry.getValue().toString());
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        organizationSectionWriter((OrganizationSection) entry.getValue(),dataOutputStream);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        listWriter((ListSection) entry.getValue(), dataOutputStream);
                        break;
                }
            }
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
            return resume;
        }
    }

    private void organizationSectionWriter(OrganizationSection organizationSection, DataOutputStream dataOutputStream) throws IOException {
        Map<String, Organization> organizations = organizationSection.getOrganizations();
        for (Map.Entry<String, Organization> organizationsEntry : organizations.entrySet()) {
            dataOutputStream.writeUTF(organizationsEntry.getKey());
            organizationWriter(organizationsEntry.getValue(),dataOutputStream);
        }
    }

    private void organizationWriter(Organization organization, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(organization.getTitle());
        linkWriter(organization.getHomePage(), dataOutputStream);
        List<Organization.Period> periods = organization.getPeriods();
        for (Organization.Period period : periods) {
            periodWriter(period, dataOutputStream);
        }
    }

    private void linkWriter(Link link, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(link.getName());
        dataOutputStream.writeUTF(link.getUrl());
    }

    private void periodWriter(Organization.Period period, DataOutputStream dataOutputStream) throws IOException {
        dateWriter(period.getStartTime(),dataOutputStream);
        dateWriter(period.getEndTime(), dataOutputStream);
        dataOutputStream.writeUTF(period.getPosition());
        dataOutputStream.writeUTF(period.getDescription());
    }

    private void dateWriter(LocalDate date, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(date.getYear());
        dataOutputStream.writeUTF(String.valueOf(date.getMonth()));
    }

    private void listWriter(ListSection listSection, DataOutputStream dataOutputStream) throws IOException {
        for (String string : listSection.getItems()) {
            dataOutputStream.writeUTF(string);
        }
    }
}
