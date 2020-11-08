package org.springframework.security.config.web.server

import org.springframework.security.web.server.savedrequest.ServerRequestCache

/**
 * A Kotlin DSL to configure the request cache using idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.4
 * @property requestCache allows explicit configuration of the [ServerRequestCache] to be used.
 */
@ServerSecurityMarker
class ServerRequestCacheDsl {
    var requestCache: ServerRequestCache? = null

    private var disabled = false

    /**
     * Disables the request cache.
     */
    fun disable() {
        disabled = true
    }

    internal fun get(): (ServerHttpSecurity.RequestCacheSpec) -> Unit {
        return { requestCacheConfig ->
            requestCache?.also {
                requestCacheConfig.requestCache(requestCache)
                if (disabled) {
                    requestCacheConfig.disable()
                }
            }
        }
    }
}
