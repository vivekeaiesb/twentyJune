package com.company.eaiesb.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Component
@Document(collection = "SampleUser")
public class UserSample {
	
	@Id
	@Schema(hidden = true)
	private String _id;
	
	private String name;
	private String age;
	private String gender;
	private boolean isAdmin;
	private String email;
	private String password;

}
