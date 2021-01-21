<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="https://unpkg.com/easymde/dist/easymde.min.css">
<script src="https://unpkg.com/easymde/dist/easymde.min.js"></script>

<ul class="list-group">
	<c:forEach items="${allConceptText}" var="element">
		<li class="list-group-item">
		<b>${element.title}</b>
		<br>
		${element.text}
		<div class="pull-right">
			<form action="/wic/admin/import/deleteConceptText/${element.id}" method="get">
			     <input type="submit" value="Delete" class="btn btn-primary">
		    </form>
		</div>
		<div class="pull-right">
			<!--<form method="post" action="editConceptText.jsp">
				<input type="submit" value="edit1">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			  	<input type="hidden" name="text" value=`${element.text}`>  
			</form>-->
			<!-- <input id="clickMe" type="button" value="edit" onclick="editTextInfo(`${element.text}`,`${element.id}`,`${element.conceptId}`,`${element.title}`)" />  -->
			<a type="button" href="<c:url value="/admin/import/editConceptTextView/${element.id}" />" class="btn btn-primary">edit</a>
		</div>
		</li>
	</c:forEach>
</ul>
<script>
function editTextInfo(text,id,conceptId,title) {
	
	$.ajax({
		type:"GET",
		url : '<c:url value="/admin/import/editConceptTextView" />',
		data: {
			id:id,
			text:text,
			title:title,
			conceptId:conceptId
		},
		success: function(msg) {
			$( "html" ).html( msg );
		}
	});
}
</script>
