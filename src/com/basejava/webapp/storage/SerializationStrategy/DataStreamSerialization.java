package com.basejava.webapp.storage.SerializationStrategy;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        listWriter((ListSection) entry.getValue(), dataOutputStream);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        organizationSectionWriter((OrganizationSection) entry.getValue(), dataOutputStream);
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

            for (int i = 0; i < SectionType.values().length; i++) {
                SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                        resume.addSection(SectionType.PERSONAL, new TextSection(dataInputStream.readUTF()));
                        break;
                    case OBJECTIVE:
                        resume.addSection(SectionType.OBJECTIVE, new TextSection(dataInputStream.readUTF()));
                        break;
                    case ACHIEVEMENT:
                        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(listReader(dataInputStream)));
                        break;
                    case QUALIFICATIONS:
                        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(listReader(dataInputStream)));
                        break;
                    case EXPERIENCE:
                        resume.addSection(SectionType.EXPERIENCE, organizationSectionReader(dataInputStream));
                        break;
                    case EDUCATION:
                        resume.addSection(SectionType.EDUCATION, organizationSectionReader(dataInputStream));
                }
            }
            return resume;
        }
    }

    private void organizationSectionWriter(OrganizationSection organizationSection, DataOutputStream dataOutputStream) throws IOException {
        Map<String, Organization> organizations = organizationSection.getOrganizations();
        dataOutputStream.writeInt(organizations.size());
        for (Map.Entry<String, Organization> organizationsEntry : organizations.entrySet()) {
            organizationWriter(organizationsEntry.getValue(), dataOutputStream);
        }
    }

    private void organizationWriter(Organization organization, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(organization.getTitle());
        linkWriter(organization.getHomePage(), dataOutputStream);
        List<Organization.Period> periods = organization.getPeriods();
        dataOutputStream.writeInt(periods.size());
        for (Organization.Period period : periods) {
            periodWriter(period, dataOutputStream);
        }
    }

    private void linkWriter(Link link, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(link.getName());
        dataOutputStream.writeUTF(link.getUrl());
    }

    private void periodWriter(Organization.Period period, DataOutputStream dataOutputStream) throws IOException {
        dateWriter(period.getStartTime(), dataOutputStream);
        dateWriter(period.getEndTime(), dataOutputStream);
        dataOutputStream.writeUTF(period.getPosition());
        dataOutputStream.writeUTF(period.getDescription());
    }

    private void dateWriter(LocalDate date, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(date.getYear());
        dataOutputStream.writeUTF(String.valueOf(date.getMonth()));
    }

    private void listWriter(ListSection listSection, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(listSection.getItems().size());
        for (String string : listSection.getItems()) {
            dataOutputStream.writeUTF(string);
        }
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
        return new Link(dataInputStream.readUTF(), dataInputStream.readUTF());
    }
}
