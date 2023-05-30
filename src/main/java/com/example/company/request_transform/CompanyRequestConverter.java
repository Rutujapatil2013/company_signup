package com.example.company.request_transform;

import com.example.company.entity.company;
import com.example.company.request.companyRequest;

public class CompanyRequestConverter {
	public static company postCompanyEntity(companyRequest companyRequest) {
		
        company company = new company();
        
        company.setCompanyId(companyRequest.getCompanyId());
        company.setCompanyName(companyRequest.getCompanyName());
        company.setEmailId(companyRequest.getEmailId());
        company.setPhoneNumber(companyRequest.getPhoneNumber());
        company.setIsDeleted(companyRequest.getIsDeleted());
//        company.setRegistration((companyRequest.getRegistration()));
        company.setUsers(companyRequest.getUsers());
        company.setAddress(companyRequest.getAddress());
        return company;
    }

	public static company updateCompanyEntity(company company, companyRequest companyRequest) {
		company.setCompanyId(companyRequest.getCompanyId());
		company.setCompanyName(companyRequest.getCompanyName());
		  company.setEmailId(companyRequest.getEmailId());
	        company.setPhoneNumber(companyRequest.getPhoneNumber());
	        company.setIsDeleted(companyRequest.getIsDeleted());
//	        company.setRegistration(companyRequest.getRegistration());
	        company.setUsers(companyRequest.getUsers());
	        company.setAddress(companyRequest.getAddress());
        return company;
	}
	

}
