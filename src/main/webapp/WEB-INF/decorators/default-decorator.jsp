<!DOCTYPE html>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta http-equiv=X-UA-Compatible content="IE=edge">
    <meta name=viewport content="width=device-width,initial-scale=1">

    <title>Quizz&nbsp;::&nbsp;<decorator:title/></title>

    <link href='<c:url value="/images/favicon.ico"/>' rel="icon" type="image/x-icon">

    <link href='<c:url value="/css/bootswatch/3.3.6/standstone/bootstrap.min.css"/>' rel="stylesheet" type="text/css">
    <decorator:head/>
</head>

<body>

<jsp:include page="/WEB-INF/header.jsp"/>

<br/>
<div id="pageContent" class="container">
    <div class="page-header" id="banner">
        <div class="row">
            <div class="col-lg-8 col-md-7 col-sm-6">
                <h1><decorator:title/></h1>
            </div>
        </div>
    </div>
    <decorator:body/>
</div>

<jsp:include page="/WEB-INF/footer.jsp"/>

</body>
</html>
