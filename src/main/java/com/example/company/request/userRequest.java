package com.example.company.request;

import com.example.company.entity.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userRequest {

	private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
	private boolean isDeleted;
	private UserRole role;
	private boolean verificationEnabled;
	
	public String getEmail() {
		return email;
	}
//	public Object getRoleId() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}
