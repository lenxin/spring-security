package org.springframework.security.ldap.ppolicy;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;

import org.junit.Before;
import org.junit.Test;

import org.springframework.ldap.UncategorizedLdapException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

/**
 * @author Luke Taylor
 */
public class PasswordPolicyAwareContextSourceTests {

	private PasswordPolicyAwareContextSource ctxSource;

	private final LdapContext ctx = mock(LdapContext.class);

	@Before
	public void setUp() {
		reset(this.ctx);
		this.ctxSource = new PasswordPolicyAwareContextSource("ldap://blah:789/dc=springframework,dc=org") {
			@Override
			protected DirContext createContext(Hashtable env) {
				if ("manager".equals(env.get(Context.SECURITY_PRINCIPAL))) {
					return PasswordPolicyAwareContextSourceTests.this.ctx;
				}
				return null;
			}
		};
		this.ctxSource.setUserDn("manager");
		this.ctxSource.setPassword("password");
		this.ctxSource.afterPropertiesSet();
	}

	@Test
	public void contextIsReturnedWhenNoControlsAreSetAndReconnectIsSuccessful() {
		assertThat(this.ctxSource.getContext("user", "ignored")).isNotNull();
	}

	@Test
	public void standardExceptionIsPropagatedWhenExceptionRaisedAndNoControlsAreSet() throws Exception {
		willThrow(new NamingException("some LDAP exception")).given(this.ctx).reconnect(any(Control[].class));
		assertThatExceptionOfType(UncategorizedLdapException.class)
				.isThrownBy(() -> this.ctxSource.getContext("user", "ignored"));
	}

	@Test
	public void lockedPasswordPolicyControlRaisesPasswordPolicyException() throws Exception {
		given(this.ctx.getResponseControls()).willReturn(new Control[] {
				new PasswordPolicyResponseControl(PasswordPolicyResponseControlTests.OPENLDAP_LOCKED_CTRL) });
		willThrow(new NamingException("locked message")).given(this.ctx).reconnect(any(Control[].class));
		assertThatExceptionOfType(PasswordPolicyException.class)
				.isThrownBy(() -> this.ctxSource.getContext("user", "ignored"));
	}

}
