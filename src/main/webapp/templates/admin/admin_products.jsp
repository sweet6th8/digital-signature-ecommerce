<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="../../common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Danh sách sản phẩm | Quản trị Admin</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Main CSS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/css/main.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
    <link rel="stylesheet" type="text/css"
          href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
</head>

<body onload="time()" class="app sidebar-mini rtl">
<%@include file="../../common/admin/sidebar.jsp" %>
<main class="app-content">
    <div class="app-title">
        <ul class="app-breadcrumb breadcrumb side">
            <li class="breadcrumb-item active"><a href="#"><b>List products</b></a></li>
        </ul>
        <div id="clock"></div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <div class="tile-body">
                    <div class="row element-button">
                        <div class="col-sm-2">
                            <a class="btn btn-add btn-sm" href="InsertProductServlet" title="Thêm"><i
                                    class="fas fa-plus"></i> Create new product</a>
                        </div>
                        <div class="col-sm-2">
                            <a class="btn btn-delete btn-sm print-file" type="button" title="In"
                               onclick="myApp.printTable()"><i
                                    class="fas fa-print"></i>Print products</a>
                        </div>
                    </div>
                    <h3 style="color: green; text-align: center; margin: 20px 0">${requestScope.mess}</h3>

                    <!-- PRODUCT TABLE -->
                    <table class="table table-hover table-bordered" id="sampleTable">
                        <thead>
                        <tr>
                            <th>Product Code</th>
                            <th>Product Name</th>
                            <th>describe</th>
                            <th>Price</th>
                            <th>image</th>
                            <th>Inventory</th>
                            <th>directory</th>
                            <th>condition</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- Iterate through the list of products -->
                        <c:forEach items="${requestScope.LISTPRODUCTS}" var="product">
                            <tr>
                                <td>${product.id}</td>
                                <td>${product.name}</td>
                                <td>${product.description}</td>
                                <td>${product.price}</td>
                                <td>
                                    <img src="${pageContext.request.contextPath}${product.photo}" alt="${product.name}"
                                         style="width: 100px; height: auto;">
                                </td>
                                <td>${product.stock}</td>
                                <td>${product.category.title}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${product.isInStock()}">In Stock</c:when>
                                        <c:otherwise>Stock</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="EditProductServlet?pid=${product.id}"
                                       class="btn btn-primary btn-sm">edit</a>
                                    <a href="DeleteProductServlet?pid=${product.id}" class="btn btn-danger btn-sm"
                                       onclick="return confirm('Are you sure you want to remove this product?');" >Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</main>
<div class="modal fade" id="modal_box" role="dialog"></div>
<!-- Essential javascripts for application to work-->
<script src="view/assets/admin/js/jquery-3.2.1.min.js"></script>
<script src="view/assets/admin/js/popper.min.js"></script>
<script src="view/assets/admin/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="view/assets/admin/js/main.js"></script>
<!-- The javascript plugin to display page loading on top-->
<!-- Page specific javascripts-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
<!-- Data table plugin-->
<script type="text/javascript" src="view/assets/admin/js/plugins/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="view/assets/admin/js/plugins/dataTables.bootstrap.min.js"></script>
<script type="text/javascript">
    function confirmDelete(modalID, cid) {
        let modalElement = document.getElementById(modalID);
        let modal = '<div class="modal-dialog modal-dialog-centered" role="document" style="text-align:center">' +
            '<div class="modal-content" style="width:500px; margin: 0 auto">' +
            '<div class="modal-header" style="color: black; font-size:28px; font-weight: 600; margin: 15px auto">Cảnh báo</div>' +
            '<div class="swal-text">Bạn có chắc chắn là muốn xóa danh mục này?</div>' +
            '<div class="swal-footer">' +
            '<div class="swal-button-container">' +
            '<button data-dismiss="modal" aria-hidden="true" class="swal-button swal-button--cancel">Hủy bỏ</button>' +
            '</div>' +
            '<div class="swal-button-container">' +
            '<a href="DeleteCategoryServlet?cid=' + cid + '" class="swal-button swal-button--confirm">Xác nhận</a>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';
        console.log(modal);
        let result = modalElement.innerHTML = modal;
        return result;
    }
</script>
<script>
    //In
    var myApp = new function () {
            this.printTable = function () {
                var tab = document.getElementById('sampleTable');
                var win = window.open('', '', 'height=700,width=700');
                win.document.write(tab.outerHTML);
                win.document.close();
                win.print();
            };
        }
    ;
</script>
</body>

</html>