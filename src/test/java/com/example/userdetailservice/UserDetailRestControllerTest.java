package com.example.userdetailservice;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

/**
 * To Perform JUnit Test on {@link:UserDetailRestController}
 * @author manpreetwalia
 *
 */
public class UserDetailRestControllerTest extends UserDetailServiceApplicationTests{

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testGetUser() throws Exception {
		mockMvc.perform(get("/user-detail/1")).andExpect(status().isOk()).andExpect(jsonPath("$.firstName").value("Manpreet"))
		.andExpect(jsonPath("$.address.state").value("NSW"));
	}
	
	@Test
	public void testUpdateUser() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/user-detail/1")).andReturn();	
		String content = mvcResult.getResponse().getContentAsString();
		User user = mapFromJson(content, User.class);
		user.setTitle("Master");
		MvcResult mvcPostResult = mockMvc.perform(MockMvcRequestBuilders.post("/user-detail/1").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(user))).andReturn();
		String postRes = mvcPostResult.getResponse().getContentAsString();
		User postResultUser = mapFromJson(postRes, User.class);
		assertEquals("Master", postResultUser.getTitle());
	}
	
	@Test
	public void testCreateUser() throws Exception {
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
		String uri = "/user-detail";
		String inputJson = mapToJson(user);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
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
