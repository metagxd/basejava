<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
<%@ page import="com.basejava.webapp.util.SectionUtil" %>
<%@ page import="com.basejava.webapp.model.Organization" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <jsp:useBean id="SectionUtil" class="com.basejava.webapp.util.SectionUtil"/>
    <jsp:useBean id="expSection" type="com.basejava.webapp.model.OrganizationSection" scope="request"/>
    <jsp:useBean id="eduSection" type="com.basejava.webapp.model.OrganizationSection" scope="request"/>
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
            <dd><input type="text" name="fullName" size="50" value="<c:out value="${resume.fullName}"/>"></dd>
        </dl>
        <h3>Contacts: </h3>
        <p>
            <c:forEach var="type" items="<%=ContactType.values()%>">
        <dl>
            <dt>${type.title}</dt>
            <dd>
                <input type="text" name="<c:out value="${type.name()}"/>" size=30
                       value="<c:out value="${resume.getContacts().get(type)}"/>">
            </dd>
        </dl>
        </c:forEach>
        <hr>
        <h3>Sections</h3>
        <h5>For achievement and qualification: one item in one row!</h5>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd>
                    <c:choose>
                        <c:when test="${type.name()=='PERSONAL'||type.name()=='OBJECTIVE'}">
                            <input type="text" name="<c:out value="${type.name()}"/>" size=60
                                   value="<c:out value="${resume.sections.get(type)}"/>">
                        </c:when>
                        <c:when test="${type.name()=='ACHIEVEMENT'||type.name()=='QUALIFICATIONS'}">
                            <textarea name="<c:out value="${type.name()}"/>" rows="5"
                                      cols="50"><c:out value="${resume.sections.get(type)}"/></textarea>
                        </c:when>
                        <c:when test="${type.name()=='EXPERIENCE'||type.name()=='EDUCATION'}">
                            <jsp:useBean id="organizationSection"
                                         class="com.basejava.webapp.model.OrganizationSection"/>
                            <c:choose>
                                <c:when test="${type.name()=='EXPERIENCE'}">
                                    <c:set var="organizationSection" value="${expSection}"/>
                                </c:when>
                                <c:when test="${type.name()=='EDUCATION'}">
                                    <c:set var="organizationSection" value="${eduSection}"/>
                                </c:when>
                            </c:choose>
                            <input name="${type.name()}"
                                   value="${organizationSection.organizations.size()}"
                                   type="hidden">
                            <c:forEach var="organizations"
                                       items="${organizationSection.getOrganizations().values()}">
                                <jsp:useBean id="organizations" class="com.basejava.webapp.model.Organization"/>
                                <input type="text" name="organizationTitle" size="50"
                                       value="<c:out value="${organizations.title}"/>">
                                <input type="url" name="url" size="20"
                                       value="<c:out value="${organizations.homePage.url}"/>"><br>
                                <input type="hidden" name="numOfPeriods" value="${organizations.periods.size()}">
                                <c:forEach var="periods" items="${organizations.periods}">
                                    from
                                    <input type="date" name="startTime" size="20" value="${periods.startTime}">
                                    to
                                    <input type="date" name="endTime" size="20" value="${periods.endTime}">
                                    <br>
                                    position
                                    <input type="text" name="position" size="20" value="${periods.position}">
                                    <br>
                                    <textarea name="description" rows="5" cols="50">${periods.description}</textarea>
                                    <br>
                                </c:forEach>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                </dd>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Save</button>
        <button onclick="window.history.back()">Reset</button>
        </p></p></form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>