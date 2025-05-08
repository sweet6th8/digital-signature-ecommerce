<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>GreatKart | One of the Biggest Online Shopping Platform</title>
  <%@include file="headerResource.jsp"%>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/reset.css">
</head>
<body>
<jsp:include page="includes/navbar.jsp"/>
  <section class="section-conten padding-y" style="min-height:84vh">
    <div class="card mx-auto" style="max-width: 380px; margin-top:100px;">
      <div class="card-body">
        <h4 class="card-title mb-4">Sign in</h4>
        <form action="${pageContext.request.contextPath}/templates/Login" method="POST">
          <div class="form-group">
            <input type="email" class="form-control" placeholder="Email Address" name="email">
          </div>
          <div class="form-group">
            <input type="password" class="form-control" placeholder="Password" name="password">
          </div>
          <div class="form-group">
            <a href="forgetPassword.jsp" class="float-right">Forget password?</a>
          </div>
          <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block"> Login </button>
          </div>
          <c:if test="${ not  empty requestScope.message}">
            <p class="text-danger">${requestScope.message} !</p>
          </c:if>
          <c:if test="${ not  empty requestScope.success}">
            <p class="text-success">${requestScope.success} !</p>
          </c:if>
        </form>
      </div>
    </div>
    <p class="text-center mt-4">Don't have an account? <a href="register.jsp">Sign up</a></p>
    <br><br>
  </section>
<%@ include file="/templates/includes/footer.jsp" %>
</body>
</html>