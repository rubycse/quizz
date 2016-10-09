<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Sign In To Quizz Account</title>
</head>
<body>
<div class="page-header" id="banner">
    <div class="row">
        <div class="col-sm-12">
            <h1>Sign In To Quizz Account</h1>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-6">
        <div class="well bs-component">

            <form:form method="post" commandName="credential" cssClass="form-horizontal">
                <form:errors path="*">
                    <div id="div_global_error" align="center">
                        <h1><s:message code="error.title"/></h1>
                        <s:message code="common.error.see"/>
                    </div>
                </form:errors>

                <fieldset>
                    <div class="col-xs-12" style="height: 50px"></div>
                    <div class="form-group">
                        <label for="username" class="col-md-4 control-label">Username</label>
                        <div class="col-md-7">
                            <form:input path="username" cssClass="form-control" id="username" placeholder="Username"/>
                            <form:errors path="username" cssClass="text-danger"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password" class="col-md-4 control-label">Password</label>
                        <div class="col-md-7">
                            <form:input path="password" type="password" cssClass="form-control" id="password"/>
                            <form:errors path="password" cssClass="text-danger"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-md-11">
                            <input type="submit" class="btn btn-primary pull-right" name="signin" value="Sign In"/>
                        </div>
                    </div>
                </fieldset>
            </form:form>
        </div>
        </div>
    <div class="col-md-6">

    </div>
</div>
</body>
</html>
