<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        Show Quiz
    </title>
</head>
<body>
<fieldset>
    <div class="form-group">
        <label for="name" class="col-lg-2 control-label">Name</label>
        <div class="col-lg-6">
            <c:out value="${quiz.name}"/>
        </div>
    </div>

    <div class="form-group">
        <label for="maxDurationInMin" class="col-lg-2 control-label">Duration (Min)</label>
        <div class="col-lg-4">
            <c:out value="${quiz.maxDurationInMin}"/>
        </div>
    </div>

    <div class="form-group">
        <div class="col-lg-6 col-lg-offset-2">
            <button type="reset" class="btn btn-default">Cancel</button>
        </div>
    </div>
</fieldset>
</body>
</html>
