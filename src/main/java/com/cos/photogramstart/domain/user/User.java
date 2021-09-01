package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//JPA - java persistence API
//자바로 데이터를 영구적으로 저장(DB)할 수 있는 API를 제공

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // 디비에 테이블을 생성
public class User {
	@Id //primary key 지정
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 디비를 따라간다.
	private int id;
	
	@Column(length=20, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;
	private String website; // 웹사이트
	private String bio; // 자기소개
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl; // 사진
	private String role; // 권한
	
	// 나는 연관관계의 주인이 아니다. 테이블에 칼럼 만들지 마라.
	// user를 select 할 때 user id로 등록된 image들을 전부 가져와라.
	// LAZY : user를 select 할 때 user id로 등록된 image들을 가져오지마라. 대신 getImages() 함수가 호출될 때 가져와라.
	// EAGER : user를 select 할 때 user id로 등록된 image들을 전부 JOIN 해서 가져와라.
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"user"})
	private List<Image> images; // 양방향 매핑
	
	private LocalDateTime createDate;
	
	@PrePersist // 디비에 insert 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
}
