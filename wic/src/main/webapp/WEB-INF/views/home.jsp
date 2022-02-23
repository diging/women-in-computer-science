<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link href="<c:url value="/resources/css/multiselect/bootstrap-multiselect.min.css" />" rel="stylesheet"/>
<style>
.align-right {
    text-align: right;
    padding-right: 200px;
}
</style>
<script src="https://unpkg.com/layout-base/layout-base.js"></script>
<script src="https://unpkg.com/cose-base/cose-base.js"></script>
<script src="<c:url value="/resources/js/cytoscape-3.16.3/cytoscape.min.js" />"></script>
<script src="<c:url value="/resources/js/cytoscape-layouts/cytoscape-cose-bilkent.js" />"></script>
<script src="<c:url value="/resources/js/multiselect/bootstrap-multiselect.min.js"/>"></script>
<script type="text/javascript">
</script>
<script src="<c:url value="/resources/js/graphDisplay.js" />"></script>
<div id="spinner" class="text-center">
    <div class="fas fa-spinner fa-spin"></div> Loading graph...
</div>
<div class="align-right">
<select id="dropdown" multiple="multiple" hidden>
</select>
</div>
<script>
$(document).ready(function() {
    $.ajax({
        url : "<c:url value='/network' />",
        type : "GET",
        success : function(result) {
            if (result == null || result.length == 0) {
                $("#spinner").hide();
                $("#network").append("Sorry, no network to display.")
            } else {
                $("#spinner").hide();
                data = JSON.stringify(result);
                var highlightSize = "50px";
                var nodeSize = "15px";
                var hrefLocation = "concept/";
                var cy = loadCytoScape('network', data, result, null, highlightSize, nodeSize, hrefLocation);
                addDropDown(cy);
            }
        },
        error: function() {
            $("#spinner").hide();
            $("#network").append("Sorry, could not load network.")
        }
    });
});

function addDropDown(cy) {
	$.ajax({
		url : '<c:url value="/concept/types/all" />',
		type: "GET",
		success: function(data) {	
			for (i = 0; i < data.length; i++) {
				$('#dropdown').append("<option value=\"" + data[i]['uri'] + "\">" + 
						data[i]['name'] + "</option>");	
			}
			$('#dropdown').append("<option value=\"" + "" + "\">" + 
					"Hide Node without type" + "</option>");	
			var configurationSet = {
				maxHeight: 200,
				nonSelectedText: 'Hide Nodes',
				includeSelectAllOption: true,
				buttonText: function(options) {
					return '';
				},
				buttonTitle: function(options, select) {
					return 'Hide Nodes';
				},
				onChange: function(element, checked) {
					cy = hideNodes(cy);
				},
				onSelectAll: function(options) {
					cy = hideNodes(cy);
				},
				onDeselectAll: function(options) {
					cy = hideNodes(cy);
				}
			};
			$('#dropdown').multiselect(configurationSet);
			$(".multiselect").addClass("fas fa-eye-slash");
		},
		error: function() {
			$("#network").append("Error while fetching concept types")
		}
	});
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