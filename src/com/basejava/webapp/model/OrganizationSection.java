package com.basejava.webapp.model;

import java.util.HashMap;
import java.util.Map;

public class OrganizationSection extends Section {
    private final Map<String, Organization> organizations = new HashMap<>();

    public void addOrganization(Organization organization) {
        this.organizations.put(organization.getTitle(), organization);
    }
}
