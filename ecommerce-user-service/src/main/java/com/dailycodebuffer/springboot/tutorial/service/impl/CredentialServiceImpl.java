package com.dailycodebuffer.springboot.tutorial.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dailycodebuffer.springboot.tutorial.dto.CredentialDto;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.AddressNotFoundException;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.CredentialNotFoundException;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.UserObjectNotFoundException;
import com.dailycodebuffer.springboot.tutorial.helper.AddressMappingHelper;
import com.dailycodebuffer.springboot.tutorial.helper.CredentialMappingHelper;
import com.dailycodebuffer.springboot.tutorial.repository.AddressRepository;
import com.dailycodebuffer.springboot.tutorial.repository.CredentialRepository;
import com.dailycodebuffer.springboot.tutorial.service.CredentialService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor

public class CredentialServiceImpl implements CredentialService {

	private final CredentialRepository credentialRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public List<CredentialDto> findAll() {
		
		log.info("*** CredentialDto List, service; fetch all credentials *");

		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		return this.credentialRepository.findAll().stream().map(CredentialMappingHelper::map).distinct()
				.collect(Collectors.toUnmodifiableList());

		
		
	}

	@Override
	public CredentialDto findById(Integer credentialId) {
		
		// TODO Auto-generated method stub
		log.info("*** CredentialDto, service; fetch credential by id *");

		// TODO Auto-generated method stub
		return this.credentialRepository.findById(credentialId).map(CredentialMappingHelper::map).orElseThrow(
				() -> new CredentialNotFoundException(String.format("Credential with id: %d not found", credentialId)));

	}

	@Override
	public CredentialDto save(CredentialDto credentialDto) {
		// TODO Auto-generated method stub
		log.info("*** CredentialDto, service; save credential *");
		
		CredentialDto credDto =   credentialDto;
		
		if(credDto!=null)
		{
			credDto.setPassword(new BCryptPasswordEncoder().encode(credDto.getPassword()));
			
			credentialDto = credDto ;
		}
		

		return CredentialMappingHelper.map(this.credentialRepository.save(CredentialMappingHelper.map(credentialDto)));

	}

	@Override
	public CredentialDto update(CredentialDto credentialDto) {
		log.info("*** credentialDto, service; update credential *");

		// TODO Auto-generated method stub
		return CredentialMappingHelper.map(this.credentialRepository.save(CredentialMappingHelper.map(credentialDto)));
	}

	@Override
	public CredentialDto update(Integer credentialId, CredentialDto credentialDto) {
		log.info("*** CredentialDto, service; update credential with credentialId *");

		return CredentialMappingHelper
				.map(this.credentialRepository.save(CredentialMappingHelper.map(this.findById(credentialId))));
	}

	@Override
	public void deleteById(Integer credentialId) {
		// TODO Auto-generated method stub
		

		log.info("*** CredentialDto, service; delete credential*");

		credentialRepository.deleteById(credentialId);

		
		
	}

	@Override
	public CredentialDto findByUsername(String username) {
		return CredentialMappingHelper.map(this.credentialRepository.findByUsername(username)
				.orElseThrow(() -> new CredentialNotFoundException(String.format("#### Credential with username: %s not found! ####", username))));
	}

}
