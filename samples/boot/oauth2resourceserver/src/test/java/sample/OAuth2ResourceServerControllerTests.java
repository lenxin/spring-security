package sample;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Jérôme Wacongne &lt;ch4mp@c4-soft.com&gt;
 * @author Josh Cummings
 * @since 5.2.0
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(OAuth2ResourceServerController.class)
public class OAuth2ResourceServerControllerTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	public void indexGreetsAuthenticatedUser() throws Exception {
		mockMvc.perform(get("/").with(jwt().jwt((jwt) -> jwt.subject("ch4mpy"))))
				.andExpect(content().string(is("Hello, ch4mpy!")));
	}

	@Test
	public void messageCanBeReadWithScopeMessageReadAuthority() throws Exception {
		mockMvc.perform(get("/message").with(jwt().jwt((jwt) -> jwt.claim("scope", "message:read"))))
				.andExpect(content().string(is("secret message")));

		mockMvc.perform(get("/message")
				.with(jwt().authorities(new SimpleGrantedAuthority(("SCOPE_message:read")))))
				.andExpect(content().string(is("secret message")));
	}

	@Test
	public void messageCanNotBeReadWithoutScopeMessageReadAuthority() throws Exception {
		mockMvc.perform(get("/message").with(jwt()))
				.andExpect(status().isForbidden());
	}

	@Test
	public void messageCanNotBeCreatedWithoutAnyScope() throws Exception {
		mockMvc.perform(post("/message")
				.content("Hello message")
				.with(jwt()))
				.andExpect(status().isForbidden());
	}

	@Test
	public void messageCanNotBeCreatedWithScopeMessageReadAuthority() throws Exception {
		mockMvc.perform(post("/message")
				.content("Hello message")
				.with(jwt().jwt((jwt) -> jwt.claim("scope", "message:read"))))
				.andExpect(status().isForbidden());
	}

	@Test
	public void messageCanBeCreatedWithScopeMessageWriteAuthority()
			throws Exception {
		mockMvc.perform(post("/message")
				.content("Hello message")
				.with(jwt().jwt((jwt) -> jwt.claim("scope", "message:write"))))
				.andExpect(status().isOk())
				.andExpect(content().string(is("Message was created. Content: Hello message")));
	}
}
