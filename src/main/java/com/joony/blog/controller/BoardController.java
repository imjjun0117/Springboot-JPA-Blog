package com.joony.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.joony.blog.service.BoardService;

@Controller
public class BoardController {
	//@AuthenticationPrincipal PrincipalDetail principal 컨트롤러 단에서 시큐리티 세션을 매개변수로 받을 수 있다
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping({"/",""})
	public String index(Model model,@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
		model.addAttribute("boards",boardService.글목록(pageable));
		return "index";
	}//index
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/detail";
	}
	
	// USER 권한이 필
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}//index
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/updateForm";
	}//updateForm
	
}//class
