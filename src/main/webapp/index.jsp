<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE HTML>

<html lang="en">
<head>
    <meta charset="UTF-8">


    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>GreatKart | One of the Biggest Online Shopping Platform</title>
    <jsp:include page="/templates/headerResource.jsp"/>
</head>
<body>
<!-- Navbar -->
<%@ include file="templates/includes/navbar.jsp" %>
<!-- Main Content Area -->
<div class="container">
<%--    <h1>${requestScope.welcome}</h1>--%>
    <jsp:include page="templates/home.jsp">
        <jsp:param name="contentPage" value="/templates/home.jsp"/>
    </jsp:include>
</div>
<!-- Footer -->
<%@ include file="templates/includes/footer.jsp" %>
<script src="static/js/script.js" type="text/javascript"></script>
</body>
</html>
