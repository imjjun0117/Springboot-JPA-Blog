package com.joony.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

	//http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		//파일리턴 기본경로 : src/main/resources/static
		
//		return "home.html";//src/main/resources/statichome.html
		return "/home.html";//src/main/resources/static/home.html
		
		
	}//tempHome

	//static js,css,img,html 등과 같은 정적 파일들을 위치시킨다
	//하지만 jsp는 동적파일이므로 찾지 못함 -> jasper 의존성 추가
	//webapp 폴더를 따로 생성하고 context-path 설정
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		return "test";
	}//tempJsp
	
}//class
