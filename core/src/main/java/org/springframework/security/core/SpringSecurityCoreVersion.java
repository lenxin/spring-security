package org.springframework.security.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.SpringVersion;

/**
 * Internal class used for checking version compatibility in a deployed application.
 *
 * @author Luke Taylor
 * @author Rob Winch
 */
public final class SpringSecurityCoreVersion {

	private static final String DISABLE_CHECKS = SpringSecurityCoreVersion.class.getName().concat(".DISABLE_CHECKS");

	private static final Log logger = LogFactory.getLog(SpringSecurityCoreVersion.class);

	/**
	 * Global Serialization value for Spring Security classes.
	 *
	 * N.B. Classes are not intended to be serializable between different versions. See
	 * SEC-1709 for why we still need a serial version.
	 */
	public static final long SERIAL_VERSION_UID = 550L;

	static final String MIN_SPRING_VERSION = getSpringVersion();

	static {
		performVersionChecks();
	}

	private SpringSecurityCoreVersion() {
	}

	private static void performVersionChecks() {
		performVersionChecks(MIN_SPRING_VERSION);
	}

	/**
	 * Perform version checks with specific min Spring Version
	 * @param minSpringVersion
	 */
	private static void performVersionChecks(String minSpringVersion) {
		if (minSpringVersion == null) {
			return;
		}
		// Check Spring Compatibility
		String springVersion = SpringVersion.getVersion();
		String version = getVersion();
		if (disableChecks(springVersion, version)) {
			return;
		}
		logger.info("You are running with Spring Security Core " + version);
		if (new ComparableVersion(springVersion).compareTo(new ComparableVersion(minSpringVersion)) < 0) {
			logger.warn("**** You are advised to use Spring " + minSpringVersion
					+ " or later with this version. You are running: " + springVersion);
		}
	}

	public static String getVersion() {
		Package pkg = SpringSecurityCoreVersion.class.getPackage();
		return (pkg != null) ? pkg.getImplementationVersion() : null;
	}

	/**
	 * Disable if springVersion and springSecurityVersion are the same to allow working
	 * with Uber Jars.
	 * @param springVersion
	 * @param springSecurityVersion
	 * @return
	 */
	private static boolean disableChecks(String springVersion, String springSecurityVersion) {
		if (springVersion == null || springVersion.equals(springSecurityVersion)) {
			return true;
		}
		return Boolean.getBoolean(DISABLE_CHECKS);
	}

	/**
	 * Loads the spring version or null if it cannot be found.
	 * @return
	 */
	private static String getSpringVersion() {
		Properties properties = new Properties();
		try (InputStream is = SpringSecurityCoreVersion.class.getClassLoader()
				.getResourceAsStream("META-INF/spring-security.versions")) {
			properties.load(is);
		}
		catch (IOException | NullPointerException ex) {
			return null;
		}
		return properties.getProperty("org.springframework:spring-core");
	}

}
