<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta http-equiv=X-UA-Compatible content="IE=edge">
    <meta name=viewport content="width=device-width,initial-scale=1">

    <title>Quizz&nbsp;::&nbsp;<decorator:title default="Welcome!"/></title>
    <decorator:head/>
</head>

<body>

<jsp:include page="/WEB-INF/header.jsp"/>
<div id="pageContent" class="container">
    <decorator:body/>
</div>
<jsp:include page="/WEB-INF/footer.jsp"/>

</body>
</html>
