<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><c:out value="${quiz.quizTemplate.name}"/></title>
    <link href='<c:url value="/css/quiz-1.0.0.css"/>' rel="stylesheet" type="text/css">
</head>
<body>
<div class="page-header" id="banner">
    <div class="row">
        <div class="col-sm-10">
            <h1><c:out value="${quiz.quizTemplate.name}"/></h1>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-10">
        <div class="well bs-component">
            <fieldset>
                <div class="row readonly">
                    <label class="col-md-4 control-label">Started On</label>
                    <div class="col-md-8">
                        <fmt:formatDate value="${quiz.startTime}" pattern="${datePattern}"/>
                    </div>
                </div>

                <div class="row readonly">
                    <label class="col-md-4 control-label">Completed On</label>
                    <div class="col-md-8">
                        <fmt:formatDate value="${quiz.endTime}" pattern="${datePattern}"/>
                    </div>
                </div>

                <c:choose>
                    <c:when test="${quiz.resultPublished}">
                        <div class="row readonly">
                            <label class="col-md-4 control-label">Score</label>
                            <div class="col-md-8">
                                <c:out value="${quiz.score}"/>&nbsp;&nbsp;&nbsp;(Highest: <c:out value="${highestScore}"/>)
                            </div>
                        </div>

                        <div class="row readonly">
                            <label class="col-md-4 control-label">Position</label>
                            <div class="col-md-8">
                                <c:out value="${position}"/>
                            </div>
                        </div>

                        <hr/>

                        <c:forEach items="${quiz.questions}" var="question">
                            <div class="question">
                                <div id="${question.id}" class="questionLabel" style="display: inline">${question.label}</div>
                                <br/>
                                <div>
                                    <c:forEach items="${question.options}" var="option">
                                        <div class="option">
                                            <input type="radio" <c:if test="${option.answered}">checked</c:if> disabled/>
                                            <span id="${option.id}" class="optionLabel" style="display: inline">${option.label}</span>
                                            <c:if test="${option.rightAnswer and !option.answered}">
                                                <span class="glyphicon glyphicon-triangle-left"></span>
                                                <span class="correctOption">Correct Answer</span>
                                            </c:if>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:forEach>

                    </c:when>
                    <c:otherwise>
                        <div class="row readonly">
                            <div class="col-md-4">
                                Result will be published on <strong><fmt:formatDate value="${quiz.publication.resultPublicationTime}" pattern="${datePattern}"/></strong>.
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </fieldset>
        </div>
    </div>
</div>
</body>
</html>
