package org.springframework.security.config.authentication;

import org.junit.After;
import org.junit.Test;

import org.springframework.beans.FatalBeanException;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.security.config.util.InMemoryXmlApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Luke Taylor
 */
public class UserServiceBeanDefinitionParserTests {

	private AbstractXmlApplicationContext appContext;

	@After
	public void closeAppContext() {
		if (this.appContext != null) {
			this.appContext.close();
		}
	}

	@Test
	public void userServiceWithValidPropertiesFileWorksSuccessfully() {
		setContext("<user-service id='service' "
				+ "properties='classpath:org/springframework/security/config/users.properties'/>");
		UserDetailsService userService = (UserDetailsService) this.appContext.getBean("service");
		userService.loadUserByUsername("bob");
		userService.loadUserByUsername("joe");
	}

	@Test
	public void userServiceWithEmbeddedUsersWorksSuccessfully() {
		// @formatter:off
		setContext("<user-service id='service'>"
				+ "    <user name='joe' password='joespassword' authorities='ROLE_A'/>"
				+ "</user-service>");
		// @formatter:on
		UserDetailsService userService = (UserDetailsService) this.appContext.getBean("service");
		userService.loadUserByUsername("joe");
	}

	@Test
	public void namePasswordAndAuthoritiesSupportPlaceholders() {
		System.setProperty("principal.name", "joe");
		System.setProperty("principal.pass", "joespassword");
		System.setProperty("principal.authorities", "ROLE_A,ROLE_B");
		// @formatter:off
		setContext("<b:bean class='org.springframework.beans.factory.config.PropertyPlaceholderConfigurer'/>"
				+ "<user-service id='service'>"
				+ "    <user name='${principal.name}' password='${principal.pass}' authorities='${principal.authorities}'/>"
				+ "</user-service>");
		// @formatter:on
		UserDetailsService userService = (UserDetailsService) this.appContext.getBean("service");
		UserDetails joe = userService.loadUserByUsername("joe");
		assertThat(joe.getPassword()).isEqualTo("joespassword");
		assertThat(joe.getAuthorities()).hasSize(2);
	}

	@Test
	public void embeddedUsersWithNoPasswordIsGivenGeneratedValue() {
		// @formatter:off
		setContext("<user-service id='service'>"
				+ "    <user name='joe' authorities='ROLE_A'/>"
				+ "</user-service>");
		// @formatter:on
		UserDetailsService userService = (UserDetailsService) this.appContext.getBean("service");
		UserDetails joe = userService.loadUserByUsername("joe");
		assertThat(joe.getPassword().length() > 0).isTrue();
		Long.parseLong(joe.getPassword());
	}

	@Test
	public void worksWithOpenIDUrlsAsNames() {
		// @formatter:off
		setContext("<user-service id='service'>"
				+ "    <user name='https://joe.myopenid.com/' authorities='ROLE_A'/>"
				+ "    <user name='https://www.google.com/accounts/o8/id?id=MPtOaenBIk5yzW9n7n9' authorities='ROLE_A'/>"
				+ "</user-service>");
		// @formatter:on
		UserDetailsService userService = (UserDetailsService) this.appContext.getBean("service");
		assertThat(userService.loadUserByUsername("https://joe.myopenid.com/").getUsername())
				.isEqualTo("https://joe.myopenid.com/");
		assertThat(userService.loadUserByUsername("https://www.google.com/accounts/o8/id?id=MPtOaenBIk5yzW9n7n9")
				.getUsername()).isEqualTo("https://www.google.com/accounts/o8/id?id=MPtOaenBIk5yzW9n7n9");
	}

	@Test
	public void disabledAndEmbeddedFlagsAreSupported() {
		// @formatter:off
		setContext("<user-service id='service'>"
				+ "    <user name='joe' password='joespassword' authorities='ROLE_A' locked='true'/>"
				+ "    <user name='Bob' password='bobspassword' authorities='ROLE_A' disabled='true'/>"
				+ "</user-service>");
		// @formatter:on
		UserDetailsService userService = (UserDetailsService) this.appContext.getBean("service");
		UserDetails joe = userService.loadUserByUsername("joe");
		assertThat(joe.isAccountNonLocked()).isFalse();
		// Check case-sensitive lookup SEC-1432
		UserDetails bob = userService.loadUserByUsername("Bob");
		assertThat(bob.isEnabled()).isFalse();
	}

	@Test
	public void userWithBothPropertiesAndEmbeddedUsersThrowsException() {
		assertThatExceptionOfType(FatalBeanException.class).isThrownBy(() ->
		// @formatter:off
			setContext("<user-service id='service' properties='doesntmatter.props'>"
					+ "    <user name='joe' password='joespassword' authorities='ROLE_A'/>"
					+ "</user-service>")
		// @formatter:on
		);
	}

	@Test
	public void multipleTopLevelUseWithoutIdThrowsException() {
		assertThatExceptionOfType(FatalBeanException.class).isThrownBy(() -> setContext(
				"<user-service properties='classpath:org/springframework/security/config/users.properties'/>"
						+ "<user-service properties='classpath:org/springframework/security/config/users.properties'/>"));
	}

	@Test
	public void userServiceWithMissingPropertiesFileThrowsException() {
		assertThatExceptionOfType(FatalBeanException.class).isThrownBy(
				() -> setContext("<user-service id='service' properties='classpath:doesntexist.properties'/>"));
	}

	private void setContext(String context) {
		this.appContext = new InMemoryXmlApplicationContext(context);
	}

}
