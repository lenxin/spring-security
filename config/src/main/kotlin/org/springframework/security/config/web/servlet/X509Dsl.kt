package org.springframework.security.config.web.servlet

import org.springframework.security.authentication.AuthenticationDetailsSource
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.X509Configurer
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter
import org.springframework.security.web.authentication.preauth.x509.X509PrincipalExtractor
import javax.servlet.http.HttpServletRequest

/**
 * A Kotlin DSL to configure [HttpSecurity] X509 based pre authentication
 * using idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 * @property x509AuthenticationFilter the entire [X509AuthenticationFilter]. If
 * this is specified, the properties on [X509Configurer] will not be populated
 * on the {@link X509AuthenticationFilter}.
 * @property x509PrincipalExtractor the [X509PrincipalExtractor]
 * @property authenticationDetailsSource the [X509PrincipalExtractor]
 * @property userDetailsService shortcut for invoking
 * [authenticationUserDetailsService] with a [UserDetailsByNameServiceWrapper]
 * @property authenticationUserDetailsService the [AuthenticationUserDetailsService] to use
 * @property subjectPrincipalRegex the regex to extract the principal from the certificate
 */
@SecurityMarker
class X509Dsl {
    var x509AuthenticationFilter: X509AuthenticationFilter? = null
    var x509PrincipalExtractor: X509PrincipalExtractor? = null
    var authenticationDetailsSource: AuthenticationDetailsSource<HttpServletRequest, PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails>? = null
    var userDetailsService: UserDetailsService? = null
    var authenticationUserDetailsService: AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>? = null
    var subjectPrincipalRegex: String? = null

    internal fun get(): (X509Configurer<HttpSecurity>) -> Unit {
        return { x509 ->
            x509AuthenticationFilter?.also { x509.x509AuthenticationFilter(x509AuthenticationFilter) }
            x509PrincipalExtractor?.also { x509.x509PrincipalExtractor(x509PrincipalExtractor) }
            authenticationDetailsSource?.also { x509.authenticationDetailsSource(authenticationDetailsSource) }
            userDetailsService?.also { x509.userDetailsService(userDetailsService) }
            authenticationUserDetailsService?.also { x509.authenticationUserDetailsService(authenticationUserDetailsService) }
            subjectPrincipalRegex?.also { x509.subjectPrincipalRegex(subjectPrincipalRegex) }
        }
    }
}
