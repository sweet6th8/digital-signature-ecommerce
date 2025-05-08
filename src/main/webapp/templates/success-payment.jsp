<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Successful</title>
    <style>
        /* Global Styles */
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            background-color: #ffffff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            border-radius: 8px;
            padding: 20px;
            max-width: 600px;
            width: 90%;
            text-align: center;
        }

        h1 {
            color: #2ecc71;
            font-size: 28px;
            margin-bottom: 15px;
        }

        h2 {
            color: #34495e;
            font-size: 22px;
            margin-bottom: 10px;
        }

        p {
            color: #7f8c8d;
            font-size: 16px;
            line-height: 1.6;
            margin-bottom: 20px;
        }

        ul {
            list-style: none;
            padding: 0;
            margin: 20px 0;
        }

        ul li {
            color: #34495e;
            font-size: 16px;
            text-align: left;
            margin: 8px 0;
        }

        ul li strong {
            color: #2c3e50;
        }

        .btn {
            display: inline-block;
            text-decoration: none;
            background-color: #3498db;
            color: #ffffff;
            padding: 12px 25px;
            border-radius: 4px;
            font-size: 16px;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }

        .btn:hover {
            background-color: #2980b9;
        }

        /* Add responsive layout for smaller screens */
        @media (max-width: 768px) {
            h1 {
                font-size: 24px;
            }

            h2 {
                font-size: 20px;
            }

            p,
            ul li {
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Payment Successful!</h1>
    <p>Thank you for your purchase. Your payment has been processed successfully.</p>

    <h2>Payment Details:</h2>
    <ul>
        <li><strong>Transaction ID:</strong> ${payment.id}</li>
        <li><strong>Payment State:</strong> ${payment.state}</li>
        <li><strong>Total Paid:</strong> ${payment.transactions[0].amount.total} ${payment.transactions[0].amount.currency}</li>
        <li><strong>Payment Method:</strong> ${payment.payer.paymentMethod}</li>
    </ul>

    <p>We will begin processing your order shortly. If you have any questions, please contact our support team.</p>

    <a href="${pageContext.request.contextPath}" class="btn">Continue Shopping</a>
</div>
</body>
</html>