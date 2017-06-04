<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <h1><c:out value="${publication.quizTemplate.name}"/></h1>
    </title>
    <link href='<c:url value="/css/quiz-1.0.0.css"/>' rel="stylesheet" type="text/css">
</head>
<body>
<div class="page-header" id="banner">
    <div class="row">
        <div class="col-sm-10">
            <h1><c:out value="${publication.quizTemplate.name}"/></h1>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-6">
        <div class="well bs-component">
            <c:choose>
                <c:when test="${publication.open}">
                    This quiz contains ${fn:length(publication.quizTemplate.questionTemplates)} multiple choice question
                    and should be completed within ${publication.durationInMin} minutes.
                </c:when>
                <c:when test="${publication.due}">
                    This quiz is due on <strong><fmt:formatDate value="${publication.scheduleFrom}" pattern="${datePattern}"/></strong>
                    and you should be complete it by <strong><fmt:formatDate value="${publication.scheduleTo}" pattern="${datePattern}"/></strong>.
                </c:when>
                <c:when test="${publication.overDue}">
                    Sorry you can not start this quiz now. It was scheduled from <strong><fmt:formatDate value="${publication.scheduleFrom}" pattern="${datePattern}"/></strong>
                    to <strong><fmt:formatDate value="${publication.scheduleTo}" pattern="${datePattern}"/></strong>.
                </c:when>
            </c:choose>

            <br/><br/>

            <c:choose>
                <c:when test="${not empty quiz}">
                    <a class="btn btn-md btn-success" href="<c:url value="/quiz/quiz/show?quizId=${quiz.id}"/>">Go to your Quiz</a>
                </c:when>
                <c:when test="${empty quiz and publication.open}">
                    <a class="btn btn-md btn-success" href="<c:url value="/quiz/quiz/run?publicationId=${publication.id}"/>">Start Quiz</a>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>
