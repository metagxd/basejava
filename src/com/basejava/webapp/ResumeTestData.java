package com.basejava.webapp;

import com.basejava.webapp.model.*;

import static java.time.Month.*;
import java.util.Arrays;


public class ResumeTestData {
    private static final OrganizationSection EDUCATION_SECTION = new OrganizationSection();
    private static final OrganizationSection EXPERIENCE_SECTION = new OrganizationSection();
    private static final Organization SCHOOL = new Organization("School", "school.com",
            new Organization.Period(2002, SEPTEMBER,2014,MAY,"student",""));
    private static final Organization JOB = new Organization("CompanyName", "site.com",
            new Organization.Period(2016, SEPTEMBER,2019,MAY,"Engineer","Machine engineer"),
            new Organization.Period(2019, MAY,"Chief engineer","Chief engineer"));


    public static Resume getResume(String uuid, String name) {
        Resume resume = new Resume(uuid, name);
        EDUCATION_SECTION.addOrganization(SCHOOL);
        EXPERIENCE_SECTION.addOrganization(JOB);
        resume.addSection(SectionType.PERSONAL, "personals example");
        resume.addSection(SectionType.OBJECTIVE, "objective example");
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("achievement1", "achievement2")));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("qualification1", "qualification2")));
        resume.addSection(SectionType.EXPERIENCE, EXPERIENCE_SECTION);
        resume.addSection(SectionType.EDUCATION, EDUCATION_SECTION);
        resume.addContact(ContactType.PHONE, "8-800-000-00-00");
        resume.addContact(ContactType.MOBILE, "8-800-000-00-00");
        resume.addContact(ContactType.SKYPE, "skype_example");
        resume.addContact(ContactType.LINKEDIN, "@example");
        resume.addContact(ContactType.MAIL, "example@mail.com");
        resume.addContact(ContactType.GITHUB, new Link("GitHub", "github.com"));
        resume.addContact(ContactType.STACKOVERFLOW, new Link("Home page", "www.site.com"));
        resume.addContact(ContactType.HOME_PAGE, new Link("Home page", "www.site.com"));
        resume.addSection(SectionType.PERSONAL, "personals example");
        return resume;
    }
}