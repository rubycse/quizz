<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        Publish Quiz
    </title>

    <script src='<c:url value="/js/select2.min.js"/>' type="text/javascript"></script>
    <link href='<c:url value="/css/select2.min.css"/>' rel="stylesheet" type="text/css">

    <script type="text/javascript">
        $(document).ready(function () {

            $("#publishFor").select2({
                minimumResultsForSearch: Infinity
            });

            $("#publishToEmails").select2({
                tags: true,
                tokenSeparators: [',', ' ']
            });


            showHidePublishTo();
            $("#publishFor").on("change", function() {
                showHidePublishTo();
            });

            function showHidePublishTo() {
                if ($("#publishFor").val() == 'SELECTED_USER') {
                    $("#publishToEmailsDiv").show();
                } else {
                    $("#publishToEmailsDiv").hide();
                }
            }
        });
    </script>
</head>
<body>
<div class="page-header" id="banner">
    <div class="row">
        <div class="col-sm-12">
            <h1>Publish Quiz</h1>
            <c:url var="quizUrl" value="show">
                <c:param name="id" value="${publication.quizTemplate.id}"/>
            </c:url>
            <strong>Quiz:</strong>&nbsp;<a href="${quizUrl}"><c:out value="${publication.quizTemplate.name}"/></a>
        </div>
    </div>
</div>

<div class="row">
    <div class="well bs-component">
        <div class="row">

        <form:form method="post" commandName="publication" cssClass="form-horizontal">
            <form:errors path="*">
                <div id="div_global_error" align="center">
                    <h1><s:message code="error.title"/></h1>
                    <s:message code="common.error.see"/>
                </div>
            </form:errors>

            <fieldset>
                <div class="form-group">
                    <label for="durationInMin" class="col-lg-2 control-label">Duration (Min)</label>
                    <div class="col-lg-4">
                        <form:input path="durationInMin" cssClass="form-control" id="durationInMin"/>
                        <form:errors path="durationInMin" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="scheduleFrom" class="col-lg-2 control-label">Schedule</label>
                    <div class="col-lg-10">
                        <form:input path="scheduleFrom" cssClass="form-control" id="scheduleFrom" placeholder="From"/> To
                        <form:input path="scheduleTo" cssClass="form-control" id="scheduleTo" placeholder="To"/>
                        <form:errors path="scheduleFrom" cssClass="text-danger"/>
                        <form:errors path="scheduleTo" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="resultPublicationTime" class="col-lg-2 control-label">Result Will Be Published On</label>
                    <div class="col-lg-10">
                        <form:input path="resultPublicationTime" cssClass="form-control" id="resultPublicationTime"/>
                        <form:errors path="resultPublicationTime" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="publishFor" class="col-lg-2 control-label">Publish For</label>
                    <div class="col-lg-4">
                        <form:select id="publishFor" path="publishFor" cssClass="form-control">
                            <form:option value=""/>
                            <form:options items="${publishOptions}" itemLabel="label"/>
                        </form:select>
                        <form:errors path="publishFor" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group" id="publishToEmailsDiv">
                    <label for="publishToEmails" class="col-lg-2 control-label">Publish To</label>
                    <div class="col-lg-10">
                        <form:select id="publishToEmails" path="publishToEmails" cssClass="form-control" multiple="multiple">
                            <form:options items="${contacts}"/>
                        </form:select>
                        <form:errors path="publishToEmails" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-lg-6 col-lg-offset-2">
                        <input type="submit" class="btn btn-primary" name="save" value="Publish"/>
                    </div>
                </div>
            </fieldset>
        </form:form>
        </div>
    </div>
</div>

</body>
</html>
