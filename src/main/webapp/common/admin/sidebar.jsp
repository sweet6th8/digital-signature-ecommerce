<%@page contentType="text/html" pageEncoding="UTF-8"%>   
<!-- Navbar-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<header class="app-header">
    <a class="app-sidebar__toggle" href="#" data-toggle="sidebar"
                                    aria-label="Hide Sidebar"></a>
    <ul class="app-nav">
        <li><a class="app-nav__item" href="${context}/secure/logout"><i class='bx bx-log-out bx-rotate-180'></i> </a></li>
    </ul>
</header>
<div class="app-sidebar__overlay" data-toggle="sidebar"></div>
<aside class="app-sidebar">
    <div class="app-sidebar__user"><img class="app-sidebar__user-avatar" src="${context}/static/admin/images/user.png" alt="Avatar">
        <div>
            <p class="app-sidebar__user-name"><b>${sessionScope.account.firstName} ${sessionScope.account.lastName}</b></p>
            <p class="app-sidebar__user-designation">Welcome back admin </p>
        </div>
    </div>
    <hr>
    <ul class="app-menu">
        <li><a class="app-menu__item ${requestScope.CURRENTSERVLET == "Home" ? "active" : ""}" href="${context}/secure/admin"><i class='app-menu__icon bx bx-home'></i><span
                    class="app-menu__label">Home</span></a></li>
        <li><a class="app-menu__item ${requestScope.CURRENTSERVLET == "User" ? "active" : ""}" href="${context}/secure/ManageUserServlet"><i class='app-menu__icon bx bx-user-voice'></i><span
                    class="app-menu__label">User management</span></a></li>
        <li><a class="app-menu__item ${requestScope.CURRENTSERVLET == "Product" ? "active" : ""}" href="${context}/secure/ManageProductServlet"><i class='app-menu__icon bx bx-purchase-tag-alt'></i><span
                    class="app-menu__label">Product management</span></a></li>
        <li><a class="app-menu__item ${requestScope.CURRENTSERVLET == "Category" ? "active" : ""}" href="${context}/secure/ManageCategoryServlet"><i class='app-menu__icon fa fa-layer-group'></i><span
                    class="app-menu__label">Category management</span></a></li>
        <li><a class="app-menu__item ${requestScope.CURRENTSERVLET == "Order" ? "active" : ""}" href="${context}/secure/ManageOrderServlet"><i class='app-menu__icon bx bx-task'></i><span
                    class="app-menu__label">Order management</span></a></li>
    </ul>
</aside>