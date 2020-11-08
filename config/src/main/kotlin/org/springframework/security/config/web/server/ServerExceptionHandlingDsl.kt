package org.springframework.security.config.web.server

import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler

/**
 * A Kotlin DSL to configure [ServerHttpSecurity] exception handling using idiomatic Kotlin
 * code.
 *
 * @author Eleftheria Stein
 * @since 5.4
 * @property authenticationEntryPoint the [ServerAuthenticationEntryPoint] to use when
 * the application request authentication
 * @property accessDeniedHandler the [ServerAccessDeniedHandler] to use when an
 * authenticated user does not hold a required authority
 */
@ServerSecurityMarker
class ServerExceptionHandlingDsl {
    var authenticationEntryPoint: ServerAuthenticationEntryPoint? = null
    var accessDeniedHandler: ServerAccessDeniedHandler? = null

    internal fun get(): (ServerHttpSecurity.ExceptionHandlingSpec) -> Unit {
        return { exceptionHandling ->
            authenticationEntryPoint?.also { exceptionHandling.authenticationEntryPoint(authenticationEntryPoint) }
            accessDeniedHandler?.also { exceptionHandling.accessDeniedHandler(accessDeniedHandler) }
        }
    }
}
