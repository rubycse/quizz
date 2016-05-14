<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a href="../" class="navbar-brand">
                <img src='<c:url value="/images/logo-original.png"/>' style="height: 30px;">
            </a>
            <a href="../" class="navbar-brand">
                Quizz
            </a>
        </div>
        <div class="navbar-collapse collapse" id="navbar-main">
            <ul class="nav navbar-nav">
                <c:if test="${sessionScope.USER != null}">
                    <li><a href="<c:url value='/quiz/create'/>">Create</a></li>
                    <li><a href="<c:url value='/quiz/list'/>">List</a></li>
                </c:if>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <c:when test="${sessionScope.USER == null}">
                        <li><a href="<c:url value='/auth/signin'/>">Sign In</a></li>
                        <li><a href="<c:url value='/auth/signup'/>">Sign Up</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<c:url value='/auth/signout'/>">Sign Out</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</div>
