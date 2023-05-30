package com.example.company.transformer;

import org.springframework.context.annotation.Configuration;

import com.example.company.entity.Users;
import com.example.company.request.userRequest;

@Configuration
public class userRequestConverter {
	
	public static Users postUsersEntity(userRequest userRequest) {
		Users user=new Users();
		user.setUserId(userRequest.getUserId());
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
//		user.setType(userRequest.getType()); 
		user.setEmail(userRequest.getEmail()); 
		user.setPassword(userRequest.getPassword());
//		user.setCompanyId(userRequest.getCompanyId());
		user.setDeleted(userRequest.isDeleted());
//		user.setCompany(userRequest.getCompany());
		user.setRole(userRequest.getRole());
		return user;
	}
	
	public static Users updateUsersEntity(Users user, userRequest userRequest) {
		user.setUserId(userRequest.getUserId());
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setEmail(userRequest.getEmail()); 
		user.setPassword(userRequest.getPassword());
//		user.setCompanyId(userRequest.getCompanyId()); 
		user.setDeleted(userRequest.isDeleted());
		user.setRole(userRequest.getRole());
		return user;
	}

}
