<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            <h1><c:out value="${quizTemplate.name}"/></h1>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-6">
        <div class="well bs-component">
            This quiz contains ${fn:length(quizTemplate.questionTemplates)} multiple choice question and should be completed within ${quizTemplate.maxDurationInMin} minutes.
            <br/><br/><a class="btn btn-md btn-success" href="<c:url value="/quiz/quiz/run?id=${quizTemplate.id}"/>">Start Quiz</a>
        </div>
    </div>
</div>
</body>
</html>
