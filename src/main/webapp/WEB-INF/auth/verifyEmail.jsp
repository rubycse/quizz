<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Please Verify Your Email Address</title>
</head>
<body>
    <div class="page-header" id="banner">
        <div class="row">
            <div class="col-sm-12">
                <h1>Please Verify Your Email Address</h1>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-6">
            <div class="well bs-component">
                Before you can start using Quizz, we need you to verify your email address.
                An email containing verification instructions was sent to ${email}.
            </div>
        </div>
    </div>
</body>
</html>
