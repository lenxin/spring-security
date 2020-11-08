package org.springframework.security.config.web.servlet.oauth2.resourceserver

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder

/**
 * A Kotlin DSL to configure JWT Resource Server Support using idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 * @property jwtAuthenticationConverter the [Converter] to use for converting a [Jwt] into
 * an [AbstractAuthenticationToken].
 * @property jwtDecoder the [JwtDecoder] to use.
 * @property jwkSetUri configures a [JwtDecoder] using a
 * <a target="_blank" href="https://tools.ietf.org/html/rfc7517">JSON Web Key (JWK)</a> URL
 */
@OAuth2ResourceServerSecurityMarker
class JwtDsl {
    private var _jwtDecoder: JwtDecoder? = null
    private var _jwkSetUri: String? = null

    var jwtAuthenticationConverter: Converter<Jwt, out AbstractAuthenticationToken>? = null
    var jwtDecoder: JwtDecoder?
        get() = _jwtDecoder
        set(value) {
            _jwtDecoder = value
            _jwkSetUri = null
        }
    var jwkSetUri: String?
        get() = _jwkSetUri
        set(value) {
            _jwkSetUri = value
            _jwtDecoder = null
        }

    internal fun get(): (OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer) -> Unit {
        return { jwt ->
            jwtAuthenticationConverter?.also { jwt.jwtAuthenticationConverter(jwtAuthenticationConverter) }
            jwtDecoder?.also { jwt.decoder(jwtDecoder) }
            jwkSetUri?.also { jwt.jwkSetUri(jwkSetUri) }
        }
    }
}
