<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Edit Product</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/css/main.css">
    <script src="static/admin/js/main.js"></script>
    <style>
        img {
            width: 200px;
            height: 120px;
        }
        select {
            width: 32.3%;
            margin: 0;
            font-size: 100%;
            padding: 5px 10px;
            font-family: Segoe UI, Helvetica, sans-serif;
            border: 1px solid #D0D0D0;
            box-sizing: border-box;
            border-radius: 20px;
            outline: none;
        }
    </style>
</head>
<body>
<form id="form" action="EditProductServlet" method="post" enctype="multipart/form-data">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <div class="row">
                    <div class="form-group col-md-12">
                                <span>
                                                                        <h5>Edit your product information</h5>
                                </span>
                    </div>
                </div>

                <!-- Product ID -->
                <div class="row">
                    <div class="form-group col-md-6">
                        <label class="control-label">Product ID</label>
                        <input class="form-control" type="text" name="id" readonly
                               value="${PRODUCT.id}" />
                    </div>
                </div>

                <!-- Product Name -->
                <div class="row">
                    <div class="form-group col-md-6">
                        <label class="control-label">Product name</label>
                        <input class="form-control" type="text" name="name" required
                               value="${PRODUCT.name}" />
                    </div>
                </div>

                <!-- Product Description -->
                <div class="row">
                    <div class="form-group col-md-6">
                        <label class="control-label">Description</label>
                        <textarea class="form-control" name="description" required rows="4">${PRODUCT.description}</textarea>
                    </div>
                </div>

                <!-- Product Price -->
                <div class="row">
                    <div class="form-group col-md-6">
                        <label class="control-label">Product price</label>
                        <input class="form-control" type="number" name="price" min="1" step="0.01" required
                               value="${PRODUCT.price}" />
                    </div>
                </div>

                <!-- Product Stock -->
                <div class="row">
                    <div class="form-group col-md-6">
                        <label class="control-label">Stock</label>
                        <input class="form-control" type="number" name="stock" min="0" required
                               value="${PRODUCT.stock}" />
                    </div>
                </div>

                <!-- Product Category -->
                <div class="row">
                    <div class="form-group col-md-6">
                        <label for="category" class="control-label">Category</label>
                        <select name="categoryId" class="form-control" id="category">
                            <c:forEach items="${requestScope.LIST_CATEGORIES}" var="cat">
                                <option value="${cat.id}" ${cat.id == PRODUCT.category.id ? "selected" : ""}>${cat.title}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <!-- Product Photo -->
                <div class="row">
                    <div class="form-group col-md-6">
                        <label class="control-label">Image</label><br>
                        <img src="${pageContext.request.contextPath}${PRODUCT.photo}" alt="Product Image" />
                        <input class="form-control" type="file" name="photo" accept="image/*" />
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <!-- Submit Button -->
                <button class="btn btn-primary" type="submit">Save change</button>
                <a class="btn btn-secondary" href="ManageProductServlet">Cancel</a>
            </div>
        </div>
    </div>
</form>
</body>
</html>