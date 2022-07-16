package com.joony.blog.test;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joony.blog.model.RoleType;
import com.joony.blog.model.User;
import com.joony.blog.repository.UserRepository;

//html파일이 아닌 data를 리턴해주는 객체
@RequestMapping("/dummy")
@RestController
public class DummyControllerTest {

	@Autowired // 의존성 주입(DI)
	private UserRepository userRepository;
	
	@DeleteMapping("/user/{id}")
	public String deleteUser(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 존재하지 않습니다.";
		}//end catch
		
		return id+"번이 삭제되었습니다.";
	}//deleteUser
	
	//save 함수는 id를 전달하지 않으면 insert를 해주고
	//save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	//save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해준다
	//password,email
	@Transactional //함수 종료시에 자동 commit이 된다.
	@PutMapping("/user/{id}")
	public User udateUser(@PathVariable int id, @RequestBody User requestUser) {//json 데이터 요청 -> Java Object(MessageConverter의 Jackson라이브러리로 변환해서 받는다
		System.out.println("id : "+id);
		System.out.println("password : "+requestUser.getPassword());
		System.out.println("email : "+requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException(id+"번 수정에 실패했습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		
//		userRepository.save(user);
		
		// 더티 체킹
		return user;
	}//updateUser
	
	@GetMapping("/user/{id}")
	public User detail(@PathVariable int id) {
		//user/4을 찾을때 데이터베이스에서 못찾아오게 되면 user가 null이 된다
		// return null이 될 문제로 java.util.Optional을 사용
		
		Optional<User> user= userRepository.findById(id);
		
		//user.get() --> orElseGet() null일 때 빈 객체를 넣는다. --> orElseThrow() -> IllegalArgumentException을 넣어 예외발생
		
		//요청:웹브라우저
		//user 객체 = 자바 오브젝트
		//변환(웹브라우저가 이해할 수 있는 데이터) -> json(Gson 라이브러리)
		//스프링부트 = MessageConverter라는 애가 응답 시에 자동 작동
		//만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리르 호출해서
		//user 오브젝트를 json으로 변환해서 브라우저에게 던져준다
		return user.orElseThrow(()->{
				return new IllegalArgumentException(id+"번 유저는 존재하지 않습니다.");
		});
	}//detail
	
	//모든 사용자 정보 조회
	@GetMapping("/users")
	public List<User> list(){
		return userRepository.findAll();
	}//list
	
	//한페이지당 2건의 데이터를 리턴받는다
	@GetMapping("/user")
	public List<User> pageList(@PageableDefault(size=1,sort="id",direction = Sort.Direction.DESC)Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);
		
//		if(pagingUser.isLast()) {
//				
//		}
		List<User> users = pagingUser.getContent();
		return users;
	}//pageList
	
	// http://localhost:8000/blog/dummy/join(요청)\
	// http의 body에 username, password, email 데이터를 가지고 요청
	@PostMapping("/join")
	public String join(User user) { // key=value(약속된 규칙)
		System.out.println("id : "+user.getId());
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		System.out.println("role : "+user.getRole());
		System.out.println("createDate : "+user.getCreateDate());
		user.setRole(RoleType.USER);
		
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}//join
}//class
