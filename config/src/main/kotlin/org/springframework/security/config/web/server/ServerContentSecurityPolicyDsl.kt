package org.springframework.security.config.web.server

/**
 * A Kotlin DSL to configure the [ServerHttpSecurity] Content-Security-Policy header using
 * idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.4
 */
@ServerSecurityMarker
class ServerContentSecurityPolicyDsl {
    var policyDirectives: String? = null
    var reportOnly: Boolean? = null

    internal fun get(): (ServerHttpSecurity.HeaderSpec.ContentSecurityPolicySpec) -> Unit {
        return { contentSecurityPolicy ->
            policyDirectives?.also {
                contentSecurityPolicy.policyDirectives(policyDirectives)
            }
            reportOnly?.also {
                contentSecurityPolicy.reportOnly(reportOnly!!)
            }
        }
    }
}
