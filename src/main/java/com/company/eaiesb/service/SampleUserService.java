package com.company.eaiesb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.eaiesb.models.UserSample;
import com.company.eaiesb.repository.SampleUserRepository;
import com.company.eaiesb.response.GeneralResponse;
import com.company.eaiesb.util.JwtUtil;

@Service
public class SampleUserService {
	
	
	@Autowired
	private SampleUserRepository sampleUserRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	public GeneralResponse getAllSampleUsers(){
		List<UserSample> users = sampleUserRepo.findAll();
		
		GeneralResponse<String, String,List<UserSample>> generics= new GeneralResponse<String, String,List<UserSample>>();
		generics.setObject(users);
		generics.setResponse("Showing List of all User Sample Records");
		
		return generics;
		
	}
	
	public GeneralResponse saveSampleUser(UserSample userSample) {
		
		GeneralResponse<String, String,UserSample> generics= new GeneralResponse<String, String,UserSample>();
		UserSample user = sampleUserRepo.findByEmail(userSample.getEmail());
		
		if(user != null) {
			
			generics.setResponse("Couldnt Save. User already exists with mail "+userSample.getEmail());
			return generics;
		}
		else {
			String pass = userSample.getPassword();
			userSample.setPassword(encoder.encode(pass));
			
			sampleUserRepo.save(userSample);
			generics.setObject(userSample);
			generics.setResponse("User Sample saved Successfully");
			return generics;
		}
		
		
	}
	
	public GeneralResponse updateSampleUser(UserSample existingUser, UserSample userSample) {
		existingUser.setAdmin(userSample.isAdmin());
		existingUser.setAge(userSample.getAge());
		existingUser.setName(userSample.getName());
		existingUser.setGender(userSample.getGender());
		existingUser.setEmail(userSample.getEmail());
		
		sampleUserRepo.save(existingUser);
		
		GeneralResponse<String, String,UserSample> generics= new GeneralResponse<String, String,UserSample>();
		generics.setResponse("User Sample Updated Successfully");
		generics.setObject(existingUser);
		
		return generics;
	}
	
	public GeneralResponse updateSampleUserPassword(UserSample existingUser, UserSample userSample) {
		
		
		String pass = userSample.getPassword();
		existingUser.setPassword(encoder.encode(pass));
		sampleUserRepo.save(existingUser);
		
		GeneralResponse<String, String,UserSample> generics= new GeneralResponse<String, String,UserSample>();
		generics.setResponse("User Sample Password Updated Successfully");
		
		return generics;
	}
	
	public GeneralResponse deleteSampleUsers(String id) {
		sampleUserRepo.deleteById(id);
		
		GeneralResponse<String, String,UserSample> generics= new GeneralResponse<String, String,UserSample>();
		generics.setResponse("User Sample Record Deleted Successfully");
		
		return generics;
		
	}
	
	
	
	public GeneralResponse sampleLogin(UserSample userSample) {
		UserSample sampleUserMail = sampleUserRepo.findByEmail(userSample.getEmail());
		
		GeneralResponse<String, String,UserSample > generics= new GeneralResponse<String, String,UserSample>();
		
		if(sampleUserMail == null) {
			generics.setResponse("Couldnt Find User with mail "+userSample.getEmail());
			
			return generics;
		}
		String pass = userSample.getPassword();
		String currentSampleUserPassword = encoder.encode(pass);
		String oldSampleUserPassword = sampleUserMail.getEmail();
		
		
		if(encoder.matches(userSample.getPassword(), sampleUserMail.getPassword())) {
			String token = jwtUtil.generateToken(userSample.getEmail());
			generics.setObject(sampleUserMail);
			generics.setResponse("Sample User credentials Matched with Mail "+userSample.getEmail()+ " and Password");
			generics.setToken(token);
			
			return generics;
		}
		else {
			generics.setResponse("Sample User Credentials Didnt Match");
			generics.setToken("No Token");
			
			return generics;
		}
	}

}
