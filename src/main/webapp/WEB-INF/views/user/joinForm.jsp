<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">

	<form action="/user/join" method="POST">
		<div class="form-group">
			<label for="username">User name</label> <input type="text" class="form-control" placeholder="Enter username" id="username">
		</div>
		<div class="form-group">
			<label for="pwd">Password</label> <input type="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		<div class="form-group">
			<label for="email">Email</label> <input type="email" class="form-control" placeholder="Enter email" id="email">
		</div>
	</form>
	<button class="btn btn-primary" id="btn_save">회원가입완료</button>

</div>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>