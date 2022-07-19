<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">
	<form>
	<input type="hidden" value="${board.id }" id="id"/>
		<div class="form-group">
			<input value="${board.title }" type="text" class="form-control" placeholder="Enter title" id="title">
		</div>
		<div class="form-group">
			<textarea class="form-control summernote" rows="5" id="content" name="text">${board.content}</textarea>
		</div>
	</form>
	<button class="btn btn-primary" id="btn_update">글수정 완료</button>
</div>
<script>
	$('.summernote').summernote({
		placeholder : 'Hello Bootstrap 4',
		tabsize : 2,
		height : 300
	});
</script>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>