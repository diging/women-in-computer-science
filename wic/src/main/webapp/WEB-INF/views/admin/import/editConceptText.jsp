<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="https://unpkg.com/easymde/dist/easymde.min.css">
<script src="https://unpkg.com/easymde/dist/easymde.min.js"></script>

<b>Previous Text</b>
<br>${text}<br>
<b>Enter new Text</b>
<textarea cols="150" rows="3"></textarea>
<script>var easyMDE = new EasyMDE();</script>
<input id="clickMe" type="button" value="Update"/>

<script>

document.getElementById("clickMe").onclick = function() {
	updateText(${idOfData},easyMDE);
};

function updateText(id,easyMDE) {
	console.log("inside");
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
				$.ajax({
					type:"GET",
					url : '<c:url value="/admin/import/showConceptText" />',
					success: function(msg) {
						console.log(msg);
						$( "html" ).html( msg );
					}
				})
				
			}
		});
	}
}
</script>