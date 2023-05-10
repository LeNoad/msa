package com.example.jkds.user.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userIdx;
	
	private String userId;
	private String userPassword;
	private String userRole;
	private String userName;
	private String userMobile;
	private String userAddrs;
	private LocalDateTime insDate;
	private LocalDateTime uptDate;
	
	public User() {
		LocalDateTime dateTime = LocalDateTime.now();
		insDate = dateTime;
		uptDate = dateTime;
	}
}
