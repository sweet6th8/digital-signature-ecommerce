<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<fmt:message key="exchangeRate" var="rate"/>
<fmt:message key="currency" var="currency"/>

<html>
<head><title>Title</title>
    <meta charset="UTF-8">

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <title>GreatKart | One of the Biggest Online Shopping Platform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/history.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/reset.css">

    <jsp:include page="../headerResource.jsp"/>
</head>
<body>
<%@ include file="/templates/includes/navbar.jsp" %>
<section class="section-content" style="margin-top: 120px;">
    <div class="container p-4">
        <div class="row mt-4">

                    <c:choose>
                        <c:when test="${not empty requestScope.list }">
                            <c:forEach var="item" items="${requestScope.list}">
                                <div class="col-md-4" >
                                    <figure class="card card-product-grid border-dark">
                                        <div class="img-wrap">
                                            <a href="${pageContext.request.contextPath}/product?id=${item.getProductId()}"><img
                                                    src="${pageContext.request.contextPath}${item.getProductImage()}"
                                                    alt="${item.getProductName()}"></a>
                                        </div>
                                        <figcaption class="info-wrap">
                                            <div class=" mb-2">
                                                <a href="${pageContext.request.contextPath}/product?id=${item.getProductId()}"
                                                   class="title text-center">${item.getProductName()}</a>
                                                <div class="price-wrap mt-2 fa-2x text-right">
                                                    <fmt:formatNumber value="${item.getProductPrice() * rate}"
                                                                      maxFractionDigits="0"
                                                    />
                                                        ${currency}
                                                </div>
                                            </div>

                                            <form action="${pageContext.request.contextPath}/secure/cart" method="post">
                                                <input type="hidden" name="action" value="addToCart">
                                                <input type="hidden" name="productId" value="${item.getProductId()}">
                                                <input type="hidden" name="quantity" min="1" value="1"
                                                       class="form-control mb-2" required>
                                                <button type="submit" class="btn btn-primary float-right">
                                                    <span class="text"> <fmt:message key="BuyAgain"/> </span>
                                                    <i class="fas fa-shopping-cart"></i>
                                                </button>
                                            </form>
                                        </figcaption>
                                    </figure>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise><h1 class="fa-4x text-center">You don't have any orders yet</h1></c:otherwise>

                    </c:choose>
                </div>
            </div>
    </section>
</body>
</html>