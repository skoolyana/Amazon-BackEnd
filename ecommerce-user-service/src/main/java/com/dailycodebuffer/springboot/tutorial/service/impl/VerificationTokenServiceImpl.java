package com.dailycodebuffer.springboot.tutorial.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.dailycodebuffer.springboot.tutorial.dto.VerificationTokenDto;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.AddressNotFoundException;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.VerificationTokenNotFoundException;
import com.dailycodebuffer.springboot.tutorial.helper.AddressMappingHelper;
import com.dailycodebuffer.springboot.tutorial.helper.VerificationTokenMappingHelper;
import com.dailycodebuffer.springboot.tutorial.repository.AddressRepository;
import com.dailycodebuffer.springboot.tutorial.repository.VerificationTokenRepository;
import com.dailycodebuffer.springboot.tutorial.service.VerificationTokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor

public class VerificationTokenServiceImpl implements VerificationTokenService {

	private final VerificationTokenRepository verificationTokenRepository;
		
	@Override
	public List<VerificationTokenDto> findAll() {
		// TODO Auto-generated method stub

		log.info("*** VerificationTokenDto List, service; fetch all tokens *");

		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		return this.verificationTokenRepository.findAll().stream().map(VerificationTokenMappingHelper::map).distinct()
				.collect(Collectors.toUnmodifiableList());

	
	
	}

	@Override
	public VerificationTokenDto findById(Integer verificationTokenId) {

		// TODO Auto-generated method stub
		log.info("*** VerificationTokenDto, service; fetch address by id *");

		// TODO Auto-generated method stub
		return this.verificationTokenRepository.findById(verificationTokenId).map(VerificationTokenMappingHelper::map).orElseThrow(
				() -> new VerificationTokenNotFoundException(String.format("verificationToken with id: %d not found", verificationTokenId)));


	
	
	}

	@Override
	public VerificationTokenDto save(VerificationTokenDto verificationTokenDto) {

		
		log.info("*** verificationTokenDto, service; save token *");

		return VerificationTokenMappingHelper.map(this.verificationTokenRepository.save(VerificationTokenMappingHelper.map(verificationTokenDto)));

		
	}

	@Override
	public VerificationTokenDto update(VerificationTokenDto verificationTokenDto) {
		log.info("*** verificationTokenDto, service; update token *");

		// TODO Auto-generated method stub
		return VerificationTokenMappingHelper.map(this.verificationTokenRepository.save(VerificationTokenMappingHelper.map(verificationTokenDto)));

	}

	@Override
	public VerificationTokenDto update(Integer verificationTokenId, VerificationTokenDto verificationTokenDto) {


		log.info("*** VerificationTokenDto, service; update token with tokenId *");

		return VerificationTokenMappingHelper
				.map(this.verificationTokenRepository.save(VerificationTokenMappingHelper.map(this.findById(verificationTokenId))));

	
	}

	@Override
	public void deleteById(Integer verificationTokenId) {
		// TODO Auto-generated method stub
		
		
		log.info("*** VerificationTokenDto, service; delete token*");

		verificationTokenRepository.deleteById(verificationTokenId);

	}

	
}
