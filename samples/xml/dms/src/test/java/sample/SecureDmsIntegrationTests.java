package sample;

import org.junit.Test;

import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic integration test for DMS sample when security has been added.
 *
 * @author Ben Alex
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext-dms-shared.xml",
		"classpath:applicationContext-dms-secure.xml" })
public class SecureDmsIntegrationTests extends DmsIntegrationTests {

	@Override
	@Test
	public void testBasePopulation() {
		assertThat(this.jdbcTemplate.queryForObject("select count(id) from DIRECTORY",
				Integer.class)).isEqualTo(9);
		assertThat(this.jdbcTemplate.queryForObject("select count(id) from FILE",
				Integer.class)).isEqualTo(90);
		assertThat(this.jdbcTemplate.queryForObject("select count(id) from ACL_SID",
				Integer.class)).isEqualTo(4); // 3 users + 1 role
		assertThat(this.jdbcTemplate.queryForObject("select count(id) from ACL_CLASS",
				Integer.class)).isEqualTo(2); // Directory
		// and
		// File
		assertThat(this.jdbcTemplate.queryForObject(
				"select count(id) from ACL_OBJECT_IDENTITY", Integer.class))
						.isEqualTo(100);
		assertThat(this.jdbcTemplate.queryForObject("select count(id) from ACL_ENTRY",
				Integer.class)).isEqualTo(115);
	}

	@Override
	public void testMarissaRetrieval() {
		process("rod", "koala", true);
	}

	@Override
	public void testScottRetrieval() {
		process("scott", "wombat", true);
	}

	@Override
	public void testDianneRetrieval() {
		process("dianne", "emu", true);
	}
}
