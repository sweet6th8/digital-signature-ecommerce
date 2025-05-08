<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<fmt:message key="exchangeRate" var="rate"/>
<fmt:message key="currency" var="currency"/>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>GreatKart | Shopping Cart</title>
    <jsp:include page="../headerResource.jsp"/>
    <script src="${pageContext.request.contextPath}/static/js/cart_ajax.js"></script> <!-- Thêm liên kết file JavaScript -->
</head>
<body>
<%@ include file="/templates/includes/navbar.jsp" %>

<section class="section-content padding-y bg" style="margin-top: 120px;">
    <div class="container">
        <!-- Messaging Area -->
        <div id="cartMessage" class="alert" style="display: none;"></div>

        <div class="row">
            <aside class="col-lg-9">
                <div class="card">
                    <table class="table table-borderless table-shopping-cart">
                        <thead class="text-muted">
                        <tr class="small text-uppercase">
                            <th scope="col">Product Name</th>
                            <th scope="col">Image</th>
                            <th scope="col" width="120">Quantity</th>
                            <th scope="col" width="120">Price</th>
                            <th scope="col"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${not empty cart.items}">
                            <c:forEach var="cartItem" items="${cart.items.values()}">
                                <tr id="row-${fn:escapeXml(cartItem.product.id)}">
                                    <td>
                                        <figure class="itemside align-items-center">
                                            <figcaption class="info">
                                                <a href="#" class="title text-dark">
                                                    <c:out value="${cartItem.product.name}"/>
                                                </a>
                                            </figcaption>
                                        </figure>
                                    </td>
                                    <td>
                                        <figure class="itemside align-items-center">
                                            <div class="aside">
                                                <img src="${pageContext.request.contextPath}${cartItem.product.photo}"
                                                     class="img-sm img-thumbnail"
                                                     alt="<c:out value='${cartItem.product.photo}'/>">
                                            </div>

                                        </figure>
                                    </td>
                                    <td>
                                        <input
                                                type="number"
                                                onchange="updateQuantity('<c:out
                                                        value="${fn:escapeXml(cartItem.product.id)}"/>', this)"
                                                class="form-control"
                                                value="${cartItem.quantity}"
                                                min="1">
                                    </td>
                                    <td>
                                        <div class="price-wrap">
                        <span id="item-total-${fn:escapeXml(cartItem.product.id)}">
                                <fmt:formatNumber value="${cartItem.totalPrice * rate}" maxFractionDigits="0"/>
                            ${currency}
                        </span>
                                            <small class="text-muted">
                                                <fmt:formatNumber value="${cartItem.product.price * rate}"
                                                                  maxFractionDigits="0"/>
                                                    ${currency}
                                                each </small>
                                        </div>
                                    </td>
                                    <td class="text-right">
                                        <button
                                                onclick="removeItem('<c:out
                                                        value="${fn:escapeXml(cartItem.product.id)}"/>', document.getElementById('row-${fn:escapeXml(cartItem.product.id)}'))"
                                                class="btn btn-danger">Remove
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty cart.items}">
                            <tr>
                                <td colspan="4" class="text-center">
                                    <p> <fmt:message key="NotifyCardEmpty"/> <a href="${pageContext.request.contextPath}/"><fmt:message key="BuyNow"/></a></p>
                                </td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </aside>
            <aside class="col-lg-3">
                <div class="card">
                    <div class="card-body">
                        <dl class="dlist-align">
                            <dt>Total:</dt>
                            <dd class="text-right">
                                <strong id="totalPrice">
                                    <fmt:formatNumber value="${cart.totalPrice * rate}" maxFractionDigits="0"/> ${currency}
                                </strong>
                            </dd>
                        </dl>
                        <hr>

                        <a href="${pageContext.request.contextPath}/secure/place-order" class="btn btn-primary btn-block"> Checkout </a>
                        <a href="${pageContext.request.contextPath}" class="btn btn-light btn-block">Continue
                            Shopping</a>
                    </div>
                </div>
            </aside>
        </div>
    </div>
</section>

<%@ include file="/templates/includes/footer.jsp" %>
</body>
</html>
