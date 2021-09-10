<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="<c:url value="/resources/bootpag/jquery.bootpag.min.js" />"></script>
<script src="<c:url value="/resources/notify/bootstrap-notify.min.js" />"></script>
<link type="text/css" href="<c:url value="/resources/notify/animate.css" />" rel="stylesheet"/>
<link type="text/css" href="<c:url value="/resources/DigInGIconPack/diging-icon-pack.css" />" rel="stylesheet">

<div class="headerData">
	<h3>List of Texts</h3>
	<a type="button" href="<c:url value="/admin/text/add" />" 
		class="btn btn-primary"  id="addConcept">
		<span class="icon-circle-add" ></span>
	</a>
</div>

<ul class="list-group">
	<c:forEach items="${conceptTexts}" var="element">
		<li id="tr-${element.id}" class="list-group-item">
			<div id="coneptText">
				<b>${element.title}</b>
				<br>
				<span style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">${element.htmlRenderedText()}</span>
			</div>
			
			<button type="button" class="btn btn-primary" data-id="${element.id}" data-toggle="modal" data-target="#deleteModal">
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
			        <a id='deleteAPI' class="btn btn-primary">Delete</a>
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
.headerData {
	display: flex;
    flex-direction: row;
    align-items: center;
    justify-content:space-between;
}
</style>

<script>

$('#deleteModal').on('show.bs.modal', function (event) {
	var button = $(event.relatedTarget)
	var recipient = button.data('id')
	var modal = $(this)
	modal.find('.modal-footer').find('a').removeAttr('onclick');
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
	  
function deleteConceptText(id) {

	var url = "${pageContext.request.contextPath}/admin/text/"+id+"/delete?${_csrf.parameterName}=${_csrf.token}";

	$.ajax({
		type:"DELETE",
		url:url,
		success: function(msg) {
			$("#tr-"+id).remove();
			$.notify('<i class="icon-checkmark-alt"></i> Concept successfully deleted!', {
				type: 'success',
				offset: {
					x: 50,
					y: 90
				},
				animate: {
					enter: 'animated fadeInRight',
					exit: 'animated fadeOutRight'
				}
			});
		},
		async:false
	});
	$('#deleteModal').modal("hide");
	$('.modal-backdrop').remove();
}
</script>