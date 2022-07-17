let index={
	init:function(){
		$("#btn_save").on("click",()=>{ 
		// function(){this-> window를 가리킴},
		//()=>{} this를 바인딩하기 위해서
			this.save();
		});
	},
	
	save:function(){
		//alert('user의 save함수 호출됨');
		let data={
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		};//data		
		//console.log(data);
		
		//ajax가 통신을 성공하고 json형태를 리턴하면 서버가 자동으로 오브젝트로 변환해준다.
		$.ajax({
			//회원가입 수행 요청
			type:"post",
			url:"/auth/joinProc",
			data:JSON.stringify(data),//http body 데이터
			contentType:"application/json; charset=UTF-8",//body 데이터가 어떤 타입인지(MIME)
			dataType:"json" //서버에서 응답이 왔을 때 기본적으로 모든 것이 문자열(json이면)=>javaScript 오브젝트로 변환
		}).done(function(resp){
			alert("회원가입이 완료되었습니다.");
			//console.log(resp);
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});//ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
		
	}//save
	
}//index

index.init();