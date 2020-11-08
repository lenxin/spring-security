package org.springframework.security.config.web.server

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.oauth2.client.web.server.ServerAuthorizationRequestRepository
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.web.server.ServerWebExchange

/**
 * A Kotlin DSL to configure the [ServerHttpSecurity] OAuth 2.0 client using idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.4
 * @property authenticationManager the [ReactiveAuthenticationManager] used to determine if the provided
 * [Authentication] can be authenticated.
 * @property authenticationConverter the [ServerAuthenticationConverter] used for converting from a [ServerWebExchange]
 * to an [Authentication].
 * @property clientRegistrationRepository the repository of client registrations.
 * @property authorizedClientRepository the repository for authorized client(s).
 * @property authorizationRequestRepository the repository to use for storing [OAuth2AuthorizationRequest]s.
 */
@ServerSecurityMarker
class ServerOAuth2ClientDsl {
    var authenticationManager: ReactiveAuthenticationManager? = null
    var authenticationConverter: ServerAuthenticationConverter? = null
    var clientRegistrationRepository: ReactiveClientRegistrationRepository? = null
    var authorizedClientRepository: ServerOAuth2AuthorizedClientRepository? = null
    var authorizationRequestRepository: ServerAuthorizationRequestRepository<OAuth2AuthorizationRequest>? = null

    internal fun get(): (ServerHttpSecurity.OAuth2ClientSpec) -> Unit {
        return { oauth2Client ->
            authenticationManager?.also { oauth2Client.authenticationManager(authenticationManager) }
            authenticationConverter?.also { oauth2Client.authenticationConverter(authenticationConverter) }
            clientRegistrationRepository?.also { oauth2Client.clientRegistrationRepository(clientRegistrationRepository) }
            authorizedClientRepository?.also { oauth2Client.authorizedClientRepository(authorizedClientRepository) }
            authorizationRequestRepository?.also { oauth2Client.authorizationRequestRepository(authorizationRequestRepository) }
        }
    }
}
