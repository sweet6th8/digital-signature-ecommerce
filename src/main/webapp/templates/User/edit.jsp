<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <title>FASHION AI  | One of the Biggest Online Shopping Platform</title>
    <%@include file="../headerResource.jsp"%>
  </head>
<body>
<jsp:include page="../includes/navbar.jsp"/>
<c:set var="user" value="${requestScope.user}"/>
<section class="section-content container-fluid" style="margin-top: 100px">
  <div class="card mx-auto " style="max-width: 520px">
    <article class="card-body">
      <header class="mb-4"><h4 class="card-title">Edit profile</h4></header>
      <form action="${pageContext.request.contextPath}/secure/EditServlet" method="POST" enctype="multipart/form-data" >
        <div class="form-row">
          <div class="col form-group">
            <input name="id" value="${user.id}" type="hidden">
            <input name="username" value="${user.username}" type="hidden">
            <label>First name </label>
            <input
                    name="FirstName"
                    type="text"
                    class="form-control"
                    value="${user.fullName.split(" ")[0]}"
                    maxlength="255"
                    autofocus
                    required
            />
          </div>
          <div class="col form-group">
            <label>Last name</label>
            <input
                    name="LastName"
                    type="text"
                    class="form-control"
                    value="${user.fullName.split(" ")[1]}"
                    maxlength="255"
                    required
            />
          </div>
        </div>
        <div class="form-group">
          <label>Email</label>
          <input
                  id="email"
                  name="Email"
                  type="email"
                  class="form-control"
                  value="${user.getEmail()}"
                  required
                  onChange="validateEmail()"
          />
          <p id="error-email" style="color: red; display: none">
            Email invalid
          </p>
        </div>
        <div class="form-group">
          <label>phone</label>
          <input type="text" id="phone" name="Phone"      value="${user.getPhone()}" oninput="validatePhone()"  required   class="form-control" />
          <span id="error-phone" style="color: red; display: none;"></span>


        </div>
        <div class="form-group">
          <label class="custom-control custom-radio custom-control-inline">
            <input
                    name="gender"
                    class="custom-control-input"
                    checked
                    type="radio"
                    value="1"
            />
            <span class="custom-control-label"> Male </span>
          </label>
          <label class="custom-control custom-radio custom-control-inline">
            <input
                    required
                    name="gender"
                    class="custom-control-input"
                    type="radio"
                    value="0"
            />
            <span class="custom-control-label"> Female </span>
          </label>
        </div>
        <div class="form-row">
          <div class="form-group col-md-6">
            <label>Country</label>
            <input type="text" class="form-control" name="Country" value="${user.getAddress().split(",")[0]}" />
          </div>
          <div class="form-group col-md-6">
            <label>City</label>
            <input list="inputState" class="form-control" name="City" value="${user.getAddress().split(",")[1]}" />
            <datalist name="city" id="inputState">
              <option name="city" selected  value="Long An"></option>
              <option name="city" value="Binh Dinh"></option>
              <option name="city" value="Quy Nhon"></option>
              <option name="city" value="Quang Ngai"></option>
              <option name="city" value="Ha Noi"></option>
              <option name="city" value="Ho Chi Minh"></option>
            </datalist>
          </div>
        </div>
        <div class="form-row">
          <div class="form-group col-md-6">
            <label for="password">Password:</label>
            <input
                    required
                    type="password"
                    name="password"
                    id="password"
                    class="form-control"
                    onchange="validatePassword()"
            />
            <p id="password-error" style="color: red; display: none">
              The password must be between 8 and 20 characters, including uppercase letters,
              usually, numbers, and special characters.
            </p>
          </div>
          <div class="form-group col-md-6">
            <label>Confirm password</label>
            <input
                    id="Checkpassword"
                    name="Checkpassword"
                    class="form-control"
                    type="password"
                    required
                    onChange="validateCheckPassword()"
            />
            <p id="Checkpassword-error" style="color: red; display: none">
              password not match
            </p>
          </div>
          <div class="form-group col">
            <label>Avatar</label>
            <input
                    id="avatar"
                    name="avatar"
                    class="form-control"
                    type="file"
                    accept="image/*"
                    required
            />

          </div>
        </div>
        <div class="form-group">
          <button type="submit" class="btn btn-primary btn-block">
            Update Profile
          </button>
        </div>
      </form>
    </article>
    <!-- card-body.// -->
  </div>
  <!-- card .// -->
  <br /><br />
</section>
<jsp:include page="../includes/footer.jsp"/>
<script src="${pageContext.request.contextPath}/static/js/register.js"></script>
</body>
</html>
