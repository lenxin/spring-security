package org.springframework.security.acls.jdbc;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.util.FileCopyUtils;

/**
 * Helper class to initialize the database for BasicLookupStrategyTests.
 *
 * @author Andrei Stefan
 * @author Paul Wheeler
 */
public class BasicLookupStrategyTestsDbHelper {

	private static final String ACL_SCHEMA_SQL_FILE = "createAclSchema.sql";

	private static final String ACL_SCHEMA_SQL_FILE_WITH_ACL_CLASS_ID = "createAclSchemaWithAclClassIdType.sql";

	private SingleConnectionDataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	private boolean withAclClassIdType;

	public BasicLookupStrategyTestsDbHelper() {
	}

	public BasicLookupStrategyTestsDbHelper(boolean withAclClassIdType) {
		this.withAclClassIdType = withAclClassIdType;
	}

	public void createDatabase() throws Exception {
		// Use a different connection url so the tests can run in parallel
		String connectionUrl;
		String sqlClassPathResource;
		if (!this.withAclClassIdType) {
			connectionUrl = "jdbc:hsqldb:mem:lookupstrategytest";
			sqlClassPathResource = ACL_SCHEMA_SQL_FILE;
		}
		else {
			connectionUrl = "jdbc:hsqldb:mem:lookupstrategytestWithAclClassIdType";
			sqlClassPathResource = ACL_SCHEMA_SQL_FILE_WITH_ACL_CLASS_ID;
		}
		this.dataSource = new SingleConnectionDataSource(connectionUrl, "sa", "", true);
		this.dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
		Resource resource = new ClassPathResource(sqlClassPathResource);
		String sql = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
		this.jdbcTemplate.execute(sql);
	}

	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public SingleConnectionDataSource getDataSource() {
		return this.dataSource;
	}

}
