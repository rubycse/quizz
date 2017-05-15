<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        Show Quiz
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
                    This quiz contains ${fn:length(publication.quizTemplate.questionTemplates)} multiple choice question and should be completed within ${publication.durationInMin} minutes.
                    <br/><br/><a class="btn btn-md btn-success" href="<c:url value="/quiz/quiz/run?publicationId=${publication.id}"/>">Start Quiz</a>
                </c:when>
                <c:otherwise>
                    This quiz was scheduled from <strong><fmt:formatDate value="${publication.scheduleFrom}" pattern="${datePattern}"/></strong>
                    to <strong><fmt:formatDate value="${publication.scheduleTo}" pattern="${datePattern}"/></strong>.
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</div>
</body>
</html>
