<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                <c:choose>
                    <c:when test="${quiz.resultPublished}">
                        <div class="row">
                            <label class="col-md-2 control-label">Score</label>
                            <div class="col-md-10">
                                <c:out value="${quiz.score}"/>
                            </div>
                        </div>

                        <div class="row">
                            <label class="col-md-2 control-label">Duration</label>
                            <div class="col-md-10">
                                <c:out value="${quiz.duration}"/>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        Result will be published on <strong><fmt:formatDate value="${quiz.publication.resultPublicationTime}" pattern="${datePattern}"/></strong>.
                    </c:otherwise>
                </c:choose>
            </fieldset>
        </div>
    </div>
</div>
</body>
</html>
