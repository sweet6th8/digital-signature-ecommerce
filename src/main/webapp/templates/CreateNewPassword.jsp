<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>

    <meta
            name="viewport"
            content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <title>GreatKart | One of the Biggest Online Shopping Platform</title>
    <jsp:include page="headerResource.jsp"/>

</head>
<body>
<jsp:include page="includes/navbar.jsp"/>
<section class="section-conten padding-y" style="min-height: 84vh">
    <div class="card mx-auto" style="max-width: 380px; margin-top: 100px">
        <div class="card-body">
            <h4 class="card-title mb-4">Create a new password</h4>
            <form action="${pageContext.request.contextPath}/templates/CreatePassword" method="Post">
                <div class="form-row">
                    <label for="password">New password:</label>
                    <input type="password" name="password" id="password" class="form-control"
                           onchange="validatePassword()" required>
                    <p id="password-error" style="color: red; display: none;">The password must be between 8 and 20 characters, including uppercase letters,
                        usually, numbers, and special characters.</p>

                </div>
                <div class="form-row">
                    <label>Confirm new password  </label>
                    <input id="Checkpassword" name="Checkpassword" class="form-control" type="password"
                           onChange="validateCheckPassword()" required>
                    <p id="Checkpassword-error" style="color: red; display: none;">Passwords do not match</p>
                </div>
                <div class="form-group mt-4">
                    <button type="submit" class="btn btn-primary btn-block">
                        Update your password
                    </button>
                </div>
            </form>
        </div>
    </div>
    <p class="text-center mt-4">
        Don't have an account? <a href="register.jsp">Sign up</a>
    </p>
    <br/><br/>
</section>
<script>
    function validateCheckPassword() {
        const password = document.getElementById("password").value;
        const checkPassword = document.getElementById("Checkpassword").value;
        const checkPasswordError = document.getElementById("Checkpassword-error");

        // Kiểm tra xem mật khẩu có khớp không
        if (password === checkPassword) {
            checkPasswordError.style.display = "none"; // Ẩn thông báo lỗi nếu mật khẩu khớp
        } else {
            checkPasswordError.style.display = "block"; // Hiện thông báo lỗi nếu mật khẩu không khớp
        }
    }

</script>
</body>
</html>
