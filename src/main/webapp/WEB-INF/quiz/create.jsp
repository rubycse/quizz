<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        Create Quiz
    </title>
</head>
<body>
<div class="row">
    <div class="well bs-component">

    <form:form method="post" commandName="quiz" cssClass="form-horizontal">
        <form:errors path="*">
            <div id="div_global_error" align="center">
                <h1><s:message code="error.title"/></h1>
                <s:message code="common.error.see"/>
            </div>
        </form:errors>

        <fieldset>
            <div class="form-group">
                <label for="name" class="col-lg-2 control-label">Name</label>
                <div class="col-lg-6">
                    <form:input path="name" cssClass="form-control" id="name" placeholder="Name"/>
                    <form:errors path="name" cssClass="ferror"/>
                </div>
            </div>

            <div class="form-group">
                <label for="maxDurationInMin" class="col-lg-2 control-label">Duration (Min)</label>
                <div class="col-lg-4">
                    <form:input path="maxDurationInMin" cssClass="form-control" id="maxDurationInMin"/>
                    <form:errors path="maxDurationInMin" cssClass="ferror"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-6 col-lg-offset-2">
                    <button type="reset" class="btn btn-default">Cancel</button>
                    <input type="submit" class="btn btn-primary" name="save" value="Save"/>
                </div>
            </div>
        </fieldset>
    </form:form>
    </div>
</div>

</body>
</html>
