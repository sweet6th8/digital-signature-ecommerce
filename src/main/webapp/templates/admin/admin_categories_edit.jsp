<%--
    Document   : admin_categories_edit.jsp
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Edit Category | Admin</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Main CSS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/css/main.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
    <link rel="stylesheet" type="text/css"
          href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
    <script src="http://code.jquery.com/jquery.min.js" type="text/javascript"></script>

    <style>
        .form-container {
            background: #ffffff;
            border-radius: 10px;
            padding: 20px 30px;
            box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.1);
            margin: 20px auto;
            width: 60%;
        }

        .form-container h1 {
            font-size: 24px;
            color: #333;
            text-align: center;
            margin-bottom: 20px;
            font-weight: bold;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            font-size: 14px;
            font-weight: 600;
            color: #555;
        }

        input[type="text"], textarea {
            width: 100%;
            padding: 10px 15px;
            font-size: 14px;
            color: #333;
            border: 1px solid #ddd;
            border-radius: 5px;
            transition: border-color 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
        }

        input[type="text"]:focus, textarea:focus {
            border-color: #007BFF;
            outline: none;
            box-shadow: 0px 0px 5px rgba(0, 123, 255, 0.5);
        }

        textarea {
            resize: none;
            height: 150px;
        }

        button[type="submit"] {
            background: #007BFF;
            border: none;
            color: white;
            padding: 12px 20px;
            font-size: 16px;
            font-weight: bold;
            border-radius: 5px;
            margin-top: 10px;
            cursor: pointer;
            width: 100%;
            transition: background-color 0.3s ease-in-out;
        }

        button[type="submit"]:hover {
            background: #0056b3;
        }
    </style>
</head>

<body onload="time()" class="app sidebar-mini rtl">
<!-- Navbar -->
<%@include file="../../common/admin/sidebar.jsp"%>
<main class="app-content">
    <!-- Breadcrumb and Clock -->
    <div class="app-title">
        <ul class="app-breadcrumb breadcrumb side">
            <li class="breadcrumb-item"><a href="admin_categories.jsp"><i class="fa fa-list"></i> Category List</a></li>
            <li class="breadcrumb-item active"><a href="#"><b>Edit Category</b></a></li>
        </ul>
        <div id="clock"></div>
    </div>

    <!-- Main Content -->
    <div class="form-container">
        <h1>Edit Category</h1>
        <form action="${pageContext.request.contextPath}/secure/EditCategoryServlet" method="post">
            <input type="hidden" name="id" value="${category.id}">

            <div class="form-group">
                <label for="title">Category Title</label>
                <input type="text" id="title" name="title" value="${category.title}" required>
            </div>

            <div class="form-group">
                <label for="description">Description</label>
                <textarea id="description" name="description" required>${category.description}</textarea>
            </div>

            <button type="submit">Save Changes</button>
        </form>
    </div>
</main>

<!-- Footer Scripts -->
<script>
    function time() {
        const clock = document.getElementById("clock");
        const now = new Date();
        clock.innerHTML = now.toLocaleString();
    }
</script>
</body>
</html>