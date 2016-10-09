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
                <c:param name="id" value="${publication.quiz.id}"/>
            </c:url>
            <strong>Quiz:</strong>&nbsp;<a href="${quizUrl}"><c:out value="${publication.quiz.name}"/></a>
        </div>
    </div>
</div>

<div class="row">
    <div class="well bs-component">

        <form:form method="post" commandName="publication" cssClass="form-horizontal">
            <form:errors path="*">
                <div id="div_global_error" align="center">
                    <h1><s:message code="error.title"/></h1>
                    <s:message code="common.error.see"/>
                </div>
            </form:errors>

            <fieldset>
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

</body>
</html>
