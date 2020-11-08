package org.springframework.security.acls.domain;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.AuditableAccessControlEntry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Test class for {@link ConsoleAuditLogger}.
 *
 * @author Andrei Stefan
 */
public class AuditLoggerTests {

	private PrintStream console;

	private ByteArrayOutputStream bytes = new ByteArrayOutputStream();

	private ConsoleAuditLogger logger;

	private AuditableAccessControlEntry ace;

	@Before
	public void setUp() {
		this.logger = new ConsoleAuditLogger();
		this.ace = mock(AuditableAccessControlEntry.class);
		this.console = System.out;
		System.setOut(new PrintStream(this.bytes));
	}

	@After
	public void tearDown() {
		System.setOut(this.console);
		this.bytes.reset();
	}

	@Test
	public void nonAuditableAceIsIgnored() {
		AccessControlEntry ace = mock(AccessControlEntry.class);
		this.logger.logIfNeeded(true, ace);
		assertThat(this.bytes.size()).isZero();
	}

	@Test
	public void successIsNotLoggedIfAceDoesntRequireSuccessAudit() {
		given(this.ace.isAuditSuccess()).willReturn(false);
		this.logger.logIfNeeded(true, this.ace);
		assertThat(this.bytes.size()).isZero();
	}

	@Test
	public void successIsLoggedIfAceRequiresSuccessAudit() {
		given(this.ace.isAuditSuccess()).willReturn(true);
		this.logger.logIfNeeded(true, this.ace);
		assertThat(this.bytes.toString()).startsWith("GRANTED due to ACE");
	}

	@Test
	public void failureIsntLoggedIfAceDoesntRequireFailureAudit() {
		given(this.ace.isAuditFailure()).willReturn(false);
		this.logger.logIfNeeded(false, this.ace);
		assertThat(this.bytes.size()).isZero();
	}

	@Test
	public void failureIsLoggedIfAceRequiresFailureAudit() {
		given(this.ace.isAuditFailure()).willReturn(true);
		this.logger.logIfNeeded(false, this.ace);
		assertThat(this.bytes.toString()).startsWith("DENIED due to ACE");
	}

}
