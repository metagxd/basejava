package com.basejava.webapp.web;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.Storage;
import com.basejava.webapp.util.TestSqlStorageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    private final Storage storage = TestSqlStorageUtil.getStorage();

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
