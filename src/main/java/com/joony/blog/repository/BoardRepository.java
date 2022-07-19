package com.joony.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joony.blog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	
}// class

