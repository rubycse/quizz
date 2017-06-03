<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>
        Publications of <c:out value="${quizTemplate.name}"/>
    </title>
</head>
<body>
<div class="page-header" id="banner">
    <div class="row">
        <div class="col-sm-12">
            <c:url var="quizTemplateUrl" value="/quiz/template/show">
                <c:param name="id" value="${quizTemplate.id}"/>
            </c:url>
            <h2>Publications of <a href="${quizTemplateUrl}"><c:out value="${quizTemplate.name}"/></a></h2>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-lg-12">
        <div class="bs-component">
            <table class="table table-striped table-hover ">
                <thead>
                <tr>
                    <th>Published On</th>
                    <th>Published By</th>
                    <th>Scheduled From</th>
                    <th>Scheduled To</th>
                    <th>Result Publication Time</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${publications}" var="publication">
                    <tr>
                        <c:url var="publicationUrl" value="show">
                            <c:param name="id" value="${publication.id}"/>
                        </c:url>
                        <td><a href="${publicationUrl}"><fmt:formatDate value="${publication.publishedOn}"/></a></td>
                        <td><c:out value="${publication.quizTemplate.createdBy.name}"/></td>
                        <td><fmt:formatDate value="${publication.scheduleFrom}" pattern="${datePattern}"/></td>
                        <td><fmt:formatDate value="${publication.scheduleTo}" pattern="${datePattern}"/></td>
                        <td><fmt:formatDate value="${publication.resultPublicationTime}" pattern="${datePattern}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <c:url var="publishUrl" value='/quiz/publication/publish'>
            <c:param name="quizTemplateId" value="${quizTemplate.id}"/>
        </c:url>
        <a class="btn btn-md btn-success" href="${publishUrl}">Publish Quiz</a>
    </div>
</div>
</body>
</html>
