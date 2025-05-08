<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--declare resource--%>
<fmt:requestEncoding value="UTF-8" />
<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="messages" />

<c:set var="Context" value="${pageContext.request.contextPath}"/>

<style>
    #tryOnButton {
        position: fixed; /* Định vị cố định */
        bottom: 60px; /* Cách đáy màn hình 20px */
        right: 30px; /* Cách phải màn hình 20px */
        background-color: #001b19; /* Màu nền xanh lá cây */
        color: white; /* Màu chữ trắng */
        padding: 10px 20px; /* Kích thước padding */
        border-radius: 25px; /* Bo tròn các góc */
        font-size: 16px; /* Cỡ chữ */
        font-weight: bold; /* Đậm chữ */
        text-align: center; /* Căn giữa chữ */
        text-decoration: none; /* Xóa gạch chân */
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2); /* Hiệu ứng đổ bóng */
        transition: background-color 0.3s ease; /* Hiệu ứng hover */
        z-index: 1000; /* Hiển thị trên các phần tử khác */
    }

    #tryOnButton:hover {
        background-color: #007BFF; /* Màu nền khi hover */
    }

</style>
<!-- Nội dung của home.jsp -->
<section class="section-intro padding-y-sm" style="margin-top: 120px;">
    <div class="container">
        <div class="intro-banner-wrap">
            <img src="${Context}/static/images/banners/banner.jpg" class="img-fluid rounded"
                 alt="Intro Banner">
        </div>
        <!--  Try On Button -->
        <div class="text-center mt-3">
            <a href="${Context}/templates/tryon.jsp" id="tryOnButton" class="btn btn-primary">Try-On with AI</a>
        </div>

    </div>
</section>

<section class="section-name padding-y-sm">
    <p class="fa-2x">
        <fmt:message key="welcome" /></p>

    <div class="container">
        <header class="section-heading">
            <a href="${Context}/category?id=all" class="btn btn-outline-primary float-right">See
                all</a>
            <h3 class="section-title">Popular products</h3>
        </header>
        <div class="row">
            <c:choose>
                <c:when test="${not empty productList}">
                    <c:forEach var="product" items="${productList}">
                        <div class="col-md-3">
                            <div class="card card-product-grid">
                                <div class="img-wrap">
                                    <a href="./product?id=${product.id}">
                                        <img src="${Context}/${product.photo}"
                                             alt="${product.name}">
                                    </a>
                                </div>

                                <figcaption class="info-wrap">
                                    <a href="./product?id=${product.id}" class="title">${product.name}</a>
                                    <div class="price mt-1">
                                        <fmt:message key="exchangeRate" var="rate"/>
                                        <fmt:formatNumber value="${product.price * rate }"
                                                          maxFractionDigits="0"
                                        />
                                        <fmt:message key="currency"/>
                                    </div>
                                </figcaption>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="col-md-12">
                        <p>No products available.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</section>
