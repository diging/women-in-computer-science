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
<textarea id="titleInput" cols="150"></textarea>

<h3> Add author for the text</h3>
<textarea id="authorInput" cols="150"></textarea>

<h3>Add Text in markdown</h3>
<textarea id="paragraph_textInput" cols="150" rows="8"></textarea>
<script>
var easyMDE = new EasyMDE({element: document.getElementById('paragraph_textInput')});
</script>

<form action="<c:url value='/admin/text/add' />" onsubmit="return verifyNonEmpty()" method='POST'>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<input type="hidden" id="title" name="title"/>
	<input type="hidden" id="author" name="author"/>
	<input type="hidden" id="conceptId" name="conceptId"/>
	<input type="hidden" id="text" name="text"/>
	<input type="submit" id="Add Concept Text">
</form>
    
<script>

var conceptIdInput;

function verifyNonEmpty() {
	
	if(window.conceptIdInput === undefined) {
		alert("Concept is not selected");
		return false;
	} else if(document.getElementById('titleInput').value.trim() === "") {
		alert("Title is null or empty");
		return false;
	} else if(document.getElementById('authorInput').value.trim() === "") {
		alert("Author is null or empty");
		return false;
	} else if(easyMDE.value().trim() === "") {
		alert("Concept Text is null or empty");
		return false;
	} else {
		 document.getElementById("conceptId").value = window.conceptIdInput;
		 document.getElementById("title").value = document.getElementById("titleInput").value;
		 document.getElementById("author").value = document.getElementById("authorInput").value;
		 document.getElementById("text").value = easyMDE.value().trim();
		 return true;
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
    	    	window.conceptIdInput = element.id;
    	    });
    	    container.append(li);
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