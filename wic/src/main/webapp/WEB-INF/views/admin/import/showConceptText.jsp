<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<ul class="list-group">
	<c:forEach items="${allConceptText}" var="element">
		<li class="list-group-item">
		${element.text}
			<div class="pull-right">
				<form action="/wic/admin/import/deleteConceptText/${element.id}" method="get">
				     <input type="submit" value="Delete">
			    </form>
				<!--<button class="btn btn-default" type="button" id="{$element.id}">Delete</button>  -->
			</div>
		</li>
	</c:forEach>
</ul>