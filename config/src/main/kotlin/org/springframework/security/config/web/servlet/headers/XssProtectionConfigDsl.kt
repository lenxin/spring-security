package org.springframework.security.config.web.servlet.headers

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer

/**
 * A Kotlin DSL to configure the [HttpSecurity] XSS protection header using
 * idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 * @property block whether to specify the mode as blocked
 * @property xssProtectionEnabled if true, the header value will contain a value of 1.
 * If false, will explicitly disable specify that X-XSS-Protection is disabled.
 */
@HeadersSecurityMarker
class XssProtectionConfigDsl {
    var block: Boolean? = null
    var xssProtectionEnabled: Boolean? = null

    private var disabled = false

    /**
     * Do not include the X-XSS-Protection header in the response.
     */
    fun disable() {
        disabled = true
    }

    internal fun get(): (HeadersConfigurer<HttpSecurity>.XXssConfig) -> Unit {
        return { xssProtection ->
            block?.also { xssProtection.block(block!!) }
            xssProtectionEnabled?.also { xssProtection.xssProtectionEnabled(xssProtectionEnabled!!) }

            if (disabled) {
                xssProtection.disable()
            }
        }
    }
}
