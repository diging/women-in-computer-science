<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
<script src="/resources/bootstrap/js/bootstrap.js"></script>

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

<!-- <div class="dropdown">
  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Import <span class="caret"></span></a>
    <a href="<c:url value="/admin/import/person" />">Import Person</a>
    <a href="<c:url value="/admin/import/list" />">Show Imports</a>
    <a href="<c:url value="/admin/import/addConceptText" />">Add Text</a>
    <a href="<c:url value="/admin/import/showConceptText" />">Show Text</a>
  <button onclick="myFunction()" class="dropbtn">Hide Nodes</button>
  <div id="myDropdown" class="dropdown-content">
    <a href="#">Link 1</a>
    <a href="#">Link 2</a>
    <a href="#">Link 3</a>
  </div>
</div>  

<script>
function myFunction() {
  document.getElementById("myDropdown").classList.toggle("show");
}
</script> -->

<div class="dropdown pull-left" id="dropdownMenu">
  <button class="btn btn-secondary dropdown-toggle" type="button" 
          id="dropdownMenu1" data-toggle="dropdown" 
          aria-haspopup="true" aria-expanded="true">
    Hide Nodes
    <span class="caret"></span>
  </button>
  <ul class="dropdown-menu checkbox-menu allow-focus" aria-labelledby="dropdownMenu1">
  
    <li >
      <label>
        <input type="checkbox"> Cheese
      </label>
    </li>
    
    <li >
      <label>
        <input type="checkbox"> Pepperoni
      </label>
    </li>
    
    <li >
      <label>
        <input type="checkbox"> Peppers
      </label>
    </li>
    
  </ul>
</div>

<style>
.btn {
  background-color: #4CAF50; /* Green */
  border: none;
  color: white;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
}
.checkbox-menu li label {
    display: block;
    padding: 3px 10px;
    clear: both;
    font-weight: normal;
    line-height: 1.42857143;
    color: #333;
    white-space: nowrap;
    margin:0;
    transition: background-color .4s ease;
}
.checkbox-menu li input {
    margin: 0px 5px;
    top: 2px;
    position: relative;
}

.checkbox-menu li.active label {
    background-color: #cbcbff;
    font-weight:bold;
}

.checkbox-menu li label:hover,
.checkbox-menu li label:focus {
    background-color: #f5f5f5;
}

.checkbox-menu li.active label:hover,
.checkbox-menu li.active label:focus {
    background-color: #b8b8ff;
}
</style>

<script>
$(document).ready(function () {
    $('#dropdownMenu li').on('click', function () {
      var txt= ($(this).text());
      alert("Your Favourite Sports is "+txt);
    });
  });
  
[].forEach.call( document.querySelectorAll('#dropdownMenu li'), function(el) {
	   el.addEventListener('click', function() {
	     // anchor was clicked
	     console.log("jiiiii"+document.getElementById("dropdownMenu").value);
	  }, false);

	  

	});
</script>

<div id="network" class="graph" style="min-width: 500px; min-height: 500px;">
</div>