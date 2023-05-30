package com.example.company.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.example.company.repository.UserRepo;
import com.example.company.repository.usersRepository;
import com.example.company.service.UserService;
import com.example.company.transformer.userRequestConverter;
import com.example.company.transformer.userResponseConverter;
//import com.companydatabase.repository.UserRepository;
import com.example.company.entity.Users;
import com.example.company.exception.ResourceNotFoundException;
import com.example.company.request.userRequest;
import com.example.company.request_transform.UserRequestConverter;
import com.example.company.responce_transform.UserResponseConverter;
import com.example.company.response.userResponse;


@Service
public class userServiceImpl implements UserService{
	
	@Autowired
	private usersRepository repository;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo repo;

	 @Autowired
	 private JavaMailSender mailSender;
	 
	// Store the verification codes in a map
 private HashMap<String, String> verificationCodes = new HashMap<>();
 private HashMap<String, String> resetPasswordCodes = new HashMap<>();
 
 
	@Override
	public userResponse createUser(userRequest request) throws ResourceNotFoundException {

			Users user = UserRequestConverter.toEntity(request);
			Users savedUser = repository.save(user);
			System.out.println("data saved");
			System.out.println("going to email meth");
			  sendVerificationEmail(user);
			  return UserResponseConverter.convertToResponse(savedUser);

	}
	
	

    @Override
    public List<Users> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public Users getUserById(Long userId) {
        Optional<Users> user = repository.findById(userId);
        return user.orElse(null);
    }

    @Override
    public Users saveUser(Users user) {
    	String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        Users savedUser = this.repository.save(user);
        return repository.save(user);
    }
    
    @Override
	public String updateUser(Long userId, userRequest request) throws Exception {
		try {
			Optional<Users> optionaUser = repository.findById(userId);
			if (optionaUser.isPresent()) {
				Users user = optionaUser.get();
				userRequestConverter.updateUsersEntity(user, request);
				repository.save(user);
				return "User updated successfully.";
			} else {
				return "User not found.";
			}
		} catch (Exception e) {
			// Handle exception
			throw new Exception("Error occurred while updating user with ID: " + userId, e);
		}
	}

    @Override
    public void deleteUser(Long userId) {
    	repository.deleteById(userId);
    }

	 private void sendVerificationEmail(Users user) {
	        // Generate a random verification code
		 String verificationCode = UUID.randomUUID().toString().substring(0, 4);
			System.out.println("token genrated : "+verificationCode);
			
	        // Store the verification code in the map
	        verificationCodes.put(user.getEmail(), verificationCode);
	        System.out.println("saved in hash set"+verificationCodes);
	        // Send the verification email
	        String subject = "Verify your email address";
	        String message = "Please click on the following link to verify your email address: "
	            + "http://localhost:8082/users/verify?email=" + user.getEmail() + "&code=" + verificationCode;
	        System.out.println("mail genrated");
	        sendEmail(user.getEmail(), subject, message);
	    }
	 
	 private void sendEmail(String email, String subject, String body) {
		 System.out.print("send email called ");
	        SimpleMailMessage message = new SimpleMailMessage();
			 System.out.println("send with my mail id called ");
	        message.setFrom("pranumore1234@gmail.com");
	        System.out.println("send with my mail id called "+email);
	        message.setTo(email);
	        message.setSubject(subject);
	        message.setText(body);
	        mailSender.send(message);
	        System.out.println("mail successfully send");
	    }
	 
//	 @Override
//	  public ResponseEntity<String> verifyEmail(String email, String code) {
//	        // Get the verification code from the map
//		 System.out.println("email : "+email+" verificationCode : "+code);
//	        
//	        if (verificationCodes.get(email).equals(code)) {
//	            // Update the isEnable field to true
//	        	System.out.println("verification code checked");
//	            Users user = repository.findByEmail(email);
//	            System.out.println("verification enable");
//	            user.setVerificationEnabled(true);
//	            
//	            repository.save(user);
//	            // Remove the verification code from the map
//	            verificationCodes.remove(email);
//	            System.out.println("verification sucessfull");
//	            return ResponseEntity.ok().body("successfully verified");
//	        } else {
//	        	return ResponseEntity.badRequest().body("Error :couldent verify email");
//	        }
//	    }
//	 
//	 
//	 @Override
//		public void sendResetPasswordEmail(String email) {
//			 String resetCode = UUID.randomUUID().toString().substring(0, 4);
//			 Users user = repository.findByEmail(email);
//			 resetPasswordCodes.put(user.getEmail(), resetCode);
//			 String subject = "Reset your Password";
//		     String message = "Please click on the following link to reset your password: "
//		            + "http://localhost:8082/users/forget_password?email=" + user.getEmail() + "&code=" + resetCode;
//		        System.out.println("mail genrated");
//		        sendEmail(user.getEmail(), subject, message);
//			 
//		}
//	 
//
//		@Override
//		public void resetPassword(String email, String code) {
//			 String storedResetToken = resetPasswordCodes.get(email);
//	            System.out.println("going to the resetpass link");
//		        // Check if the user was found and the token is valid
//			 if (storedResetToken != null && storedResetToken.equals(code)) {
//			        // Reset the password for the user
//				    System.out.println("email found");
//			        Users user = repository.findByEmail(email);
//			        System.out.println("setting new password");
//			        String newPassword="Pranita123";
//			        user.setPassword(newPassword);
//			        repository.save(user);
//			        System.out.println("save data in repo");
//			        // Remove the reset token from the Map
//			        resetPasswordCodes.remove(email);
//			    } else {
//			        throw new RuntimeException("Invalid reset token.");
//			    }
//		}
		
		public void UserServiceImpl(usersRepository userRepo, BCryptPasswordEncoder bCryptPasswordEncoder){
	        this.repository = userRepo;
	        this.passwordEncoder = bCryptPasswordEncoder;
	    }
		

		
		public String login(String email, String password) {
		    try {
		        List<Users> users = repository.findByEmail(email);
		        if (users.isEmpty()) {
		            return "User not found";
		        }
		      
		        Users user = users.get(0); // Assuming the first user is the desired one
		        if (passwordEncoder.matches(password, user.getPassword())) {
		            userResponse userResponse = userResponseConverter.convertToResponse(user);
		            return "Login successful\n" + userResponse.toString();
		        } else {
		            return "Password does not match";
		        }
		    } catch (Exception e) {
		        return "Internal server error";
		    }
		}
		
		
		
}
