package org.springframework.security.config.web.servlet.headers

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer

/**
 * A Kotlin DSL to configure [HttpSecurity] X-Content-Type-Options header using idiomatic
 * Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 */
@HeadersSecurityMarker
class ContentTypeOptionsDsl {
    private var disabled = false

    /**
     * Disable the X-Content-Type-Options header.
     */
    fun disable() {
        disabled = true
    }

    internal fun get(): (HeadersConfigurer<HttpSecurity>.ContentTypeOptionsConfig) -> Unit {
        return { contentTypeOptions ->
            if (disabled) {
                contentTypeOptions.disable()
            }
        }
    }
}
