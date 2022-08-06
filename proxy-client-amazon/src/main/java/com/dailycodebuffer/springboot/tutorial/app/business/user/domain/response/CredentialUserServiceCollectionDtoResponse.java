package com.dailycodebuffer.springboot.tutorial.app.business.user.domain.response;

import java.util.Collection;



import com.dailycodebuffer.springboot.tutorial.app.business.user.domain.CredentialDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CredentialUserServiceCollectionDtoResponse {

	private static final long serialVersionUID = 1L;
	private Collection<CredentialDto> collection;
		
}
