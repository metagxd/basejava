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
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        Resume resume;
        if (action != null) {
            switch (action) {
                case "delete":
                    storage.delete(uuid);
                    response.sendRedirect("resume");
                    break;
                case "view":
                case "edit":
                    resume = storage.get(uuid);
                    break;
                default:
                    throw new IllegalArgumentException("Action" + action + "not supported");
            }
        } else {
            request.setAttribute("resume", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
        }
    }
}
