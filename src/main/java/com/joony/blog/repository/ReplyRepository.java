package com.joony.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.joony.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{
	@Modifying//없으면 select로 인식한다
	@Query(value="INSERT INTO REPLY(userId,boardId,content,createDate) values(?1,?2,?3,now())",nativeQuery = true)
	int mSave(int userId,int boardId,String content); // 업데이트된 행의 개수를 리턴해줌
}
