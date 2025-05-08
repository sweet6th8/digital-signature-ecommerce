<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <title>GreatKart | One of the Biggest Online Shopping Platform</title>
    <jsp:include page="headerResource.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/navbar.css" type="text/css"/>
</head>
<body>
<!-- Navbar -->
<%@ include file="includes/navbar.jsp" %>
<c:set var="singleProduct" value="${requestScope.product}"/>
<section class="section-content padding-y bg" style="margin-top: 120px; ">
    <div class="container">
        <div class="card">
            <div class="row no-gutters">
                <aside class="col-md-6">
                    <article class="gallery-wrap">
                        <div class="img-big-wrap">
                            <a href="#"><img src="${pageContext.request.contextPath}/${singleProduct.getPhoto()}"
                                             alt="${singleProduct.getName()}"></a>
                        </div>
                    </article>
                </aside>
                <main class="col-md-6 border-left">
                    <article class="content-body">

                        <h2 class="title">${singleProduct.getName()}</h2>

                        <div class="mb-3">
                            <fmt:formatNumber value="${singleProduct.getPrice() * rate}"
                                              maxFractionDigits="0"
                            />
                            ${currency}
                        </div>

                        <p>${singleProduct.getDescription()}</p>
                        <hr>
                         <form action="${pageContext.request.contextPath}/secure/cart" method="post">
                            <input type="hidden" name="action" value="addToCart">
                           <input type="hidden" name="productId" value="${singleProduct.getId()}">
                            <input type="number" name="quantity" min="1" value="1" required
                                   style="width: 80px; padding: 10px; font-size: 14px; text-align: left;">
                            <button type="submit" class="btn btn-primary  ml-3">
                                <span class="text"> <fmt:message key="btnAddToCart"/></span>
                                <i class="fas fa-shopping-cart"></i>
                            </button>
                             <a href="${pageContext.request.contextPath}/secure/AddToSaved?productId=${product.getId()}" class="w-auto btn  float-right   "><i class="fas fa-heart float-right fa-2x text-light "></i></a>

                        </form>
                    </article>
                </main>
            </div>
        </div>
        <br>
    </div>
</section>
<%@ include file="includes/footer.jsp" %>
</body>

