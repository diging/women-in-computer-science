<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

<textarea name="title" cols="150"></textarea>

<h3> Add author for the text</h3>

<textarea name="author" cols="150"></textarea>

<h3>Add Text in markdown</h3>

<textarea name="paragraph_text" cols="150" rows="8"></textarea>

<form action="verifyNonEmpty">
     <input type="submit" value="Add Concept Text">
</form>
			    
<script>

var conceptId;

function verifyNonEmpty() {
	if(conceptId === undefined) {
		alert("Concept is not selected");
	} else if($.trim($("title").val()) === "") {
		alert("Concept is null or empty");
	} else if($.trim($("author").val()) === "") {
		alert("Author is null or empty");
	} else if($.trim($("paragraph_text").val()) === "") {
		alert("Concept Text is null or empty");
	} else {
		
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
    	    	conceptId = element.id;
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

<!-- 



<form class="form-inline">
<div class="row">
<div class="col-md-12">
  <div class="input-group col-md-12">
    <input type="text" id="addText" class="form-control" placeholder="Enter Text to add...">
    <span class="input-group-btn">
      <button class="btn btn-default" type="button" id="selectConcept"><i class="fas fa-search"></i></button>
    </span>
  </div>
</div>
</div>
</form>


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



<div id="confirmModal" class="modal fade" tabindex="-1" role="dialog">
	<form action="<c:url value='/admin/import/person' />" method="POST">
	<input id="input-concept-id" type="hidden" name="id" value="">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<div class="modal-dialog" role="document">
	  <div class="modal-content">
	    <div class="modal-header">
	      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	      <h4 class="modal-title">Import <span id="import-title" /></h4>
	    </div>
	    <div class="modal-body">
	      <p>Are you sure you want to import the following concept?</p>
	      <p class="text-warning">
	      <strong><span id="concept-name"></span></strong> (<span id="concept-id"></span>)
	      </p>
	      <p class="text-muted" id="concept-desc"></p>
	    </div>
	    <div class="modal-footer">
	      <button type="button" class="btn btn-default" data-dismiss="modal">No, cancel.</button>
	      <button type="submit" class="btn btn-primary">Yes, import!</button>
	    </div>
	  </div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
	</form>
</div><!-- /.modal -->
 -->