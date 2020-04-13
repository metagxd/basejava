package com.basejava.webapp.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ResumeTest {
    private static final String UUID_1 = "uuid1";
    private static final String NAME = "DUMMY";
    private static final String PERSONAL = "personals example";
    private static final String OBJECTIVE = "objective example";
    private static final String ACHIEVEMENT = "achievement example";
    private static final String QUALIFICATIONS = "qualification example";
    private static final String EXPERIENCE = "experience example";
    private static final String EDUCATION = "education example";
    private static final String PHONE = "8-800-000-00-00";
    private static final String MOBILE = "education example";
    private static final String SKYPE = "skype_example";
    private static final String LINKEDIN = "@example";
    private static final String MAIL = "example@mail.com";
    private static final String GITHUB = "github_example";
    private static final String HOME_PAGE = "www.example.com";
    private static final Organization organization = new Organization("link", "www.company.com", LocalDate.of(1994,10, 1), LocalDate.of(1994,12, 1), "CompanyName", "example description");
    Resume resume = new Resume(UUID_1, NAME);

    @Before
    public void setUp() throws Exception {
        resume.addSection(SectionType.PERSONAL, PERSONAL);
        resume.addSection(SectionType.OBJECTIVE, OBJECTIVE);
        resume.addSection(SectionType.ACHIEVEMENT, ACHIEVEMENT);
        resume.addSection(SectionType.QUALIFICATIONS, QUALIFICATIONS);
        resume.addSection(SectionType.EXPERIENCE, organization);
        resume.addSection(SectionType.EDUCATION, EDUCATION);

        resume.addContact(ContactType.PHONE, PHONE);
        resume.addContact(ContactType.MOBILE, MOBILE);
        resume.addContact(ContactType.SKYPE, SKYPE);
        resume.addContact(ContactType.LINKEDIN, LINKEDIN);
        resume.addContact(ContactType.MAIL, MAIL);
        resume.addContact(ContactType.GITHUB, GITHUB);
        resume.addContact(ContactType.HOME_PAGE, HOME_PAGE);
    }

    @Test
    public void testContact() {
        List<String> expected = Arrays.asList(PHONE, MOBILE, SKYPE, LINKEDIN, MAIL, GITHUB, HOME_PAGE);
        List<String> actual = new ArrayList<>();
        for (ContactType contactType : ContactType.values()) {
            actual.add(resume.getContact(contactType));
        }
        assertThat(actual, is(expected));
    }

    @Test
    public void testSections() {
        List<Object> expected = Arrays.asList(PERSONAL, OBJECTIVE, ACHIEVEMENT, QUALIFICATIONS, organization, EDUCATION);
        List<Object> actual = new ArrayList<>();
        for (SectionType sectionType : SectionType.values()) {
            actual.add(resume.getSection(sectionType));
        }
        assertThat(actual,is(expected));
    }

}