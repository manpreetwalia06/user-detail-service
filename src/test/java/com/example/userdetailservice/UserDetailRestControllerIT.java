package com.example.userdetailservice;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.userdetailservice.model.Address;
import com.example.userdetailservice.model.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserDetailRestControllerIT {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;


	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}	
	
	@Test
	public void testGetUser_HttpStatus200() throws Exception {
		MvcResult result = mockMvc.perform(get("/user-detail/1")).andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}
	
	@Test
	public void testGetUser_HttpStatus400() throws Exception {
		MvcResult result = mockMvc.perform(get("/user-detail/100")).andReturn();
		assertEquals(404, result.getResponse().getStatus());
	}
	
	@Test
	public void testCreateUser_Json() throws Exception {
		User user = creatData();
		String uri = "/user-detail";
		String inputJson = mapToJson(user);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		String response = mvcResult.getResponse().getContentAsString();
		assertEquals(true, response.contains("Steve"));
	}

	private User creatData() {
		Address address = new Address();
		long id = Calendar.getInstance().getTimeInMillis();
		address.setId(id);
		address.setFullAddress("45 Merian St");
		address.setPostcode(2201);
		address.setState("NSW");
		address.setSuburb("Lindfield");
		User user = new User();
		user.setId(id);
		user.setFirstName("Steve");
		user.setLastName("Waugh");
		user.setTitle("Mr");
		user.setGender("Male");
		user.setAddress(address);
		address.setUser(user);
		return user;
	}
	
	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
}

