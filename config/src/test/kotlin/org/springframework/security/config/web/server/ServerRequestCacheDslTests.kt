package org.springframework.security.config.web.server

import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.config.test.SpringTestRule
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.savedrequest.ServerRequestCache
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.config.EnableWebFlux
import reactor.core.publisher.Mono

/**
 * Tests for [ServerRequestCacheDsl]
 *
 * @author Eleftheria Stein
 */
class ServerRequestCacheDslTests {
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
    fun `GET when request cache enabled then redirected to cached page`() {
        this.spring.register(RequestCacheConfig::class.java, UserDetailsConfig::class.java).autowire()
        `when`(RequestCacheConfig.REQUEST_CACHE.removeMatchingRequest(any())).thenReturn(Mono.empty())

        this.client.get()
                .uri("/test")
                .exchange()

        verify(RequestCacheConfig.REQUEST_CACHE).saveRequest(any())
    }

    @EnableWebFluxSecurity
    @EnableWebFlux
    open class RequestCacheConfig {
        companion object {
            var REQUEST_CACHE: ServerRequestCache = Mockito.mock(ServerRequestCache::class.java)
        }

        @Bean
        open fun springWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
            return http {
                authorizeExchange {
                    authorize(anyExchange, authenticated)
                }
                formLogin { }
                requestCache {
                    requestCache = REQUEST_CACHE
                }
            }
        }
    }

    @Configuration
    open class UserDetailsConfig {
        @Bean
        open fun userDetailsService(): MapReactiveUserDetailsService {
            val user = User.withDefaultPasswordEncoder()
                    .username("user")
                    .password("password")
                    .roles("USER")
                    .build()
            return MapReactiveUserDetailsService(user)
        }
    }
}
