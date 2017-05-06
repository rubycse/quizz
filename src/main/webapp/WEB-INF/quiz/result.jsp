<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title><fmt:message key="quiz.result.title"/></title>
    <link href='<c:url value="/css/quiz-1.0.0.css"/>' rel="stylesheet" type="text/css">
</head>
<body>
<div class="page-header" id="banner">
    <div class="row">
        <div class="col-sm-10">
            <h1><fmt:message key="quiz.result.title"/></h1>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-6">
        <div class="well bs-component">
            <fieldset>
                <div class="row">
                    <label class="col-md-2 control-label">Score</label>
                    <div class="col-md-10">
                        <c:out value="${result.rightAnswerCount}"/> / <c:out value="${result.totalQuestionCount}"/>
                    </div>
                </div>

                <div class="row">
                    <label class="col-md-2 control-label">Duration</label>
                    <div class="col-md-10">
                        <c:out value="${result.duration}"/>
                    </div>
                </div>
            </fieldset>
        </div>
    </div>
</div>
</body>
</html>
