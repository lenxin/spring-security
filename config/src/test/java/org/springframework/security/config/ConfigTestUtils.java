package org.springframework.security.config;

public abstract class ConfigTestUtils {

	// @formatter:off
	public static final String AUTH_PROVIDER_XML = "<authentication-manager alias='authManager'>"
			+ "    <authentication-provider>"
			+ "        <user-service id='us'>"
			+ "            <user name='bob' password='{noop}bobspassword' authorities='ROLE_A,ROLE_B' />"
			+ "            <user name='bill' password='{noop}billspassword' authorities='ROLE_A,ROLE_B,AUTH_OTHER' />"
			+ "            <user name='admin' password='{noop}password' authorities='ROLE_ADMIN,ROLE_USER' />"
			+ "            <user name='user' password='{noop}password' authorities='ROLE_USER' />"
			+ "        </user-service>"
			+ "    </authentication-provider>"
			+ "</authentication-manager>";
	// @formatter:on

}
