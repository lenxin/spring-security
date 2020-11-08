package org.springframework.security.config.web.server

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import reactor.core.publisher.Mono
import java.security.interfaces.RSAPublicKey

/**
 * A Kotlin DSL to configure [ServerHttpSecurity] JWT Resource Server support using idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.4
 * @property authenticationManager the [ReactiveAuthenticationManager] used to determine if the provided
 * [Authentication] can be authenticated.
 * @property jwtAuthenticationConverter the [Converter] to use for converting a [Jwt] into an
 * [AbstractAuthenticationToken].
 * @property jwtDecoder the [ReactiveJwtDecoder] to use.
 * @property publicKey configures a [ReactiveJwtDecoder] that leverages the provided [RSAPublicKey]
 * @property jwkSetUri configures a [ReactiveJwtDecoder] using a
 * <a target="_blank" href="https://tools.ietf.org/html/rfc7517">JSON Web Key (JWK)</a> URL
 */
@ServerSecurityMarker
class ServerJwtDsl {
    private var _jwtDecoder: ReactiveJwtDecoder? = null
    private var _publicKey: RSAPublicKey? = null
    private var _jwkSetUri: String? = null

    var authenticationManager: ReactiveAuthenticationManager? = null
    var jwtAuthenticationConverter: Converter<Jwt, out Mono<out AbstractAuthenticationToken>>? = null

    var jwtDecoder: ReactiveJwtDecoder?
        get() = _jwtDecoder
        set(value) {
            _jwtDecoder = value
            _publicKey = null
            _jwkSetUri = null
        }
    var publicKey: RSAPublicKey?
        get() = _publicKey
        set(value) {
            _publicKey = value
            _jwtDecoder = null
            _jwkSetUri = null
        }
    var jwkSetUri: String?
        get() = _jwkSetUri
        set(value) {
            _jwkSetUri = value
            _jwtDecoder = null
            _publicKey = null
        }

    internal fun get(): (ServerHttpSecurity.OAuth2ResourceServerSpec.JwtSpec) -> Unit {
        return { jwt ->
            authenticationManager?.also { jwt.authenticationManager(authenticationManager) }
            jwtAuthenticationConverter?.also { jwt.jwtAuthenticationConverter(jwtAuthenticationConverter) }
            jwtDecoder?.also { jwt.jwtDecoder(jwtDecoder) }
            publicKey?.also { jwt.publicKey(publicKey) }
            jwkSetUri?.also { jwt.jwkSetUri(jwkSetUri) }
        }
    }
}
