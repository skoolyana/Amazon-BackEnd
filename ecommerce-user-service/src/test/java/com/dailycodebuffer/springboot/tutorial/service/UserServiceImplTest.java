package com.dailycodebuffer.springboot.tutorial.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dailycodebuffer.springboot.tutorial.domain.Address;
import com.dailycodebuffer.springboot.tutorial.domain.Credential;
import com.dailycodebuffer.springboot.tutorial.domain.RoleBasedAuthority;
import com.dailycodebuffer.springboot.tutorial.domain.User;
import com.dailycodebuffer.springboot.tutorial.dto.UserDto;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.UserObjectNotFoundException;
import com.dailycodebuffer.springboot.tutorial.helper.UserMappingHelper;
import com.dailycodebuffer.springboot.tutorial.repository.UserRepository;
import com.dailycodebuffer.springboot.tutorial.service.impl.UserServiceImpl;

import static org.mockito.BDDMockito.willDoNothing;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.times;


public class UserServiceImplTest {

	private UserServiceImpl userServiceimpl;

	@Mock
	private UserRepository userRepository;

	private User user1;

	@BeforeEach
	public void setUp() {

		// With this call to initMocks we tell Mockito to process the annotations
		MockitoAnnotations.initMocks(this);
		userServiceimpl = new UserServiceImpl(userRepository);

		Set<Address> addressSet = new HashSet<>();

		Credential credDto = Credential.builder().credentialId(111).username("skulyana").password("abcdefg")
				.isAccountNonExpired(true).isAccountNonLocked(true).isCredentialsNonExpired(true).isEnabled(true)
				.roleBasedAuthority(RoleBasedAuthority.ROLE_ADMIN).build();

		addressSet.add(
				Address.builder().addressId(100).city("Delhi").fullAddress("296 Joor").postalCode("110003").build());

		user1 = User.builder().userId(1).firstName("Sunil").lastName("Kulyana").email("sunil.kulyana@gmail.com")
				.imageUrl("www.google.com").addresses(addressSet).credential(credDto).build();

	}

	// JUnit test for findAll users method

	@DisplayName("JUnit test for findAll Users method")
	@Test
	public void givenUserList_whenfindAllUsers_thenReturnUserList() {

		given(userRepository.findAll()).willReturn(List.of(user1));

		List<UserDto> userList = userServiceimpl.findAll();

		// then - verify the output
		assertThat(userList).isNotNull();

		assertThat(userList.size()).isEqualTo(1);

		verify(userRepository).findAll();

	}

	// JUnit test for findAll users method: negative Scenario

	@DisplayName("JUnit test for findAll method (negative scenario)")
	@Test
	public void givenEmptyUserList_whenfindAllUsers_thenReturnEmptyUserList() {

		given(userRepository.findAll()).willReturn(Collections.EMPTY_LIST);

		List<UserDto> userList = userServiceimpl.findAll();

		// then - verify the output
		assertThat(userList).isEmpty();
		assertThat(userList.size()).isEqualTo(0);
		verify(userRepository).findAll();
	}

	// JUnit test for getUserById method
	@DisplayName("JUnit test for getUserById method")
	@Test
	public void givenUserId_whenGetUserById_thenRetuernUserObject() {

		given(userRepository.findById(1)).willReturn(Optional.of(user1));

		// when
		UserDto savedUser = userServiceimpl.findById(user1.getUserId());

		// then
		assertThat(savedUser).isNotNull();

	}

	@Test()
	public void should_throw_exception_when_user_doesnt_exist() {

		given(userRepository.findById(1)).willReturn(Optional.ofNullable(null));

		Assertions.assertThrows(UserObjectNotFoundException.class, () -> userServiceimpl.findById(user1.getUserId()));

	}

	// JUnit test for getUserById method
	@DisplayName("JUnit test for getUserByName method")
	@Test
	public void givenUserName_whenGetUserByName_thenRetuernUserObject() {

		given(userRepository.findByCredentialUsername(user1.getCredential().getUsername()))
				.willReturn(Optional.of(user1));

		// when
		UserDto savedUser = userServiceimpl.findByUsername(user1.getCredential().getUsername());

		// then
		assertThat(savedUser).isNotNull();

		verify(userRepository).findByCredentialUsername(user1.getCredential().getUsername());

	}

	@Test()
	public void should_throw_exception_when_user_by_name_doesnt_exist() {

		given(userRepository.findByCredentialUsername(user1.getCredential().getUsername()))
				.willReturn(Optional.ofNullable(null));

		Assertions.assertThrows(UserObjectNotFoundException.class,
				() -> userServiceimpl.findByUsername(user1.getCredential().getUsername()));

	}

	// JUnit test for saveEmployee method
	@DisplayName("JUnit test for saveUser method")
	@Test
	public void givenUserObject_whenSaveUser_thenReturnUserObject() {

		// given - precondition or setup

		given(userRepository.save(user1)).willReturn(user1);

		// when
		UserDto userDto = userServiceimpl.save(UserMappingHelper.map(user1));

		// then - verify the output

		User user = UserMappingHelper.map(userDto);

		assertThat(user).isNotNull();

		assertThat(user.getUserId()).isEqualTo(user1.getUserId());

	}

	// JUnit test for updateEmployee method
	@DisplayName("JUnit test for updateUser method")
	@Test
	public void givenUserObject_whenUpdateUser_thenReturnUpdatedUser() {

		// given - precondition or setup

		given(userRepository.save(user1)).willReturn(user1);

		// when

		UserDto userDto = userServiceimpl.update(UserMappingHelper.map(user1));

		// then - verify the output

		User user = UserMappingHelper.map(userDto);

		assertThat(user).isNotNull();

		assertThat(user.getUserId()).isEqualTo(user1.getUserId());

	}

	// JUnit test for updateEmployee method
	@DisplayName("JUnit test for updateUser method using userId")
	@Test
	public void givenUserId_whenUpdateUser_thenReturnUpdatedUser() {

		// given - precondition or setup

		given(userRepository.save(user1)).willReturn(user1);
		given(userRepository.findById(user1.getUserId())).willReturn(Optional.of(user1));
		
		// when

		UserDto userDto = userServiceimpl.update(user1.getUserId(),UserMappingHelper.map(user1));

		// then - verify the output

		User user = UserMappingHelper.map(userDto);

		assertThat(user).isNotNull();

		assertThat(user.getUserId()).isEqualTo(user1.getUserId());

	}
	

	@Test()
	public void update_should_throw_exception_when_user_doesnt_exist() {

		given(userRepository.findById(1)).willReturn(Optional.ofNullable(null));

		Assertions.assertThrows(UserObjectNotFoundException.class, () -> userServiceimpl.update(user1.getUserId(),UserMappingHelper.map(user1)));

	}

	
	 // JUnit test for deleteEmployee method
    @DisplayName("JUnit test for deleteUser method")
    @Test
    public void givenUserId_whenDeleteUser_thenNothing(){
    	
    	 // given - precondition or setup
        Integer userId = 1;
        
        
        willDoNothing().given(userRepository).deleteById(userId);

        // when -  action or the behaviour that we are going test
        
        userServiceimpl.deleteById(userId);
        
        
        // then - verify the output
        
        verify(userRepository, times(1)).deleteById(userId);
        
    	
    	
    }
    
    
    
	
	
	
	
	
	

}
