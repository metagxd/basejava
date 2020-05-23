package com.basejava.webapp.web;

import com.basejava.webapp.ResumeTestData;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.ArrayStorage;
import com.basejava.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeTable extends HttpServlet {
    /*public static final Config config = Config.getInstance();
    private static final SqlStorage storage = new SqlStorage(config.getDbUrl(), config.getDbUser(), config.getDbPassword());*/
    private static final Storage storage = new ArrayStorage();
    private static final Resume resume1 = ResumeTestData.getResume("uuid1", "name1");
    private static final Resume resume2 = ResumeTestData.getResume("uuid2", "name2");
    private static final Resume resume3 = ResumeTestData.getResume("uuid3", "name3");

    static {
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("<style>" +
                "table {border-collapse: collapse}" +
                "table, th, td {padding: 3px; border: 1px solid black}" +
                "</style>" +
                "<table> " +
                "<tr>" +
                "<th>uuid</th>" +
                "<th>First Name</th>" +
                "</tr>"
        );
        for (Resume resume : storage.getAllSorted()) {
            response.getWriter().write("" +
                    "<tr>" +
                    "<td>" + resume.getUuid() + "</td>" +
                    "<td>" + resume.getFullName() + "</td>" +
                    "</tr>");
        }
        response.getWriter().write("</table>");
    }
}
