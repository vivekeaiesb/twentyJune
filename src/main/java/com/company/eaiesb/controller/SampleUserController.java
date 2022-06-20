package com.company.eaiesb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.eaiesb.models.UserSample;
import com.company.eaiesb.repository.SampleUserRepository;
import com.company.eaiesb.response.GeneralResponse;
import com.company.eaiesb.service.SampleUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin
@RequestMapping("/api/v1")
@RestController
public class SampleUserController {
	
	
	@Autowired
	 private SampleUserService userService;
	
	@Autowired
	private SampleUserRepository userRepo;
	 
	private static final String AUTH_MECHANISM = "bearerAuth";
	
	
	@GetMapping("/sampleUsers")
	 @Operation(security = @SecurityRequirement(name = AUTH_MECHANISM),summary = "Find All sample Users", description = "Get All sample User Records", tags = { "Sample Users" })
	public GeneralResponse fetchAllUserSamples()     {
      

		return userService.getAllSampleUsers();
	}
	
	@PostMapping("/sampleUsers")
    @Operation(security = @SecurityRequirement(name = AUTH_MECHANISM),summary = "Insert Sample Users", description = "Add SampleUser", tags = { "Sample Users" })
    public GeneralResponse createSampleUser(@Valid @RequestBody UserSample userSample){

        return userService.saveSampleUser(userSample);
    }
	
	
	@PutMapping("/sampleUsers")
    @Operation(security = @SecurityRequirement(name = AUTH_MECHANISM),summary = "Update Sample Users", description = "Update/Modify Sample Users Record", tags = { "Sample Users" })
    public GeneralResponse editSampleUser(@Valid @RequestBody UserSample userSample){
		UserSample existinguser = userRepo.findById(userSample.get_id()).orElse(null);
        return userService.updateSampleUser(existinguser,userSample);
    }
	
	@PutMapping("/sampleUsers/updatePassword")
    @Operation(security = @SecurityRequirement(name = AUTH_MECHANISM),summary = "Update Sample Users password", description = "Update/Modify Sample Users Password", tags = { "Sample Users" })
    public GeneralResponse editSampleUserPassword(@Valid @RequestBody UserSample userSample){
		UserSample existinguser = userRepo.findById(userSample.get_id()).orElse(null);
        return userService.updateSampleUserPassword(existinguser,userSample);
    }
	
	@DeleteMapping("/sampleUsers/{id}")
    @Operation(security = @SecurityRequirement(name = AUTH_MECHANISM),summary = "Delete Sample Users", description = "Delete Sample Users By ID", tags = { "Sample Users" })
		public GeneralResponse removeSampleUsers(@PathVariable String id ) {
			return userService.deleteSampleUsers(id);
		}
	
	
	@PostMapping("/login")
	 @Operation(security = @SecurityRequirement(name = AUTH_MECHANISM),summary = "Login Sample Users", description = "Login SampleUser", tags = { "Sample Users" })
	public GeneralResponse loginUserSample(@Valid @RequestBody UserSample userSample) {
		return userService.sampleLogin(userSample);
	}
	
	
	
	
}
