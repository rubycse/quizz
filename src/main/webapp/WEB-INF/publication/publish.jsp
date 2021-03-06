<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        Publication of <c:out value="${publication.quizTemplate.name}"/>
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
                var $publishToEmailsDiv = $("#publishToEmailsDiv");
                var $studentGroupDiv = $("#studentGroupDiv");
                var $publishFor = $("#publishFor").val();

                $publishToEmailsDiv.hide();
                $studentGroupDiv.hide();

                if ($publishFor == 'SELECTED_USER') {
                    $publishToEmailsDiv.show();
                } else if ($publishFor == 'GROUP') {
                    $studentGroupDiv.show();
                }
            }
        });

        $(function () {
            $('#scheduleFrom_picker').datetimepicker({
                format: 'DD/MM/YYYY hh:mm a',
                useStrict: true,
                keepInvalid: true
            }).on('dp.error', function () {
                $('#scheduleFrom_dateTimePickerError').removeClass('hidden');
            }).on('dp.change', function () {
                $('#scheduleFrom_dateTimePickerError').addClass('hidden');
            });

            $('#scheduleTo_picker').datetimepicker({
                format: 'DD/MM/YYYY hh:mm a',
                useStrict: true,
                keepInvalid: true
            }).on('dp.error', function () {
                $('#scheduleTo_dateTimePickerError').removeClass('hidden');
            }).on('dp.change', function () {
                $('#scheduleTo_dateTimePickerError').addClass('hidden');
            });

            $('#resultPublicationTime_picker').datetimepicker({
                format: 'DD/MM/YYYY hh:mm a',
                useStrict: true,
                keepInvalid: true
            }).on('dp.error', function () {
                $('#resultPublicationTime_dateTimePickerError').removeClass('hidden');
            }).on('dp.change', function () {
                $('#resultPublicationTime_dateTimePickerError').addClass('hidden');
            });

            function escapeDots(value) {
                return value.replace(/\./g, '\\.').replace(/\[/g, '\\[').replace(/\]/g, '\\]');
            }
        });
    </script>

</head>
<body>
<div class="page-header" id="banner">
    <div class="row">
        <div class="col-sm-12">
            <c:url var="quizTemplateUrl" value="show">
                <c:param name="id" value="${publication.quizTemplate.id}"/>
            </c:url>
            <h1>Publication of <a href="${quizTemplateUrl}"><c:out value="${publication.quizTemplate.name}"/></a></h1>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <s:hasBindErrors name="publication">
            <div class="alert alert-danger">
                <strong><s:message code="error.title"/></strong>
                <ul class="list-unstyled" style="font-weight: normal">
                    <c:forEach items="${errors.globalErrors}" var="errorMessage">
                        <li><s:message message="${errorMessage}"/></li>
                    </c:forEach>
                </ul>
            </div>
        </s:hasBindErrors>

        <div class="well bs-component">

            <s:hasBindErrors name="publication">
                <div class="alert alert-danger">
                    <strong><s:message code="error.title"/></strong>
                    <ul class="list-unstyled" style="font-weight: normal">
                        <c:forEach items="${errors.globalErrors}" var="errorMessage">
                            <li><s:message message="${errorMessage}"/></li>
                        </c:forEach>
                    </ul>
                </div>
            </s:hasBindErrors>

            <form:form method="post" commandName="publication" cssClass="form-horizontal">

                <fieldset>
                    <div class="form-group">
                        <label for="durationInMin" class="col-md-2 control-label">Duration (Min)</label>
                        <div class="col-md-4">
                            <form:input path="durationInMin" cssClass="form-control" id="durationInMin"/>
                            <form:errors path="durationInMin" cssClass="text-danger"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="scheduleFrom" class="col-md-2 control-label">Schedule From</label>
                        <div class="col-md-4">
                            <div class="input-group" id="scheduleFrom_picker">
                                <form:input id="scheduleFrom" path="scheduleFrom" class="form-control" placeholder="DD/MM/YYYY hh:mm a"
                                            maxlength="19" htmlEscape="true"/>
                                <span class="input-group-addon" style="cursor: pointer">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                            <div id="scheduleFrom_dateTimePickerError" class="text-danger hidden">Invalid!</div>
                            <form:errors path="scheduleFrom" cssClass="text-danger"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="scheduleTo" class="col-md-2 control-label"><span class="text-center">Schedule To</span></label>
                        <div class="col-md-4">
                            <div class="input-group" id="scheduleTo_picker">
                                <form:input id="scheduleTo" path="scheduleTo" class="form-control" placeholder="DD/MM/YYYY hh:mm a"
                                            maxlength="19" htmlEscape="true"/>
                                <span class="input-group-addon" style="cursor: pointer">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                            <div id="scheduleTo_dateTimePickerError" class="text-danger hidden">Invalid!</div>
                            <form:errors path="scheduleTo" cssClass="text-danger"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="resultPublicationTime" class="col-md-2 control-label">Result Publication</label>
                        <div class="col-md-4">
                            <div class="input-group" id="resultPublicationTime_picker">
                                <form:input id="resultPublicationTime" path="resultPublicationTime" class="form-control"
                                            placeholder="DD/MM/YYYY hh:mm a"
                                            maxlength="19" htmlEscape="true"/>
                                <span class="input-group-addon" style="cursor: pointer">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                            <div id="resultPublicationTime_dateTimePickerError" class="text-danger hidden">Invalid!</div>
                            <form:errors path="resultPublicationTime" cssClass="text-danger"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="publishFor" class="col-md-2 control-label">Publish For</label>
                        <div class="col-md-4">
                            <form:select id="publishFor" path="publishFor" cssClass="form-control">
                                <form:option value=""/>
                                <form:options items="${publishOptions}" itemLabel="label"/>
                            </form:select>
                            <form:errors path="publishFor" cssClass="text-danger"/>
                        </div>
                    </div>

                    <div class="form-group" id="publishToEmailsDiv">
                        <label for="publishToEmails" class="col-md-2 control-label">Publish To</label>
                        <div class="col-md-9">
                            <form:select id="publishToEmails" path="publishToEmails" cssClass="form-control" multiple="multiple">
                                <form:options items="${contacts}"/>
                            </form:select>
                            <form:errors path="publishToEmails" cssClass="text-danger"/>
                        </div>
                    </div>

                    <div class="form-group" id="studentGroupDiv">
                        <label for="studentGroup" class="col-md-2 control-label">Group</label>
                        <div class="col-md-4">
                            <form:select path="studentGroup" cssClass="form-control">
                                <form:option value="" label=""/>
                                <form:options items="${studentGroups}" itemLabel="name" itemValue="id"/>
                            </form:select>
                            <form:errors path="studentGroup" cssClass="text-danger"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-md-6 col-md-offset-2">
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
