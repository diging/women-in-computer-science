<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<ul class="list-group">
	<c:forEach items="${allConceptText}" var="element">
		<li class="list-group-item">
		<b>${element.title}</b>
		<br>
		${element.text}
		<div class="pull-right">
			<form action="/wic/admin/import/deleteConceptText/${element.id}" method="get">
			     <input type="submit" value="Delete">
		    </form>
		</div>
		<div class="pull-right">
			<input id="clickMe" type="button" value="edit" onclick="editTextInfo('${element.text}',
			'${element.id}','${element.conceptId}','${element.title}')" />
		</div>
		</li>
	</c:forEach>
</ul>
<script>
function editTextInfo(text,id,conceptId,title) {
	console.log(id);
	var div = document.createElement("div");
	var textAreaId = "paragraph_text"+id;
	div.className = 'container';
	var textArea = '<b>Previous Text</b>'+'<br>'+text+'<br>'+'<b>Enter new Text</b>';
	textArea += '<textarea id=';
	textArea += "'" +textAreaId +"' "
	textArea += 'cols="150" rows="3"></textarea><br>';
	div.innerHTML += textArea;
	
	var inputButton = '<input type="button" value="Update" onclick="updateText(';
	inputButton += "'" +textAreaId+ "'";
	inputButton += ',';
	inputButton += "'" +id+ "'";
	inputButton += ')">';
	div.innerHTML += inputButton;
	console.log(inputButton);
	document.body.appendChild(div);
}
function updateText(textAreaId,id) {
	console.log(textAreaId+"kkkk");
	if(document.getElementById(textAreaId).value.trim() === "") {
		console.log("bye");
		alert("Concept Text is null or empty");
	} else {
		console.log("hi");
		$.ajax({
			type:"POST",
			url : '<c:url value="/admin/import/updateConceptTextData/?${_csrf.parameterName}=${_csrf.token}" />',
			data: {
				id:id, 
				text:document.getElementById(textAreaId).value.trim()
			},
			success: function(msg) {
				alert("ConcepText updated");
				window.location.reload();
			}
		});
	}
}
</script>