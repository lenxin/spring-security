package org.springframework.security.config.web.servlet

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer

/**
 * A Kotlin DSL to configure [HttpSecurity] CORS using idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 */
@SecurityMarker
class CorsDsl {
    private var disabled = false

    /**
     * Disable CORS.
     */
    fun disable() {
        disabled = true
    }

    internal fun get(): (CorsConfigurer<HttpSecurity>) -> Unit {
        return { cors ->
            if (disabled) {
                cors.disable()
            }
        }
    }
}
