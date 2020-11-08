package org.springframework.security.web.server.authentication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.given;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RunWith(MockitoJUnitRunner.class)
public class ServerFormLoginAuthenticationConverterTests {

	@Mock
	private ServerWebExchange exchange;

	private MultiValueMap<String, String> data = new LinkedMultiValueMap<>();

	private ServerFormLoginAuthenticationConverter converter = new ServerFormLoginAuthenticationConverter();

	@Before
	public void setup() {
		given(this.exchange.getFormData()).willReturn(Mono.just(this.data));
	}

	@Test
	public void applyWhenUsernameAndPasswordThenCreatesTokenSuccess() {
		String username = "username";
		String password = "password";
		this.data.add("username", username);
		this.data.add("password", password);
		Authentication authentication = this.converter.convert(this.exchange).block();
		assertThat(authentication.getName()).isEqualTo(username);
		assertThat(authentication.getCredentials()).isEqualTo(password);
		assertThat(authentication.getAuthorities()).isEmpty();
	}

	@Test
	public void applyWhenCustomParametersAndUsernameAndPasswordThenCreatesTokenSuccess() {
		String usernameParameter = "j_username";
		String passwordParameter = "j_password";
		String username = "username";
		String password = "password";
		this.converter.setUsernameParameter(usernameParameter);
		this.converter.setPasswordParameter(passwordParameter);
		this.data.add(usernameParameter, username);
		this.data.add(passwordParameter, password);
		Authentication authentication = this.converter.convert(this.exchange).block();
		assertThat(authentication.getName()).isEqualTo(username);
		assertThat(authentication.getCredentials()).isEqualTo(password);
		assertThat(authentication.getAuthorities()).isEmpty();
	}

	@Test
	public void applyWhenNoDataThenCreatesTokenSuccess() {
		Authentication authentication = this.converter.convert(this.exchange).block();
		assertThat(authentication.getName()).isNullOrEmpty();
		assertThat(authentication.getCredentials()).isNull();
		assertThat(authentication.getAuthorities()).isEmpty();
	}

	@Test
	public void setUsernameParameterWhenNullThenIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> this.converter.setUsernameParameter(null));
	}

	@Test
	public void setPasswordParameterWhenNullThenIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> this.converter.setPasswordParameter(null));
	}

}
