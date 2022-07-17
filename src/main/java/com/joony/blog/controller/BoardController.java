package com.joony.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.joony.blog.config.auth.PrincipalDetail;

@Controller
public class BoardController {
	//@AuthenticationPrincipal PrincipalDetail principal 컨트롤러 단에서 시큐리티 세션을 매개변수로 받을 수 있다
	
	@GetMapping({"/",""})
	public String index() {
		//System.out.println("로그인 사용자 아이디 : "+principal.getUsername());
		return "index";
	}//index
	
}//class
