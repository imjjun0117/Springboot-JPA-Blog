package com.joony.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joony.blog.dto.ResponseDto;
import com.joony.blog.model.RoleType;
import com.joony.blog.model.User;
import com.joony.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;

	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		// System.out.println("호출됨");
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.CREATED.value(), 1);
	}// save

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
