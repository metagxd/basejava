<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List of resumes</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table>
        <tr>
            <th>Name</th>
            <th>E-mail</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume"/>
            <tr>
                <td>
                    <a href="resumes?uuid=${resume.uuid}&action=view">${resume.fullName}</a>
                </td>
                <td>
                    <%=
                    ContactType.MAIL.toHtml(resume.getContacts().get(ContactType.MAIL))
                    %>
                </td>
                <td>
                    <a href="resumes?uuid=${resume.uuid}&action=delete"><img src="img/delete.png" alt="Delete"
                                                                             width="24" height="24"></a>
                </td>
                <td>
                    <a href="resumes?uuid=${resume.uuid}&action=edit"><img src="img/edit.png" alt="Edit" width="24"
                                                                           height="24"></a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="resumes?action=add"><img src="img/add.png" alt="Add resume"></a>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
