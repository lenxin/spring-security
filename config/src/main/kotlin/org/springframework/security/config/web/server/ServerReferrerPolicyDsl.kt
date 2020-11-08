package org.springframework.security.config.web.server

import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter

/**
 * A Kotlin DSL to configure the [ServerHttpSecurity] referrer policy header using
 * idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.4
 * @property policy the policy to be used in the response header.
 */
@ServerSecurityMarker
class ServerReferrerPolicyDsl {
    var policy: ReferrerPolicyServerHttpHeadersWriter.ReferrerPolicy? = null

    internal fun get(): (ServerHttpSecurity.HeaderSpec.ReferrerPolicySpec) -> Unit {
        return { referrerPolicy ->
            policy?.also {
                referrerPolicy.policy(policy)
            }
        }
    }
}
