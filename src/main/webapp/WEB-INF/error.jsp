<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
<div class="page-header" id="banner">
    <div class="row">
        <div class="col-sm-12">
            <h1>Error</h1>
        </div>
    </div>
</div>
<div class="alert alert-danger">
    <strong><s:message code="error.title"/></strong>
    <s:message code="${messageKey}"/>
</div>
</body>

</html>
