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

<h1>Show Quiz</h1>
Name: <c:out value="${quiz.name}"/> <br/>
Duration: <c:out value="${quiz.maxDurationInMin}"/>
</body>
</html>
