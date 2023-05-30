package com.example.company.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.company.transformer.companyResponseConverter;
import com.example.company.entity.UserRole;
import com.example.company.entity.Users;
import com.example.company.entity.company;
import com.example.company.repository.UserRepo;
import com.example.company.repository.companyRepository;
import com.example.company.repository.usersRepository;
import com.example.company.request.companyRequest;
import com.example.company.request_transform.CompanyRequestConverter;
import com.example.company.response.companyResponse;
import com.example.company.service.companyService;
import com.example.company.transformer.companyRequestConverter;

@Service
public class companyServiceImpl implements companyService{
	
//	@Autowired
    private final companyRepository repository;
    
    
    @Autowired
    private final UserRepo userRepo;
    
    @Autowired
	BCryptPasswordEncoder passwordEncoder;

    @Autowired 
    companyResponseConverter companyResponseConverter;
    
    
	
	public companyServiceImpl(companyRepository repository, UserRepo userRepo) {
		super();
		this.repository = repository;
		this.userRepo = userRepo;
	}

	// get all company
    @Override
    public List<companyResponse> getAllCompanies() throws Exception {
        try {
            List<company> companies = repository.findAll();
            return companies.stream().map(companyResponseConverter::convertToResponse).collect(Collectors.toList());
            
        } catch (Exception e) {
            throw new Exception("Error occurred while fetching companies.", e);
        }
    }
    
    //get company by id
    @Override
    public Optional<companyResponse> getCompanyById(Long companyId) throws Exception {
        try {
            Optional<company> companyOptional = repository.findById(companyId);
            if (companyOptional.isPresent()) {
                companyResponse response = companyResponseConverter.convertToResponse(companyOptional.get());
                return Optional.of(response);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new Exception("Error occurred while fetching company with id: " + companyId, e);
        }
    }
    

//    //post company
//    @Override
//	public companyResponse createCompany(companyRequest companyRequest,Users user) {
//		
//		company companyModel =companyRequestConverter.postCompanyEntity(companyRequest);
//		String encryptedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encryptedPassword);
//        Users savedUser = this.repository.save(user);
//		company saveCompany = this.repository.save(companyModel);
//		return companyResponseConverter.convertToResponse(saveCompany);
//		
//		
//	}
    
    
 // post company
    @Override
    public String createCompany(companyRequest companyRequest) throws Exception {
        // Check if a company with the same name already exists
        String companyName = companyRequest.getCompanyName();
        Optional<company> existingCompanyByName = repository.findByCompanyName(companyName);
        if (existingCompanyByName.isPresent()) {
            return "Company with the name '" + companyName + "' already exists.";
        }
        
        String emailId = companyRequest.getEmailId();
        Optional<company> existingCompanyByEmail = repository.findByEmailId(emailId);
        if (existingCompanyByEmail.isPresent()) {
            return "Company with the email '" + emailId + "' already exists.";
        }

        // Check if any user with the same email already exists
        List<Users> usersForRegistration = companyRequest.getUsers();
        for (Users user : usersForRegistration) {
            String userEmail = user.getEmail();
            Optional<Users> existingUserByEmail = userRepo.findByEmail(userEmail);
            if (existingUserByEmail.isPresent()) {
                return "User with the email '" + userEmail + "' already exists.";
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            // Add your condition here
            if (user.getRole().getRoleId() != 1 || !user.getRole().getRoleName().equalsIgnoreCase("admin")) {
                return "Invalid role for user: " + userEmail;
            }
        }

        company company = companyRequestConverter.postCompanyEntity(companyRequest);
        company.setUsers(usersForRegistration);
        company com = this.repository.save(company);
//        return companyResponseConverter.convertToResponse(com).toString;
        return "Company Registered Successfully!";
    }
    
    
    
// // update company
    @Override
    public String updateCompany(Long companyId, companyRequest request) throws Exception {
        try {
            Optional<company> optionalCompany = repository.findById(companyId);
            if (optionalCompany.isPresent()) {
                company company = optionalCompany.get();
                companyRequestConverter.updateCompanyEntity(company, request);
                repository.save(company);
                return "Company updated successfully.";
            } else {
                return "Company not found.";
            }
        } catch (Exception e) {
            throw new Exception("Error occurred while updating company with id: " + companyId, e);
        }
    }
    
    
// // update company
//    @Override
//    public String updateCompany(Long companyId, companyRequest request) throws Exception {
//        try {
//            Optional<company> optionalCompany = repository.findById(companyId);
//            if (optionalCompany.isPresent()) {
//                company company = optionalCompany.get();
//
//                // Check if the user has the role "admin"
//                if (userHasAdminRole()) {
//                    companyRequestConverter.updateCompanyEntity(company, request);
//                    repository.save(company);
//                    return "Company updated successfully.";
//                } else {
//                    return "Only users with the role 'admin' are allowed to update company details.";
//                }
//            } else {
//                return "Company not found.";
//            }
//        } catch (Exception e) {
//            throw new Exception("Error occurred while updating company with id: " + companyId, e);
//        }
//    }



    

    // delete company
    @Override
    public String deleteCompany(Long companyId) throws Exception {
        try {
            Optional<company> optionalCompany = repository.findById(companyId);
            if (optionalCompany.isPresent()) {
                company companyEntity = optionalCompany.get();
                companyEntity.setIsDeleted(true); // set the deleted flag
//                companyEntity.getUsers().forEach(user -> user.setDeleted(true)); 
                repository.save(companyEntity);
                return "Company deleted successfully.";
            } else {
                return "Company not found.";
            }
        } catch (Exception e) {
            throw new Exception("Error occurred while deleting company with id: " + companyId, e);
        }
    }


	


}
