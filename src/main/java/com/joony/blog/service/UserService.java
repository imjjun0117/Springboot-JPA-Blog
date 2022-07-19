package com.joony.blog.service;

import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joony.blog.model.RoleType;
import com.joony.blog.model.User;
import com.joony.blog.repository.UserRepository;

//서비스 스피링이 컴포넌트 스캔을 통해서 Bean에 등록해줌 IoC를 해준다
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword();

		String encPassword = encoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}// 회원가입

	@Transactional
	public void 회원수정(User user) {
		//수정시에는 JPA 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		//select로 영속화 -> 영속화된 오브젝트를 변경하면 자동으로 DB update 실행
		User persistance = userRepository.findById(user.getId()).orElseThrow(() -> {
			return new IllegalArgumentException("회원수정 실패 : 아이디를 찾을 수 없습니다.");
		});//영속화 끝
		
		//postman 공격 방지 //Validate 체크 //이메일 비밀번호 수정불가
		if(persistance.getOauth()==null || "".equals(persistance.getOauth())) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());
		}//end if
	
		//회원수정 함수 종료 -> 서비스 종료 = 트랜잭션 종료 = 커밋이 자동으로 된다.
		//영속화된 persistence 객체의 변화가 감지되면 더티체킹
	}// 회원수정

	@Transactional(readOnly = true)
	public User 회원찾기(String username) {
		return userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		}); // 존재하지 않으면 NULL 반환
	}//회원찾기
	
}//class

//기본 로그인

//	@Transactional(readOnly = true) // Select 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료(정합성)
//	public User 로그인(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//		
//	}// 로그인