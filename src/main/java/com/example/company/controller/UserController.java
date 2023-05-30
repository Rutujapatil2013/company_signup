package com.example.company.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.companydatabase.response.UserResponse;
import com.example.company.entity.UserRole;
import com.example.company.entity.Users;
import com.example.company.exception.ResourceNotFoundException;
import com.example.company.repository.usersRepository;
import com.example.company.repository.usersRoleRepository;
import com.example.company.request.userRequest;
import com.example.company.response.userResponse;
import com.example.company.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	@Autowired
	private UserService userService;
	BCryptPasswordEncoder passwordEncoder;
	
    
    @GetMapping("/getall")
    @Operation(summary = "Get all users present in database", description = " ")
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getById/{userId}")
    @Operation(summary = "Get user by its userId", description = " ")
    public Users getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }
    
//    @GetMapping("/getById/{userId}")
//    public Users getUserById(@PathVariable Long userId) {
//        return userService.getUserById(userId);
//    }

    @PostMapping("/post/")
    @Operation(summary = "Register new user", description = " ")
    public Users saveUser(@RequestBody Users user) {
        return userService.saveUser(user);
    }

    @PutMapping("/update/{userId}")
    @Operation(summary = "Update user from company", description = " ")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody userRequest userRequest)
            throws Exception {
        String response = userService.updateUser(userId, userRequest);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/delete/{userId}")
    @Operation(summary = "Delete user from company", description = " ")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
    
    @Autowired
    private usersRoleRepository repository;
    
    @Autowired
    private usersRepository userRepository;
 
//    @PostMapping("/post")
//    @Operation(summary = "", description = " ")
//	public userResponse addUser(@RequestBody userRequest request) throws ResourceNotFoundException {
//		log.info("Adding new user");
//		return userService.createUser(request);
//	}
    
//    @GetMapping("/verify")
//    @Operation(summary = "verifying an email address using a verification code", description = " ")
//    public ResponseEntity<Void> verifyEmail(@RequestParam String email, @RequestParam String code) {
//        userService.verifyEmail(email, code);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//    
////    forget password mail send
//    @PostMapping("/forget_password")
//    @Operation(summary = "Send email when click on forgot password", description = " ")
//    public ResponseEntity<Void> forgotPassword(@RequestBody userRequest request ) {
//    	String email=request.getEmail();
//        userService.sendResetPasswordEmail(email);
//        return ResponseEntity.ok().build();
//    }
//    
//    @PostMapping("/reset-password")
//    @Operation(summary = "Send email when Reset password", description = " ")
//    public ResponseEntity<Void> resetPassword(@RequestParam String email, @RequestParam String code ) {
//        userService.resetPassword(email, code);
//        return ResponseEntity.ok().build();
//    }
    
    
//    @PostMapping("/login")
//    public ResponseEntity<userResponse> login(@RequestBody Users user) throws Exception{
//       Optional<userResponse> object =  userService.login(user.getEmail(), user.getPassword());
//       return ResponseEntity.of(object);
//    }
    
	    @PostMapping("/login")
	    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) throws Exception{
	       String object =  userService.login(email,password);
	       return ResponseEntity.ok(object);
	    }
}
