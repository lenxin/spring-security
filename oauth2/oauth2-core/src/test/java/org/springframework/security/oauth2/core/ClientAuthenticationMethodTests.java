package org.springframework.security.oauth2.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link ClientAuthenticationMethod}.
 *
 * @author Joe Grandja
 */
public class ClientAuthenticationMethodTests {

	@Test
	public void constructorWhenValueIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ClientAuthenticationMethod(null));
	}

	@Test
	public void getValueWhenAuthenticationMethodBasicThenReturnBasic() {
		assertThat(ClientAuthenticationMethod.BASIC.getValue()).isEqualTo("basic");
	}

	@Test
	public void getValueWhenAuthenticationMethodPostThenReturnPost() {
		assertThat(ClientAuthenticationMethod.POST.getValue()).isEqualTo("post");
	}

	@Test
	public void getValueWhenAuthenticationMethodNoneThenReturnNone() {
		assertThat(ClientAuthenticationMethod.NONE.getValue()).isEqualTo("none");
	}

}
