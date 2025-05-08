<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Edit</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/css/main.css">
    <script src="${pageContext.request.contextPath}/static/admin/js/main.js"></script>
    <style>
        img{
            width: 200px;
            height: 120px;
        }
        select {
            width: 32.3%;
            margin: 0;
            font-size: 100%;
            padding: 5px 10px 5px 10px;
            font-family: Segoe UI, Helvetica, sans-serif;
            border: 1px solid #D0D0D0;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
            border-radius: 20px;
            outline: none;
        }
    </style>
<body>
<form id="form" action="${pageContext.request.contextPath}/secure/EditUserServlet" method="post" enctype="multipart/form-data">
    <div class="" >
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-md-12">
 <span class="thong-news-thanh-toan">
 <h5>Edit user information</h5>
 </span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-md-6">
                            <label class="control-label">Login name</label>
                            <input class="form-control" type="text" readonly name="username" value="${requestScope.user.username}">
                        </div>
                        <div class="form-group col-md-6">
                            <label class="control-label">First name</label>
                            <input class="form-control" type="text" name="firstname" value="${requestScope.user.getFullName().split(' ')[0]}">
                        </div>
                        <div class="form-group col-md-6">
                            <label class="control-label">Last name</label>
                            <input class="form-control" type="text" name="lastname" value="${requestScope.user.getFullName().split(' ')[1]}">
                        </div>
                        <div class="form-group col-md-6">
                            <label class="control-label">Phone number</label>
                            <input class="form-control" type="text" name="phone" value="${requestScope.user.phone}">
                        </div>
                        <div class="form-group col-md-6">
                            <label class="control-label">Email</label>
                            <input class="form-control" type="text" name="email" value="${requestScope.user.email}">
                        </div>
                        <div class="form-group col-md-6">
                            <label class="control-label">Address</label>
                            <input class="form-control" type="text" name="address" value="${requestScope.user.address}">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="exampleSelect1" class="control-label">Administrative permissions</label>
                            <input hidden name="user_id" value="${requestScope.user.role}">
                            <select name="permission" class="form-control" id="exampleSelect1">
                                <option value="True">Allow</option>
                                <option value="False">Cancel</option>
                            </select>
                        </div>
                        <div class="form-group col-md-12">
                            <label class="control-label">Representative image</label>
                            <div id="myfileupload">
                                <input type="file" id="uploadfile" name="avatar" value="${requestScope.user.getImage()}"  accept="image/*" onchange="readURL(this);" />
                            </div>
                            <div id="thumbbox">
                                <img height="450" width="400" alt="Thumb image" id="thumbimage" style="display: none" />
                                <a class="removeimg" href="javascript:"></a>
                            </div>
                        </div>
                    </div>
                    <BR>
                    <button onclick="setValue()" class="btn btn-save" name="action" value="Update" type="submit">Lưu lại</button>
                    <a class="btn btn-cancel" data-dismiss="modal" href="${pageContext.request.contextPath}/secure/ManageUserServlet">Hủy bỏ</a>
                    <BR>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>