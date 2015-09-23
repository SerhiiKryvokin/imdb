<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}"
      method="post" id="logoutForm">
  <input type="hidden"
         name="${_csrf.parameterName}"
         value="${_csrf.token}"/>
</form>
<script type="text/javascript">
    function logoutFormSubmit() {
        document.getElementById("logoutForm").submit();
    }
    if (window.addEventListener)
        window.addEventListener("load", logoutFormSubmit, false);
    else if (window.attachEvent)
        window.attachEvent("onload", logoutFormSubmit);
    else window.onload = logoutFormSubmit;
</script>
</body>
</html>
