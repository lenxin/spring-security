package org.springframework.security.config.web.servlet.headers

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer

/**
 * A Kotlin DSL to configure the [HttpSecurity] cache control headers using idiomatic
 * Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 */
@HeadersSecurityMarker
class CacheControlDsl {
    private var disabled = false

    /**
     * Disable cache control headers.
     */
    fun disable() {
        disabled = true
    }

    internal fun get(): (HeadersConfigurer<HttpSecurity>.CacheControlConfig) -> Unit {
        return { cacheControl ->
            if (disabled) {
                cacheControl.disable()
            }
        }
    }
}
