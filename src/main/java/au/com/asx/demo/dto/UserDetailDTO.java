package au.com.asx.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDetailDTO {

	@JsonIgnore
	private String userId;
	private String title;
	private String firstname;
	private String lastname;
	private String gender;
	@JsonProperty("address")
	private AddressDTO addressDTO;
	
 }
