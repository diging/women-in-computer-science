<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="https://unpkg.com/easymde/dist/easymde.min.css">
<script src="https://unpkg.com/easymde/dist/easymde.min.js"></script>

<b>Enter new Text</b>
<textarea id="paragraph_text" cols="150" rows="3">${text}</textarea>
<script>var easyMDE = new EasyMDE({element: document.getElementById('paragraph_text')});</script>

<a onClick="updateText(${idOfData}); return true;" href="<c:url value="/admin/import/showConceptText"/>" class="btn btn-primary">Update</a>
<script>


function updateText(id) {
	console.log("inside:"+id);
	if(easyMDE.value().trim() === "") {
		console.log("bye");
		alert("Concept Text is null or empty");
	} else {
		console.log("hi");
		$.ajax({
			type:"POST",
			url : '<c:url value="/admin/import/updateConceptTextData/?${_csrf.parameterName}=${_csrf.token}" />',
			data: {
				id:id, 
				text:easyMDE.value().trim()
			},
			success: function(msg) {
				alert("ConcepText updated");

			},
			async: false
		});
	}
}
</script>