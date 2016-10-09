<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Your Quizz Account</title>
</head>
<body>
<div class="page-header" id="banner">
    <div class="row">
        <div class="col-sm-12">
            <h1>Create Your Quizz Account</h1>
        </div>
    </div>
</div>
<div class="row">
    <div class="well bs-component">

        <form:form method="post" commandName="user" cssClass="form-horizontal">
            <form:errors path="*">
                <div id="div_global_error" align="center">
                    <h1><s:message code="error.title"/></h1>
                    <s:message code="common.error.see"/>
                </div>
            </form:errors>

            <fieldset>
                <div class="form-group">
                    <label for="firstName" class="col-lg-2 control-label">Name</label>
                    <div class="col-lg-3">
                        <form:input path="firstName" cssClass="form-control" id="firstName" placeholder="First"/>
                        <form:errors path="firstName" cssClass="text-danger"/>
                    </div>
                    <div class="col-lg-3">
                        <form:input path="lastName" cssClass="form-control" id="lastName" placeholder="Last"/>
                        <form:errors path="lastName" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="username" class="col-lg-2 control-label">Username</label>
                    <div class="col-lg-6">
                        <form:input path="username" cssClass="form-control" id="username" placeholder="Username"/>
                        <form:errors path="username" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="password" class="col-lg-2 control-label">Password</label>
                    <div class="col-lg-6">
                        <form:input path="password" type="password" cssClass="form-control" id="password" placeholder="Password"/>
                        <form:errors path="password" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="email" class="col-lg-2 control-label">Email</label>
                    <div class="col-lg-6">
                        <form:input path="email" cssClass="form-control" id="email" placeholder="Email"/>
                        <form:errors path="email" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="birthDate" class="col-lg-2 control-label">Birth Date</label>
                    <div class="col-lg-6">
                        <form:input path="birthDate" cssClass="form-control" id="birthDate" placeholder="Birth Date"/>
                        <form:errors path="birthDate" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="gender" class="col-lg-2 control-label">Gender</label>
                    <div class="col-lg-6">
                        <form:select path="gender" cssClass="form-control" id="select">
                            <form:option value="" label="Please Select"/>
                            <form:options path="gender" items="${genders}" itemLabel="displayName" cssClass="form-control" id="gender"/>
                        </form:select>
                    </div>
                </div>

                <div class="form-group">
                    <label for="phone" class="col-lg-2 control-label">Phone</label>
                    <div class="col-lg-6">
                        <form:input path="phone" cssClass="form-control" id="phone" placeholder="Phone"/>
                        <form:errors path="phone" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="student" class="col-lg-2 control-label">I am a</label>
                    <div class="col-lg-6">
                        <div class="radio">
                            <label>
                                <form:radiobutton path="student" id="student" label="Student" value="true"/>
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <form:radiobutton path="student" id="student" label="Teacher" value="false"/>
                            </label>
                        </div>
                        <form:errors path="student" cssClass="text-danger"/>
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
