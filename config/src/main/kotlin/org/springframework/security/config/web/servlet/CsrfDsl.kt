package org.springframework.security.config.web.servlet

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import org.springframework.security.web.csrf.CsrfTokenRepository
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.http.HttpServletRequest

/**
 * A Kotlin DSL to configure [HttpSecurity] CSRF protection
 * using idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 * @property csrfTokenRepository the [CsrfTokenRepository] to use.
 * @property requireCsrfProtectionMatcher specify the [RequestMatcher] to use for
 * determining when CSRF should be applied.
 * @property sessionAuthenticationStrategy the [SessionAuthenticationStrategy] to use.
 */
@SecurityMarker
class CsrfDsl {
    var csrfTokenRepository: CsrfTokenRepository? = null
    var requireCsrfProtectionMatcher: RequestMatcher? = null
    var sessionAuthenticationStrategy: SessionAuthenticationStrategy? = null

    private var ignoringAntMatchers: Array<out String>? = null
    private var ignoringRequestMatchers: Array<out RequestMatcher>? = null
    private var disabled = false

    /**
     * Allows specifying [HttpServletRequest]s that should not use CSRF Protection
     * even if they match the [requireCsrfProtectionMatcher].
     *
     * @param antMatchers the ANT pattern matchers that should not use CSRF
     * protection
     */
    fun ignoringAntMatchers(vararg antMatchers: String) {
        ignoringAntMatchers = antMatchers
    }

    /**
     * Allows specifying [HttpServletRequest]s that should not use CSRF Protection
     * even if they match the [requireCsrfProtectionMatcher].
     *
     * @param requestMatchers the request matchers that should not use CSRF
     * protection
     */
    fun ignoringRequestMatchers(vararg requestMatchers: RequestMatcher) {
        ignoringRequestMatchers = requestMatchers
    }

    /**
     * Disable CSRF protection
     */
    fun disable() {
        disabled = true
    }

    internal fun get(): (CsrfConfigurer<HttpSecurity>) -> Unit {
        return { csrf ->
            csrfTokenRepository?.also { csrf.csrfTokenRepository(csrfTokenRepository) }
            requireCsrfProtectionMatcher?.also { csrf.requireCsrfProtectionMatcher(requireCsrfProtectionMatcher) }
            sessionAuthenticationStrategy?.also { csrf.sessionAuthenticationStrategy(sessionAuthenticationStrategy) }
            ignoringAntMatchers?.also { csrf.ignoringAntMatchers(*ignoringAntMatchers!!) }
            ignoringRequestMatchers?.also { csrf.ignoringRequestMatchers(*ignoringRequestMatchers!!) }
            if (disabled) {
                csrf.disable()
            }
        }
    }
}
