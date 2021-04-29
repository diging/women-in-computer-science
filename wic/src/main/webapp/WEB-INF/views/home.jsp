<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/multiselect/bootstrap-multiselect.min.css" />" rel="stylesheet"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/bootstrap/js/bootstrap.js" />"></script>
<script src="https://unpkg.com/layout-base/layout-base.js"></script>
<script src="https://unpkg.com/cose-base/cose-base.js"></script>
<script src="<c:url value="/resources/js/cytoscape-3.16.3/cytoscape.min.js" />"></script>
<script src="<c:url value="/resources/js/cytoscape-layouts/cytoscape-cose-bilkent.js" />"></script>
<script src="<c:url value="/resources/js/multiselect/bootstrap-multiselect.min.js"/>"></script>
<script type="text/javascript">
    var cy;
    var highlightSize = "50px";
    var nodeSize = "15px";
    var url = '<c:url value="/network" />';
    var hrefLocation = "concept/";
    var animate = true;
</script>
<script src="<c:url value="/resources/js/graphDisplay.js" />"></script>
<div id="spinner" class="text-center">
            <div class="fas fa-spinner fa-spin"></div> Loading graph...
    </div>

<select id="dropdown" multiple="multiple" hidden>

</select>

<script>
var globalData;
(function() {
	$.ajax({
		url : '<c:url value="/getAllConceptsTypes" />',
		type:"GET",
		success: function(data) {	
			globalData = data;
		},
		error: function() {
			$("#network").append("Error while fetching concept types")
		}
	});
})()
function addDropDown() {
	for (i = 0; i < globalData.length; i++) {
		$('#dropdown').append("<option value=\"" + globalData[i]['uri'] + "\">" + 
				globalData[i]['name'] + "</option>");	
	}
	$('#dropdown').append("<option value=\"" + "" + "\">" + 
			"Hide Node without type" + "</option>");	
	var configurationSet = {
		maxHeight: 200,
		nonSelectedText: 'Hide Node Types',
		onChange: function(element, checked) {
					var brands = $('#dropdown option:selected');
					var selected = [];
					$(brands).each(function(index, brand){
					    selected.push($(this).val());
					});
					data = selected;
					loadCytoScape(stringifiedResult,apiResult,data);
    			}
    };
	$('#dropdown').multiselect(configurationSet);
}
</script>
<style>
.dropdown-item {
    display: block;
    width: 100%;
    padding: .25rem 1.5rem;
    clear: both;
    font-weight: 400;
    color: #212529;
    text-align: inherit;
    white-space: nowrap;
    background-color: #d80e0e00;
    border: 0;
}
</style>

<div id="network" class="graph" style="min-width: 500px; min-height: 500px;">

</div>