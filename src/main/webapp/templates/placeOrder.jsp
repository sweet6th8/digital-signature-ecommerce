<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<fmt:message key="exchangeRate" var="rate"/>
<fmt:message key="currency" var="currency"/>
<!DOCTYPE HTML>
<html lang="en">
<head>
  <meta charset="utf-8">

  <script type="text/javascript">
    $(document).ready(function() {
      // Any additional JS needed for interactions
    });
  </script>
</head>
<body>
<jsp:include page="headerResource.jsp"/>

<section class="section-content padding-y bg">
  <div class="container">
    <div class="row">
      <main class="col-md-8">
        <!-- Cart items here, dynamically populated -->
        <article class="card mb-4">
          <div class="card-body">
            <h4 class="card-title mb-4">Review Cart</h4>
            <div class="row">
              <c:forEach var="item" items="${cartItems}">


                <div class="col-md-6">
                  <figure class="itemside  mb-4">
<%--                    <div class="aside"><img src="${item.product.photo}" class="border img-sm"></div>--%>
                    <figcaption class="info">
                      <p>${item.product.name}</p>

                      <span class="text-muted">${item.quantity} x

                         <fmt:formatNumber value="${item.product.price}" maxFractionDigits="0"/>
                            ${currency} =
                       <fmt:formatNumber value="${item.totalPrice * rate}" maxFractionDigits="0"/>
                            ${currency}
                      </span>
                    </figcaption>
                  </figure>
                </div>
              </c:forEach>
            </div>
          </div>
        </article>

<%--        <!-- Contact Info Form -->--%>
<%--        <article class="card mb-4">--%>
<%--          <div class="card-body">--%>
<%--            <h4 class="card-title mb-4">Contact info</h4>--%>
<%--            <form action="" method="POST">--%>
<%--              <div class="row">--%>
<%--                <div class="form-group col-sm-6">--%>
<%--                  <label>First name</label>--%>
<%--                  <input type="text" placeholder="Type here" class="form-control" name="firstName">--%>
<%--                </div>--%>
<%--                <div class="form-group col-sm-6">--%>
<%--                  <label>Last name</label>--%>
<%--                  <input type="text" placeholder="Type here" class="form-control" name="lastName">--%>
<%--                </div>--%>
<%--                <div class="form-group col-sm-6">--%>
<%--                  <label>Phone Number</label>--%>
<%--                  <input type="text" value="+998" class="form-control" name="phone">--%>
<%--                </div>--%>
<%--                <div class="form-group col-sm-6">--%>
<%--                  <label>Email</label>--%>
<%--                  <input type="email" placeholder="example@gmail.com" class="form-control" name="email">--%>
<%--                </div>--%>
<%--              </div> <!-- row -->--%>
<%--            </form>--%>
<%--          </div>--%>
<%--        </article>--%>

<%--        <!-- Delivery Info Form -->--%>
<%--        <article class="card mb-4">--%>
<%--          <div class="card-body">--%>
<%--            <h4 class="card-title mb-4">Delivery info</h4>--%>
<%--            <form action="" method="POST">--%>
<%--              <div class="row">--%>
<%--                <div class="form-group col-sm-6">--%>
<%--                  <label>Country*</label>--%>
<%--                  <select name="country" class="form-control">--%>
<%--                    <option value="India">India</option>--%>
<%--                    <option value="United States">United States</option>--%>
<%--                    <option value="France">France</option>--%>
<%--                    <option value="Italy">Italy</option>--%>
<%--                  </select>--%>
<%--                </div>--%>
<%--                <div class="form-group col-sm-6">--%>
<%--                  <label>State*</label>--%>
<%--                  <input type="text" placeholder="Type here" class="form-control" name="state">--%>
<%--                </div>--%>
<%--                <div class="form-group col-sm-8">--%>
<%--                  <label>Street*</label>--%>
<%--                  <input type="text" placeholder="Type here" class="form-control" name="street">--%>
<%--                </div>--%>
<%--                <div class="form-group col-sm-4">--%>
<%--                  <label>Building</label>--%>
<%--                  <input type="text" placeholder="" class="form-control" name="building">--%>
<%--                </div>--%>
<%--                <div class="form-group col-sm-4">--%>
<%--                  <label>House</label>--%>
<%--                  <input type="text" placeholder="Type here" class="form-control" name="house">--%>
<%--                </div>--%>
<%--                <div class="form-group col-sm-4">--%>
<%--                  <label>Postal code</label>--%>
<%--                  <input type="text" placeholder="" class="form-control" name="postalCode">--%>
<%--                </div>--%>
<%--                <div class="form-group col-sm-4">--%>
<%--                  <label>Zip</label>--%>
<%--                  <input type="text" placeholder="" class="form-control" name="zip">--%>
<%--                </div>--%>
<%--              </div> <!-- row -->--%>
<%--            </form>--%>
<%--          </div>--%>
<%--        </article>--%>

        <!-- Payment Methods -->
        <article class="accordion" id="accordion_pay">
          <div class="card">
            <header class="card-header">
              <img src="${pageContext.request.contextPath}/static/images/misc/payment-paypal.png" class="float-right" height="24">
              <label class="form-check collapsed" data-toggle="collapse" data-target="#pay_paypal">
                <input class="form-check-input" name="payment-option" checked type="radio" value="option2">
                <h6 class="form-check-label"> Paypal </h6>
              </label>
            </header>
            <div id="pay_paypal" class="collapse show" data-parent="#accordion_pay">
              <div class="card-body">
                <form action="${pageContext.request.contextPath}/PaymentServlet" method="POST">
                  <input type="hidden" name="total" value="${cart.totalPrice}" />
                  <button type="submit" class="btn btn-primary">Pay with PayPal</button>
                </form>
              </div>
            </div>
          </div>

          <!-- Credit Card option -->
          <div class="card">
            <header class="card-header">
              <img src="${pageContext.request.contextPath}/static/images/misc/payment-card.png" class="float-right" height="24">
              <label class="form-check" data-toggle="collapse" data-target="#pay_payme">
                <input class="form-check-input" name="payment-option" type="radio" value="option2">
                <h6 class="form-check-label"> Credit Card </h6>
              </label>
            </header>
            <div id="pay_payme" class="collapse" data-parent="#accordion_pay">
              <div class="card-body">
                <form class="form-inline">
                  <input type="text" class="form-control mr-2" placeholder="xxxx-xxxx-xxxx-xxxx" name="creditCard">
                  <input type="text" class="form-control mr-2" style="width: 100px" placeholder="dd/yy" name="expiry">
                  <input type="number" maxlength="3" class="form-control mr-2" style="width: 100px" placeholder="cvc" name="cvc">
                  <button class="btn btn-success">Pay</button>
                </form>
              </div>
            </div>
          </div>
        </article>

      </main>

      <!-- Order Summary -->
      <aside class="col-md-4">
        <div class="card">
          <div class="card-body">

            <dl class="dlist-align">
              <dt>Total:</dt>
              <dd class="text-right text-dark b"><strong> $<fmt:formatNumber value="${cart.totalPrice}" pattern="#0.00"/></strong></dd>
            </dl>
            <hr>
            <p class="text-center mb-3">
              <img src="${pageContext.request.contextPath}/static/images/misc/payments.png" height="26">
            </p>
            <a href="#" class="btn btn-primary btn-block" id="placeOrderBtn">Place Order</a>
          </div>
        </div>
      </aside>
    </div> <!-- row -->
  </div> <!-- container -->
</section>

</body>
</html>
