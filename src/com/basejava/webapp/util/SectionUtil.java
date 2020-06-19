package com.basejava.webapp.util;

import com.basejava.webapp.model.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SectionUtil {
    public static OrganizationSection toOrganizationSection(Section section) {
        return (OrganizationSection) section;
    }

    public static void parseOrganizationSection(HttpServletRequest request, SectionType sectionType, Resume resume) {
        resume.removeSection(sectionType);
        int experienceSize;
        if (!request.getParameter("EXPERIENCE").equals("")) {
            experienceSize = Integer.parseInt(request.getParameter("EXPERIENCE"));
        } else {
            experienceSize = 0;
        }
        int size = Integer.parseInt(request.getParameter(sectionType.name()));
        int start;
        int end;
        if (sectionType.name().equals("EXPERIENCE")) {
            start = 0;
            end = size;
        } else {
            start = experienceSize;
            end = start + size;
        }
        if (end == 0) {
            return;
        }
        OrganizationSection organizationSection = new OrganizationSection();
        List<Integer> periods = new ArrayList<>();
        Arrays.asList(request.getParameterMap().get("numOfPeriods")).forEach(s -> periods.add(Integer.parseInt(s)));
        for (int i = start; i < end; i++) {
            String organizationTitle = request.getParameterValues("organizationTitle")[i];
            if (organizationTitle.equals("")) {
                break;
            }
            Link url = new Link(organizationTitle + " site", request.getParameterValues("url")[i]);
            int periodsStartPosition = 0;
            if (i != 0) {
                for (int j = 0; j < i; j++) {
                    periodsStartPosition += periods.get(j);
                }
            }
            Organization organization = new Organization(organizationTitle, url);
            for (int j = periodsStartPosition; j < periodsStartPosition + periods.get(i); j++) {
                String position = request.getParameterValues("position")[j];
                if (!position.equals("")) {
                    LocalDate startTime = LocalDate.parse(request.getParameterValues("startTime")[j], DateUtil.formatter());
                    LocalDate endTime = LocalDate.parse(request.getParameterValues("endTime")[j], DateUtil.formatter());
                    String description = request.getParameterValues("description")[j];
                    Organization.Period period = new Organization.Period(startTime, endTime, position, description);
                    organization.addPeriod(period);
                }
            }
            organizationSection.addOrganization(organization);
        }
        if (organizationSection.getOrganizations().size() != 0) {
            resume.addSection(sectionType, organizationSection);
        }
    }
}
