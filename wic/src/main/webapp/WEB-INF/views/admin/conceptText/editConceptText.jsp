<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://unpkg.com/easymde/dist/easymde.min.js"></script>
<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
<link type="text/css" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="https://unpkg.com/easymde/dist/easymde.min.css">

<form action="<c:url value='/admin/text/update' />" method='POST'>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<input type="hidden" name="id" value=${idOfData} />
	<div class="form-group">
	  <label for="title">Title</label>
	  <input type="text" class="form-control" id="title" name="title" value=${title}>
	</div>
	<div class="form-group">
	  <label for="author">Author</label>
	  <input type="text" class="form-control" name="author" id="author" value=${author}>
	</div>
	<div class="form-group">
	  <label for="text">Add Text in Markdown</label>
	  <input type="text" class="form-control" id="text" name="text" >
	</div>
	<input type="hidden" id="conceptId" name="conceptId" value=${conceptId}/>
	<button type="submit" class="btn btn-primary">Submit</button>
</form>

<script>
var easyMDE = new EasyMDE({element: document.getElementById('text')});
easyMDE.value(`${text}`);
</script>