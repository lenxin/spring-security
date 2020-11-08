package org.springframework.security.config.web.servlet.headers

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer

/**
 * A Kotlin DSL to configure the [HttpSecurity] HTTP Public Key Pinning header using
 * idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 * @property pins the value for the pin- directive of the Public-Key-Pins header.
 * @property maxAgeInSeconds the value (in seconds) for the max-age directive of the
 * Public-Key-Pins header.
 * @property includeSubDomains if true, the pinning policy applies to this pinned host
 * as well as any subdomains of the host's domain name.
 * @property reportOnly if true, the browser should not terminate the connection with
 * the server.
 * @property reportUri the URI to which the browser should report pin validation failures.
 */
@HeadersSecurityMarker
class HttpPublicKeyPinningDsl {
    var pins: Map<String, String>? = null
    var maxAgeInSeconds: Long? = null
    var includeSubDomains: Boolean? = null
    var reportOnly: Boolean? = null
    var reportUri: String? = null

    private var disabled = false

    /**
     * Disable the HTTP Public Key Pinning header.
     */
    fun disable() {
        disabled = true
    }

    internal fun get(): (HeadersConfigurer<HttpSecurity>.HpkpConfig) -> Unit {
        return { hpkp ->
            pins?.also {
                hpkp.withPins(pins)
            }
            maxAgeInSeconds?.also {
                hpkp.maxAgeInSeconds(maxAgeInSeconds!!)
            }
            includeSubDomains?.also {
                hpkp.includeSubDomains(includeSubDomains!!)
            }
            reportOnly?.also {
                hpkp.reportOnly(reportOnly!!)
            }
            reportUri?.also {
                hpkp.reportUri(reportUri)
            }
            if (disabled) {
                hpkp.disable()
            }
        }
    }
}
