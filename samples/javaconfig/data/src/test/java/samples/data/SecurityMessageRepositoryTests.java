package samples.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import samples.DataConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataConfig.class)
public class SecurityMessageRepositoryTests {
	@Autowired
	SecurityMessageRepository repository;

	User user;

	@Before
	public void setup() {
		user = new User();
		user.setId(0L);
		List<GrantedAuthority> authorities = AuthorityUtils
				.createAuthorityList("ROLE_USER");
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				user, "notused", authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@After
	public void cleanup() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void findAllOnlyToCurrentUser() {
		Long expectedId = user.getId();
		List<Message> messages = repository.findAll();
		assertThat(messages).hasSize(3);
		for (Message m : messages) {
			assertThat(m.getTo().getId()).isEqualTo(expectedId);
		}
	}
}
