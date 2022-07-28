package com.dailycodebuffer.springboot.tutorial.controller;

import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.MediaType;

import java.util.List;

import com.dailycodebuffer.springboot.tutorial.domain.User;
import com.dailycodebuffer.springboot.tutorial.dto.UserDto;
import com.dailycodebuffer.springboot.tutorial.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@AutoConfigureJsonTesters
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@MockBean
	private UserService userService;

	@Autowired
	private MockMvc mvc;


	// This object will be magically initialized by the initFields method below.
    private JacksonTester<User> json;

    private UserDto userDto;
    
     

    @BeforeEach
    public void setup()
    {
    	
    	userDto = UserDto.builder().email("skulyana@gmail.com").firstName("Sunil").lastName("Kulyana").imageUrl("google.com").phone("111").build();
    
    }
    
    @Test
    void shouldFetchAllUsers() throws Exception {
    	
    	given(userService.findAll()).willReturn(List.of(userDto));
    	
    	// when
        MockHttpServletResponse response = mvc.perform(
                get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    	

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    	
    }
    
    
	
	
	
	
}



