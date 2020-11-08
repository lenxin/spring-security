package org.springframework.security.config.web.server

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.preauth.x509.X509PrincipalExtractor

/**
 * A Kotlin DSL to configure [ServerHttpSecurity] X509 based pre authentication using idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.4
 * @property principalExtractor the [X509PrincipalExtractor] used to obtain the principal for use within the framework.
 * @property authenticationManager the [ReactiveAuthenticationManager] used to determine if the provided
 * [Authentication] can be authenticated.
 */
@ServerSecurityMarker
class ServerX509Dsl {
    var principalExtractor: X509PrincipalExtractor? = null
    var authenticationManager: ReactiveAuthenticationManager? = null

    internal fun get(): (ServerHttpSecurity.X509Spec) -> Unit {
        return { x509 ->
            authenticationManager?.also { x509.authenticationManager(authenticationManager) }
            principalExtractor?.also { x509.principalExtractor(principalExtractor) }
        }
    }
}
