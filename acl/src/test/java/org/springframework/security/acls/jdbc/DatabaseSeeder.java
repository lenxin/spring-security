package org.springframework.security.acls.jdbc;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

/**
 * Seeds the database for {@link JdbcMutableAclServiceTests}.
 *
 * @author Ben Alex
 */
public class DatabaseSeeder {

	public DatabaseSeeder(DataSource dataSource, Resource resource) throws IOException {
		Assert.notNull(dataSource, "dataSource required");
		Assert.notNull(resource, "resource required");
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
		template.execute(sql);
	}

}
