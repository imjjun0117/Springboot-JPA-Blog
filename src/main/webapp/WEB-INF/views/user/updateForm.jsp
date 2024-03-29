<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">

	<form>
	<input type="hidden" id="id" value="${principal.user.id }"/>
		<div class="form-group">
			<label for="username">User name</label> 
			<input type="text" value="${principal.user.username }"class="form-control" placeholder="Enter username" id="username" readonly>
		</div>
		<c:if test="${empty principal.user.oauth}">
		<div class="form-group">
			<label for="pwd">Password</label> 
			<input type="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		</c:if>
		<div class="form-group">
			<label for="email">Email</label> <input type="email"${not empty principal.user.oauth ? 'readonly':'' } value="${principal.user.email }"class="form-control" placeholder="Enter email" id="email">
		</div>
	</form>
	<button class="btn btn-primary" id="btn_update" style=${not empty principal.user.oauth ? 'display:none':'' }>회원수정완료</button>

</div>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>