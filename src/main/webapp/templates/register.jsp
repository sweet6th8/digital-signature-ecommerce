<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <head>
    <meta charset="utf-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <title>Register your account </title>
    <%@include file="headerResource.jsp"%>
  </head>
  <body>
<jsp:include page="includes/navbar.jsp"/>

    <section class="section-content container-fluid" style="margin-top: 130px;">
      <div class="card mx-auto" style="max-width: 520px; margin-top: 40px">
        <article class="card-body">
          <header class="mb-4"><h4 class="card-title">Sign up</h4></header>
          <form action="${pageContext.request.contextPath}/templates/RegisterServlet" method="POST">
            <div class="form-row">
              <div class="col form-group">
                <label>First name </label>
                <input
                  name="FirstName"
                  type="text"
                  class="form-control"
                  placeholder="Enter your first name"
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
                  placeholder="Enter your last name"
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
                placeholder="Enter your email address "
                required
                onChange="validateEmail()"
              />
              <p id="error-email" style="color: red; display: none">
                Email invalid
              </p>
              <p  style="color: red;">${message}</p>
            </div>
            <div class="form-group">
              <label>Phone Number</label>
             <input name="phone" type="text" id="phone" placeholder="Enter your phone" oninput="validatePhone()"  required   class="form-control" />
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
                <input type="text" class="form-control" name="Country" value="VN" />
              </div>
              <div class="form-group col-md-6">
                <label>City</label>
                <label>
                  <input list="inputState" class="form-control" name="City" value="Ho Chi Minh" />
                </label>
                <datalist name="city" id="inputState">
                  <option name="city" value="Long An"></option>
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
                        placeholder="Enter your password"
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
                  placeholder="Confirm your password"
                  class="form-control"
                  type="password"
                  required
                  onChange="validateCheckPassword()"
                />
                <p id="Checkpassword-error" style="color: red; display: none">
                  password not match
                </p>
              </div>
            </div>
            <div class="form-group">
              <button type="submit" class="btn btn-primary btn-block">
                Register
              </button>
            </div>
          </form>
        </article>
        <!-- card-body.// -->
      </div>
      <!-- card .// -->
      <p class="text-center mt-4">
        Have an account?  <a href="${pageContext.request.contextPath}/templates/login">Login</a>
      </p>
      <c:set var="message" value="${requestScope.IEmail}"/>
      <c:if test="${not empty message  }">
        <p> ${message}</p>
      </c:if>
      <br /><br />
    </section>
    <footer class="section-footer border-top padding-y">
      <div class="container">
        <p class="float-md-right">&copy Copyright 2019 All rights reserved</p>
        <p>
          <a href="#">Terms and conditions</a>
        </p>
      </div>
    </footer>
    <script src="${pageContext.request.contextPath}/static/js/register.js"></script>
  </body>
</html>
