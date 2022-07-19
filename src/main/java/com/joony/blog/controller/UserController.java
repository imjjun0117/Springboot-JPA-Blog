package com.joony.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.joony.blog.model.KakaoProfile;
import com.joony.blog.model.OAuthToken;
import com.joony.blog.model.User;
import com.joony.blog.service.UserService;

//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/ **허용
//그냥 주소가 / 이면 index.jsp허용
//static 이하에 있는 /js/**, /css/**, /image/**

@Controller
public class UserController {

	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired 
	UserService userService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {

		return "user/joinForm";
	}// join

	@GetMapping("/auth/loginForm")
	public String loginForm() {

		return "user/loginForm";
	}// join

	@GetMapping("/user/updateForm")
	public String updateForm() {

		return "user/updateForm";
	}// join

	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) { // @ResponseBody Data를 리턴해주는 컨트롤러 함수

		// POST 방식으로 key=value 데이터를 요청(카카오쪽으로)
		// HttpURLConnection,Restrofit2,OkHttp,RestTemplate 라이브러리로 key=value전송 가능
		RestTemplate rt = new RestTemplate();

		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // content-type을 담음으로
																						// key=value형태 데이터 전송

		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "15dc4c8b0574c96134422f2d9c68a87e");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);

		// HttpHeader와 HttpBody를 하나의 오브젝트에 넣기 reposeEntity 매개변수로 들어갈 타입
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
		// Http 요청하기
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest, String.class);

		// Gson, Json Simple, ObjectMapper로 JSON 받기(리소스 서버 권한 획득)
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} // end catch
		
		RestTemplate rt2 = new RestTemplate();

		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization","Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // content-type을 담음으로
																						// key=value형태 데이터 전송
		// HttpHeader와 HttpBody를 하나의 오브젝트에 넣기 reposeEntity 매개변수로 들어갈 타입
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);
		// Htpp 요청하기
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me", 
				HttpMethod.POST,
				kakaoProfileRequest2, 
				String.class);

		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} // end catch

		//User 오브젝트 : username, password, email
		System.out.println(kakaoProfile.getId()+" / "+kakaoProfile.getKakao_account().getEmail());

		//블로그 정보 만들기(임시)
		//UUID garbagePassword = UUID.randomUUID(); // 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘
		String username =kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId();
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.email(kakaoProfile.getKakao_account().getEmail())
				.password(cosKey)
				.oauth("kakao")
				.build();
		
		//가입자 혹은 비가입자 체크해서 처리
		//조회 
		User originUser =  userService.회원찾기(username);
		
		//비가입자일 경우 가입
		if(originUser.getUsername() == null) {
			userService.회원가입(kakaoUser);
		}//end if
		
		//가입자인 경우 로그인 & 세션
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(),cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/";
	}// kakaoCallback
}// class
