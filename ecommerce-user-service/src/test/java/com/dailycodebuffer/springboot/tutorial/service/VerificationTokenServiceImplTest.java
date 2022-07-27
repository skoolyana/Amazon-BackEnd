package com.dailycodebuffer.springboot.tutorial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dailycodebuffer.springboot.tutorial.domain.Address;
import com.dailycodebuffer.springboot.tutorial.domain.Credential;
import com.dailycodebuffer.springboot.tutorial.domain.User;
import com.dailycodebuffer.springboot.tutorial.domain.VerificationToken;
import com.dailycodebuffer.springboot.tutorial.dto.AddressDto;
import com.dailycodebuffer.springboot.tutorial.dto.VerificationTokenDto;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.AddressNotFoundException;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.VerificationTokenNotFoundException;
import com.dailycodebuffer.springboot.tutorial.helper.AddressMappingHelper;
import com.dailycodebuffer.springboot.tutorial.helper.VerificationTokenMappingHelper;
import com.dailycodebuffer.springboot.tutorial.repository.VerificationTokenRepository;
import com.dailycodebuffer.springboot.tutorial.service.impl.AddressServiceImpl;
import com.dailycodebuffer.springboot.tutorial.service.impl.VerificationTokenServiceImpl;

public class VerificationTokenServiceImplTest {

	private VerificationTokenServiceImpl verificationTokenImpl;

	@Mock
	private VerificationTokenRepository verificationTokenRepository;

	private VerificationToken verificationToken1;

	@BeforeEach
	public void setup() {

		// With this call to initMocks we tell Mockito to process the annotations
		MockitoAnnotations.initMocks(this);
		verificationTokenImpl = new VerificationTokenServiceImpl(verificationTokenRepository);

		verificationToken1 = VerificationToken.builder().verificationTokenId(1).token("abcd")
				.expireDate(LocalDate.of(2023, 8, 20)).credential(

						Credential.builder().credentialId(2).isAccountNonExpired(true).isAccountNonLocked(true)
								.isCredentialsNonExpired(true).password("password").build()

				).build();

	}

	// JUnit test for findAll users method

	@DisplayName("JUnit test for findAll Token method")
	@Test
	public void givenTokenList_whenfindAllToken_thenReturnTokenList() {

		given(verificationTokenRepository.findAll()).willReturn(List.of(verificationToken1));

		List<VerificationTokenDto> verificationTokenList = verificationTokenImpl.findAll();

		// then - verify the output
		assertThat(verificationTokenList).isNotNull();

		assertThat(verificationTokenList.size()).isEqualTo(1);

		verify(verificationTokenRepository).findAll();

	}

	// JUnit test for findAll tokens method: negative Scenario

	@DisplayName("JUnit test for findAll method (negative scenario)")
	@Test
	public void givenEmptyTokenList_whenfindAllToken_thenReturnEmptyTokenList() {

		given(verificationTokenRepository.findAll()).willReturn(Collections.EMPTY_LIST);

		List<VerificationTokenDto> verificationTokenList = verificationTokenImpl.findAll();

		// then - verify the output
		assertThat(verificationTokenList).isEmpty();
		assertThat(verificationTokenList.size()).isEqualTo(0);
		verify(verificationTokenRepository).findAll();
	}

	// JUnit test for getTokenById method
	@DisplayName("JUnit test for getTokenById method")
	@Test
	public void givenTokenId_whenGetTokenById_thenRetuernTokenObject() {

		given(verificationTokenRepository.findById(1)).willReturn(Optional.of(verificationToken1));

		// when
		VerificationTokenDto savedToken = verificationTokenImpl.findById(verificationToken1.getVerificationTokenId());

		// then
		assertThat(savedToken).isNotNull();

	}

	@Test()
	public void should_throw_exception_when_token_doesnt_exist() {

		given(verificationTokenRepository.findById(1)).willReturn(Optional.ofNullable(null));

		Assertions.assertThrows(VerificationTokenNotFoundException.class,
				() -> verificationTokenImpl.findById(verificationToken1.getVerificationTokenId()));

	}

	// JUnit test for saveAddress method
	@DisplayName("JUnit test for saveToken method")
	@Test
	public void givenTokenObject_whenSaveToken_thenReturnTokenObject() {

		// given - precondition or setup

		given(verificationTokenRepository.save(verificationToken1)).willReturn(verificationToken1);

		// when
		VerificationTokenDto verificationTokenDto = verificationTokenImpl
				.save(VerificationTokenMappingHelper.map(verificationToken1));

		// then - verify the output

		VerificationToken verificationToken = VerificationTokenMappingHelper.map(verificationTokenDto);

		assertThat(verificationToken).isNotNull();

		assertThat(verificationToken.getToken()).isEqualTo(verificationToken1.getToken());

	}

	
	// JUnit test for updateEmployee method
	@DisplayName("JUnit test for updateToken method")
	@Test
	public void givenTokenObject_whenUpdateToken_thenReturnUpdatedToken() {

		// given - precondition or setup

		given(verificationTokenRepository.save(verificationToken1)).willReturn(verificationToken1);

		// when

		VerificationTokenDto verificationTokenDto = verificationTokenImpl.update(VerificationTokenMappingHelper.map(verificationToken1));

		// then - verify the output

		VerificationToken verificationToken = VerificationTokenMappingHelper.map(verificationTokenDto);

		assertThat(verificationToken).isNotNull();

		assertThat(verificationToken.getToken()).isEqualTo(verificationToken1.getToken());

	}

	
	
	// JUnit test for updateEmployee method
	@DisplayName("JUnit test for updateToken method using tokenId")
	@Test
	public void givenTokenId_whenUpdateToken_thenReturnUpdatedToken() {

		// given - precondition or setup

		given(verificationTokenRepository.save(verificationToken1)).willReturn(verificationToken1);
		given(verificationTokenRepository.findById(verificationToken1.getVerificationTokenId())).willReturn(Optional.of(verificationToken1));
		
		// when

		VerificationTokenDto verificationTokenDto = verificationTokenImpl.update(verificationToken1.getVerificationTokenId(),VerificationTokenMappingHelper.map(verificationToken1));

		// then - verify the output

		VerificationToken  verifyToken = VerificationTokenMappingHelper.map(verificationTokenDto);

		assertThat(verifyToken).isNotNull();

		assertThat(verifyToken.getToken()).isEqualTo(verificationToken1.getToken());

	}
	

	
	@Test()
	public void update_should_throw_exception_when_token_doesnt_exist() {

		given(verificationTokenRepository.findById(1)).willReturn(Optional.ofNullable(null));

		Assertions.assertThrows(VerificationTokenNotFoundException.class, () -> verificationTokenImpl.update(verificationToken1.getVerificationTokenId(),VerificationTokenMappingHelper.map(verificationToken1)));

	}

	
	 // JUnit test for deleteEmployee method
    @DisplayName("JUnit test for deleteToken method")
    @Test
    public void givenTokenId_whenDeleteToken_thenNothing(){
    	
    	 // given - precondition or setup
        Integer tokenId = 1;
        
        
        willDoNothing().given(verificationTokenRepository).deleteById(tokenId);

        // when -  action or the behaviour that we are going test
        
        verificationTokenImpl.deleteById(tokenId);
        
        
        // then - verify the output
        
        verify(verificationTokenRepository, times(1)).deleteById(tokenId);
        
    	
    	
    }
	
	
	
	
}
