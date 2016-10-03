<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <th>Max Duration (Min)</th>
                    <th>Published</th>
                    <th>Publication</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${quizzes}" var="quiz">
                    <tr>
                        <c:url var="quizUrl" value="show">
                            <c:param name="id" value="${quiz.id}"/>
                        </c:url>
                        <td><a href="${quizUrl}"><c:out value="${quiz.name}"/></a></td>
                        <td><c:out value="${quiz.maxDurationInMin}"/></td>
                        <td><c:out value="${quiz.published ? 'Yes' : 'No'}"/></td>
                        <td>
                            <c:if test="${quiz.published}">
                                <c:url var="publishUrl" value='publish'>
                                    <c:param name="quizId" value="${quiz.id}"/>
                                </c:url>
                                <a href="${publishUrl}">Publication</a>
                            </c:if>
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
