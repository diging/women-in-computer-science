<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="https://unpkg.com/layout-base/layout-base.js"></script>
<script src="https://unpkg.com/cose-base/cose-base.js"></script>
<script src="<c:url value="/resources/js/cytoscape-3.16.3/cytoscape.min.js" />"></script>
<script src="<c:url value="/resources/js/cytoscape-layouts/cytoscape-cose-bilkent.js" />"></script>
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
<div id="network" class="graph" style="min-width: 500px; min-height: 500px;">

</div>