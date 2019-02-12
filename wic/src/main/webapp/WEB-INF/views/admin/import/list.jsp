<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h1>Imported Persons</h1>

<p>The following ${fn:length(imports)} persons have been imported:</p>
<ul class="list-group">
<c:forEach items="${imports}" var="imported">
<li class="list-group-item">
<a href="<c:url value="/admin/import/person" />?import=${imported.id}">
${concepts[imported.conceptId].word}
</a>
<div class="pull-right">
<c:choose>
        <c:when test="${imported.status == 'STARTED'}">
        <span class="label label-warning">Started</span> on <span class="stupiddate" style="display: none;">${imported.startDate}</span>.
        </c:when>
        <c:when test="${imported.status == 'DONE'}">
        <span class="label label-success">Done</span> on <span class="stupiddate" style="display: none;">${imported.endDate}</span>.
        </c:when>
        <c:otherwise>
        <span class="label label-danger">Failed</span>
        </c:otherwise>
    </c:choose>
</div>
</li>
</c:forEach>
</ul>

<script>
$(function() {
	$(".stupiddate").each(function() {
		var date = $(this).text();
		date = date.replace("[", " (");
		date = date.replace("]", ")");
		$(this).text($.format.date(date, "MMM dd, yyyy HH:mm:ss"));
		$(this).show();
	});
});
</script>