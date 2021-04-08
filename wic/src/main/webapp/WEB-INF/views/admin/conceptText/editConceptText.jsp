<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src="https://unpkg.com/easymde/dist/easymde.min.js"></script>
<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
<link type="text/css" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="https://unpkg.com/easymde/dist/easymde.min.css">

<form:form action="/wic/admin/text/update" modelAttribute="conceptTextFormData" method='POST'>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<input type="hidden" name="id" value="${idOfData}" />
	
	<spring:bind path="title">
	<div class="form-group ${status.error ? 'has-error' : ''}">
	  <label for="title">Title</label>
	  <form:input type="text" path="title" class="form-control" id="title" name="title" value="${title}" />
	  <form:errors path="title" />
	</div>
	</spring:bind>

	<spring:bind path="author">
	<div class="form-group ${status.error ? 'has-error' : ''}">
		<label for="author">Author</label>
		<form:input type="text" path="author" class="form-control" name="author" id="author" value="${author}"/>
		<form:errors path="author" />
	</div>
	</spring:bind>

	<spring:bind path="text">
	<div class="form-group ${status.error ? 'has-error' : ''}">
		<label for="text">Add Text in Markdown</label>
	  	<form:input type="text" path="text" class="form-control" id="text" name="text"/>
	  	<form:errors path="text" />
	</div>
	</spring:bind>
	
	<input type="hidden" id="conceptId" name="conceptId" value="${conceptId}" />
	<button type="submit" class="btn btn-primary">Submit</button>
</form:form>

<style>
.has-error .form-control {
    border-color: #dc3545;
}
span[id*='errors']{
	color: #dc3545;
}
</style>

<script>
var easyMDE = new EasyMDE({element: document.getElementById('text')});
easyMDE.value(`${text}`);
</script>