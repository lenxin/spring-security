package org.springframework.security.core;

import java.io.DataInputStream;
import java.io.InputStream;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 *
 */
public class JavaVersionTests {

	private static final int JDK8_CLASS_VERSION = 52;

	@Test
	public void authenticationCorrectJdkCompatibility() throws Exception {
		assertClassVersion(Authentication.class);
	}

	private void assertClassVersion(Class<?> clazz) throws Exception {
		String classResourceName = clazz.getName().replaceAll("\\.", "/") + ".class";
		try (InputStream input = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(classResourceName)) {
			DataInputStream data = new DataInputStream(input);
			data.readInt();
			data.readShort(); // minor
			int major = data.readShort();
			assertThat(major).isEqualTo(JDK8_CLASS_VERSION);
		}
	}

}
