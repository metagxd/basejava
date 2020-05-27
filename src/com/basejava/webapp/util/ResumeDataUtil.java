package com.basejava.webapp.util;

import com.basejava.webapp.model.*;

import java.util.Arrays;

import static java.time.Month.MAY;
import static java.time.Month.SEPTEMBER;


public class ResumeDataUtil {
    private static final OrganizationSection EDUCATION_SECTION = new OrganizationSection();
    private static final OrganizationSection EXPERIENCE_SECTION = new OrganizationSection();
    private static final Organization SCHOOL = new Organization("School", new Link("school site", ""),
            new Organization.Period(2002, SEPTEMBER, 2014, MAY, "student"));
    private static final Organization JOB = new Organization("CompanyName", new Link("Job Site", "job.com"),
            new Organization.Period(2016, SEPTEMBER, 2019, MAY, "Engineer", "Machine engineer"),
            new Organization.Period(2019, MAY, "Chief engineer", ""));

    static {
        EDUCATION_SECTION.addOrganization(SCHOOL);
        EXPERIENCE_SECTION.addOrganization(JOB);
    }

    public static Resume getResume(String uuid, String name) {
        Resume resume = new Resume(uuid, name);
        resume.addSection(SectionType.PERSONAL, new TextSection("personals example"));
        resume.addSection(SectionType.OBJECTIVE, new TextSection("objective example"));
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("achievement1", "achievement2")));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("qualification1", "qualification2")));
        /*resume.addSection(SectionType.EXPERIENCE, EXPERIENCE_SECTION);
        resume.addSection(SectionType.EDUCATION, EDUCATION_SECTION);*/
        resume.addSection(SectionType.PERSONAL, new TextSection("personals example"));
        resume.addContact(ContactType.PHONE, "8-800-000-00-00");
        resume.addContact(ContactType.MOBILE, "8-800-000-00-00");
        resume.addContact(ContactType.SKYPE, "skype_example");
        resume.addContact(ContactType.LINKEDIN, "@example");
        resume.addContact(ContactType.MAIL, "example@mail.com");
        resume.addContact(ContactType.GITHUB, "github.com");
        resume.addContact(ContactType.STACKOVERFLOW, "www.site.com");
        resume.addContact(ContactType.HOME_PAGE, "www.site.com");
        return resume;
    }

    public static Resume getUpdatedResume(String uuid, String name) {
        Resume resume = getResume(uuid, name);
        resume.addContact(ContactType.PHONE, "8-850-666-88-91");
        resume.addContact(ContactType.MAIL, "mailbox@mail.com");
        String s = resume.getContacts().get(ContactType.MAIL);
        return resume;
    }


}