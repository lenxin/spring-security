package org.springframework.security.config.web.servlet

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer
import org.springframework.security.web.savedrequest.RequestCache

/**
 * A Kotlin DSL to enable request caching for [HttpSecurity] using idiomatic
 * Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 * @property requestCache allows explicit configuration of the [RequestCache] to be used
 */
@SecurityMarker
class RequestCacheDsl {
    var requestCache: RequestCache? = null

    internal fun get(): (RequestCacheConfigurer<HttpSecurity>) -> Unit {
        return { requestCacheConfig ->
            requestCache?.also {
                requestCacheConfig.requestCache(requestCache)
            }
        }
    }
}
