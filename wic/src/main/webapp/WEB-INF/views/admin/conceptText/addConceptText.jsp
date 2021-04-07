<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://unpkg.com/easymde/dist/easymde.min.js"></script>
<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
<link type="text/css" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="https://unpkg.com/easymde/dist/easymde.min.css">

<label>Search and Select Concept</label>
<form class="form-inline">
<div class="row">
<div class="col-md-12">
  <div class="input-group col-md-12">
    <input type="text" id="searchbox" class="form-control" placeholder="Search Conceptpower...">
    <span class="input-group-btn">
      <button class="btn btn-default" type="button" id="searchGo"><i class="fas fa-search"></i></button>
    </span>
  </div>
</div>
</div>
</form>
<div id="searchResults" class="list-group">
</div>

<form action="<c:url value='/admin/text/add' />"  onsubmit="return verifyNonEmpty()" method='POST'>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<div class="form-group">
	  <label for="title">Title</label>
	  <input type="text" class="form-control is-valid" id="title" name="title" placeholder="Enter Title">
	  <div class="invalid-feedback">
        Please provide a valid city.
      </div>
	</div>
	<div class="form-group">
	  <label for="author">Author</label>
	  <input type="text" class="form-control" name="author" id="author" placeholder="Enter Author">
	</div>
	<div class="form-group">
	  <label for="text">Add Text in Markdown</label>
	  <input type="text" class="form-control" id="text" name="text"  placeholder="Enter Text">
	</div>
	<input type="hidden" id="conceptId" name="conceptId"/>
	<button type="submit" class="btn btn-primary">Submit</button>
</form>

<style> 
#searchResults {
	max-height:195px;
}
.list-group {
	overflow: auto;
}
</style>

<script>
var easyMDE = new EasyMDE({element: document.getElementById('text')});
var conceptIdInput;

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