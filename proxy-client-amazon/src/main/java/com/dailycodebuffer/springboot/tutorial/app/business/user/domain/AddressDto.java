package com.dailycodebuffer.springboot.tutorial.app.business.user.domain;

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
public class AddressDto {

	private Integer addressId;

	private String fullAddress;

	private String postalCode;

	private String city;

	@JsonProperty("user")
	@JsonInclude(value = Include.NON_NULL)
	private UserDto userDto;

}
