package org.springframework.security.config.web.servlet.headers

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter

/**
 * A Kotlin DSL to configure the [HttpSecurity] referrer policy header using
 * idiomatic Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 * @property policy the policy to be used in the response header.
 */
@HeadersSecurityMarker
class ReferrerPolicyDsl {
    var policy: ReferrerPolicyHeaderWriter.ReferrerPolicy? = null

    internal fun get(): (HeadersConfigurer<HttpSecurity>.ReferrerPolicyConfig) -> Unit {
        return { referrerPolicy ->
            policy?.also {
                referrerPolicy.policy(policy)
            }
        }
    }
}
