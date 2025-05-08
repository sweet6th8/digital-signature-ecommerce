<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Staff List | Admin Management</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Main CSS-->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/css/main.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">

    <!-- Font-icon css-->
    <link rel="stylesheet"
          href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
</head>

<body onload="time()" class="app sidebar-mini rtl">
<%@include file="../../common/admin/sidebar.jsp"%>
<main class="app-content">
    <div class="app-title">
        <ul class="app-breadcrumb breadcrumb side">
            <li class="breadcrumb-item active"><a href="#"><b>List of users</b></a></li>
        </ul>
        <div>${requestScope.mess}</div>
        <div id="clock"></div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <div class="tile-body">
                    <div class="row element-button">
                        <div class="col-sm-2">
                            <a class="btn btn-add btn-sm" href="ManageUserServlet?action=Insert" title="More"><i class="fas fa-plus"></i>
                                Create a new account</a>
                        </div>
                        <div class="col-sm-2">
                            <a class="btn btn-delete btn-sm print-file" type="button" title="In" onclick="myApp.printTable()"><i
                                    class="fas fa-print"></i> Print data</a>
                        </div>
                    </div>
                    <h3 style="color: green; text-align: center; margin: 20px 0">${requestScope.mess}</h3>
                    <table class="table table-hover table-bordered js-copytextarea" cellpadding="0" cellspacing="0" border="0"
                           id="sampleTable">
                        <thead>
                        <tr>
                            <th>Customer ID</th>
                            <th>Username</th>
                            <th>Email</th>
                            <th>Address</th>
                            <th>Phone Number</th>
                            <th>Gender</th>
                            <th>Function</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.LISTUSERS}" var="u">
                            <tr>
                                <td>${u.getId()}</td>
                                <td>${u.getFullName()}</td>
                                <td>${u.getEmail()}</td>
                                <td>${u.getAddress()}</td>
                                <td>${u.getPhone()}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${u.isGender()}">Nam</c:when>
                                        <c:otherwise>Nữ</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/secure/EditUserServlet?uid=${u.getId()}" class="btn btn-edit" title="Edit"><i class="fas fa-edit"></i></a>
                                    <a href="${pageContext.request.contextPath}/secure/DeleteUserServlet?uid=${u.getId()}" class="btn btn-delete" title="Delete"><i class="fas fa-trash-alt"></i></a>
                                </td>
                            </tr>
                        </c:forEach>
                        <div class="modal fade" id="modal_box" role="dialog"></div>
                        <!-- Essential javascripts for application to work-->
                        <script src="${pageContext.request.contextPath}/static/admin/js/jquery-3.2.1.min.js"></script>
                        <script src="${pageContext.request.contextPath}/static/admin/js/popper.min.js"></script>
                        <script src="${pageContext.request.contextPath}/static/admin/js/bootstrap.min.js"></script>
                        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
                        <script src="${pageContext.request.contextPath}/static/admin/js/main.js"></script>
                        <!-- Page specific javascripts-->
                        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
                        <!-- Data table plugin-->
                        <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/plugins/jquery.dataTables.min.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/plugins/dataTables.bootstrap.min.js"></script>
                        <script type="text/javascript">$('#sampleTable').DataTable();</script>
                        <script type="text/javascript">
                            function confirmDelete(modalID, uid) {
                                let modalElement = document.getElementById(modalID);
                                let modal = '<div class="modal-dialog modal-dialog-centered" role="document" style="text-align:center">' +
                                    '<div class="modal-content" style="width:500px; margin: 0 auto">' +
                                    '<div class="modal-header" style="color: black; font-size:28px; font-weight: 600; margin: 15px auto">Cảnh báo</div>' +
                                    '<div class="swal-text">Bạn có chắc chắn là muốn xóa người dùng này?</div>' +
                                    '<div class="swal-footer">' +
                                    '<div class="swal-button-container">' +
                                    '<button data-dismiss="modal" aria-hidden="true" class="swal-button swal-button--cancel">Hủy bỏ</button>' +
                                    '</div>' +
                                    '<div class="swal-button-container">' +
                                    '<a href="DeleteUserServlet?uid=' + uid + '" class="swal-button swal-button--confirm">Xác nhận</a>' +
                                    '</div>' +
                                    '</div>' +
                                    '</div>' +
                                    '</div>';
                                console.log(modal);
                                let result = modalElement.innerHTML = modal;
                                return result;
                            }

                            //Thời Gian
                            function time() {
                                var today = new Date();
                                var weekday = new Array(7);
                                weekday[0] = "Chủ Nhật";
                                weekday[1] = "Thứ Hai";
                                weekday[2] = "Thứ Ba";
                                weekday[3] = "Thứ Tư";
                                weekday[4] = "Thứ Năm";
                                weekday[5] = "Thứ Sáu";
                                weekday[6] = "Thứ Bảy";
                                var day = weekday[today.getDay()];
                                var dd = today.getDate();
                                var mm = today.getMonth() + 1;
                                var yyyy = today.getFullYear();
                                var h = today.getHours();
                                var m = today.getMinutes();
                                var s = today.getSeconds();
                                m = checkTime(m);
                                s = checkTime(s);
                                nowTime = h + " giờ " + m + " phút " + s + " giây";
                                if (dd < 10) {
                                    dd = '0' + dd
                                }
                                if (mm < 10) {
                                    mm = '0' + mm
                                }
                                today = day + ', ' + dd + '/' + mm + '/' + yyyy;
                                tmp = '<span class="date"> ' + today + ' - ' + nowTime +
                                    '</span>';
                                document.getElementById("clock").innerHTML = tmp;
                                clocktime = setTimeout("time()", "1000", "Javascript");

                                function checkTime(i) {
                                    if (i < 10) {
                                        i = "0" + i;
                                    }
                                    return i;
                                }
                            }
                            //In dữ liệu
                            var myApp = new function () {
                                this.printTable = function () {
                                    var tab = document.getElementById('sampleTable');
                                    var win = window.open('', '', 'height=700,width=700');
                                    win.document.write(tab.outerHTML);
                                    win.document.close();
                                    win.print();
                                }
                            }

                        </script>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</main>
</body>
</html>