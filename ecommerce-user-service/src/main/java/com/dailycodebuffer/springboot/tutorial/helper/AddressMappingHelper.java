package com.dailycodebuffer.springboot.tutorial.helper;

import com.dailycodebuffer.springboot.tutorial.domain.Address;
import com.dailycodebuffer.springboot.tutorial.domain.User;
import com.dailycodebuffer.springboot.tutorial.dto.AddressDto;
import com.dailycodebuffer.springboot.tutorial.dto.UserDto;

public class AddressMappingHelper {

	public static AddressDto map(final Address address) {
		
		return AddressDto.builder().addressId(address.getAddressId()).fullAddress(address.getFullAddress())
				.postalCode(address.getPostalCode())
				.city(address.getCity()).userDto(
					UserDto.builder()
						.userId(address.getUser().getUserId())
						.firstName(address.getUser().getFirstName())
						.lastName(address.getUser().getLastName())
						.imageUrl(address.getUser().getImageUrl())
						.email(address.getUser().getEmail())
						.phone(address.getUser().getPhone())
						.build())
				.build();	
	}
	
	
	
	public static Address map(final AddressDto addressDto) {
		return Address.builder()
				.addressId(addressDto.getAddressId())
				.fullAddress(addressDto.getFullAddress())
				.postalCode(addressDto.getPostalCode())
				.city(addressDto.getCity())
				.user(
					User.builder()
						.userId(addressDto.getUserDto().getUserId())
						.firstName(addressDto.getUserDto().getFirstName())
						.lastName(addressDto.getUserDto().getLastName())
						.imageUrl(addressDto.getUserDto().getImageUrl())
						.email(addressDto.getUserDto().getEmail())
						.phone(addressDto.getUserDto().getPhone())
						.build())
				.build();
	}
	
	
	
}
