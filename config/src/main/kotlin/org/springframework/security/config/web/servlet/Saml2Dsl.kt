package org.springframework.security.config.web.servlet

import org.springframework.security.config.annotation.web.HttpSecurityBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.saml2.Saml2LoginConfigurer
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

/**
 * A Kotlin DSL to configure [HttpSecurity] SAML2 login using idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 * @property relyingPartyRegistrationRepository the [RelyingPartyRegistrationRepository] of relying parties,
 * each party representing a service provider, SP and this host, and identity provider, IDP pair that
 * communicate with each other.
 * @property loginPage the login page to redirect to if authentication is required (i.e.
 * "/login")
 * @property authenticationSuccessHandler the [AuthenticationSuccessHandler] used after
 * authentication success
 * @property authenticationFailureHandler the [AuthenticationFailureHandler] used after
 * authentication success
 * @property failureUrl the URL to send users if authentication fails
 * @property loginProcessingUrl the URL to validate the credentials
 * @property permitAll whether to grant access to the urls for [failureUrl] as well as
 * for the [HttpSecurityBuilder], the [loginPage] and [loginProcessingUrl] for every user
 */
@SecurityMarker
class Saml2Dsl {
    var relyingPartyRegistrationRepository: RelyingPartyRegistrationRepository? = null
    var loginPage: String? = null
    var authenticationSuccessHandler: AuthenticationSuccessHandler? = null
    var authenticationFailureHandler: AuthenticationFailureHandler? = null
    var failureUrl: String? = null
    var loginProcessingUrl: String? = null
    var permitAll: Boolean? = null

    private var defaultSuccessUrlOption: Pair<String, Boolean>? = null

    /**
     * Grants access to the urls for [failureUrl] as well as for the [HttpSecurityBuilder], the
     * [loginPage] and [loginProcessingUrl] for every user.
     */
    fun permitAll() {
        permitAll = true
    }

    /**
     * Specifies where users will be redirected after authenticating successfully if
     * they have not visited a secured page prior to authenticating or [alwaysUse]
     * is true.
     *
     * @param defaultSuccessUrl the default success url
     * @param alwaysUse true if the [defaultSuccessUrl] should be used after
     * authentication despite if a protected page had been previously visited
     */
    fun defaultSuccessUrl(defaultSuccessUrl: String, alwaysUse: Boolean) {
        defaultSuccessUrlOption = Pair(defaultSuccessUrl, alwaysUse)
    }

    internal fun get(): (Saml2LoginConfigurer<HttpSecurity>) -> Unit {
        return { saml2Login ->
            relyingPartyRegistrationRepository?.also { saml2Login.relyingPartyRegistrationRepository(relyingPartyRegistrationRepository) }
            loginPage?.also { saml2Login.loginPage(loginPage) }
            failureUrl?.also { saml2Login.failureUrl(failureUrl) }
            loginProcessingUrl?.also { saml2Login.loginProcessingUrl(loginProcessingUrl) }
            permitAll?.also { saml2Login.permitAll(permitAll!!) }
            defaultSuccessUrlOption?.also {
                saml2Login.defaultSuccessUrl(defaultSuccessUrlOption!!.first, defaultSuccessUrlOption!!.second)
            }
            authenticationSuccessHandler?.also { saml2Login.successHandler(authenticationSuccessHandler) }
            authenticationFailureHandler?.also { saml2Login.failureHandler(authenticationFailureHandler) }
        }
    }
}
