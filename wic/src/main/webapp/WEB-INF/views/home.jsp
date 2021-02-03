<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/bootstrap/js/bootstrap.js" />"></script>

<script src="<c:url value="/resources/js/bootstrap-multiselect.js"/>"></script>
<link href="<c:url value="/resources/js/bootstrap-multiselect.css" />" rel="stylesheet"/>

<script src="https://unpkg.com/layout-base/layout-base.js"></script>
<script src="https://unpkg.com/cose-base/cose-base.js"></script>
<script src="<c:url value="/resources/js/cytoscape-3.16.3/cytoscape.min.js" />"></script>
<script src="<c:url value="/resources/js/cytoscape-layouts/cytoscape-cose-bilkent.js" />"></script>

<script>
function loadData() {
	$.ajax({
			url : '<c:url value="/getAllConceptsType" />',
			type:"GET",
			success: function(data) {
				console.log("Hi");
				
			},
			error: function() {
				console.log("error");
			},
			async: false
		});
}
</script>
<head>
<script>loadData();</script>
</head>

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

<select id="dropdown" multiple="multiple">
    <option value="http://www.digitalhps.org/types/TYPE_986a7cc9-c0c1-4720-b344-853f08c136ab">Person</option>
    <option value="http://www.digitalhps.org/types/TYPE_dfc95f97-f128-42ae-b54c-ee40333eae8c">Place</option>
    <option value="http://www.digitalhps.org/types/TYPE_7f6adab7-943b-4a13-bd08-58afaadfec07">Place Name</option>
    <option value="http://www.digitalhps.org/types/TYPE_a2edb02a-dc1c-460d-b7ad-419a7738ebfa">Man Made Thing</option>
</select>

<style>
button.multiselect {
    background-color: #4CAF50;
    color: white;
}
</style>

<script type="text/javascript">
	var data = [];
	var apiResult;
	var stringifiedResult;
    $(document).ready(function() {
        $('#dropdown').multiselect({
        	onChange: function(element, checked) {
                var brands = $('#dropdown option:selected');
                var selected = [];
                $(brands).each(function(index, brand){
                    selected.push($(this).val());
                });
				data = selected;
				loadCytoScape(stringifiedResult,apiResult,data);
            }
        });
    });
</script>
<!--
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
  -->
<div id="network" class="graph" style="min-width: 500px; min-height: 500px;">
</div>