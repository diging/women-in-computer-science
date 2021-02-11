<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="https://unpkg.com/easymde/dist/easymde.min.css">
<script src="https://unpkg.com/easymde/dist/easymde.min.js"></script>
<script src="<c:url value="/resources/bootpag/jquery.bootpag.min.js" />"></script>

<ul class="list-group">
	<c:forEach items="${allConceptText}" var="element">
		<li class="list-group-item">
		<b>${element.title}</b>
		<br>
		${element.text}
		<div class="pull-right">
			<form onclick="deleteConceptText('${element.id}')">
			     <input type="submit" value="Delete" class="btn btn-primary">
		    </form>
		</div>
		<div class="pull-right">
			<a type="button" href="<c:url value="/admin/text/${element.id}/edit" />" class="btn btn-primary">edit</a>
		</div>
		</li>
	</c:forEach>
</ul>

<div id="page-selection"></div>

<script>

var pageNumber = 1;
$( document ).ready(function() {
	  $('#page-selection').bootpag({
	    total: [(`${totalPages}`)],
	    page: [(`${currentPageNumber}`)],
	    maxVisible: 10,
	    leaps: true,
	    firstLastUse: true,
	    first: '|<',
	    last: '>|',
	    wrapClass: 'pagination',
	    activeClass: 'active',
	    disabledClass: 'disabled',
	    nextClass: 'next',
	    prevClass: 'prev',
	    lastClass: 'last',
	    firstClass: 'first'
	  }).on("page", function(event, num){
		pageNumber = num;
	    window.location.assign("./"+num);
	  });
});
	  
function deleteConceptText(deleteValue) {
	var url = "/wic/admin/text/delete/"+deleteValue+"/"+pageNumber+"/?${_csrf.parameterName}=${_csrf.token}";
	console.log(url);
	$.ajax({
		type:"DELETE",
		url:url,
		success: function(msg) {
		},
		async:false
	});
}
</script>
