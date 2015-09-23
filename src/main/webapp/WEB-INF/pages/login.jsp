<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Login Page</title></head>
<body onload='document.f.username.focus();'>
<h3>Login with Username and Password</h3>
<form name='f' action='<c:url value="/j_spring_security_check"/>' method='POST'>
  <table>
    <tr><td>User:</td><td>
      <input type='text' name='j_username' value=''></td></tr>
    <tr><td>Password:</td>
      <td><input type='password' name='j_password'/></td></tr>
    <tr><td>
      <input name="submit" type="submit" value="Login"/></td><td>
      <input id="remember_me" name="remember" type="checkbox" />
      <label for="remember_me" class="inline">Remember me</label></td></tr>
      <input type="hidden"
             name="${_csrf.parameterName}"
             value="${_csrf.token}"/>
  </table>
</form>
</body>
</html>