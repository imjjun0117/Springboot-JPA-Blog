package com.joony.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joony.blog.dto.ReplySaveRepositoryDto;
import com.joony.blog.model.Board;
import com.joony.blog.model.Reply;
import com.joony.blog.model.User;
import com.joony.blog.repository.BoardRepository;
import com.joony.blog.repository.ReplyRepository;
import com.joony.blog.repository.UserRepository;

//서비스 스피링이 컴포넌트 스캔을 통해서 Bean에 등록해줌 IoC를 해준다
@Service
public class BoardService {

	
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private ReplyRepository replyRepository;
	
	@Transactional
	public void 글쓰기(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}// 글쓰기
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}//글목록
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id).
				orElseThrow(()->{
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});
	}//글상세보기
	
	@Transactional
	public void 글삭제(int id) {
		boardRepository.deleteById(id);
	}//글삭제
	
	@Transactional
	public void 글수정(int id, Board reqBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
			return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
		});//영속화 완료
		board.setTitle(reqBoard.getTitle());
		board.setContent(reqBoard.getContent());
		//해당 함수로 종료시(Serviece가 종료될 때) 트랜잭션이 종료된다. 이때 더티체킹 - 자동 업데이트가 됨 db flush
	}//글수정

	@Transactional
	public void 댓글쓰기(ReplySaveRepositoryDto requestReplyDto) {
		replyRepository.mSave(requestReplyDto.getUserId(),requestReplyDto.getBoardId(),requestReplyDto.getContent());
	}//댓글쓰기
	
	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
	}
}//class


