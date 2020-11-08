package org.springframework.security.config.web.servlet

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter

/**
 * A Kotlin DSL to configure [HttpSecurity] anonymous authentication using idiomatic
 * Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 * @property key the key to identify tokens created for anonymous authentication
 * @property principal the principal for [Authentication] objects of anonymous users
 * @property authorities the [Authentication.getAuthorities] for anonymous users
 * @property authenticationProvider the [AuthenticationProvider] used to validate an
 * anonymous user
 * @property authenticationFilter the [AnonymousAuthenticationFilter] used to populate
 * an anonymous user.
 */
@SecurityMarker
class AnonymousDsl {
    var key: String? = null
    var principal: Any? = null
    var authorities: List<GrantedAuthority>? = null
    var authenticationProvider: AuthenticationProvider? = null
    var authenticationFilter: AnonymousAuthenticationFilter? = null

    private var disabled = false

    /**
     * Disable anonymous authentication
     */
    fun disable() {
        disabled = true
    }

    internal fun get(): (AnonymousConfigurer<HttpSecurity>) -> Unit {
        return { anonymous ->
            key?.also { anonymous.key(key) }
            principal?.also { anonymous.principal(principal) }
            authorities?.also { anonymous.authorities(authorities) }
            authenticationProvider?.also { anonymous.authenticationProvider(authenticationProvider) }
            authenticationFilter?.also { anonymous.authenticationFilter(authenticationFilter) }
            if (disabled) {
                anonymous.disable()
            }
        }
    }
}
