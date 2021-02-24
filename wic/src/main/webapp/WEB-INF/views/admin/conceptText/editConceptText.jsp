<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="https://unpkg.com/easymde/dist/easymde.min.css">
<script src="https://unpkg.com/easymde/dist/easymde.min.js"></script>


<h3> Add title for the text</h3>
<textarea id="titleInput" cols="150">${title}</textarea>

<h3> Add author for the text</h3>
<textarea id="authorInput" cols="150">${author}</textarea>

<h3>Enter new Text</h3>
<textarea id="paragraph_text" cols="150" rows="3">${text}</textarea>
<script>var easyMDE = new EasyMDE({element: document.getElementById('paragraph_text')});</script>

<form action="<c:url value='/admin/text/update' />" onsubmit="return verifyNonEmpty()" method='POST'>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<input type="hidden" name="id" value=${idOfData} />
	<input type="hidden" id="title" name="title"/>
	<input type="hidden" id="author" name="author"/>
	<input type="hidden" id="conceptId" name="conceptId" value=${conceptId}/>
	<input type="hidden" id="text" name="text"/>
	<input type="submit" id="Add Concept Text">
</form>
<script>

function verifyNonEmpty() {
	
	if(document.getElementById('titleInput').value.trim() === "") {
		alert("Title is empty");
		return false;
	} else if(document.getElementById('authorInput').value.trim() === "") {
		alert("Author is empty");
		return false;
	} else if(easyMDE.value().trim() === "") {
		alert("Concept Text is empty");
		return false;
	} else {
		document.getElementById("title").value = document.getElementById("titleInput").value;
		document.getElementById("author").value = document.getElementById("authorInput").value;
		document.getElementById("text").value = easyMDE.value().trim();
		return true;
	}
}
</script>