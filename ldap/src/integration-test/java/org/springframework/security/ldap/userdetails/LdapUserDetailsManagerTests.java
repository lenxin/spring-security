package org.springframework.security.ldap.userdetails;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.ApacheDsContainerConfig;
import org.springframework.security.ldap.DefaultLdapUsernameToDnMapper;
import org.springframework.security.ldap.SpringSecurityLdapTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Luke Taylor
 * @author Eddú Meléndez
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApacheDsContainerConfig.class)
public class LdapUserDetailsManagerTests {

	@Autowired
	private ContextSource contextSource;

	private static final List<GrantedAuthority> TEST_AUTHORITIES = AuthorityUtils.createAuthorityList("ROLE_CLOWNS",
			"ROLE_ACROBATS");

	private LdapUserDetailsManager mgr;

	private SpringSecurityLdapTemplate template;

	@Before
	public void setUp() {
		this.mgr = new LdapUserDetailsManager(this.contextSource);
		this.template = new SpringSecurityLdapTemplate(this.contextSource);
		DirContextAdapter ctx = new DirContextAdapter();

		ctx.setAttributeValue("objectclass", "organizationalUnit");
		ctx.setAttributeValue("ou", "test people");
		this.template.bind("ou=test people", ctx, null);

		ctx.setAttributeValue("ou", "testgroups");
		this.template.bind("ou=testgroups", ctx, null);

		DirContextAdapter group = new DirContextAdapter();

		group.setAttributeValue("objectclass", "groupOfNames");
		group.setAttributeValue("cn", "clowns");
		group.setAttributeValue("member", "cn=nobody,ou=test people,dc=springframework,dc=org");
		this.template.bind("cn=clowns,ou=testgroups", group, null);

		group.setAttributeValue("cn", "acrobats");
		this.template.bind("cn=acrobats,ou=testgroups", group, null);

		this.mgr.setUsernameMapper(new DefaultLdapUsernameToDnMapper("ou=test people", "uid"));
		this.mgr.setGroupSearchBase("ou=testgroups");
		this.mgr.setGroupRoleAttributeName("cn");
		this.mgr.setGroupMemberAttributeName("member");
		this.mgr.setUserDetailsMapper(new PersonContextMapper());
	}

	@After
	public void onTearDown() {
		// Iterator people = template.list("ou=testpeople").iterator();

		// DirContext rootCtx = new DirContextAdapter(new
		// DistinguishedName(getInitialCtxFactory().getRootDn()));
		//
		// while(people.hasNext()) {
		// template.unbind((String) people.next() + ",ou=testpeople");
		// }

		this.template.unbind("ou=test people", true);
		this.template.unbind("ou=testgroups", true);

		SecurityContextHolder.clearContext();
	}

	@Test
	public void testLoadUserByUsernameReturnsCorrectData() {
		this.mgr.setUsernameMapper(new DefaultLdapUsernameToDnMapper("ou=people", "uid"));
		this.mgr.setGroupSearchBase("ou=groups");
		LdapUserDetails bob = (LdapUserDetails) this.mgr.loadUserByUsername("bob");
		assertThat(bob.getUsername()).isEqualTo("bob");
		assertThat(bob.getDn()).isEqualTo("uid=bob,ou=people,dc=springframework,dc=org");
		assertThat(bob.getPassword()).isEqualTo("bobspassword");

		assertThat(bob.getAuthorities()).hasSize(1);
	}

	@Test
	public void testLoadingInvalidUsernameThrowsUsernameNotFoundException() {
		assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> this.mgr.loadUserByUsername("jim"));
	}

	@Test
	public void testUserExistsReturnsTrueForValidUser() {
		this.mgr.setUsernameMapper(new DefaultLdapUsernameToDnMapper("ou=people", "uid"));
		assertThat(this.mgr.userExists("bob")).isTrue();
	}

	@Test
	public void testUserExistsReturnsFalseForInValidUser() {
		assertThat(this.mgr.userExists("jim")).isFalse();
	}

	@Test
	public void testCreateNewUserSucceeds() {
		InetOrgPerson.Essence p = new InetOrgPerson.Essence();
		p.setCarLicense("XXX");
		p.setCn(new String[] { "Joe Smeth" });
		p.setDepartmentNumber("5679");
		p.setDescription("Some description");
		p.setDn("whocares");
		p.setEmployeeNumber("E781");
		p.setInitials("J");
		p.setMail("joe@smeth.com");
		p.setMobile("+44776542911");
		p.setOu("Joes Unit");
		p.setO("Organization");
		p.setRoomNumber("500X");
		p.setSn("Smeth");
		p.setUid("joe");

		p.setAuthorities(TEST_AUTHORITIES);

		this.mgr.createUser(p.createUserDetails());
	}

	@Test
	public void testDeleteUserSucceeds() {
		InetOrgPerson.Essence p = new InetOrgPerson.Essence();
		p.setDn("whocares");
		p.setCn(new String[] { "Don Smeth" });
		p.setSn("Smeth");
		p.setUid("don");
		p.setAuthorities(TEST_AUTHORITIES);

		this.mgr.createUser(p.createUserDetails());
		this.mgr.setUserDetailsMapper(new InetOrgPersonContextMapper());

		InetOrgPerson don = (InetOrgPerson) this.mgr.loadUserByUsername("don");

		assertThat(don.getAuthorities()).hasSize(2);

		this.mgr.deleteUser("don");
		assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> this.mgr.loadUserByUsername("don"));

		// Check that no authorities are left
		assertThat(this.mgr.getUserAuthorities(this.mgr.usernameMapper.buildDn("don"), "don")).hasSize(0);
	}

	@Test
	public void testPasswordChangeWithCorrectOldPasswordSucceeds() {
		InetOrgPerson.Essence p = new InetOrgPerson.Essence();
		p.setDn("whocares");
		p.setCn(new String[] { "John Yossarian" });
		p.setSn("Yossarian");
		p.setUid("johnyossarian");
		p.setPassword("yossarianspassword");
		p.setAuthorities(TEST_AUTHORITIES);

		this.mgr.createUser(p.createUserDetails());

		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("johnyossarian", "yossarianspassword", TEST_AUTHORITIES));

		this.mgr.changePassword("yossarianspassword", "yossariansnewpassword");

		assertThat(this.template.compare("uid=johnyossarian,ou=test people", "userPassword", "yossariansnewpassword"))
				.isTrue();
	}

	@Test
	public void testPasswordChangeWithWrongOldPasswordFails() {
		InetOrgPerson.Essence p = new InetOrgPerson.Essence();
		p.setDn("whocares");
		p.setCn(new String[] { "John Yossarian" });
		p.setSn("Yossarian");
		p.setUid("johnyossarian");
		p.setPassword("yossarianspassword");
		p.setAuthorities(TEST_AUTHORITIES);
		this.mgr.createUser(p.createUserDetails());
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("johnyossarian", "yossarianspassword", TEST_AUTHORITIES));
		assertThatExceptionOfType(BadCredentialsException.class)
				.isThrownBy(() -> this.mgr.changePassword("wrongpassword", "yossariansnewpassword"));
	}

}
