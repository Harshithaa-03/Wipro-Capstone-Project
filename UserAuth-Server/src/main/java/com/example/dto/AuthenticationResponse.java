package com.example.dto;


import com.example.enums.UserRole;

import lombok.Data;

@Data
public class AuthenticationResponse {
	
	private String jwt;
	private Long userId;
	private UserRole userRole;
	//private String profession; 

}
