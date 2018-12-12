package com.dmr;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dmr.model.Person;
import com.dmr.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = { JPAConfig.class})
public class AuthenticationControllerTest {
	@Autowired
	private WebApplicationContext context;
	private MockMvc mockmvc;
	@Autowired
	Environment env;

	@Before
	public void beforeEach() {
		mockmvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void getUser() throws Exception {
		mockmvc.perform(get("/api/private/user?login=mail&password=123")).andExpect(status().isOk());
	}

	@Test
	public void postUser() throws Exception {
		Person userForm = new User();
		userForm.setLogin("hamza");
		userForm.setEmail("wind21@hotmail.fr");
		userForm.setPassword("password");
		mockmvc.perform(post("/api/private/user").flashAttr("user", userForm)).andExpect(status().isOk());

		Person personBis = new User();
		personBis.setEmail("wind21@hotmail.fr");
		mockmvc.perform(post("/api/private/user").flashAttr("user", personBis)).andExpect(status().isBadRequest());
	}

}
