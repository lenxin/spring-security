package org.springframework.security.openid;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Luke Taylor
 * @since 3.1
 * @deprecated The OpenID 1.0 and 2.0 protocols have been deprecated and users are
 * <a href="https://openid.net/specs/openid-connect-migration-1_0.html">encouraged to
 * migrate</a> to <a href="https://openid.net/connect/">OpenID Connect</a>, which is
 * supported by <code>spring-security-oauth2</code>.
 */
@Deprecated
public class RegexBasedAxFetchListFactory implements AxFetchListFactory {

	private final Map<Pattern, List<OpenIDAttribute>> idToAttributes;

	/**
	 * @param regexMap map of regular-expressions (matching the identifier) to attributes
	 * which should be fetched for that pattern.
	 */
	public RegexBasedAxFetchListFactory(Map<String, List<OpenIDAttribute>> regexMap) {
		this.idToAttributes = new LinkedHashMap<>();
		for (Map.Entry<String, List<OpenIDAttribute>> entry : regexMap.entrySet()) {
			this.idToAttributes.put(Pattern.compile(entry.getKey()), entry.getValue());
		}
	}

	/**
	 * Iterates through the patterns stored in the map and returns the list of attributes
	 * defined for the first match. If no match is found, returns an empty list.
	 */
	@Override
	public List<OpenIDAttribute> createAttributeList(String identifier) {
		for (Map.Entry<Pattern, List<OpenIDAttribute>> entry : this.idToAttributes.entrySet()) {
			if (entry.getKey().matcher(identifier).matches()) {
				return entry.getValue();
			}
		}
		return Collections.emptyList();
	}

}
