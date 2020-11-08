package org.springframework.security.provisioning;

import org.junit.Test;

import org.springframework.security.core.userdetails.PasswordEncodedUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 * @since 5.1
 */
public class InMemoryUserDetailsManagerTests {

	private final UserDetails user = PasswordEncodedUser.user();

	private InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(this.user);

	@Test
	public void changePassword() {
		String newPassword = "newPassword";
		this.manager.updatePassword(this.user, newPassword);
		assertThat(this.manager.loadUserByUsername(this.user.getUsername()).getPassword()).isEqualTo(newPassword);
	}

	@Test
	public void changePasswordWhenUsernameIsNotInLowercase() {
		UserDetails userNotLowerCase = User.withUserDetails(PasswordEncodedUser.user()).username("User").build();
		String newPassword = "newPassword";
		this.manager.updatePassword(userNotLowerCase, newPassword);
		assertThat(this.manager.loadUserByUsername(userNotLowerCase.getUsername()).getPassword())
				.isEqualTo(newPassword);
	}

}
