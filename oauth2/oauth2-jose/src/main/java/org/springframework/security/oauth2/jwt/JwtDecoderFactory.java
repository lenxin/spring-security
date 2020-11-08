package org.springframework.security.oauth2.jwt;

/**
 * A factory for {@link JwtDecoder}(s). This factory should be supplied with a type that
 * provides contextual information used to create a specific {@code JwtDecoder}.
 *
 * @author Joe Grandja
 * @since 5.2
 * @see JwtDecoder
 * @param <C> The type that provides contextual information used to create a specific
 * {@code JwtDecoder}.
 */
@FunctionalInterface
public interface JwtDecoderFactory<C> {

	/**
	 * Creates a {@code JwtDecoder} using the supplied "contextual" type.
	 * @param context the type that provides contextual information
	 * @return a {@link JwtDecoder}
	 */
	JwtDecoder createDecoder(C context);

}
