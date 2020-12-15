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
			<input id="clickMe" type="button" value="edit" onclick="editTextInfo('${element}')" />
		</div>
		</li>
	</c:forEach>
</ul>
<script>
function editTextInfo(element) {
	car = eval(element);
	console.log('${element.text}');
	var div = document.createElement("div");
	div.innerHTML = "hi";
	document.body.appendChild(div);
}
</script>