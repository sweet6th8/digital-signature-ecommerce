<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib.jsp"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>More Products | Admin</title> Admin
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Main CSS-->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/css/main.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
</head>
<body class="app sidebar-mini rtl">
<%@include file="../../common/admin/sidebar.jsp"%>
<main class="app-content">
    <div class="app-title">
        <ul class="app-breadcrumb breadcrumb side">
            <li class="breadcrumb-item"><a href="ManageProductServlet">Manage Products</a></li>
            <li class="breadcrumb-item active"><a href="#">Add a product</a></li>
        </ul>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <!-- Display success or error messages -->
                <c:if test="${not empty requestScope.mess}">
                    <div class="alert alert-info" role="alert">
                            ${requestScope.mess}
                    </div>
                </c:if>
                <h3 class="tile-title">Add new product</h3>
                <form action="InsertProductServlet" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label class="control-label">Product name</label>
                        <input class="form-control" type="text" name="name" placeholder="Enter product name" required>
                    </div>
                    <div class="form-group">
                        <label class="control-label">Description</label>
                        <textarea class="form-control" name="description" rows="4" placeholder="Enter product description" required></textarea>
                    </div>
                    <div class="form-group">
                        <label class="control-label">Product price</label>
                        <input class="form-control" type="number" name="price" min="1" step="0.01" placeholder="Enter product price" required>
                    </div>
                    <div class="form-group">
                        <label class="control-label">Inventory quantity</label>
                        <input class="form-control" type="number" name="stock" min="0" placeholder="Enter stock quantity" required>
                    </div>
                    <div class="form-group">
                        <label class="control-label">Category</label>
                        <select class="form-control" name="categoryId" required>
                            <!-- Display list of categories -->
                            <c:forEach items="${requestScope.LIST_CATEGORIES}" var="category">
                                <option value="${category.id}">${category.title}</option>
                            </c:forEach>
                            <!-- If no categories are available -->
                            <c:if test="${empty requestScope.LIST_CATEGORIES}">
                                <option value="">No categories available</option>
                            </c:if>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="control-label">Hình ảnh</label>
                        <input class="form-control" type="file" name="photo" accept="image/*" required>
                    </div>
                    <div class="tile-footer">
                        <button class="btn btn-primary" type="submit">Lưu sản phẩm</button>
                        <a class="btn btn-secondary" href="ManageProductServlet">Hủy bỏ</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>
</body>
</html>