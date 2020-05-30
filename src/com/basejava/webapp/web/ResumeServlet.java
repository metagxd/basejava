package com.basejava.webapp.web;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;
import com.basejava.webapp.util.TestSqlStorageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class ResumeServlet extends HttpServlet {
    private final Storage storage = TestSqlStorageUtil.getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume;
        try {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        } catch (NotExistStorageException e) {
            resume = new Resume(uuid, fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType sectionType : SectionType.values()) {
            String value = request.getParameter(sectionType.name());
            if (value != null && value.trim().length() != 0) {
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        String[] strings = value.split("\n");
                        ListSection listSection = new ListSection(Arrays.asList(strings));
                        resume.addSection(sectionType, listSection);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        break;
                }
            } else {
                resume.getSections().remove(sectionType);
            }
        }
        try {
            storage.update(resume);
        } catch (NotExistStorageException e) {
            storage.save(resume);
        }
        response.sendRedirect("resumes");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        Resume resume;
        if (action != null) {
            switch (action) {
                case "delete":
                    storage.delete(uuid);
                    response.sendRedirect("resumes");
                    return;
                case "view":
                case "edit":
                    resume = storage.get(uuid);
                    break;
                case "add":
                    resume = new Resume("");
                    break;
                default:
                    throw new IllegalArgumentException("Action" + action + "not supported");
            }
            request.setAttribute("resume", resume);
            request.getRequestDispatcher(("view".equals(action) ? "WEB-INF/jsp/view.jsp" : "WEB-INF/jsp/edit.jsp"))
                    .forward(request, response);
        } else {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
        }
    }
}
