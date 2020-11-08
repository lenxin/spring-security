package org.springframework.security.ldap.userdetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.ApacheDsContainerConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hanik
 * @author Eddú Meléndez
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApacheDsContainerConfig.class)
public class NestedLdapAuthoritiesPopulatorTests {

	@Autowired
	private ContextSource contextSource;

	private NestedLdapAuthoritiesPopulator populator;

	private LdapAuthority javaDevelopers;

	private LdapAuthority groovyDevelopers;

	private LdapAuthority scalaDevelopers;

	private LdapAuthority closureDevelopers;

	private LdapAuthority jDevelopers;

	private LdapAuthority circularJavaDevelopers;

	@Before
	public void setUp() {
		this.populator = new NestedLdapAuthoritiesPopulator(this.contextSource, "ou=jdeveloper");
		this.populator.setGroupSearchFilter("(member={0})");
		this.populator.setIgnorePartialResultException(false);
		this.populator.setRolePrefix("");
		this.populator.setSearchSubtree(true);
		this.populator.setConvertToUpperCase(false);
		this.jDevelopers = new LdapAuthority("j-developers", "cn=j-developers,ou=jdeveloper,dc=springframework,dc=org");
		this.javaDevelopers = new LdapAuthority("java-developers",
				"cn=java-developers,ou=jdeveloper,dc=springframework,dc=org");
		this.groovyDevelopers = new LdapAuthority("groovy-developers",
				"cn=groovy-developers,ou=jdeveloper,dc=springframework,dc=org");
		this.scalaDevelopers = new LdapAuthority("scala-developers",
				"cn=scala-developers,ou=jdeveloper,dc=springframework,dc=org");
		this.closureDevelopers = new LdapAuthority("closure-developers",
				"cn=closure-developers,ou=jdeveloper,dc=springframework,dc=org");
		this.circularJavaDevelopers = new LdapAuthority("circular-java-developers",
				"cn=circular-java-developers,ou=jdeveloper,dc=springframework,dc=org");
	}

	@Test
	public void testScalaDudeJDevelopersAuthorities() {
		DirContextAdapter ctx = new DirContextAdapter("uid=scaladude,ou=people,dc=springframework,dc=org");
		Collection<GrantedAuthority> authorities = this.populator.getGrantedAuthorities(ctx, "scaladude");
		assertThat(authorities).hasSize(5);
		assertThat(authorities).isEqualTo(Arrays.asList(this.javaDevelopers, this.circularJavaDevelopers,
				this.scalaDevelopers, this.groovyDevelopers, this.jDevelopers));
	}

	@Test
	public void testJavaDudeJDevelopersAuthorities() {
		DirContextAdapter ctx = new DirContextAdapter("uid=javadude,ou=people,dc=springframework,dc=org");
		Collection<GrantedAuthority> authorities = this.populator.getGrantedAuthorities(ctx, "javadude");
		assertThat(authorities).hasSize(4);
		assertThat(authorities).contains(this.javaDevelopers);
	}

	@Test
	public void testScalaDudeJDevelopersAuthoritiesWithSearchLimit() {
		this.populator.setMaxSearchDepth(1);
		DirContextAdapter ctx = new DirContextAdapter("uid=scaladude,ou=people,dc=springframework,dc=org");
		Collection<GrantedAuthority> authorities = this.populator.getGrantedAuthorities(ctx, "scaladude");
		assertThat(authorities).hasSize(1);
		assertThat(authorities).isEqualTo(Arrays.asList(this.scalaDevelopers));
	}

	@Test
	public void testGroovyDudeJDevelopersAuthorities() {
		DirContextAdapter ctx = new DirContextAdapter("uid=groovydude,ou=people,dc=springframework,dc=org");
		Collection<GrantedAuthority> authorities = this.populator.getGrantedAuthorities(ctx, "groovydude");
		assertThat(authorities).hasSize(4);
		assertThat(authorities).isEqualTo(Arrays.asList(this.javaDevelopers, this.circularJavaDevelopers,
				this.groovyDevelopers, this.jDevelopers));
	}

	@Test
	public void testClosureDudeJDevelopersWithMembershipAsAttributeValues() {
		this.populator.setAttributeNames(new HashSet(Arrays.asList("member")));

		DirContextAdapter ctx = new DirContextAdapter("uid=closuredude,ou=people,dc=springframework,dc=org");
		Collection<GrantedAuthority> authorities = this.populator.getGrantedAuthorities(ctx, "closuredude");
		assertThat(authorities).hasSize(5);
		assertThat(authorities).isEqualTo(Arrays.asList(this.javaDevelopers, this.circularJavaDevelopers,
				this.closureDevelopers, this.groovyDevelopers, this.jDevelopers));

		LdapAuthority[] ldapAuthorities = authorities.toArray(new LdapAuthority[0]);
		assertThat(ldapAuthorities).hasSize(5);
		// groovy-developers group
		assertThat(ldapAuthorities[0].getAttributes().containsKey("member")).isTrue();
		assertThat(ldapAuthorities[0].getAttributes().get("member")).isNotNull();
		assertThat(ldapAuthorities[0].getAttributes().get("member")).hasSize(3);
		assertThat(ldapAuthorities[0].getFirstAttributeValue("member"))
				.isEqualTo("cn=groovy-developers,ou=jdeveloper,dc=springframework,dc=org");

		// java group
		assertThat(ldapAuthorities[1].getAttributes().containsKey("member")).isTrue();
		assertThat(ldapAuthorities[1].getAttributes().get("member")).isNotNull();
		assertThat(ldapAuthorities[1].getAttributes().get("member")).hasSize(3);
		assertThat(this.groovyDevelopers.getDn()).isEqualTo(ldapAuthorities[1].getFirstAttributeValue("member"));
		assertThat(ldapAuthorities[2].getAttributes().get("member"))
				.contains("uid=closuredude,ou=people,dc=springframework,dc=org");

		// test non existent attribute
		assertThat(ldapAuthorities[2].getFirstAttributeValue("test")).isNull();
		assertThat(ldapAuthorities[2].getAttributeValues("test")).isNotNull();
		assertThat(ldapAuthorities[2].getAttributeValues("test")).isEmpty();
		// test role name
		assertThat(ldapAuthorities[3].getAuthority()).isEqualTo(this.groovyDevelopers.getAuthority());
	}

}
