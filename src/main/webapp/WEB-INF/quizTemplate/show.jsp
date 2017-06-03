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
        <div class="col-sm-9">
            <h1><span class="quizName" id="${quizTemplate.id}" style="display: inline"><c:out value="${quizTemplate.name}"/></span></h1>
        </div>
        <div class="col-sm-3">
            <div class="verticalSpace">&nbsp;</div>
            <a class="btn btn-md btn-warning pull-right" href="<c:url value='/quiz/publication/publish?quizTemplateId=${quizTemplate.id}'/>">Publish</a>
            <a class="topLink pull-right" href="<c:url value='/quiz/publication/quizPublications?quizTemplateId=${quizTemplate.id}'/>">See Publications</a>
        </div>
    </div>
</div>
<fieldset>
    <div class="well bs-component">
    <div id="questionTemplates" style="margin-bottom: 20px;">
        <c:forEach items="${quizTemplate.questionTemplates}" var="question">
            <div class="question">
                <div id="${question.id}" class="questionLabel" style="display: inline">${question.label}</div>
                <br/>
                <div>
                    <c:forEach items="${question.options}" var="option">
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
