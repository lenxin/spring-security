package org.springframework.security.web.authentication.preauth.x509;

import org.junit.Before;
import org.junit.Test;

import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Luke Taylor
 */
public class SubjectDnX509PrincipalExtractorTests {

	SubjectDnX509PrincipalExtractor extractor;

	@Before
	public void setUp() {
		this.extractor = new SubjectDnX509PrincipalExtractor();
	}

	@Test
	public void invalidRegexFails() {
		// missing closing bracket on group
		assertThatIllegalArgumentException().isThrownBy(() -> this.extractor.setSubjectDnRegex("CN=(.*?,"));
	}

	@Test
	public void defaultCNPatternReturnsExcpectedPrincipal() throws Exception {
		Object principal = this.extractor.extractPrincipal(X509TestUtils.buildTestCertificate());
		assertThat(principal).isEqualTo("Luke Taylor");
	}

	@Test
	public void matchOnEmailReturnsExpectedPrincipal() throws Exception {
		this.extractor.setSubjectDnRegex("emailAddress=(.*?),");
		Object principal = this.extractor.extractPrincipal(X509TestUtils.buildTestCertificate());
		assertThat(principal).isEqualTo("luke@monkeymachine");
	}

	@Test
	public void matchOnShoeSizeThrowsBadCredentials() throws Exception {
		this.extractor.setSubjectDnRegex("shoeSize=(.*?),");
		assertThatExceptionOfType(BadCredentialsException.class)
				.isThrownBy(() -> this.extractor.extractPrincipal(X509TestUtils.buildTestCertificate()));
	}

	@Test
	public void defaultCNPatternReturnsPrincipalAtEndOfDNString() throws Exception {
		Object principal = this.extractor.extractPrincipal(X509TestUtils.buildTestCertificateWithCnAtEnd());
		assertThat(principal).isEqualTo("Duke");
	}

	@Test
	public void setMessageSourceWhenNullThenThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> this.extractor.setMessageSource(null));
	}

	@Test
	public void setMessageSourceWhenNotNullThenCanGet() {
		MessageSource source = mock(MessageSource.class);
		this.extractor.setMessageSource(source);
		String code = "code";
		this.extractor.messages.getMessage(code);
		verify(source).getMessage(eq(code), any(), any());
	}

}
