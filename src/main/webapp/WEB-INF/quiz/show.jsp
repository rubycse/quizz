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
<div class="page-header" id="banner">
    <div class="row">
        <div class="col-sm-12">
            <h1>Show Quiz</h1>
        </div>
    </div>
</div>
<fieldset>
    <div class="row">
        <div class="form-group">
            <div class="col-lg-6">
                <h1><c:out value="${quiz.name}"/></h1>
                <br/>(Duration: <c:out value="${quiz.maxDurationInMin}"/> Minute)
            </div>
        </div>
    </div>

    <div id="questions">

    </div>

    <div class="row">
        <div class="form-group">
            <div class="col-lg-6">
                <button type="reset" class="btn btn-sm">Add Question</button>
            </div>
        </div>
    </div>
</fieldset>
</body>
</html>
