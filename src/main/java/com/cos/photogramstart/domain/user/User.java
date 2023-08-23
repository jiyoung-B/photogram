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

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // 자동 증가 전략이 데이터베이스를 따라간다
	private int id;
	
	@Column(unique=true)
	private String username;
	@Column(nullable=false)
	private String password;
	@Column(nullable=false)
	private String name;
	private String website;
	private String bio;
	@Column(nullable=false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl;
	private String role;
	
	// 나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼을 만들지마
	// User를 Select할 때 해당 User id로 등록된 image들을 다 가져와
	// Lazy = User를 Select할 때 해당 User id로 등록된 image들을 가져오지마 - 대신 getImages() 함수의 image들이 호출될 때 가져와!!
	// eager = User를 Select할 때 해당 User id로 등록된 image들을 전부 Join해서 가져와
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY) 
	@JsonIgnoreProperties({"user"}) // JPA 무한참조 방지. (getter 호출 방지 json으로 파싱할 때 user는 하지마: images의 내부에 있는 user는 무시하고 파싱해줌.)
	private List<Image> images;
	
	private LocalDateTime createDate;
	
	
	@PrePersist // 디비에 INSERT 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
