package com.example.company.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.company.transformer.companyResponseConverter;
import com.example.company.entity.UserRole;
import com.example.company.entity.Users;
import com.example.company.entity.company;
import com.example.company.repository.companyRepository;
import com.example.company.request.companyRequest;
import com.example.company.response.companyResponse;
import com.example.company.service.companyService;
import com.example.company.transformer.companyRequestConverter;

@Service
public class companyServiceImpl implements companyService{
	
//	@Autowired
    private final companyRepository repository;
	
	public companyServiceImpl(companyRepository repository) {
		super();
		this.repository = repository;
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
    

    //post company
    @Override
	public companyResponse createCompany(companyRequest companyRequest) {
		
		company companyModel =companyRequestConverter.postCompanyEntity(companyRequest);
		
		company saveCompany = this.repository.save(companyModel);
		return companyResponseConverter.convertToResponse(saveCompany);
		
		
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
