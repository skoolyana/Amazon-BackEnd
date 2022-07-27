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

import com.dailycodebuffer.springboot.tutorial.domain.Address;
import com.dailycodebuffer.springboot.tutorial.domain.User;
import com.dailycodebuffer.springboot.tutorial.dto.AddressDto;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.AddressNotFoundException;
import com.dailycodebuffer.springboot.tutorial.helper.AddressMappingHelper;
import com.dailycodebuffer.springboot.tutorial.repository.AddressRepository;
import com.dailycodebuffer.springboot.tutorial.service.impl.AddressServiceImpl;

public class AddressServiceImplTest {

	private AddressServiceImpl addressServiceimpl;

	@Mock
	private AddressRepository addressRepository;

	private Address address1;

	@BeforeEach
	public void setup() {

		// With this call to initMocks we tell Mockito to process the annotations
		MockitoAnnotations.initMocks(this);
		addressServiceimpl = new AddressServiceImpl(addressRepository);

		address1 = Address.builder().addressId(1).city("Delhi").fullAddress("Maxi").postalCode("28017")
				.user(User.builder().email("kulyanasun@gmail.com").firstName("Sunil").lastName("Kulyana")
						.imageUrl("google.com").phone("110003").build())
				.build();

	}

	// JUnit test for findAll users method

	@DisplayName("JUnit test for findAll Addresses method")
	@Test
	public void givenAddressList_whenfindAllAddress_thenReturnAddressList() {

		given(addressRepository.findAll()).willReturn(List.of(address1));

		List<AddressDto> addressList = addressServiceimpl.findAll();

		// then - verify the output
		assertThat(addressList).isNotNull();

		assertThat(addressList.size()).isEqualTo(1);

		verify(addressRepository).findAll();

	}

	// JUnit test for findAll users method: negative Scenario

	@DisplayName("JUnit test for findAll method (negative scenario)")
	@Test
	public void givenEmptyAddressList_whenfindAllAddress_thenReturnEmptyAddressList() {

		given(addressRepository.findAll()).willReturn(Collections.EMPTY_LIST);

		List<AddressDto> addressList = addressServiceimpl.findAll();

		// then - verify the output
		assertThat(addressList).isEmpty();
		assertThat(addressList.size()).isEqualTo(0);
		verify(addressRepository).findAll();
	}

	// JUnit test for getUserById method
	@DisplayName("JUnit test for getAddressById method")
	@Test
	public void givenAddressId_whenGetAddressById_thenRetuernUserObject() {

		given(addressRepository.findById(1)).willReturn(Optional.of(address1));

		// when
		AddressDto savedAddress = addressServiceimpl.findById(address1.getAddressId());

		// then
		assertThat(savedAddress).isNotNull();

	}

	@Test()
	public void should_throw_exception_when_address_doesnt_exist() {

		given(addressRepository.findById(1)).willReturn(Optional.ofNullable(null));

		Assertions.assertThrows(AddressNotFoundException.class,
				() -> addressServiceimpl.findById(address1.getAddressId()));

	}

	// JUnit test for saveAddress method
	@DisplayName("JUnit test for saveAddress method")
	@Test
	public void givenAddressObject_whenSaveAddress_thenReturnAddressObject() {

		// given - precondition or setup

		given(addressRepository.save(address1)).willReturn(address1);

		// when
		AddressDto addressDto = addressServiceimpl.save(AddressMappingHelper.map(address1));

		// then - verify the output

		Address address = AddressMappingHelper.map(addressDto);

		assertThat(address).isNotNull();

		assertThat(address.getAddressId()).isEqualTo(address1.getAddressId());

	}

	// JUnit test for updateEmployee method
	@DisplayName("JUnit test for updateAddress method")
	@Test
	public void givenAddressObject_whenUpdateAddress_thenReturnUpdatedAddress() {

		// given - precondition or setup

		given(addressRepository.save(address1)).willReturn(address1);

		// when

		AddressDto addressDto = addressServiceimpl.update(AddressMappingHelper.map(address1));

		// then - verify the output

		Address address = AddressMappingHelper.map(addressDto);

		assertThat(address).isNotNull();

		assertThat(address.getAddressId()).isEqualTo(address1.getAddressId());

	}
	
	
	// JUnit test for updateEmployee method
	@DisplayName("JUnit test for updateUser method using userId")
	@Test
	public void givenAddressId_whenUpdateAddress_thenReturnUpdatedAddress() {

		// given - precondition or setup

		given(addressRepository.save(address1)).willReturn(address1);
		given(addressRepository.findById(address1.getAddressId())).willReturn(Optional.of(address1));
		
		// when

		AddressDto addressDto = addressServiceimpl.update(address1.getAddressId(),AddressMappingHelper.map(address1));

		// then - verify the output

		Address  address = AddressMappingHelper.map(addressDto);

		assertThat(address).isNotNull();

		assertThat(address.getAddressId()).isEqualTo(address1.getAddressId());

	}
	
	
	@Test()
	public void update_should_throw_exception_when_address_doesnt_exist() {

		given(addressRepository.findById(1)).willReturn(Optional.ofNullable(null));

		Assertions.assertThrows(AddressNotFoundException.class, () -> addressServiceimpl.update(address1.getAddressId(),AddressMappingHelper.map(address1)));

	}
	
	
	 // JUnit test for deleteEmployee method
    @DisplayName("JUnit test for deleteAddress method")
    @Test
    public void givenAddressId_whenDeleteAddress_thenNothing(){
    	
    	 // given - precondition or setup
        Integer addressId = 1;
        
        
        willDoNothing().given(addressRepository).deleteById(addressId);

        // when -  action or the behaviour that we are going test
        
        addressServiceimpl.deleteById(addressId);
        
        
        // then - verify the output
        
        verify(addressRepository, times(1)).deleteById(addressId);
        
    	
    	
    }



}
