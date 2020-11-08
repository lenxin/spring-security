package org.springframework.security.oauth2.core.converter;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * A {@link Converter} that provides type conversion for claim values.
 *
 * @author Joe Grandja
 * @since 5.2
 * @see Converter
 */
public final class ClaimTypeConverter implements Converter<Map<String, Object>, Map<String, Object>> {

	private final Map<String, Converter<Object, ?>> claimTypeConverters;

	/**
	 * Constructs a {@code ClaimTypeConverter} using the provided parameters.
	 * @param claimTypeConverters a {@link Map} of {@link Converter}(s) keyed by claim
	 * name
	 */
	public ClaimTypeConverter(Map<String, Converter<Object, ?>> claimTypeConverters) {
		Assert.notEmpty(claimTypeConverters, "claimTypeConverters cannot be empty");
		Assert.noNullElements(claimTypeConverters.values().toArray(), "Converter(s) cannot be null");
		this.claimTypeConverters = Collections.unmodifiableMap(new LinkedHashMap<>(claimTypeConverters));
	}

	@Override
	public Map<String, Object> convert(Map<String, Object> claims) {
		if (CollectionUtils.isEmpty(claims)) {
			return claims;
		}
		Map<String, Object> result = new HashMap<>(claims);
		this.claimTypeConverters.forEach((claimName, typeConverter) -> {
			if (claims.containsKey(claimName)) {
				Object claim = claims.get(claimName);
				Object mappedClaim = typeConverter.convert(claim);
				if (mappedClaim != null) {
					result.put(claimName, mappedClaim);
				}
			}
		});
		return result;
	}

}
