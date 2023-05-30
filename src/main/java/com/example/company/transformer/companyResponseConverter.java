package com.example.company.transformer;


import com.example.company.entity.company;
import com.example.company.response.companyResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class companyResponseConverter {
	
	public  companyResponse convertToResponse(company companyEntity) {
	    companyResponse response = new companyResponse(); 
	    
	    response.setCompanyId(companyEntity.getCompanyId());
	    response.setCompanyName(companyEntity.getCompanyName());
	    response.setEmailId(companyEntity.getEmailId());
	    response.setPhoneNumber(companyEntity.getPhoneNumber());
	    response.setIsDeleted(companyEntity.getIsDeleted());
//	    response.setRole(companyEntity.getRole());
	    response.setUsers(companyEntity.getUsers());
	    response.setAddress(companyEntity.getAddress());
	    if(response.getIsDeleted() != null) {
	    	response.setIsDeleted(companyEntity.getIsDeleted());
	    }
	    return response;
	}
	
	
	

	
	
}
