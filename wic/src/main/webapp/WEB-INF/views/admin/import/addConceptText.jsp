<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="https://unpkg.com/easymde/dist/easymde.min.css">
<script src="https://unpkg.com/easymde/dist/easymde.min.js"></script>

<h3>Search Concept</h3>

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

<h3> Add title for the text</h3>
<textarea id="title" cols="150"></textarea>

<h3> Add author for the text</h3>
<textarea id="author" cols="150"></textarea>

<h3>Add Text in markdown</h3>
<textarea id="paragraph_text" cols="150" rows="8"></textarea>
<script>
var easyMDE = new EasyMDE({element: document.getElementById('paragraph_text')});
</script>

<form onSubmit="verifyNonEmpty()">
     <input type="submit" value="Add Concept Text">
</form>	    
<script>

var conceptId;

function verifyNonEmpty() {
	
	if(window.conceptId === undefined) {
		alert("Concept is not selected");
	} else if(document.getElementById('title').value.trim() === "") {
		alert("Title is null or empty");
	} else if(document.getElementById('author').value.trim() === "") {
		alert("Author is null or empty");
	} else if(easyMDE.value().trim() === "") {
		alert("Concept Text is null or empty");
	} else {
		$.ajax({
			type:"POST",
			url : '<c:url value="/admin/import/addConceptTextData/?${_csrf.parameterName}=${_csrf.token}" />',
			data: {
				conceptId:window.conceptId, 
				title:document.getElementById('title').value.trim(),
				author:document.getElementById('author').value.trim(),
				paragraph_text:easyMDE.value().trim()
				},
			success: function(msg) {
				alert("ConcepText added");
				window.location.reload();
			}
		});
	}
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
    $.get( "<c:url value='/admin/import/person/search' />?searchTerm=" + searchTerm, function( data ) {
    	   var container = $("#searchResults");
    	   container.empty();
       data.forEach(function(element) {
    	    var li = $('<a href="#" class="list-group-item"></a>');
    	    li.append('<i class="fas fa-tag"></i> <strong> ' + element.word + '</strong><br>');
    	    li.append('<small>' + element.description + '</small>');
    	    li.on('click', function() {
    	    	window.conceptId = element.id;
    	    });
    	    container.append(li);
    	   });
    });
}
//#sourceUrl=search.js
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