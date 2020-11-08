package org.springframework.security.acls.domain;

import org.junit.Before;
import org.junit.Test;

import org.springframework.security.acls.model.Permission;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests classes associated with Permission.
 *
 * @author Ben Alex
 */
public class PermissionTests {

	private DefaultPermissionFactory permissionFactory;

	@Before
	public void createPermissionfactory() {
		this.permissionFactory = new DefaultPermissionFactory();
	}

	@Test
	public void basePermissionTest() {
		Permission p = this.permissionFactory.buildFromName("WRITE");
		assertThat(p).isNotNull();
	}

	@Test
	public void expectedIntegerValues() {
		assertThat(BasePermission.READ.getMask()).isEqualTo(1);
		assertThat(BasePermission.ADMINISTRATION.getMask()).isEqualTo(16);
		assertThat(new CumulativePermission().set(BasePermission.READ).set(BasePermission.WRITE)
				.set(BasePermission.CREATE).getMask()).isEqualTo(7);
		assertThat(new CumulativePermission().set(BasePermission.READ).set(BasePermission.ADMINISTRATION).getMask())
				.isEqualTo(17);
	}

	@Test
	public void fromInteger() {
		Permission permission = this.permissionFactory.buildFromMask(7);
		permission = this.permissionFactory.buildFromMask(4);
	}

	@Test
	public void stringConversion() {
		this.permissionFactory.registerPublicPermissions(SpecialPermission.class);
		assertThat(BasePermission.READ.toString()).isEqualTo("BasePermission[...............................R=1]");
		assertThat(BasePermission.ADMINISTRATION.toString())
				.isEqualTo("BasePermission[...........................A....=16]");
		assertThat(new CumulativePermission().set(BasePermission.READ).toString())
				.isEqualTo("CumulativePermission[...............................R=1]");
		assertThat(
				new CumulativePermission().set(SpecialPermission.ENTER).set(BasePermission.ADMINISTRATION).toString())
						.isEqualTo("CumulativePermission[..........................EA....=48]");
		assertThat(new CumulativePermission().set(BasePermission.ADMINISTRATION).set(BasePermission.READ).toString())
				.isEqualTo("CumulativePermission[...........................A...R=17]");
		assertThat(new CumulativePermission().set(BasePermission.ADMINISTRATION).set(BasePermission.READ)
				.clear(BasePermission.ADMINISTRATION).toString())
						.isEqualTo("CumulativePermission[...............................R=1]");
		assertThat(new CumulativePermission().set(BasePermission.ADMINISTRATION).set(BasePermission.READ)
				.clear(BasePermission.ADMINISTRATION).clear(BasePermission.READ).toString())
						.isEqualTo("CumulativePermission[................................=0]");
	}

}
