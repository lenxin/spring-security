package org.springframework.security.config.web.servlet.oauth2.login

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer

/**
 * A Kotlin DSL to configure the Authorization Server's Redirection Endpoint using
 * idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 * @property baseUri the URI where the authorization response will be processed.
 */
@OAuth2LoginSecurityMarker
class RedirectionEndpointDsl {
    var baseUri: String? = null

    internal fun get(): (OAuth2LoginConfigurer<HttpSecurity>.RedirectionEndpointConfig) -> Unit {
        return { redirectionEndpoint ->
            baseUri?.also { redirectionEndpoint.baseUri(baseUri) }
        }
    }
}
