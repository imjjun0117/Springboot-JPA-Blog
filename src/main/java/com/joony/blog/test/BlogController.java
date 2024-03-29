package com.joony.blog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 스프링 component-scan 안에 모든 파일을 메모리에 new하는 것이 아니고
// 특정 어노테이션이 붙어있는 클래스 파일들을 new해서(IoC) 스프링 컨테이너에 관리해준다
@RestController
public class BlogController {
	
	@GetMapping("/test/hello")
	public String hello() {
		return "<h1>hello spring boot<h1>";
	}//hello
	
}//class
