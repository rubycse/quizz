<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>
        <c:set var="title" value="${sharedWithMe ? 'Shared With Me' : 'Public Quizzes'}"/>
        ${title}
    </title>
</head>
<body>
<div class="page-header" id="banner">
    <div class="row">
        <div class="col-sm-12">
            <h1>${title}</h1>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-lg-12">
        <div class="bs-component">
            <table class="table table-striped table-hover ">
                <thead>
                <tr>
                    <th>Quiz Name</th>
                    <th>Published On</th>
                    <th>Published By</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${publications}" var="publication">
                    <tr>
                        <c:url var="quizUrl" value="show">
                            <c:param name="id" value="${publication.id}"/>
                        </c:url>
                        <td><a href="${quizUrl}"><c:out value="${publication.quizTemplate.name}"/></a></td>
                        <td><fmt:formatDate value="${publication.publishedOn}"/></td>
                        <td><c:out value="${publication.quizTemplate.createdBy.name}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
