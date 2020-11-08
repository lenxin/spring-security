package org.springframework.security.config.web.server

import org.springframework.web.cors.reactive.CorsConfigurationSource

/**
 * A Kotlin DSL to configure [ServerHttpSecurity] CORS headers using idiomatic
 * Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.4
 * @property configurationSource the [CorsConfigurationSource] to use.
 */
@ServerSecurityMarker
class ServerCorsDsl {
    var configurationSource: CorsConfigurationSource? = null

    private var disabled = false

    /**
     * Disables CORS support within Spring Security.
     */
    fun disable() {
        disabled = true
    }

    internal fun get(): (ServerHttpSecurity.CorsSpec) -> Unit {
        return { cors ->
            configurationSource?.also { cors.configurationSource(configurationSource) }
            if (disabled) {
                cors.disable()
            }
        }
    }
}
