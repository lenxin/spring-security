package org.springframework.security.config.web.servlet.headers

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer

/**
 * A Kotlin DSL to configure the [HttpSecurity] X-Frame-Options header using
 * idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 * @property sameOrigin allow any request that comes from the same origin to frame this
 * application.
 * @property deny deny framing any content from this application.
 */
@HeadersSecurityMarker
class FrameOptionsDsl {
    var sameOrigin: Boolean? = null
    var deny: Boolean? = null

    private var disabled = false

    /**
     * Disable the X-Frame-Options header.
     */
    fun disable() {
        disabled = true
    }

    internal fun get(): (HeadersConfigurer<HttpSecurity>.FrameOptionsConfig) -> Unit {
        return { frameOptions ->
            sameOrigin?.also {
                if (sameOrigin!!) {
                    frameOptions.sameOrigin()
                }
            }
            deny?.also {
                if (deny!!) {
                    frameOptions.deny()
                }
            }
            if (disabled) {
                frameOptions.disable()
            }
        }
    }
}
