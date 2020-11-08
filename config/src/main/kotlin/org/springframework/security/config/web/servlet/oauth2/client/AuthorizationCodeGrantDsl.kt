package org.springframework.security.config.web.servlet.oauth2.client

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2ClientConfigurer
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest

/**
 * A Kotlin DSL to configure OAuth 2.0 Authorization Code Grant.
 *
 * @author Eleftheria Stein
 * @since 5.3
 * @property authorizationRequestResolver the resolver used for resolving [OAuth2AuthorizationRequest]'s.
 * @property authorizationRequestRepository the repository used for storing [OAuth2AuthorizationRequest]'s.
 * @property accessTokenResponseClient the client used for requesting the access token credential
 * from the Token Endpoint.
 */
@OAuth2ClientSecurityMarker
class AuthorizationCodeGrantDsl {
    var authorizationRequestResolver: OAuth2AuthorizationRequestResolver? = null
    var authorizationRequestRepository: AuthorizationRequestRepository<OAuth2AuthorizationRequest>? = null
    var accessTokenResponseClient: OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>? = null

    internal fun get(): (OAuth2ClientConfigurer<HttpSecurity>.AuthorizationCodeGrantConfigurer) -> Unit {
        return { authorizationCodeGrant ->
            authorizationRequestResolver?.also { authorizationCodeGrant.authorizationRequestResolver(authorizationRequestResolver) }
            authorizationRequestRepository?.also { authorizationCodeGrant.authorizationRequestRepository(authorizationRequestRepository) }
            accessTokenResponseClient?.also { authorizationCodeGrant.accessTokenResponseClient(accessTokenResponseClient) }
        }
    }
}
