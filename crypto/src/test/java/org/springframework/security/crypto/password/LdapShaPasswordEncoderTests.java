package org.springframework.security.crypto.password;

import org.junit.Test;

import org.springframework.security.crypto.keygen.KeyGenerators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests {@link LdapShaPasswordEncoder}.
 *
 * @author Luke Taylor
 */
@SuppressWarnings("deprecation")
public class LdapShaPasswordEncoderTests {

	LdapShaPasswordEncoder sha = new LdapShaPasswordEncoder();

	@Test
	public void invalidPasswordFails() {
		assertThat(this.sha.matches("wrongpassword", "{SHA}ddSFGmjXYPbZC+NXR2kCzBRjqiE=")).isFalse();
	}

	@Test
	public void invalidSaltedPasswordFails() {
		assertThat(this.sha.matches("wrongpassword", "{SSHA}25ro4PKC8jhQZ26jVsozhX/xaP0suHgX")).isFalse();
		assertThat(this.sha.matches("wrongpassword", "{SSHA}PQy2j+6n5ytA+YlAKkM8Fh4p6u2JxfVd")).isFalse();
	}

	/**
	 * Test values generated by 'slappasswd -h {SHA} -s boabspasswurd'
	 */
	@Test
	public void validPasswordSucceeds() {
		this.sha.setForceLowerCasePrefix(false);
		assertThat(this.sha.matches("boabspasswurd", "{SHA}ddSFGmjXYPbZC+NXR2kCzBRjqiE=")).isTrue();
		assertThat(this.sha.matches("boabspasswurd", "{sha}ddSFGmjXYPbZC+NXR2kCzBRjqiE=")).isTrue();
		this.sha.setForceLowerCasePrefix(true);
		assertThat(this.sha.matches("boabspasswurd", "{SHA}ddSFGmjXYPbZC+NXR2kCzBRjqiE=")).isTrue();
		assertThat(this.sha.matches("boabspasswurd", "{sha}ddSFGmjXYPbZC+NXR2kCzBRjqiE=")).isTrue();
	}

	/**
	 * Test values generated by 'slappasswd -s boabspasswurd'
	 */
	@Test
	public void validSaltedPasswordSucceeds() {
		this.sha.setForceLowerCasePrefix(false);
		assertThat(this.sha.matches("boabspasswurd", "{SSHA}25ro4PKC8jhQZ26jVsozhX/xaP0suHgX")).isTrue();
		assertThat(this.sha.matches("boabspasswurd", "{ssha}PQy2j+6n5ytA+YlAKkM8Fh4p6u2JxfVd")).isTrue();
		this.sha.setForceLowerCasePrefix(true);
		assertThat(this.sha.matches("boabspasswurd", "{SSHA}25ro4PKC8jhQZ26jVsozhX/xaP0suHgX")).isTrue();
		assertThat(this.sha.matches("boabspasswurd", "{ssha}PQy2j+6n5ytA+YlAKkM8Fh4p6u2JxfVd")).isTrue();
	}

	@Test
	// SEC-1031
	public void fullLengthOfHashIsUsedInComparison() {
		assertThat(this.sha.matches("boabspasswurd", "{SSHA}25ro4PKC8jhQZ26jVsozhX/xaP0suHgX")).isTrue();
		// Change the first hash character from '2' to '3'
		assertThat(this.sha.matches("boabspasswurd", "{SSHA}35ro4PKC8jhQZ26jVsozhX/xaP0suHgX")).isFalse();
		// Change the last hash character from 'X' to 'Y'
		assertThat(this.sha.matches("boabspasswurd", "{SSHA}25ro4PKC8jhQZ26jVsozhX/xaP0suHgY")).isFalse();
	}

	@Test
	public void correctPrefixCaseIsUsed() {
		this.sha.setForceLowerCasePrefix(false);
		assertThat(this.sha.encode("somepassword").startsWith("{SSHA}"));
		this.sha.setForceLowerCasePrefix(true);
		assertThat(this.sha.encode("somepassword").startsWith("{ssha}"));
		this.sha = new LdapShaPasswordEncoder(KeyGenerators.shared(0));
		this.sha.setForceLowerCasePrefix(false);
		assertThat(this.sha.encode("somepassword").startsWith("{SHA}"));
		this.sha.setForceLowerCasePrefix(true);
		assertThat(this.sha.encode("somepassword").startsWith("{SSHA}"));
	}

	@Test
	public void invalidPrefixIsRejected() {
		assertThatIllegalArgumentException().isThrownBy(() -> this.sha.matches("somepassword", "{MD9}xxxxxxxxxx"));
	}

	@Test
	public void malformedPrefixIsRejected() {
		// No right brace
		assertThatIllegalArgumentException()
				.isThrownBy(() -> this.sha.matches("somepassword", "{SSHA25ro4PKC8jhQZ26jVsozhX/xaP0suHgX"));
	}

}
