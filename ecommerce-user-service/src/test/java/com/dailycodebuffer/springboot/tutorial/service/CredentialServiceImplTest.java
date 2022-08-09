package com.dailycodebuffer.springboot.tutorial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dailycodebuffer.springboot.tutorial.domain.Address;
import com.dailycodebuffer.springboot.tutorial.domain.Credential;
import com.dailycodebuffer.springboot.tutorial.domain.User;
import com.dailycodebuffer.springboot.tutorial.dto.AddressDto;
import com.dailycodebuffer.springboot.tutorial.dto.CredentialDto;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.AddressNotFoundException;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.CredentialNotFoundException;
import com.dailycodebuffer.springboot.tutorial.helper.AddressMappingHelper;
import com.dailycodebuffer.springboot.tutorial.helper.CredentialMappingHelper;
import com.dailycodebuffer.springboot.tutorial.repository.CredentialRepository;
import com.dailycodebuffer.springboot.tutorial.service.impl.AddressServiceImpl;
import com.dailycodebuffer.springboot.tutorial.service.impl.CredentialServiceImpl;

public class CredentialServiceImplTest {

	
	private CredentialServiceImpl credentialServiceImpl;
	
	@Mock
	private CredentialRepository credentialRepository;
	
	private Credential credential1;
	
	@Mock
	private PasswordEncoder passwordEmncoder;
	
	
	@BeforeEach
	public void setup() {

		// With this call to initMocks we tell Mockito to process the annotations
		MockitoAnnotations.initMocks(this);
		credentialServiceImpl = new CredentialServiceImpl(credentialRepository,passwordEmncoder);

		credential1 = Credential.builder().credentialId(1).isAccountNonExpired(true).isAccountNonLocked(true).password("password").username("skulyana")
				.user(User.builder().email("skulyana@gmail.com").firstName("Sunil").imageUrl("www.google.com").lastName("Kulyana").build()).build();

	}

	

	// JUnit test for findAll users method

	@DisplayName("JUnit test for findAll Credentials method")
	@Test
	public void givenCredentialList_whenfindAllCredentials_thenReturnCredentialList() {

		given(credentialRepository.findAll()).willReturn(List.of(credential1));

		List<CredentialDto> credentialList = credentialServiceImpl.findAll();

		// then - verify the output
		assertThat(credentialList).isNotNull();

		assertThat(credentialList.size()).isEqualTo(1);

		verify(credentialRepository).findAll();

	}

	
	// JUnit test for findAll users method: negative Scenario

