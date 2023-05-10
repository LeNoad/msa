package com.example.jkds.user.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
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
	private String insDate;
	private String uptDate;
}
