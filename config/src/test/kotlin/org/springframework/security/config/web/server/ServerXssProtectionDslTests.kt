package org.springframework.security.config.web.server

import org.junit.Rule
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.test.SpringTestRule
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.header.XXssProtectionServerHttpHeadersWriter
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.config.EnableWebFlux

/**
 * Tests for [ServerXssProtectionDsl]
 *
 * @author Eleftheria Stein
 */
class ServerXssProtectionDslTests {
    @Rule
    @JvmField
    val spring = SpringTestRule()

    private lateinit var client: WebTestClient

    @Autowired
    fun setup(context: ApplicationContext) {
        this.client = WebTestClient
                .bindToApplicationContext(context)
                .configureClient()
                .build()
    }

    @Test
    fun `request when xss protection configured then xss header in response`() {
        this.spring.register(XssConfig::class.java).autowire()

        this.client.get()
                .uri("/")
                .exchange()
                .expectHeader().valueEquals(XXssProtectionServerHttpHeadersWriter.X_XSS_PROTECTION, "1 ; mode=block")
    }

    @EnableWebFluxSecurity
    @EnableWebFlux
    open class XssConfig {
        @Bean
        open fun springWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
            return http {
                headers {
                    xssProtection { }
                }
            }
        }
    }

    @Test
    fun `request when xss protection disabled then no xss header in response`() {
        this.spring.register(XssDisabledConfig::class.java).autowire()

        this.client.get()
                .uri("/")
                .exchange()
                .expectHeader().doesNotExist(XXssProtectionServerHttpHeadersWriter.X_XSS_PROTECTION)
    }

    @EnableWebFluxSecurity
    @EnableWebFlux
    open class XssDisabledConfig {
        @Bean
        open fun springWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
            return http {
                headers {
                    xssProtection {
                        disable()
                    }
                }
            }
        }
    }
}
