<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List of resumes</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<jsp:include page="WEB-INF/jsp/fragments/header.jsp"/>
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
                    <a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a>
                </td>
                <td>
                    ${resume.getContacts().get(ContactType.MAIL)}
                </td>
                <td>
                    <a href="resume?uuid=${resume.uuid}&action=delete"></a>
                </td>
                <td>
                    <a href="resume?uuid=${resume.uuid}&action=delete"></a>
                </td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>
