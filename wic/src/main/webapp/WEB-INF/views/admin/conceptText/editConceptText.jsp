<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="https://unpkg.com/easymde/dist/easymde.min.css">
<script src="https://unpkg.com/easymde/dist/easymde.min.js"></script>


<h3> Add title for the text</h3>
<textarea id="title" cols="150">${title}</textarea>

<h3> Add author for the text</h3>
<textarea id="author" cols="150">${author}</textarea>

<h3>Enter new Text</h3>
<textarea id="paragraph_text" cols="150" rows="3">${text}</textarea>
<script>var easyMDE = new EasyMDE({element: document.getElementById('paragraph_text')});</script>

<a onClick="updateText(${idOfData}); return true;" href="<c:url value="/admin/text/list?pageNumber=1"/>" class="btn btn-primary">Update</a>
<script>


function updateText(id) {
	console.log(id)
	if(document.getElementById('title').value.trim() === "") {
		alert("Title is null or empty");
	} else if(document.getElementById('author').value.trim() === "") {
		alert("Author is null or empty");
	} else if(easyMDE.value().trim() === "") {
		alert("Concept Text is null or empty");
	} else {
		console.log("hi");
		$.ajax({
			type:"POST",
			url : '<c:url value="/admin/text/update/?${_csrf.parameterName}=${_csrf.token}" />',
			data: {
				id:id, 
				text:easyMDE.value().trim(),
				title:document.getElementById('title').value.trim(),
				author:document.getElementById('author').value.trim()
			},
			success: function(msg) {
				alert("ConcepText updated");

			},
			async: false
		});
	}
}
</script>