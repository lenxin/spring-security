package org.springframework.security.config.web.server

import org.junit.Rule
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.test.SpringTestRule
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.config.EnableWebFlux

/**
 * Tests for [ServerFrameOptionsDsl]
 *
 * @author Eleftheria Stein
 */
class ServerFrameOptionsDslTests {
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
    fun `request when frame options configured then header in response`() {
        this.spring.register(FrameOptionsConfig::class.java).autowire()

        this.client.get()
                .uri("/")
                .exchange()
                .expectHeader().valueEquals(XFrameOptionsServerHttpHeadersWriter.X_FRAME_OPTIONS, XFrameOptionsHeaderWriter.XFrameOptionsMode.DENY.name)
    }

    @EnableWebFluxSecurity
    @EnableWebFlux
    open class FrameOptionsConfig {
        @Bean
        open fun springWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
            return http {
                headers {
                    frameOptions { }
                }
            }
        }
    }

    @Test
    fun `request when frame options disabled then no frame options header in response`() {
        this.spring.register(FrameOptionsDisabledConfig::class.java).autowire()

        this.client.get()
                .uri("/")
                .exchange()
                .expectHeader().doesNotExist(XFrameOptionsServerHttpHeadersWriter.X_FRAME_OPTIONS)
    }

    @EnableWebFluxSecurity
    @EnableWebFlux
    open class FrameOptionsDisabledConfig {
        @Bean
        open fun springWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
            return http {
                headers {
                    frameOptions {
                        disable()
                    }
                }
            }
        }
    }

    @Test
    fun `request when frame options mode set then frame options response header has mode value`() {
        this.spring.register(CustomModeConfig::class.java).autowire()

        this.client.get()
                .uri("/")
                .exchange()
                .expectHeader().valueEquals(XFrameOptionsServerHttpHeadersWriter.X_FRAME_OPTIONS, XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN.name)
    }

    @EnableWebFluxSecurity
    @EnableWebFlux
    open class CustomModeConfig {
        @Bean
        open fun springWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
            return http {
                headers {
                    frameOptions {
                        mode = XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN
                    }
                }
            }
        }
    }
}
