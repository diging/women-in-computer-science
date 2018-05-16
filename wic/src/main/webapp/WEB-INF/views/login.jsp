<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

  <form style="width: 300px; margin: auto" name='f' action="<c:url value='/login/authenticate' />" method='POST'>
    <h4>Administrator Login</h4> 
    <div class="form-group">
      <input type="text" class="form-control input-sm" name="username" placeholder="Username">
    </div>
    <div class="form-group">
      <input type="password" class="form-control input-sm" name="password" placeholder="Password">
    </div>
    <div class="form-group">
      <button type="submit" class="btn btn-primary btn-sm">Sign In</button>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  </form>