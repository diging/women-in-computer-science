<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src="https://unpkg.com/easymde/dist/easymde.min.js"></script>
<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
<link type="text/css" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="https://unpkg.com/easymde/dist/easymde.min.css">


<form:form method='POST' action="/wic/admin/text/add" modelAttribute="conceptTextFormData" onsubmit="return verifyNonEmpty()">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	
	<spring:bind path="conceptId">
	<label>Search and Select Concept</label>
	<div class="row">
	<div class="col-md-12">
	  <div class="input-group ${status.error ? 'has-error' : ''} col-md-12">
	    <input type="text" id="searchbox" class="form-control" placeholder="Search Conceptpower...">
	    <form:input path="conceptId" type="hidden" id="conceptId" name="conceptId"/>
	    <form:errors path="conceptId" />
	    <span class="input-group-btn">
	      <button class="btn btn-default" type="button" id="searchGo"><i class="fas fa-search"></i></button>
	    </span>
	  </div>
	</div>
	</div>
	<div id="searchResults" class="list-group">
	</div>
	</spring:bind>

	<spring:bind path="title">
	<div class="form-group ${status.error ? 'has-error' : ''}">
	  <label for="title">Title</label>
	  <form:input type="text" path="title" class="form-control" id="title" name="title" placeholder="Enter Title"/>
	  <form:errors path="title" />
	</div>
	</spring:bind>
	
	<spring:bind path="author">
	<div class="form-group ${status.error ? 'has-error' : ''}">
		<label for="author">Author</label>
		<form:input type="text" path="author" class="form-control" name="author" id="author" placeholder="Enter Author"/>
		<form:errors path="author" />
	</div>
	</spring:bind>
	
	<spring:bind path="text">
	<div class="form-group ${status.error ? 'has-error' : ''}">
		<label for="text">Add Text in Markdown</label>
	  	<form:input type="text" path="text" class="form-control" id="text" name="text"  placeholder="Enter Text"/>
	  	<form:errors path="text" />
	</div>
	</spring:bind>
	
	<button type="submit" class="btn btn-primary">Submit</button>
</form:form>

<style> 
#searchResults {
	max-height:195px;
}
.list-group {
	overflow: auto;
}
.has-error .form-control {
    border-color: #dc3545;
}
span[id*='errors']{
	color: #dc3545;
}
</style>

<script>
var easyMDE = new EasyMDE({element: document.getElementById('text')});
var conceptIdInput = null;

function verifyNonEmpty() {
	 document.getElementById("conceptId").value = window.conceptIdInput;
	 return true;
}

$( document ).ready(function() {
	$('#searchGo').click(function() {
		search($("#searchbox").val());
		return false;
	});
	
	$('#searchbox').keypress(function(event){
		var keycode = (event.keyCode ? event.keyCode : event.which);
	    if(keycode == '13'){
	        search($("#searchbox").val());
	        return false;
	    }
	});
});

function search(searchTerm) {
	var counter = 0;
	$.ajaxSetup({async: false});
    $.get( "<c:url value='/admin/import/person/search' />?searchTerm=" + searchTerm, function( data ) {
    	
   	   var container = $("#searchResults");
   	   container.empty();
       data.forEach(function(element) {
    	    var li = $('<a href="#" class="list-group-item" id='+counter+'></a>');
    	    li.append('<i class="fas fa-tag"></i> <strong> ' + element.word + '</strong><br>');
    	    li.append('<small>' + element.description + '</small>');
    	    li.on('click', function() {
    	    	var color = $( this ).css( "background-color" );
    	    	console.log(color);
    	    	$(this).css('background-color', '#ffff00');
    	    	window.conceptIdInput = element.id;
    	    });
    	    container.append(li);
    	    counter = counter+1;
    	   });
    });
}

$( document ).ready(function() {
	$('#selectConcept').click(function() {
		add($("#searchbox").val());
		return false;
	});
	
	$('#searchbox').keypress(function(event){
		var keycode = (event.keyCode ? event.keyCode : event.which);
	    if(keycode == '13'){
	    	add($("#searchbox").val());
	        return false;
	    }
	});
});

function add(textToAdd) {
	$("#confirmModal").modal('show');
}

</script>