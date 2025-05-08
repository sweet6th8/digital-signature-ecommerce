<%--
  Created by IntelliJ IDEA.
  User: NGUYEN THI LANG
  Date: 1/12/2025
  Time: 3:45 PM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Virtual Try-On Result</title>
</head>
<body>
<h2>Result of Virtual Try-On</h2>
<img src="<%= request.getAttribute("resultImage") %>" alt="Result Image" />
<br>
<a href="tryon.jsp">Try Again</a>
</body>
</html>
