<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>
        <c:out value="${quiz.quizTemplate.name}"/>
    </title>
    <link href='<c:url value="/css/quizTemplate-1.0.0.css"/>' rel="stylesheet" type="text/css">
    <script type="text/javascript">
        var countDownDate = new Date();
        countDownDate.setMinutes(countDownDate.getMinutes() + ${quiz.quizTemplate.maxDurationInMin});
        var countDownTime = countDownDate.getTime();

        // Update the count down every 1 second
        var x = setInterval(function() {

            // Get todays date and time
            var now = new Date().getTime();

            // Find the distance between now an the count down date
            var distance = countDownTime - now;

            // Time calculations for days, hours, minutes and seconds
            var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
            var seconds = Math.floor((distance % (1000 * 60)) / 1000);

            // Output the result in an element with id="demo"
            document.getElementById("countDown").innerHTML = (hours == 0 ? '' : hours + ':')
                    + minutes + ':' + seconds;

            // If the count down is over, write some text
            if (distance < 0) {
                clearInterval(x);
                document.getElementById("countDown").innerHTML = "EXPIRED";
            }
        }, 1000);
    </script>
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
<div class="row">
    <div class="col-md-12">
        <div class="well bs-component">
            <c:forEach items="${quiz.questionTemplates}" var="questionTemplate">
                <div class="questionTemplate">
                    <h3><c:out value="${questionTemplate.label}"/></h3>
                    <form:form commandName="questionTemplate" method="post">
                        <input type="hidden" name="id" value="${questionTemplate.id}"/>
                        <form:radiobuttons path="options" items="${questionTemplate.options}" itemValue="id" itemLabel="label"/>
                        <input type="submit" value="Next"/>
                    </form:form>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
