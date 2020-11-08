package org.springframework.security.config.web.servlet

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.web.servlet.session.SessionConcurrencyDsl
import org.springframework.security.config.web.servlet.session.SessionFixationDsl
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import org.springframework.security.web.session.InvalidSessionStrategy

/**
 * A Kotlin DSL to configure [HttpSecurity] session management using idiomatic
 * Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 */
@SecurityMarker
class SessionManagementDsl {
    var invalidSessionUrl: String? = null
    var invalidSessionStrategy: InvalidSessionStrategy? = null
    var sessionAuthenticationErrorUrl: String? = null
    var sessionAuthenticationFailureHandler: AuthenticationFailureHandler? = null
    var enableSessionUrlRewriting: Boolean? = null
    var sessionCreationPolicy: SessionCreationPolicy? = null
    var sessionAuthenticationStrategy: SessionAuthenticationStrategy? = null
    private var sessionFixation: ((SessionManagementConfigurer<HttpSecurity>.SessionFixationConfigurer) -> Unit)? = null
    private var sessionConcurrency: ((SessionManagementConfigurer<HttpSecurity>.ConcurrencyControlConfigurer) -> Unit)? = null

    /**
     * Enables session fixation protection.
     *
     * Example:
     *
     * ```
     * @EnableWebSecurity
     * class SecurityConfig : WebSecurityConfigurerAdapter() {
     *
     *  override fun configure(http: HttpSecurity) {
     *      httpSecurity(http) {
     *          sessionManagement {
     *              sessionFixation { }
     *          }
     *      }
     *  }
     * }
     * ```
     *
     * @param sessionFixationConfig custom configurations to configure session fixation
     * protection
     * @see [SessionFixationDsl]
     */
    fun sessionFixation(sessionFixationConfig: SessionFixationDsl.() -> Unit) {
        this.sessionFixation = SessionFixationDsl().apply(sessionFixationConfig).get()
    }

    /**
     * Controls the behaviour of multiple sessions for a user.
     *
     * Example:
     *
     * ```
     * @EnableWebSecurity
     * class SecurityConfig : WebSecurityConfigurerAdapter() {
     *
     *  override fun configure(http: HttpSecurity) {
     *      httpSecurity(http) {
     *          sessionManagement {
     *              sessionConcurrency {
     *                  maximumSessions = 1
     *                  maxSessionsPreventsLogin = true
     *              }
     *          }
     *      }
     *  }
     * }
     * ```
     *
     * @param sessionConcurrencyConfig custom configurations to configure concurrency
     * control
     * @see [SessionConcurrencyDsl]
     */
    fun sessionConcurrency(sessionConcurrencyConfig: SessionConcurrencyDsl.() -> Unit) {
        this.sessionConcurrency = SessionConcurrencyDsl().apply(sessionConcurrencyConfig).get()
    }

    internal fun get(): (SessionManagementConfigurer<HttpSecurity>) -> Unit {
        return { sessionManagement ->
            invalidSessionUrl?.also { sessionManagement.invalidSessionUrl(invalidSessionUrl) }
            invalidSessionStrategy?.also { sessionManagement.invalidSessionStrategy(invalidSessionStrategy) }
            sessionAuthenticationErrorUrl?.also { sessionManagement.sessionAuthenticationErrorUrl(sessionAuthenticationErrorUrl) }
            sessionAuthenticationFailureHandler?.also { sessionManagement.sessionAuthenticationFailureHandler(sessionAuthenticationFailureHandler) }
            enableSessionUrlRewriting?.also { sessionManagement.enableSessionUrlRewriting(enableSessionUrlRewriting!!) }
            sessionCreationPolicy?.also { sessionManagement.sessionCreationPolicy(sessionCreationPolicy) }
            sessionAuthenticationStrategy?.also { sessionManagement.sessionAuthenticationStrategy(sessionAuthenticationStrategy) }
            sessionFixation?.also { sessionManagement.sessionFixation(sessionFixation) }
            sessionConcurrency?.also { sessionManagement.sessionConcurrency(sessionConcurrency) }
        }
    }
}

