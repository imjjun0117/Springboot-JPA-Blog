package com.joony.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.joony.blog.model.User;
import com.joony.blog.repository.UserRepository;
@Service
public class PrincipalDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	//스프링이 로그인 요청을 가로챌 때, username, password 변수 2개를 가로채는데
	//password 부분 처리는 알아서함
	//username DB에 있는지만 확인해주면 됨
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username)
				.orElseThrow(()->{
					return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."+username);
				});
		return new PrincipalDetail(principal); //시큐리티의 세션에 유저 정보가 저장이 됨.
		//PrincipalDetail을 그냥 반환하게 되면 아디이:user 비밀번호:콘솔창 비밀번호가 저장이된다. 즉, 사용자 정보를 가져오기 위해 사용
	}//loadUserByUsername

}
