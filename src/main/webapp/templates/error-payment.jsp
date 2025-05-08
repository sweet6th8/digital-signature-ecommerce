<%--
  Created by IntelliJ IDEA.
  User: NGUYEN THI LANG
  Date: 12/27/2024
  Time: 4:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Payment Failed</title>
    <link href="${pageContext.request.contextPath}/static/css/styles.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="container">
    <h1>Payment Failed</h1>
    <p>Unfortunately, we were unable to process your payment. Please try again or use a different payment method.</p>

    <h2>Possible Reasons:</h2>
    <ul>
        <li>Insufficient funds.</li>
        <li>Invalid payment details.</li>
        <li>Transaction canceled by the user.</li>
        <li>Technical issues with the payment gateway.</li>
    </ul>

    <p>If you continue to face issues, please contact our customer support for assistance.</p>

    <a href="place-order" class="btn btn-primary">Try Again</a>
</div>
</body>
</html>