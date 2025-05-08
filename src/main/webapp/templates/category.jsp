<!--Chức năng: Hiển thị sản phẩm theo danh mục (ví dụ: quần áo nam, nữ, trẻ em).
Nội dung: Danh sách sản phẩm thuộc danh mục cụ thể mà người dùng chọn.-->
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
    <title>Our store</title>
    <jsp:include page="headerResource.jsp"/>
</head>
</head>
<body>
<%@ include file="/templates/includes/navbar.jsp" %>
<!-- ========================= SECTION PAGETOP ========================= -->
<section class="section-pagetop bg">
    <div class="container">
        <h2 class="title-page">Our Store</h2>
    </div>
</section>
<!-- ========================= SECTION CONTENT ========================= -->
<section class="section-content padding-y">
    <div class="container">
        <div class="row">
            <aside class="col-md-3">
                <div class="card">
                    <article class="filter-group">
                        <header class="card-header">
                            <a href="#" data-toggle="collapse" data-target="#collapse_1" aria-expanded="true" class="">
                                <i class="icon-control fa fa-chevron-down"></i>
                                <h6 class="title">Categories</h6>
                            </a>
                        </header>
                        <div class="filter-content collapse show" id="collapse_1">
                            <div class="card-body">
                                <ul class="list-menu">
                                    <li><a href="${pageContext.request.contextPath}/category?id=all">All products</a>
                                    </li>
                                    <c:choose>
                                        <c:when test="${not empty categoryList}">
                                            <c:forEach var="category" items="${categoryList}">
                                                <li>
                                                    <a class="dropdown-item"
                                                       href="${pageContext.request.contextPath}/category?id=${category.id}">
                                                            ${category.title}
                                                    </a>
                                                </li>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <li>No categories available.</li>
                                        </c:otherwise>
                                    </c:choose>

                                </ul>
                            </div>
                        </div>
                    </article>


                    <form action="Filter" method="post">
                        <article class="filter-group">
                            <header class="card-header">
                                <a href="#" data-toggle="collapse" data-target="#collapse_3" aria-expanded="true"
                                   class="">
                                    <i class="icon-control fa fa-chevron-down"></i>
                                    <h6 class="title">Price range</h6>
                                </a>
                            </header>
                            <div class="filter-content collapse show" id="collapse_3">
                                <div class="card-body">
                                    <div class="form-row">
                                        <div class="form-group col-md-6">
                                            <label>Min</label>
                                            <select name="minPrice" class="mr-2 form-control">
                                                <option value="0"> 0 ${currency}</option>
                                                <option value="50"> ${50 * rate} ${currency}</option>
                                                <option value="100"> ${100 * rate} ${currency}</option>
                                                <option value="150"> ${150 * rate} ${currency}</option>
                                                <option value="200"> ${200 * rate} ${currency}</option>
                                                <option value="500"> ${500 * rate} ${currency}</option>
                                                <option value="1000"> ${1000 * rate} ${currency}</option>
                                            </select>
                                        </div>
                                        <div class="form-group text-right col-md-6">
                                            <label>Max</label>
                                            <select name="maxPrice" class="mr-2 form-control">
                                                <option value="50"><fmt:formatNumber value="${50* rate}"
                                                                                     maxFractionDigits="2"
                                                /> ${currency}</option>
                                                <option value="100">${100 * rate} ${currency}</option>
                                                <option value="150">${150 * rate} ${currency}</option>
                                                <option value="200">${200 * rate} ${currency}</option>
                                                <option value="500">${500 * rate} ${currency}</option>
                                                <option value="1000">${1000 * rate} ${currency}</option>
                                                <option value="2000">${2000 * rate} ${currency}</option>
                                            </select>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-block btn-primary"><fmt:message
                                            key="btnApply"/></button>
                                </div>
                            </div>
                        </article>
                    </form>
                </div>
            </aside>

            <main class="col-md-9">
                <header class="border-bottom mb-4 pb-3">
                    <div class="form-inline">
                        <c:set var="productList" value="${requestScope.productList}"/>
                        <span class="mr-md-auto"><b>${productList.size()}</b> Items found</span>
                    </div>
                </header>

                <div class="row">
                    <c:choose>
                        <c:when test="${productList != null}">
                            <c:forEach var="product" items="${productList}">
                                <div class="col-md-4">
                                    <figure class="card card-product-grid">
                                        <div class="img-wrap">
                                            <a href="${pageContext.request.contextPath}/product?id=${product.getId()}"><img
                                                    src="${pageContext.request.contextPath}/${product.getPhoto()}"
                                                    alt="${product.getName()}"></a>
                                        </div>
                                        <figcaption class="info-wrap">
                                            <div class="fix-height">
                                                <a href="${pageContext.request.contextPath}/product?id=${product.getId()}"
                                                   class="title">${product.getName()}</a>
                                                <div class="price-wrap mt-2">
                                                    <fmt:formatNumber value="${product.price * rate}"
                                                                      maxFractionDigits="0"
                                                    />
                                                        ${currency}
                                                </div>
                                            </div>

                                            <form action="${pageContext.request.contextPath}/secure/cart" method="post">
                                                <input type="hidden" name="action" value="addToCart">
                                                <input type="hidden" name="productId" value="${product.getId()}">
                                                <input type="hidden" name="quantity" min="1" value="1"
                                                       class="form-control mb-2" required>
                                                <button type="submit" class="btn btn-primary">
                                                    <span class="text"> <fmt:message key="btnAddToCart"/> </span>
                                                    <i class="fas fa-shopping-cart"></i>
                                                </button>
                                                <a href="${pageContext.request.contextPath}/secure/AddToSaved?productId=${product.getId()}" class="w-auto btn  float-right   "><i class="fas fa-heart float-right fa-2x text-light "></i></a>
                                            </form>
                                        </figcaption>
                                    </figure>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="col-md-12">
                                <p>No products found.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/category?id=${param.id}&page=${i}&size=10">
                                        ${i}
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </main>
        </div>
    </div>
</section>

<%@ include file="/templates/includes/footer.jsp" %>
</body>
</html>