	@DisplayName("JUnit test for findAll method (negative scenario)")
	@Test
	public void givenEmptyCredentialList_whenfindAllCredential_thenReturnEmptyCredentialList() {

		given(credentialRepository.findAll()).willReturn(Collections.EMPTY_LIST);

		List<CredentialDto> credentialList = credentialServiceImpl.findAll();

		// then - verify the output
		assertThat(credentialList).isEmpty();
		assertThat(credentialList.size()).isEqualTo(0);
		verify(credentialRepository).findAll();
	}

	
	// JUnit test for getUserById method
		@DisplayName("JUnit test for getCredentialById method")
		@Test
		public void givenCredentialId_whenGetCredentialById_thenRetuernCredentialObject() {

			given(credentialRepository.findById(1)).willReturn(Optional.of(credential1));

			// when
			CredentialDto savedCredential = credentialServiceImpl.findById(credential1.getCredentialId());

			// then
			assertThat(savedCredential).isNotNull();

		}
		
		
		@Test()
		public void should_throw_exception_when_credential_doesnt_exist() {

			given(credentialRepository.findById(1)).willReturn(Optional.ofNullable(null));

			Assertions.assertThrows(CredentialNotFoundException.class,
					() -> credentialServiceImpl.findById(credential1.getCredentialId()));

		}
		
		
		
		
		// JUnit test for getUserById method
				@DisplayName("JUnit test for getCredentialByName method")
				@Test
				public void givenCredentialName_whenGetCredentialByName_thenReturnCredentialObject() {

					String username = "skulyana";
					
					given(credentialRepository.findByUsername(username)).willReturn(Optional.of(credential1));

					// when
					CredentialDto savedCredential = credentialServiceImpl.findByUsername(credential1.getUsername());

					// then
					assertThat(savedCredential).isNotNull();

				}
				
				
				@Test()
				public void should_throw_exception_when_credential_by_name_doesnt_exist() {

					String username = "skulyana";
					
	
					given(credentialRepository.findByUsername(username)).willReturn(Optional.ofNullable(null));

					
					Assertions.assertThrows(CredentialNotFoundException.class,
							() -> credentialServiceImpl.findByUsername(credential1.getUsername()));

				}
				
		
		
		
		// JUnit test for saveAddress method
		@DisplayName("JUnit test for saveCredential method")
		@Test
		public void givenCredentialObject_whenSaveCredential_thenReturnCredentialObject() {

			// given - precondition or setup

			given(credentialRepository.save(credential1)).willReturn(credential1);

			// when
			CredentialDto  credentialDto = credentialServiceImpl.save(CredentialMappingHelper.map(credential1));

			// then - verify the output

			Credential credential = CredentialMappingHelper.map(credentialDto);

			assertThat(credential).isNotNull();

			assertThat(credential.getCredentialId()).isEqualTo(credential1.getCredentialId());

		}


		
		// JUnit test for updateEmployee method
		@DisplayName("JUnit test for updateCredential method")
		@Test
		public void givenCredentialObject_whenUpdateCredential_thenReturnUpdatedCredential() {

			// given - precondition or setup

			given(credentialRepository.save(credential1)).willReturn(credential1);

			// when

			CredentialDto credentialDto = credentialServiceImpl.update(CredentialMappingHelper.map(credential1));

			// then - verify the output

			Credential credential = CredentialMappingHelper.map(credentialDto);

			assertThat(credential).isNotNull();

			assertThat(credential.getCredentialId()).isEqualTo(credential1.getCredentialId());

		}
		
		
		// JUnit test for updateEmployee method
		@DisplayName("JUnit test for updateUser method using userId")
		@Test
		public void givenCredentialId_whenUpdateCredential_thenReturnUpdatedCredential() {

			// given - precondition or setup

			given(credentialRepository.save(credential1)).willReturn(credential1);
			given(credentialRepository.findById(credential1.getCredentialId())).willReturn(Optional.of(credential1));
			
			// when

			CredentialDto credentialDto = credentialServiceImpl.update(credential1.getCredentialId(),CredentialMappingHelper.map(credential1));

			// then - verify the output

			Credential  credential = CredentialMappingHelper.map(credentialDto);

			assertThat(credential).isNotNull();

			assertThat(credential.getCredentialId()).isEqualTo(credential1.getCredentialId());

		}


		@Test()
		public void update_should_throw_exception_when_credential_doesnt_exist() {

			given(credentialRepository.findById(1)).willReturn(Optional.ofNullable(null));

			Assertions.assertThrows(CredentialNotFoundException.class, () -> credentialServiceImpl.update(credential1.getCredentialId(),CredentialMappingHelper.map(credential1)));

		}
		
		
		 // JUnit test for deleteEmployee method
	    @DisplayName("JUnit test for deleteCredential method")
	    @Test
	    public void givenCredentialId_whenDeleteCredential_thenNothing(){
	    	
	    	 // given - precondition or setup
	        Integer credentialId = 1;
	        
	        
	        willDoNothing().given(credentialRepository).deleteById(credentialId);

	        // when -  action or the behaviour that we are going test
	        
	        credentialServiceImpl.deleteById(credentialId);
	        
	        
	        // then - verify the output
	        
	        verify(credentialRepository, times(1)).deleteById(credentialId);
	        
	    	
	    	
	    }



	
}
