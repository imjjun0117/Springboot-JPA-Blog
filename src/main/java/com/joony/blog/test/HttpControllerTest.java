package com.joony.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

//사용자가 요청 -> 응답(HTML 파일)
//@Controller

//사용자가 요청 -> 응답(Data)
@RestController
@RequestMapping("/http")
public class HttpControllerTest {

	private static final String TAG = "HttpControllerTest : ";

	//localhost:8080/http/lombok
	@GetMapping("/lombok")
	public String lombokTest() {
		//Builder 패턴을 사용했을 경우 Member 선언, 생성자 매개변수 헷갈릴 실수 없음
		Member m = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build();
		
		System.out.println(TAG+"getter : "+m.getUsername());
		m.setId(5000);
		System.out.println(TAG+"getter : "+m.getUsername());//@AllArgsConstructor
		return "lombok test 완료";
	}//lombokTest
	
	//인터넷 브라우저 요청은 get요청만 가능
	//http://localhost:8080/http/get (select)
	@GetMapping("/get")
	public String getTest(Member m) {//?id=13&username=hwang&password=1234&email=ssar@nate.com
		return "get 요청"+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}//getTest

	//http://localhost:8080/http/post (insert)
	@PostMapping("/post") // raw data -> text/plain / application/json
	//MessageConverter가 application/json 데이터를 자동으로 VO에 parsing 해줌
	//text/plain으로 json 데이터를 보낼 경우 text 형식 그대로 출력됨
	public String postTest(@RequestBody Member m) {  
		return "post 요청 : "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}//getTest
	
	//http://localhost:8080/http/put (update)
	@PutMapping("/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청"+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}//getTest
	
	//http://localhost:8080/http/delete (delete)
	@DeleteMapping("/delete/{id}")
	public String deleteTest(@PathVariable int id) {
		return "delete 요청 : "+id;
	}//getTest
}//class
