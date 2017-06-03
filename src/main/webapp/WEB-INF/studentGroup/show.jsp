<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <c:set var="title" value="${studentGroup.id == 0 ? 'Create' : ''} Student Group"/>
        ${title}
    </title>

    <script src='<c:url value="/js/select2.min.js"/>' type="text/javascript"></script>
    <link href='<c:url value="/css/select2.min.css"/>' rel="stylesheet" type="text/css">

    <script type="application/javascript">
        $(document).ready(function () {
            $("#emails").select2({
                tags: true,
                tokenSeparators: [',', ' ']
            });
        });
    </script>
</head>

<body>
<div class="page-header" id="banner">
    <div class="row">
        <div class="col-sm-12">
            <h1>${title}</h1>
        </div>
    </div>
</div>

<s:hasBindErrors name="studentGroup">
    <div class="alert alert-danger">
        <strong><s:message code="error.title"/></strong>
        <ul class="list-unstyled" style="font-weight: normal">
            <c:forEach items="${errors.globalErrors}" var="errorMessage">
                <li><s:message message="${errorMessage}"/></li>
            </c:forEach>
        </ul>
    </div>
</s:hasBindErrors>

<div class="row">
    <div class="col-md-12">
        <div class="well bs-component">

            <form:form method="post" commandName="studentGroup" cssClass="form-horizontal">
                <fieldset>
                    <div class="form-group">
                        <label for="name" class="col-lg-2 control-label">Name</label>
                        <div class="col-lg-6">
                            <form:input path="name" cssClass="form-control" id="name"/>
                            <form:errors path="name" cssClass="text-danger"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="emails" class="col-md-2 control-label">Emails</label>
                        <div class="col-md-10">
                            <form:select id="emails" path="emails" cssClass="form-control" multiple="multiple">
                                <form:options items="${studentGroup.emails}"/>
                            </form:select>
                            <form:errors path="emails" cssClass="text-danger"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-lg-6 col-lg-offset-2">
                            <input type="submit" class="btn btn-primary" name="save" value="Save"/>
                            <input type="submit" class="btn" name="delete" value="Delete" onclick="confirm('Are you sure you want to delete this group?')"/>
                        </div>
                    </div>
                </fieldset>
            </form:form>
        </div>
    </div>
</div>

</body>
</html>
