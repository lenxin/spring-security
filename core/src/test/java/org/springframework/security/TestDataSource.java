package org.springframework.security;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * A Datasource bean which starts an in-memory HSQL database with the supplied name and
 * shuts down the database when the application context it is defined in is closed.
 *
 * @author Luke Taylor
 */
public class TestDataSource extends DriverManagerDataSource implements DisposableBean {

	String name;

	public TestDataSource(String databaseName) {
		this.name = databaseName;
		System.out.println("Creating database: " + this.name);
		setDriverClassName("org.hsqldb.jdbcDriver");
		setUrl("jdbc:hsqldb:mem:" + databaseName);
		setUsername("sa");
		setPassword("");
	}

	@Override
	public void destroy() {
		System.out.println("Shutting down database: " + this.name);
		new JdbcTemplate(this).execute("SHUTDOWN");
	}

}
