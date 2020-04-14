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
    private static final OrganizationSection EDUCATION_SECTION = new OrganizationSection();
    private static final OrganizationSection EXPERIENCE_SECTION = new OrganizationSection();
    private static final Organization SCHOOL = new Organization("", "", LocalDate.of(1980, 9, 1), LocalDate.of(1988, 5, 31), "School", "example");
    private static final Organization JOB = new Organization("link", "www.company.com", LocalDate.of(1994, 10, 1), LocalDate.of(1994, 12, 1), "CompanyName", "example description");
    private static final ListSection ACHIEVEMENT = new ListSection(Arrays.asList("achievement1", "achievement2"));
    private static final ListSection QUALIFICATIONS = new ListSection(Arrays.asList("qualification1", "qualification2"));
    private static final String NAME = "DUMMY";
    private static final String UUID_1 = "uuid1";
    private static final String PHONE = "8-800-000-00-00";
    private static final String PERSONAL = "personals example";
    private static final String OBJECTIVE = "objective example";
    private static final String MOBILE = "8-800-000-00-00";
    private static final String SKYPE = "skype_example";
    private static final String LINKEDIN = "@example";
    private static final String MAIL = "example@mail.com";
    private static final Link GITHUB = new Link("GitHub", "github.com");
    private static final Link HOME_PAGE = new Link("Home page", "www.site.com");
    private final Resume resume = new Resume(UUID_1, NAME);

    @Before
    public void setUp() {
        EDUCATION_SECTION.addOrganization(SCHOOL);
        EXPERIENCE_SECTION.addOrganization(JOB);
        resume.addSection(SectionType.PERSONAL, PERSONAL);
        resume.addSection(SectionType.OBJECTIVE, OBJECTIVE);
        resume.addSection(SectionType.ACHIEVEMENT, ACHIEVEMENT);
        resume.addSection(SectionType.QUALIFICATIONS, QUALIFICATIONS);
        resume.addSection(SectionType.EXPERIENCE, EXPERIENCE_SECTION);
        resume.addSection(SectionType.EDUCATION, EDUCATION_SECTION);
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
        List<Object> expected = Arrays.asList(PHONE, MOBILE, SKYPE, LINKEDIN, MAIL, GITHUB, HOME_PAGE);
        List<Object> actual = new ArrayList<>();
        for (ContactType contactType : ContactType.values()) {
            actual.add(resume.getContact(contactType));
        }
        assertThat(actual, is(expected));
    }

    @Test
    public void testSections() {
        List<Object> expected = Arrays.asList(PERSONAL, OBJECTIVE, ACHIEVEMENT, QUALIFICATIONS, EXPERIENCE_SECTION, EDUCATION_SECTION);
        List<Object> actual = new ArrayList<>();
        for (SectionType sectionType : SectionType.values()) {
            actual.add(resume.getSection(sectionType));
        }
        assertThat(actual, is(expected));
    }

}