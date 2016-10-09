<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <h1><b class="quizName" id="${quiz.id}" style="display: inline"><c:out value="${quiz.name}"/></b></h1>
            Duration: <c:out value="${quiz.maxDurationInMin}"/> Minute
        </div>
        <div class="col-sm-2">
            <div class="verticalSpace">&nbsp;</div>
            <c:url var="publishUrl" value='publish'>
                <c:param name="quizId" value="${quiz.id}"/>
            </c:url>
            <a class="btn btn-md btn-warning pull-right" href="${publishUrl}">Publish</a>
        </div>
    </div>
</div>
<fieldset>
    <div class="well bs-component">
    <div id="questions" style="margin-bottom: 20px;">
        <c:forEach items="${quiz.questions}" var="question">
            <div class="question" id="question-${question.id}">
                <div id="${question.id}" class="questionLabel" style="display: inline">${question.label}</div>
                <br/>
                <div>
                    <c:forEach items="${question.answerOptions}" var="option">
                        <div class="option">
                            <input type="radio" <c:if test="${option.rightAnswer}">checked</c:if> disabled/><span id="${option.id}" class="optionLabel" style="display: inline">${option.label}</span>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>
    </div>
    </div>
</fieldset>
</body>
</html>
