package org.springframework.security.config.web.servlet

import org.springframework.security.authentication.AuthenticationDetailsSource
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.http.HttpServletRequest

/**
 * A Kotlin DSL to configure [HttpSecurity] basic authentication using idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 * @property realmName the HTTP Basic realm to use. If [authenticationEntryPoint]
 * has been invoked, invoking this method will result in an error.
 * @property authenticationEntryPoint the [AuthenticationEntryPoint] to be populated on
 * [BasicAuthenticationFilter] in the event that authentication fails.
 * @property authenticationDetailsSource the custom [AuthenticationDetailsSource] to use for
 * basic authentication.
 */
@SecurityMarker
class HttpBasicDsl {
    var realmName: String? = null
    var authenticationEntryPoint: AuthenticationEntryPoint? = null
    var authenticationDetailsSource: AuthenticationDetailsSource<HttpServletRequest, *>? = null

    private var disabled = false

    /**
     * Disables HTTP basic authentication
     */
    fun disable() {
        disabled = true
    }

    internal fun get(): (HttpBasicConfigurer<HttpSecurity>) -> Unit {
        return { httpBasic ->
            realmName?.also { httpBasic.realmName(realmName) }
            authenticationEntryPoint?.also { httpBasic.authenticationEntryPoint(authenticationEntryPoint) }
            authenticationDetailsSource?.also { httpBasic.authenticationDetailsSource(authenticationDetailsSource) }
            if (disabled) {
                httpBasic.disable()
            }
        }
    }
}
