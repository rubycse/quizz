<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    </script>
</head>
<body>
<div class="page-header" id="banner">
    <div class="row">
        <div class="col-sm-12">
            <h1><b class="quizName" id="${quiz.id}" style="display: inline"><c:out value="${quiz.name}"/></b></h1>
            (Duration: <c:out value="${quiz.maxDurationInMin}"/> Minute)
        </div>
    </div>
</div>
<fieldset>
    <div class="well bs-component">
    <div id="questions" style="margin-bottom: 20px;">
        <c:forEach items="${quiz.questions}" var="question">
            <div class="question">
                <div id="${question.id}" class="questionLabel" style="display: inline">${question.label}</div>
                <br/>
                <div id="options-${question.id}">
                <c:forEach items="${question.answerOptions}" var="option">
                    <div class="option">
                        <input type="radio"/>
                        <span id="${option.id}" class="optionLabel" style="display: inline">${option.label}</span>
                    </div>
                </c:forEach>
                </div>
                <a class="addOption" onclick="addOption(${question.id});">Add Option</a>
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
                                {quizId: "${quiz.id}"}, function (data) {
                                }).done(function (data, status, error) {
                                    $("#questions").append(data);
                                    makeQuestionsEditable();
                                });
                    }

                    function addOption(questionId) {
                        $.post('<c:url value="addOption"/>',
                                {questionId: questionId}, function (data) {
                                }).done(function (data, status, error) {
                                    $("#options-" + questionId).append(data);
                                    makeQuestionsEditable();
                                });
                    }
                </script>
            </div>
        </div>
    </div>

</fieldset>
</body>
</html>
