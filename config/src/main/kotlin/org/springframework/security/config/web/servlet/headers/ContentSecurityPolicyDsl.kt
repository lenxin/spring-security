package org.springframework.security.config.web.servlet.headers

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer

/**
 * A Kotlin DSL to configure the [HttpSecurity] Content-Security-Policy header using
 * idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 * @property policyDirectives the security policy directive(s) to be used in the response header.
 * @property reportOnly includes the Content-Security-Policy-Report-Only header in the response.
 */
@HeadersSecurityMarker
class ContentSecurityPolicyDsl {
    var policyDirectives: String? = null
    var reportOnly: Boolean? = null

    internal fun get(): (HeadersConfigurer<HttpSecurity>.ContentSecurityPolicyConfig) -> Unit {
        return { contentSecurityPolicy ->
            policyDirectives?.also {
                contentSecurityPolicy.policyDirectives(policyDirectives)
            }
            reportOnly?.also {
                if (reportOnly!!) {
                    contentSecurityPolicy.reportOnly()
                }
            }
        }
    }
}
