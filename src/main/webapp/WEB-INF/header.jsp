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
                <li><a href="<c:url value='/quiz/create'/>">Create</a></li>
                <li><a href="<c:url value='/quiz/list'/>">List</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Login</a></li>
                <li><a href="#">Account</a></li>
            </ul>
        </div>
    </div>
</div>
