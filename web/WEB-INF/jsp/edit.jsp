<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Resume ${resume.fullName}</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resumes" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Name:</dt>
            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}"></dd>
        </dl>
        <h3>Contacts: </h3>
        <p>
            <c:forEach var="type" items="<%=ContactType.values()%>">
        <dl>
            <dt>${type.title}</dt>
            <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContacts().get(type)}"></dd>
        </dl>
        </c:forEach>
        <hr>
        <button type="submit">Save</button>
        <button onclick="window.history.back()">Reset</button>
        </p></form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
