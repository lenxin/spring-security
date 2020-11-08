package org.springframework.security.oauth2.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.List;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.util.Assert;

/**
 * An internal implementation of {@link JWSKeySelector} that always returns the same key
 *
 * @author Josh Cummings
 * @since 5.2
 */
final class SingleKeyJWSKeySelector<C extends SecurityContext> implements JWSKeySelector<C> {

	private final List<Key> keySet;

	private final JWSAlgorithm expectedJwsAlgorithm;

	SingleKeyJWSKeySelector(JWSAlgorithm expectedJwsAlgorithm, Key key) {
		Assert.notNull(expectedJwsAlgorithm, "expectedJwsAlgorithm cannot be null");
		Assert.notNull(key, "key cannot be null");
		this.keySet = Arrays.asList(key);
		this.expectedJwsAlgorithm = expectedJwsAlgorithm;
	}

	@Override
	public List<? extends Key> selectJWSKeys(JWSHeader header, C context) {
		if (!this.expectedJwsAlgorithm.equals(header.getAlgorithm())) {
			throw new BadJwtException("Unsupported algorithm of " + header.getAlgorithm());
		}
		return this.keySet;
	}

}
