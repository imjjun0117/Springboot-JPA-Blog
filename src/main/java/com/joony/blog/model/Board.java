package com.joony.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;
	
	@Column
	private String title;
	
	@Lob // 대용량 데이터
	private String content; // 섬머노트 라이브러리<html>태그가 섞여서 디자인됨
	
	@ColumnDefault("0")
	private int count;//조회수

	//FetchType.EAGER - 무조건 들고올 때 FetchType.LAZY - 데이터를 들고올 경우와 들고오지 않을 경우가 존재할 때
	@ManyToOne(fetch = FetchType.EAGER) // Many=Board, User=one 일대다 관계 
	@JoinColumn(name = "userId")// DB에서 FK로 저장 
	private User user; // DB는 오브젝트를 저장할 수 없다. FK,자바는 오브젝트를 저장할 수 있다.
	
	//Join용 변수 
	//mappedBy 연관관계의 주인이 아니다(난 FK가 아니에요) DB에 컬럼을 만들지 않는다 
	//reply에 있는 변수명을 적는다
	@OneToMany(mappedBy = "board",fetch = FetchType.LAZY) 
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	
}//class
