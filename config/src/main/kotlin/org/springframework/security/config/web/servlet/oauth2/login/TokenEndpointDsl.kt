package org.springframework.security.config.web.servlet.oauth2.login

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest

/**
 * A Kotlin DSL to configure the Authorization Server's Token Endpoint using
 * idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 * @property accessTokenResponseClient the client used for requesting the access token credential
 * from the Token Endpoint.
 */
@OAuth2LoginSecurityMarker
class TokenEndpointDsl {
    var accessTokenResponseClient: OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>? = null

    internal fun get(): (OAuth2LoginConfigurer<HttpSecurity>.TokenEndpointConfig) -> Unit {
        return { tokenEndpoint ->
            accessTokenResponseClient?.also { tokenEndpoint.accessTokenResponseClient(accessTokenResponseClient) }
        }
    }
}
