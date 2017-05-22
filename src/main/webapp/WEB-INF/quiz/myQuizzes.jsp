<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>My Quizzes</title>
</head>
<body>
<div class="page-header" id="banner">
    <div class="row">
        <div class="col-sm-12">
            <h1>My Quizzes</h1>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-lg-12">
        <div class="bs-component">
            <table class="table table-striped table-hover ">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Completed On</th>
                    <th>Result</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${quizzes}" var="quiz">
                    <tr>
                        <c:url var="quizUrl" value="show">
                            <c:param name="quizId" value="${quiz.id}"/>
                        </c:url>
                        <td><a href="${quizUrl}"><c:out value="${quiz.quizTemplate.name}"/></a></td>
                        <td><fmt:formatDate value="${quiz.endTime}" pattern="${datePattern}"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${quiz.resultPublished}">
                                    Published
                                </c:when>
                                <c:otherwise>
                                    Due on <fmt:formatDate value="${quiz.publication.resultPublicationTime}" pattern="${datePattern}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
