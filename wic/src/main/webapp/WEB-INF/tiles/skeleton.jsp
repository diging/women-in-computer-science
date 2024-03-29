<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">

    <link href='https://fonts.googleapis.com/css?family=Raleway' rel='stylesheet' type='text/css'>
    
    <title>Women in Computer Science</title>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="<c:url value="/resources/bootstrap/assets/css/ie10-viewport-bug-workaround.css" />" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<c:url value="/resources/bootstrap/grid.css" />" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="<c:url value="/resources/bootstrap/assets/js/ie8-responsive-file-warning.js" />"></script><![endif]-->
    <script src="<c:url value="/resources/bootstrap/assets/js/ie-emulation-modes-warning.js" />"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
 	<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/resources/bootstrap/js/main.js" />"></script>
	<script src="<c:url value="/resources/js/jquery-dateformat.min.js" />"></script>
  </head>

  <body>
    <div class="container" style="padding-bottom: 150px;">

      <div class="page-header">
      <nav>
          <ul class="nav nav-pills pull-right">
          <li role="presentation">
          		<a href="<c:url value="/" />" >Home</a>
          	</li>
          	
          	<sec:authorize access="isAuthenticated()">
          	 <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Import <span class="caret"></span></a>
	          <ul class="dropdown-menu">
	            <li><a href="<c:url value="/admin/import/person" />">Import Person</a></li>
	            <li><a href="<c:url value="/admin/import/list" />">Show Imports</a></li>
	          </ul>
	        </li>
	        <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" 
	          	aria-haspopup="true" aria-expanded="false"> Texts <span class="caret"></span></a>
	          <ul class="dropdown-menu">
	            <li><a href="<c:url value="/admin/text/add" />">Add Texts</a></li>
	            <li><a href="<c:url value="/admin/text/list?page=1" />">Show Texts</a></li>
	          </ul>
	        </li>
            </sec:authorize>
          </ul>
         
        </nav>
        
        <h1><a class="appName" href="<c:url value="/" />">Women in Computer Science</a></h1>
        <p class="lead">The giants on whose shoulders we stand on.</p>   
      </div>
      
      
	  <c:if test="${show_alert}" >
	  <div class="alert alert-${alert_type}" role="alert">${alert_msg}</div>
	  </c:if>
      <tiles:insertAttribute name="content" />

    </div> <!-- /container -->
    
    <footer class="footer">
      <div class="container">
      
        <div class="row">
        <div class="col-md-12">
		<hr style="margin-bottom: 25px;">
		<p class="text-muted pull-left">
		
	    <p class="text-muted">
	    
	         
	   	<sec:authorize access="not isAuthenticated()">
	   	
		<form name='f' class="form-inline pull-right" action="<c:url value="/login/authenticate" />" method="POST">
			Login:
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  			<input placeholder="Username" class="form-control input-sm" type="text" id="username" name="username"/>        
		    <input placeholder="Password" class="form-control input-sm" type="password" id="password" name="password"/>    
		    <button type="submit" class="btn btn-default btn-sm">Log in</button>
		</form>
		</sec:authorize>
		
		<sec:authorize access="isAuthenticated()">
		<form action="<c:url value="/logout" />" method="POST" class="pull-right">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <button class="btn-link" type="submit" title="Logout"><i class="fa fa-sign-out" aria-hidden="true"></i> Logout</button>
        </form>
        </sec:authorize>
        </p>
        </div>
        </div>
      </div>
    </footer>
    
    

     </body>
</html>
