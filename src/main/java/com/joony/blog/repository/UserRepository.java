package com.joony.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joony.blog.model.User;

// DAO
// 자동으로 bean등록이 된다.
// @Repository 생략 가능하다.
public interface UserRepository extends JpaRepository<User, Integer> {

	// select * from user where username=?1;
	Optional<User> findByUsername(String username);
	
}// class




// JPA Naming 전략
// select * from user where username=? and password = ?;
// User findByUsernameAndPassword(String username, String password);

//	@Query(value="select * from user where username=?1 and password = ?2",nativeQuery = true)
//	User login(String username, String password);