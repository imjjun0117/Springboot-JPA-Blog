package com.joony.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joony.blog.config.auth.PrincipalDetail;
import com.joony.blog.dto.ReplySaveRepositoryDto;
import com.joony.blog.dto.ResponseDto;
import com.joony.blog.model.Board;
import com.joony.blog.model.Reply;
import com.joony.blog.service.BoardService;

@RestController
public class BoardApiController {

	@Autowired
	private BoardService boardService;

	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board,@AuthenticationPrincipal PrincipalDetail principal) {
		
		boardService.글쓰기(board,principal.getUser());
		
		return new ResponseDto<Integer>(HttpStatus.CREATED.value(), 1);
	}// save

	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id){
		boardService.글삭제(id);
		return new ResponseDto<Integer>(HttpStatus.CREATED.value(), 1);
	}
	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board){
		boardService.글수정(id,board);
		return new ResponseDto<Integer>(HttpStatus.CREATED.value(), 1);
	}//update
	
	//데이터 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다
	@PostMapping("/api/board/{boardId}/reply")
//	@PathVariable int boardId,@RequestBody Reply reply,
//	@AuthenticationPrincipal PrincipalDetail principal
	// @RequestBody ReplySaveRepositoryDto
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveRepositoryDto reply) {
		
		boardService.댓글쓰기(reply);
		return new ResponseDto<Integer>(HttpStatus.CREATED.value(), 1);
	}// save
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> replyDelete(@PathVariable int replyId){
		boardService.댓글삭제(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}// class

