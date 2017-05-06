<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><c:out value="${quiz.quizTemplate.name}"/></title>
    <link href='<c:url value="/css/quiz-1.0.0.css"/>' rel="stylesheet" type="text/css">
</head>
<body>
<div class="page-header" id="banner">
    <div class="row">
        <div class="col-sm-10">
            <h1><c:out value="${quiz.quizTemplate.name}"/></h1>
        </div>
        <div class="col-md-2">
            <h2><div id="countDown"></div></h2>
        </div>
    </div>
</div>

<form:form commandName="question" method="post" action="run">
    <div class="row">
        <div class="col-md-10">
            <div class="well bs-component">
                <input type="hidden" name="quizId" value="${quiz.id}">
                <input type="hidden" name="questionId" value="${question.id}">
                <div class="question">
                    <h3><c:out value="${question.label}"/></h3>
                    <div class="row">
                        <div class="col-md-6 col-sm-offset-1">
                            <form:radiobuttons id="answer"
                                               path="answer"
                                               items="${question.options}"
                                               itemValue="id"
                                               itemLabel="label"
                                               element="div class='radio'"
                                               htmlEscape="true"/>
                        </div>
                    </div>
                </div>
            </div>
            <input class="btn btn-md btn-success" type="submit" value="Next"/>
        </div>
    </div>
</form:form>

<script type="text/javascript">
    //TODO: It takes 1 s to display the time after page loading. Reduce this time.
    var countDownDate = new Date('${quiz.startTimeStr}');
    countDownDate.setMinutes(countDownDate.getMinutes() + ${quiz.quizTemplate.maxDurationInMin});
    var countDownTime = countDownDate.getTime();

    // Update the count down every 1 second
    var x = setInterval(function() {
        var distance = countDownTime - new Date().getTime();

        var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((distance % (1000 * 60)) / 1000);

        var countDownDiv = document.getElementById("countDown");
        countDownDiv.innerHTML = (hours == 0 ? '' : hours + ':') + minutes + ':' + seconds;

        // If the count down is over, write some text
        if (distance < 0) {
            clearInterval(x);
            countDownDiv.innerHTML = "EXPIRED";
        }
    }, 1000);
</script>
</body>
</html>
