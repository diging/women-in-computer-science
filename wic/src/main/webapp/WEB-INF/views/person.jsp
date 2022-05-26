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
                $("#spinner1").hide();
                $("#network1").append("Sorry, no network to display.")
            } else {
                $("#spinner1").hide();
                var highlightSize = "20px";
                var nodeSize = "10px";
                var hrefLocation = '';
                var cy = loadCytoScape('network1', result, null, highlightSize, nodeSize, hrefLocation);
            }
        },
        error: function() {
            $("#spinner1").hide();
            $("#network1").append("Sorry, could not load network.")
        }
    });
    
    $.ajax({
        url : '<c:url value="/concept/${concept.id}/secondary-network" />',
        type : "GET",
        success : function(result) {
            if (result == null || result.length == 0) {
                $("#spinner2").hide();
                $("#network2").append("Sorry, no network to display.")
            } else {
                $("#spinner2").hide();
                var highlightSize = "20px";
                var nodeSize = "10px";
                var hrefLocation = '';
                var cy = loadCytoScape('network2', result, null, highlightSize, nodeSize, hrefLocation);
            }
        },
        error: function() {
            $("#spinner2").hide();
            $("#network2").append("Sorry, could not load network.")
        }
    });
    
    $.ajax({
        url : '<c:url value="/wiki-summary/concept/${concept.id}"/>',
        type : "GET",
        success : function(result) {
        	var wikiTab = $('<li><a id="wiki-tab" data-target="#wiki-summary" data-toggle="tab">Wiki</a></li>');
        	$("#bio-tabs").append(wikiTab);
        	
        	var wikiSummary = $('<div class="tab-pane" id="wiki-summary"><br>'+result+'</div>');
        	$('#bio-content').append(wikiSummary);
        	
        	if (!$("#bio-tab").length) {
        		$("#wiki-tab").tab('show');
        	}
        },
        error : function() {
		if (!$("#bio-tab").length) {
			$("#bio-tabs").remove();
		}
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
					$("#spinner3").hide();
					$("#graphList").append("Sorry, there are no relationships yet.")
				} else {
					var list = $.parseHTML(result);
					$("#spinner3").hide();
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

<ul class="nav nav-tabs" id="bio-tabs" style="display: flex; justify-content: center;">
    <c:if test="${concept.description != null and not concept.description.isEmpty()}">
        <li id="bio-tab" class="active"><a data-target="#biography" data-toggle="tab">Bio</a></li>
    </c:if>
</ul>
<div class="tab-content" id="bio-content">
    <c:if test="${concept.description != null and not concept.description.isEmpty()}">
        <div class="tab-pane active" id="biography"><br><p>${concept.description}</p></div>
    </c:if>
</div>

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
        <div id="spinner3"><div class="fa fa-spinner fa-spin"></div> Loading relationships... Hang tight, this might take a few minutes.</div>
    </ul>
</c:if>
</div>

<div class="col-md-5">
      <ul class="nav nav-tabs" id="myTab">
        <li class="active"><a data-target="#primaryNetwork" data-toggle="tab">Primary Network</a></li>
        <li><a data-target="#secondaryNetwork" data-toggle="tab">Secondary Network</a></li>
      </ul>
      <div class="tab-content" style="text-align:left;">
        <div class="tab-pane active" id="primaryNetwork">
          <div id="spinner1" class="text-center"><div class="fa fa-spinner fa-spin"></div> Loading graph...</div>
          <div id="network1" style="min-width: 200px; min-height: 200px; "></div>
        </div>
        <div class="tab-pane" id="secondaryNetwork">
          <div id="spinner2" class="text-center"><div class="fa fa-spinner fa-spin"></div> Loading graph...</div>
          <div id="network2" style="min-width: 200px; min-height: 200px; "></div>
        </div>
      </div>
        
    <c:if test="${texts != null and not texts.isEmpty()}">
    <h4 id="relationships">Relationships</h4>
    <ul id="graphList" class="list-group">
        <div id="spinner3"><div class="fa fa-spinner fa-spin"></div> Loading relationships... Hang tight, this might take a few minutes.</div>
    </ul>
    </c:if>
</div>
</div>
