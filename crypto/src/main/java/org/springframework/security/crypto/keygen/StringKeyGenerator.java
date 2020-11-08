package org.springframework.security.crypto.keygen;

/**
 * A generator for unique string keys.
 *
 * @author Keith Donald
 */
public interface StringKeyGenerator {

	String generateKey();

}
