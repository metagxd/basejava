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
    <h2>${resume.fullName}&nbsp;<a href="resumes?uuid=${resume.uuid}&action=edit"><img src="img/edit.png" alt="Edit" width="24" height="24"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<com.basejava.webapp.model.ContactType,java.lang.String>"/>
        <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <hr/>
    
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
