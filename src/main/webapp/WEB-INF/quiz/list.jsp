<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Quizzes</title>
</head>
<body>
<div class="row">
    <div class="col-lg-12">
        <div class="bs-component">
            <table class="table table-striped table-hover ">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Max Duration (Min)</th>
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
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
