let index = {
	init: function() {
		$("#btn_save").on("click", () => {
			this.save();
		});
		$("#btn_delete").on("click", () => {
			this.deleteById();
		});
		$("#btn_update").on("click", () => {
			this.update();
		});
		$("#btn-reply-save").on("click", () => {
			this.replySave();
		});
	},

	save: function() {
		let data = {
			title: $("#title").val(),
			content: $("#content").val(),
		};//data		

		$.ajax({
			type: "post",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		}).done(function(resp) {
			alert("게시글 작성이 완료되었습니다.");
			//console.log(resp);
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});//ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청

	},//save

	deleteById: function() {
		let id = $("#id").text();
		$.ajax({
			type: "delete",
			url: "/api/board/" + id,
			dataType: "json"
		}).done(function(resp) {
			alert("게시글 삭제가 완료되었습니다.");
			//console.log(resp);
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});//ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청

	},//deleteById

	update: function() {
		let id = $("#id").val()
		let data = {
			title: $("#title").val(),
			content: $("#content").val(),
		};//data	
		$.ajax({
			type: "put",
			url: "/api/board/" + id,
			contentType: "application/json; charset=UTF-8",
			data: JSON.stringify(data),
			dataType: "json"
		}).done(function(resp) {
			alert("게시글 수정이 완료되었습니다.");
			//console.log(resp);
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});//ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청

	},//save
	replySave: function() {
		let data = {
			content: $("#reply-content").val(),
			userId: $("#userId").val(),
			boardId: $("#boardId").val()
		};//data		
		$.ajax({
			type: "post",
			url: `/api/board/${data.boardId}/reply`,
			data: JSON.stringify(data),
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		}).done(function(resp) {
			alert("댓글 작성이 완료되었습니다.");
			//console.log(resp);
			location.href = `/board/${data.boardId}`;
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});//ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청

	},//save
	replyDelete: function(boardId,replyId) {
		$.ajax({
			type: "delete",
			url: `/api/board/${boardId}/reply/${replyId}`,
			dataType: "json"
		}).done(function(resp) {
			alert("댓글 삭제가 완료되었습니다.");
			//console.log(resp);
			location.href = `/board/${boardId}`;
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});//ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청

	},//deleteById
}//index

index.init();