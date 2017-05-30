<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        Show Quiz
    </title>
    <link href='<c:url value="/css/quiz-1.0.0.css"/>' rel="stylesheet" type="text/css">
    <script src="<c:url value="/js/jquery-1.11.3.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/js/jquery.jeditable-1.7.3.js"/>" type="text/javascript"></script>
    <script type="application/javascript">
        function showRemove(id) {
            $('#' + id).css('color', '#9d9d9d');
        }

        function hideRemove(id) {
            $('#' + id).css('color', 'transparent');
        }

        $(document).ready(function() {
            makeQuizNameEditable();
            makeQuestionsEditable();
        });

        function makeQuizNameEditable() {
            $('.quizName').editable('<c:url value="updateQuizName"/>', {style  : "inherit", cssclass : 'editable'});
        }

        function makeQuestionsEditable() {
            $('.questionLabel').editable('<c:url value="updateQuestionLabel"/>', {style: "inherit", cssclass: 'editable'});
            $('.optionLabel').editable('<c:url value="updateOptionLabel"/>', {style: "inherit", cssclass: 'editable'});
        }

        var optionViewTemplate = '    <div class="option" onmouseover="showRemove(\'removeOption-#OPTION_ID#\')" onmouseout="hideRemove(\'removeOption-#OPTION_ID#\')" id="option-#OPTION_ID#">'
                + '      <span id="removeOption-#OPTION_ID#" class="removeOption glyphicon glyphicon-remove" aria-hidden="true" onclick="deleteOption(\'#OPTION_ID#\')"></span>'
                + '      <input type="radio" name="#QUESTION_ID#" onclick="updateOption(\#OPTION_ID\#)" #CHECKED#/><span id="#OPTION_ID#" class="optionLabel" style="display: inline">#OPTION_LABEL#</span>'
                + '    </div>';

        var questionViewTemplate = '<div class="question" id="question-#QUESTION_ID#">'
                + '  <div id="#QUESTION_ID#" class="questionLabel" style="display: inline">#QUESTION_LABEL#</div><br/>'
                + '  <div id="options-#QUESTION_ID#">'
                + '  #OPTIONS#'
                + '  </div>'
                + '  <div class="option">'
                + '    <span class="removeOption glyphicon glyphicon-remove" aria-hidden="true"></span>'
                + '    <input type="radio"/>&nbsp;&nbsp;<a class="addOption" onclick="addOption(\'#QUESTION_ID#\');">Add Option</a>'
                + '  </div>'
                + '  <div class="questionFooter">'
                + '    <span class="questionRequired pull-right">'
                + '      <input type="checkbox" onclick="toggleRequired(\'#QUESTION_ID#\')" #CHECKED#/>&nbsp;Required'
                + '    </span>'
                + '    <span class="verticalBar pull-right">&nbsp;</span>'
                + '    <span class="questionDelete pull-right">'
                + '      <a class="glyphicon glyphicon-trash grey" onclick="deleteQuestion(\'#QUESTION_ID#\');"></a>'
                + '    </span>'
                + '  </div>'
                + '</div>';

        function getOptionHtml(questionId, option) {
            return optionViewTemplate.replace(/#QUESTION_ID#/g, questionId)
                    .replace(/#OPTION_ID#/g, option.id)
                    .replace("#OPTION_LABEL#", option.label)
                    .replace("#CHECKED#", option.rightAnswer? 'checked' : '');
        }

        function getQuestionHtml(question) {
            var quesHtml = questionViewTemplate.replace(/#QUESTION_ID#/g, question.id)
                    .replace('#QUESTION_LABEL#', question.label)
                    .replace("#CHECKED#", question.required ? 'checked' : '');
            var optionHtml = "";
            for (var index in question.options) {
                var option = question.options[index];
                optionHtml += getOptionHtml(question.id, option);
            }

            return quesHtml.replace("#OPTIONS#", optionHtml);
        }
    </script>
</head>
<body>
<form:form commandName="quizTemplate" method="post" action="complete" cssClass="form">
    <input type="hidden" name="id" value="${quizTemplate.id}"/>
    <div class="page-header" id="banner">
        <div class="row">
            <div class="col-sm-10">
                <h1><b class="quizName" id="${quizTemplate.id}" style="display: inline"><c:out value="${quizTemplate.name}"/></b></h1>
            </div>
            <div class="col-sm-2">
                <div class="verticalSpace">&nbsp;</div>
                <input type="submit" class="btn btn-md btn-warning pull-right" value="Complete"/>
            </div>
        </div>
    </div>
</form:form>

<div class="row">
    <div class="col-sm-12">
        <c:if test="${not empty errors.globalErrors}">
            <div class="alert alert-danger">
                <strong><s:message code="error.title"/></strong>
                <ul class="list-unstyled" style="font-weight: normal">
                    <c:forEach items="${errors.globalErrors}" var="errorMessage">
                        <li><s:message message="${errorMessage}"/></li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
    </div>
</div>

<fieldset>
    <div class="well bs-component">
    <div id="questions" style="margin-bottom: 20px;">
        <c:forEach items="${quizTemplate.questionTemplates}" var="question">
            <div class="question" id="question-${question.id}">
                <div id="${question.id}" class="questionLabel" style="display: inline">${question.label}</div>
                <br/>
                <div id="options-${question.id}">
                <c:forEach items="${question.options}" var="option">
                    <div class="option" onmouseover="showRemove('removeOption-${option.id}')" onmouseout="hideRemove('removeOption-${option.id}')" id="option-${option.id}">
                        <span id="removeOption-${option.id}" class="removeOption glyphicon glyphicon-remove" aria-hidden="true" onclick="deleteOption(${option.id})"></span>
                        <input type="radio" name="${question.id}" onclick="updateOption(${option.id})" <c:if test="${option.rightAnswer}">checked</c:if>/><span id="${option.id}" class="optionLabel" style="display: inline">${option.label}</span>
                    </div>
                </c:forEach>
                </div>
                <div class="option">
                    <span class="removeOption glyphicon glyphicon-remove" aria-hidden="true"></span>
                    <input type="radio"/>&nbsp;&nbsp;<a class="addOption" onclick="addOption(${question.id});">Add Option</a>
                </div>
                <div class="questionFooter">
                    <span class="questionRequired pull-right">
                        <input type="checkbox" onclick="toggleRequired(${question.id})" <c:if test="${question.required}">checked</c:if> />&nbsp;Required
                    </span>
                    <span class="verticalBar pull-right">&nbsp;</span>
                    <span class="questionDelete pull-right">
                        <a class="glyphicon glyphicon-trash grey" onclick="deleteQuestion(${question.id});"></a>
                    </span>
                </div>
            </div>
        </c:forEach>
    </div>
    </div>

    <div class="row">
        <div class="form-group">
            <div class="col-lg-6">
                <button class="btn btn-sm" onclick="addQuestion();">Add Question</button>
                <script type="text/javascript">
                    function addQuestion() {
                        $.post('<c:url value="addQuestion"/>',
                                {quizId: "${quizTemplate.id}"}, function (data) {
                                }).success(function (data, status, error) {
                                    $("#questions").append(getQuestionHtml(data));
                                    makeQuestionsEditable();
                                });
                    }

                    function addOption(questionId) {
                        $.post('<c:url value="addOption"/>',
                                {questionId: questionId}, function (data) {
                                }).success(function (data, status, error) {
                                    $("#options-" + questionId).append(getOptionHtml(questionId, data));
                                    makeQuestionsEditable();
                                });
                    }

                    function toggleRequired(questionId) {
                        $.post('<c:url value="toggleRequired"/>',
                                {questionId: questionId}, function (data) {
                                }).success(function (data, status, error) {
                                });
                    }

                    function deleteQuestion(questionId) {
                        $.post('<c:url value="deleteQuestion"/>',
                                {id: questionId},function (data) {
                                }).success(function (data, status, error) {
                                    if (data === "SUCCESS") {
                                        $("#question-" + questionId).remove();
                                    }
                                });
                    }

                    function deleteOption(optionId) {
                        $.post('<c:url value="deleteOption"/>',
                                {id: optionId},function (data) {
                                }).success(function (data, status, error) {
                                    if (data === "SUCCESS") {
                                        $("#option-" + optionId).remove();
                                    }
                                });
                    }

                    function updateOption(optionId) {
                        $.post('<c:url value="updateOption"/>',
                                {id: optionId},function (data) {
                                }).success(function (data, status, error) {
                                    if (data === "SUCCESS") {
                                    } else {
                                        alert('Can not be updated');
                                    }
                                });
                    }
                </script>
            </div>
        </div>
    </div>

</fieldset>
</body>
</html>
