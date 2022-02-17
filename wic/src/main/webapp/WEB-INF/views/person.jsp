 <%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
#relationships {
    padding-top: 25px;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
    $.ajax({
        url : '<c:url value="/concept/${concept.id}/network" />',
        type : "GET",
        success : function(result) {
            if (result == null || result.length == 0) {
                $("#spinner").hide();
                $("#network").append("Sorry, no network to display.")
            } else {
                $("#spinner").hide();
                data = JSON.stringify(result);
                stringifiedResult = data;
                var highlightSize = "20px";
                var nodeSize = "10px";
                var hrefLocation = '';
                var cy = loadCytoScape(data, result, null, highlightSize, nodeSize, hrefLocation);
            }
        },
        error: function() {
            $("#spinner").hide();
            $("#network").append("Sorry, could not load network.")
        }
    });
});

//# sourceURL=getGraph.js
	$(function() {
	    getGraph();
	});

	function getGraph() {
		<c:if test="${not empty concept.id}" >
		$.ajax({
			url : '<c:url value="/concept/${concept.id}/statements" />',
			type : "GET",
			success : function(result) {
				if (result == null || result.trim() == '') {
					$("#spinner1").hide();
					$("#graphList").append("Sorry, there are no relationships yet.")
				} else {
					var list = $.parseHTML(result);
					$("#spinner1").hide();
					$("#graphList").append(list);
				}
			}
		});
		</c:if>
	}
</script>

<script src="https://unpkg.com/layout-base/layout-base.js"></script>
<script src="https://unpkg.com/cose-base/cose-base.js"></script>
<script src="<c:url value="/resources/js/cytoscape-3.16.3/cytoscape.min.js" />"></script>
<script src="<c:url value="/resources/js/cytoscape-layouts/cytoscape-cose-bilkent.js" />"></script>
<c:if test="${not empty concept.id}" >
</c:if>
<script src="<c:url value="/resources/js/graphDisplay.js" />"></script>

<div class="clearfix">
<h2>${concept.word}</h2>

<p>${concept.description}</p>

<div>
    <c:forEach var="listVar" items="${concept.getEqualTo() }"> 
        <c:if test="${not empty listVar }">
            <a href="${listVar}" target="_blank"> <i class="fa fa-external-link" aria-hidden="true"></i>       ${listVar} </a><br/>
        </c:if>
    </c:forEach>
</div>
<br/>

<div class="col-md-7 text-left">
<c:if test="${texts != null and not texts.isEmpty()}">
    <ul class="list-unstyled">
    <c:forEach items="${texts}" var="text">
    <li>
        <h3><strong>${text.title}</strong></h3>
        <figcaption>By ${text.author}</figcaption>
        <p>${text.htmlRenderedText()}</p>
    </li>
    </c:forEach>
    </ul>
</c:if>
<c:if test="${texts == null or texts.isEmpty()}">
    <h4 id="relationships">Relationships</h4>
    <ul id="graphList" class="list-group">
        <div id="spinner1"><div class="fa fa-spinner fa-spin"></div> Loading relationships... Hang tight, this might take a few minutes.</div>
    </ul>
</c:if>
</div>

<div class="col-md-5">
    <div id="spinner" class="text-center"><div class="fa fa-spinner fa-spin"></div> Loading graph...</div>
    <div id="network" style="min-width: 200px; min-height: 200px; "></div>
    
    <c:if test="${texts != null and not texts.isEmpty()}">
    <h4 id="relationships">Relationships</h4>
    <ul id="graphList" class="list-group">
        <div id="spinner1"><div class="fa fa-spinner fa-spin"></div> Loading relationships... Hang tight, this might take a few minutes.</div>
    </ul>
    </c:if>
</div>
</div>
