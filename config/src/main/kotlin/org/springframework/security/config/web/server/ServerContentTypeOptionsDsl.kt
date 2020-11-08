package org.springframework.security.config.web.server

/**
 * A Kotlin DSL to configure the [ServerHttpSecurity] the content type options header
 * using idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.4
 */
@ServerSecurityMarker
class ServerContentTypeOptionsDsl {
    private var disabled = false

    /**
     * Disables content type options response header
     */
    fun disable() {
        disabled = true
    }

    internal fun get(): (ServerHttpSecurity.HeaderSpec.ContentTypeOptionsSpec) -> Unit {
        return { contentTypeOptions ->
            if (disabled) {
                contentTypeOptions.disable()
            }
        }
    }
}
