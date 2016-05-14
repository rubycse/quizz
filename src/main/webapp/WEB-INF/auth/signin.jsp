<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign in to Quizz account</title>
</head>
<body>

<div class="row">
    <div class="well bs-component">

        <form:form method="post" commandName="credential" cssClass="form-horizontal">
            <form:errors path="*">
                <div id="div_global_error" align="center">
                    <h1><s:message code="error.title"/></h1>
                    <s:message code="common.error.see"/>
                </div>
            </form:errors>

            <fieldset>
                <div class="form-group">
                    <label for="username" class="col-lg-2 control-label">Username</label>
                    <div class="col-lg-6">
                        <form:input path="username" cssClass="form-control" id="username" placeholder="Username"/>
                        <form:errors path="username" cssClass="ferror"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="password" class="col-lg-2 control-label">Password</label>
                    <div class="col-lg-6">
                        <form:input path="password" type="password" cssClass="form-control" id="password" placeholder="Password"/>
                        <form:errors path="password" cssClass="ferror"/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-lg-6 col-lg-offset-2">
                        <button type="reset" class="btn btn-default">Cancel</button>
                        <input type="submit" class="btn btn-primary" name="signin" value="Sign In"/>
                    </div>
                </div>
            </fieldset>
        </form:form>
    </div>
</div>
</body>
</html>
