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
                    <th>Published</th>
                    <th>Publication</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${quizzes}" var="quizTemplate">
                    <tr>
                        <c:url var="quizUrl" value="show">
                            <c:param name="id" value="${quizTemplate.id}"/>
                        </c:url>
                        <td><a href="${quizUrl}"><c:out value="${quizTemplate.name}"/></a></td>
                        <td><c:out value="${quizTemplate.published ? 'Yes' : 'No'}"/></td>
                        <td>
                            <c:if test="${quizTemplate.published}">
                                <c:url var="publishUrl" value='publish'>
                                    <c:param name="quizId" value="${quizTemplate.id}"/>
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
