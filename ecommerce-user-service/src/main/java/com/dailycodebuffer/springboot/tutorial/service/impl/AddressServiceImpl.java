package com.dailycodebuffer.springboot.tutorial.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.dailycodebuffer.springboot.tutorial.dto.AddressDto;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.AddressNotFoundException;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.UserObjectNotFoundException;
import com.dailycodebuffer.springboot.tutorial.helper.AddressMappingHelper;
import com.dailycodebuffer.springboot.tutorial.helper.UserMappingHelper;
import com.dailycodebuffer.springboot.tutorial.repository.AddressRepository;
import com.dailycodebuffer.springboot.tutorial.repository.UserRepository;
import com.dailycodebuffer.springboot.tutorial.service.AddressService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

	private final AddressRepository addressRepository;

	@Override
	public List<AddressDto> findAll() {

		log.info("*** addressDto List, service; fetch all addresses *");

		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		return this.addressRepository.findAll().stream().map(AddressMappingHelper::map).distinct()
				.collect(Collectors.toUnmodifiableList());
	}

	@Override
	public AddressDto findById(Integer addressId) {
		// TODO Auto-generated method stub
		log.info("*** AddressDto, service; fetch address by id *");

		// TODO Auto-generated method stub
		return this.addressRepository.findById(addressId).map(AddressMappingHelper::map).orElseThrow(
				() -> new AddressNotFoundException(String.format("Address with id: %d not found", addressId)));

	}

	@Override
	public AddressDto save(AddressDto addressDto) {
		// TODO Auto-generated method stub
		log.info("*** AddressDto, service; save address *");

		return AddressMappingHelper.map(this.addressRepository.save(AddressMappingHelper.map(addressDto)));

	}

	@Override
	public AddressDto update(AddressDto addressDto) {

		log.info("*** AddressDto, service; update address *");

		// TODO Auto-generated method stub
		return AddressMappingHelper.map(this.addressRepository.save(AddressMappingHelper.map(addressDto)));

	}

	@Override
	public AddressDto update(Integer addressId, AddressDto addressDto) {
		// TODO Auto-generated method stub

		log.info("*** AddressDto, service; update address with addressId *");

		return AddressMappingHelper
				.map(this.addressRepository.save(AddressMappingHelper.map(this.findById(addressId))));

	}

	@Override
	public void deleteById(Integer addressId) {
		// TODO Auto-generated method stub

		log.info("*** AddressDto, service; delete address*");

		addressRepository.deleteById(addressId);

	}

}
