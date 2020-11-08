package org.springframework.security.config.web.server

import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.security.web.server.csrf.CsrfWebFilter
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher

/**
 * A Kotlin DSL to configure [ServerHttpSecurity] CSRF protection using idiomatic
 * Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.4
 * @property accessDeniedHandler the [ServerAccessDeniedHandler] used when a CSRF token is invalid.
 * @property csrfTokenRepository the [ServerCsrfTokenRepository] used to persist the CSRF token.
 * @property requireCsrfProtectionMatcher the [ServerWebExchangeMatcher] used to determine when CSRF protection
 * is enabled.
 * @property tokenFromMultipartDataEnabled if true, the [CsrfWebFilter] should try to resolve the actual CSRF
 * token from the body of multipart data requests.
 */
@ServerSecurityMarker
class ServerCsrfDsl {
    var accessDeniedHandler: ServerAccessDeniedHandler? = null
    var csrfTokenRepository: ServerCsrfTokenRepository? = null
    var requireCsrfProtectionMatcher: ServerWebExchangeMatcher? = null
    var tokenFromMultipartDataEnabled: Boolean? = null

    private var disabled = false

    /**
     * Disables CSRF protection
     */
    fun disable() {
        disabled = true
    }

    internal fun get(): (ServerHttpSecurity.CsrfSpec) -> Unit {
        return { csrf ->
            accessDeniedHandler?.also { csrf.accessDeniedHandler(accessDeniedHandler) }
            csrfTokenRepository?.also { csrf.csrfTokenRepository(csrfTokenRepository) }
            requireCsrfProtectionMatcher?.also { csrf.requireCsrfProtectionMatcher(requireCsrfProtectionMatcher) }
            tokenFromMultipartDataEnabled?.also { csrf.tokenFromMultipartDataEnabled(tokenFromMultipartDataEnabled!!) }
            if (disabled) {
                csrf.disable()
            }
        }
    }
}
