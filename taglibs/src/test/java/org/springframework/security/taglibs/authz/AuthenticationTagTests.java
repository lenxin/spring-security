package org.springframework.security.taglibs.authz;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.junit.After;
import org.junit.Test;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests {@link AuthenticationTag}.
 *
 * @author Ben Alex
 */
public class AuthenticationTagTests {

	private final MyAuthenticationTag authenticationTag = new MyAuthenticationTag();

	private final Authentication auth = new TestingAuthenticationToken(
			new User("rodUserDetails", "koala", true, true, true, true, AuthorityUtils.NO_AUTHORITIES), "koala",
			AuthorityUtils.NO_AUTHORITIES);

	@After
	public void tearDown() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void testOperationWhenPrincipalIsAUserDetailsInstance() throws JspException {
		SecurityContextHolder.getContext().setAuthentication(this.auth);
		this.authenticationTag.setProperty("name");
		assertThat(this.authenticationTag.doStartTag()).isEqualTo(Tag.SKIP_BODY);
		assertThat(this.authenticationTag.doEndTag()).isEqualTo(Tag.EVAL_PAGE);
		assertThat(this.authenticationTag.getLastMessage()).isEqualTo("rodUserDetails");
	}

	@Test
	public void testOperationWhenPrincipalIsAString() throws JspException {
		SecurityContextHolder.getContext().setAuthentication(
				new TestingAuthenticationToken("rodAsString", "koala", AuthorityUtils.NO_AUTHORITIES));
		this.authenticationTag.setProperty("principal");
		assertThat(this.authenticationTag.doStartTag()).isEqualTo(Tag.SKIP_BODY);
		assertThat(this.authenticationTag.doEndTag()).isEqualTo(Tag.EVAL_PAGE);
		assertThat(this.authenticationTag.getLastMessage()).isEqualTo("rodAsString");
	}

	@Test
	public void testNestedPropertyIsReadCorrectly() throws JspException {
		SecurityContextHolder.getContext().setAuthentication(this.auth);
		this.authenticationTag.setProperty("principal.username");
		assertThat(this.authenticationTag.doStartTag()).isEqualTo(Tag.SKIP_BODY);
		assertThat(this.authenticationTag.doEndTag()).isEqualTo(Tag.EVAL_PAGE);
		assertThat(this.authenticationTag.getLastMessage()).isEqualTo("rodUserDetails");
	}

	@Test
	public void testOperationWhenPrincipalIsNull() throws JspException {
		SecurityContextHolder.getContext()
				.setAuthentication(new TestingAuthenticationToken(null, "koala", AuthorityUtils.NO_AUTHORITIES));
		this.authenticationTag.setProperty("principal");
		assertThat(this.authenticationTag.doStartTag()).isEqualTo(Tag.SKIP_BODY);
		assertThat(this.authenticationTag.doEndTag()).isEqualTo(Tag.EVAL_PAGE);
	}

	@Test
	public void testOperationWhenSecurityContextIsNull() throws Exception {
		SecurityContextHolder.getContext().setAuthentication(null);
		this.authenticationTag.setProperty("principal");
		assertThat(this.authenticationTag.doStartTag()).isEqualTo(Tag.SKIP_BODY);
		assertThat(this.authenticationTag.doEndTag()).isEqualTo(Tag.EVAL_PAGE);
		assertThat(this.authenticationTag.getLastMessage()).isNull();
	}

	@Test
	public void testSkipsBodyIfNullOrEmptyOperation() throws Exception {
		this.authenticationTag.setProperty("");
		assertThat(this.authenticationTag.doStartTag()).isEqualTo(Tag.SKIP_BODY);
		assertThat(this.authenticationTag.doEndTag()).isEqualTo(Tag.EVAL_PAGE);
	}

	@Test
	public void testThrowsExceptionForUnrecognisedProperty() {
		SecurityContextHolder.getContext().setAuthentication(this.auth);
		this.authenticationTag.setProperty("qsq");
		assertThatExceptionOfType(JspException.class).isThrownBy(() -> {
			this.authenticationTag.doStartTag();
			this.authenticationTag.doEndTag();
		});
	}

	@Test
	public void htmlEscapingIsUsedByDefault() throws Exception {
		SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("<>& ", ""));
		this.authenticationTag.setProperty("name");
		this.authenticationTag.doStartTag();
		this.authenticationTag.doEndTag();
		assertThat(this.authenticationTag.getLastMessage()).isEqualTo("&lt;&gt;&amp;&#32;");
	}

	@Test
	public void settingHtmlEscapeToFalsePreventsEscaping() throws Exception {
		SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("<>& ", ""));
		this.authenticationTag.setProperty("name");
		this.authenticationTag.setHtmlEscape("false");
		this.authenticationTag.doStartTag();
		this.authenticationTag.doEndTag();
		assertThat(this.authenticationTag.getLastMessage()).isEqualTo("<>& ");
	}

	private class MyAuthenticationTag extends AuthenticationTag {

		String lastMessage = null;

		String getLastMessage() {
			return this.lastMessage;
		}

		@Override
		protected void writeMessage(String msg) {
			this.lastMessage = msg;
		}

	}

}
