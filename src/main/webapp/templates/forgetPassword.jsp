<!DOCTYPE HTML>
<html lang="en">
<head>
<meta charset="utf-8">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<title>GreatKart | One of the Biggest Online Shopping Platform</title>
    <jsp:include page="headerResource.jsp"/>
</head>
<body>
<jsp:include page="includes/navbar.jsp"/>
<section class="section-conten padding-y" style="min-height:84vh">
	<div class="card mx-auto" style="max-width: 380px; margin-top:100px;">
      <div class="card-body">
      <h4 class="card-title mb-4">Forget Password</h4>
      <form action="${pageContext.request.contextPath}/templates/Mail" method="post">
          <div class="form-group">
			 <input type="email" class="form-control" placeholder="Enter your email" name="email" >
          </div> <!-- form-group// -->
          <div class="form-group">
              <button type="submit" class="btn btn-primary btn-block"> Get OTP  </button>
          </div> <!-- form-group// -->
      </form>
      </div> <!-- card-body.// -->
    </div> <!-- card .// -->
     <p class="text-center mt-4">Don't have an account? <a href="register.jsp">Sign up</a></p>
     <br><br>
</section>
<footer class="section-footer border-top padding-y">
	<div class="container">
		<p class="float-md-right">
			&copy Copyright 2019 All rights reserved
		</p>
		<p>
			<a href="#">Terms and conditions</a>
		</p>
	</div><!-- //container -->
</footer>
</body>