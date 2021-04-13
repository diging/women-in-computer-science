<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="https://unpkg.com/easymde/dist/easymde.min.css">
<script src="https://unpkg.com/easymde/dist/easymde.min.js"></script>
<script src="<c:url value="/resources/bootpag/jquery.bootpag.min.js" />"></script>
<link type="text/css" href="<c:url value="/resources/DigInGIconPack/diging-icon-pack.css" />" rel="stylesheet">

<h3>List of Texts</h3>
<ul class="list-group">
	<c:forEach items="${allConceptText}" var="element">
		<li class="list-group-item">
			<div id="coneptText">
				<b>${element.title}</b>
				<br>
				${element.text}
			</div>
			
			<button type="button" class="btn btn-primary" data-whatever="${element.id}" data-toggle="modal" data-target="#deleteModal">
			  <span class="icon-trash-alt" ></span>
			</button>
			
			<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-body">
			        Are you sure you want to delete the selected Concept Text?
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			        <a id='deleteAPI' href="<c:url value="/admin/text/list?page=1"/>" class="btn btn-primary">
			    	Delete</a>
			      </div>
			    </div>
			  </div>
			</div>

			<div class="buttons">
				<a type="button" href="<c:url value="/admin/text/${element.id}/edit" />" 
				class="btn btn-primary"><span class="icon-edit" ></span></a>
			</div>
		</li>
	</c:forEach>
</ul>

<div id="page-selection"></div>

<style>

#coneptText {
	max-height: 60px;
	width: 90%;
	overflow: hidden;
	text-overflow: ellipsis;
}
.list-group-item {
	max-height: 70px;
	display: flex;
    flex-direction: row;
    align-items: center;
}
.buttons {
	margin: 2px;
}
</style>

<script>

$('#deleteModal').on('show.bs.modal', function (event) {
	var button = $(event.relatedTarget)
	var recipient = button.data('whatever')
	var modal = $(this)
	modal.find('.modal-footer').find('a').removeAttr('onclick');
	console.log(modal.find('.modal-footer').find('a'));
	var func = "deleteConceptText("+recipient+");";
	modal.find('.modal-footer').find('a').attr('onClick', func);
})
	
var pageNumber = 1;
$( document ).ready(function() {
	  $('#page-selection').bootpag({
	    total: [(`${totalPages}`)],
	    page: [(`${currentPageNumber}`)],
	    maxVisible: 10,
	    leaps: true,
	    firstLastUse: true,
	    first: '|<',
	    last: '>|',
	    wrapClass: 'pagination',
	    activeClass: 'active',
	    disabledClass: 'disabled',
	    nextClass: 'next',
	    prevClass: 'prev',
	    lastClass: 'last',
	    firstClass: 'first'
	  }).on("page", function(event, num){
		pageNumber = num;
	    window.location.assign("./list?page="+num);
	  });
});
	  
function deleteConceptText(deleteValue) {
	console.log(deleteValue);
	var url = "/wic/admin/text/"+deleteValue+"/delete?${_csrf.parameterName}=${_csrf.token}";
	console.log(url);
	$.ajax({
		type:"DELETE",
		url:url,
		success: function(msg) {
		},
		async:false
	});
}
</script>