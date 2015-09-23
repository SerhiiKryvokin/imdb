<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
<html>
<body>
<%--<h1>${message}</h1>--%>
    <%--<c:forEach items="${movies}" var="movie">--%>
        <%--<li>--%>
            <%--<div class="movieTitle">--%>
                <%--<c:out value="${movie.title}" />--%>
            <%--</div>--%>
        <%--</li>--%>
    <%--</c:forEach>--%>
    <div class="wide-wrapper">
        <div class="wrapper">
            <form action="<c:url value="/"/>" method="get">
                <input type="text" name="search">
                <input type="submit" value="Search">
            </form>
            <c:forEach items="${movies}" var="movie">
                <div class="movie-item">

                    <div class="movie-item-label">
                        <a href="#" class="movie-item-label-link">${movie.title}</a>
                    </div>

                    <div class="movie-item-pic">
                        <img src="<c:url value="/resources/images/${movie.picture}" />"/>
                    </div><%--No space--%><div class="movie-item-info">
                    <span class="producer-header">Director: </span> <a href="#" class="producer">${movie.director.name}</a>
                    <br><span class="descr-header">Description</span>
                    <br><span class="descr">${movie.description}</span>
                </div>

                    <c:choose>
                        <c:when test="${movie.averageRating == null}">
                            <span class="descr-header">Rating: no rating</span>
                        </c:when>
                        <c:otherwise>
                            <span class="descr-header">Rating: ${movie.averageRating}</span>
                        </c:otherwise>
                    </c:choose>

                    <sec:authorize access="isAuthenticated()">
                        <c:choose>
                            <c:when test="${movie.currentUserRating == null}">
                                <form action="<c:url value="/rate/${movie.id}"/>" method="post">
                                    <input type="number" name="value">
                                    <input type="submit" value="Rate">
                                    <input type="hidden"
                                           name="${_csrf.parameterName}"
                                           value="${_csrf.token}"/>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <span class="descr-header">Your rate: ${movie.currentUserRating}</span>
                            </c:otherwise>
                        </c:choose>
                    </sec:authorize>
                        <%--<div class="movie-item-rating">--%>
                        <%--<span class="descr-header">Rating: ${movies.rating}</span>--%>
                        <%--</div>--%>

                        <%--<c:if test="${not empty sessionScope.user}">--%>
                    <%--<% if (session.getAttribute("user") != null) { %>--%>
                    <%--<div class="movie-item-rating">--%>
                        <%--<c:choose>--%>
                            <%--<c:when test="${movies.userRating == 0}">--%>
                                <%--&lt;%&ndash;<form action="rate?user=${sessionScope.user}&mID=${movies.id}" method="post">&ndash;%&gt;--%>
                                <%--<form action="rate" method="post">--%>
                                    <%--Your rate: <input type="text" name="rate">--%>
                                    <%--<br>--%>
                                    <%--<input type="hidden" name="user" value="${sessionScope.user}">--%>
                                    <%--<input type="hidden" name="mID" value="${movies.id}">--%>
                                    <%--<input type="submit" value="Rate">--%>
                                <%--</form>--%>
                            <%--</c:when>--%>
                            <%--<c:otherwise>--%>
                                <%--<span class="descr-header">Your rate: ${movies.userRating}</span>--%>
                            <%--</c:otherwise>--%>
                        <%--</c:choose>--%>
                        <%--<% if (session.getAttribute("role").toString().equals("admin")) { %>--%>
                        <%--<form action="editmovie" method="get">--%>
                            <%--<input type="hidden" name="id" value="${movies.id}">--%>
                            <%--<input type="submit" value="Edit">--%>
                        <%--</form>--%>

                        <%--<form action="editmovie" method="post">--%>
                            <%--<input type="hidden" name="deleteId" value="${movies.id}">--%>
                            <%--<input type="submit" value="Delete">--%>
                        <%--</form>--%>
                        <%--<% } %>--%>
                            <%--&lt;%&ndash;<span class="descr-header">Your rate: ${movies.userRating}</span>&ndash;%&gt;--%>
                    <%--</div>--%>
                    <%--<% } %>--%>
                </div>
            </c:forEach>
            <%--<% if (session.getAttribute("role") != null && session.getAttribute("role").toString().equals("admin")) { %>--%>
            <%--<form action="editmovie" method="get">--%>
                <%--<input type="hidden" name="action" value="insert">--%>
                <%--<input type="submit" value="Add">--%>
            <%--</form>--%>
            <%--<% } %>--%>
        </div>
    </div>
</body>
</html>