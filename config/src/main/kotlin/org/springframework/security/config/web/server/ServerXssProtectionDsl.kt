package org.springframework.security.config.web.server

/**
 * A Kotlin DSL to configure the [ServerHttpSecurity] XSS protection header using
 * idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.4
 */
@ServerSecurityMarker
class ServerXssProtectionDsl {
    private var disabled = false

    /**
     * Disables cache control response headers
     */
    fun disable() {
        disabled = true
    }

    internal fun get(): (ServerHttpSecurity.HeaderSpec.XssProtectionSpec) -> Unit {
        return { xss ->
            if (disabled) {
                xss.disable()
            }
        }
    }
}
