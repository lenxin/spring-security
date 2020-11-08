package org.springframework.security.config.web.server

import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector

/**
 * A Kotlin DSL to configure [ServerHttpSecurity] Opaque Token Resource Server support using idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.4
 * @property introspectionUri the URI of the Introspection endpoint.
 * @property introspector the [ReactiveOpaqueTokenIntrospector] to use.
 */
@ServerSecurityMarker
class ServerOpaqueTokenDsl {
    private var _introspectionUri: String? = null
    private var _introspector: ReactiveOpaqueTokenIntrospector? = null
    private var clientCredentials: Pair<String, String>? = null

    var introspectionUri: String?
        get() = _introspectionUri
        set(value) {
            _introspectionUri = value
            _introspector = null
        }
    var introspector: ReactiveOpaqueTokenIntrospector?
        get() = _introspector
        set(value) {
            _introspector = value
            _introspectionUri = null
            clientCredentials = null
        }

    /**
     * Configures the credentials for Introspection endpoint.
     *
     * @param clientId the clientId part of the credentials.
     * @param clientSecret the clientSecret part of the credentials.
     */
    fun introspectionClientCredentials(clientId: String, clientSecret: String) {
        clientCredentials = Pair(clientId, clientSecret)
        _introspector = null
    }

    internal fun get(): (ServerHttpSecurity.OAuth2ResourceServerSpec.OpaqueTokenSpec) -> Unit {
        return { opaqueToken ->
            introspectionUri?.also { opaqueToken.introspectionUri(introspectionUri) }
            clientCredentials?.also { opaqueToken.introspectionClientCredentials(clientCredentials!!.first, clientCredentials!!.second) }
            introspector?.also { opaqueToken.introspector(introspector) }
        }
    }
}
