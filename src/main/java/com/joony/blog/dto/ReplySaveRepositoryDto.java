package com.joony.blog.dto;

import com.joony.blog.model.Reply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplySaveRepositoryDto {

	private int boardId;
	private int userId;
	private String content;
}//class
