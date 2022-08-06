package com.dailycodebuffer.springboot.tutorial.app.business.user.domain.response;

import java.io.Serializable;
import java.util.Collection;

import com.dailycodebuffer.springboot.tutorial.app.business.user.domain.AddressDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class AddressUserServiceCollectionDtoResponse  implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private Collection<AddressDto> collection;
	
	
}
