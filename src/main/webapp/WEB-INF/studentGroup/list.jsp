<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>
        <c:set var="title" value="Student Gropus"/>
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
                    <th>Name</th>
                    <th>Emails</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${studentGroups}" var="group">
                    <tr>
                        <c:url var="groupUrl" value="show">
                            <c:param name="id" value="${group.id}"/>
                        </c:url>
                        <td><a href="${groupUrl}"><c:out value="${group.name}"/></a></td>
                        <td><c:out value="${group.emailsStr}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
