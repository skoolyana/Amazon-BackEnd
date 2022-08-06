package com.dailycodebuffer.springboot.tutorial.app.business.user.domain;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data 
@Builder
public class UserDto{

	
	private Integer userId;

	private String firstName;

	private String lastName;

	private String imageUrl;

	private String email;

	private String phone;

	@JsonInclude(value = Include.NON_NULL)
	private Set<AddressDto> addressDtos;
	
	@JsonProperty("credential")
	@JsonInclude(value = Include.NON_NULL)
	private CredentialDto credentialDto;
	

	
	
	
}
