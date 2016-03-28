<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        Create Quiz
    </title>
</head>
<body>
<h1>Create Quiz</h1>
<form:form method="post" commandName="quiz">
    <form:errors path="*">
        <div id="div_global_error" align="center">
            <h1><s:message code="error.title"/></h1>
            <s:message code="common.error.see"/>
        </div>
    </form:errors>

    Name:
    <form:input path="name"/>
    <form:errors path="name" cssClass="ferror"/>
    <br/>

    Duration
    <form:input path="maxDurationInMin"/>
    <form:errors path="maxDurationInMin" cssClass="ferror"/>

    <input type="submit" name="save" value="Save"/>
</form:form>

</body>
</html>
