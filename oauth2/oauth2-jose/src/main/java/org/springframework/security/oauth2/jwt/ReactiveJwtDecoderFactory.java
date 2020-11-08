package org.springframework.security.oauth2.jwt;

/**
 * A factory for {@link ReactiveJwtDecoder}(s). This factory should be supplied with a
 * type that provides contextual information used to create a specific
 * {@code ReactiveJwtDecoder}.
 *
 * @author Joe Grandja
 * @since 5.2
 * @see ReactiveJwtDecoder
 * @param <C> The type that provides contextual information used to create a specific
 * {@code ReactiveJwtDecoder}.
 */
@FunctionalInterface
public interface ReactiveJwtDecoderFactory<C> {

	/**
	 * Creates a {@code ReactiveJwtDecoder} using the supplied "contextual" type.
	 * @param context the type that provides contextual information
	 * @return a {@link ReactiveJwtDecoder}
	 */
	ReactiveJwtDecoder createDecoder(C context);

}
