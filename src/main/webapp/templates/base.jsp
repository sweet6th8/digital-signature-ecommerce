<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="utf-8">

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>GreatKart | One of the Biggest Online Shopping Platform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/navbar.css">
    <jsp:include page="headerResource.jsp"/>


</head>
<body>
    <!-- Navbar -->
   <%@ include file="includes/navbar.jsp" %>

 <!-- Đây là phần mà các trang khác sẽ nhúng nội dung vào -->
    <jsp:include page="${param.contentPage}" />

    <!-- Footer -->
    <%@ include file="includes/footer.jsp" %>
</body>
</html>
