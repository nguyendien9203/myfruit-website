<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 3/5/2024
  Time: 7:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    session = request.getSession(false);
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect("login");
    }
%>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
