package com.joony.blog.test;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor --> final 붙은 변수 포함 생성자 생성
public class Member {
	//final - 불변성 유지하기 위해 db에서 가져온 데이터가 변경하지 않을 경우
	private int id;
	private String username;
	private String password;
	private String email;
	
	//Builder 패턴을 사용하면 자동으로 id값에 시퀀스를 부여
	@Builder
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
}//class
