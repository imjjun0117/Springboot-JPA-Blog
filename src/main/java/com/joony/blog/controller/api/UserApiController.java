package com.joony.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joony.blog.dto.ResponseDto;
import com.joony.blog.model.User;
import com.joony.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;


	@Autowired
	private AuthenticationManager authManager;
	
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		// System.out.println("호출됨");
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.CREATED.value(), 1);
	}// save

	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){
		userService.회원수정(user);
		// 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐지만
		// 세션값은 변경되지 않은 상태이기 때문에 우리가 직접 세견값을 변경해줄것임
//		//세션 등록
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}//update
	
}// class

//기본 로그인

//	@Autowired //매개변수로 받지않고 DI로 정의해도 된다.
//	private HttpSession session;

//	@PostMapping("/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user,HttpSession session){
//		System.out.println("UserApiController : login 호출됨");
//		User principal = userService.로그인(user);
//		if(principal != null) {
//			session.setAttribute("principal", principal);
//		}//end if
//		
//		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
//	}//login
