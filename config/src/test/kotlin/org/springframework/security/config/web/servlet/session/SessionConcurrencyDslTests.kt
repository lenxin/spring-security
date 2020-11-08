package org.springframework.security.config.web.servlet.session

import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mock.web.MockHttpSession
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.test.SpringTestRule
import org.springframework.security.core.session.SessionInformation
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

/**
 * Tests for [SessionConcurrencyDsl]
 *
 * @author Eleftheria Stein
 */
class SessionConcurrencyDslTests {
    @Rule
    @JvmField
    var spring = SpringTestRule()

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `session concurrency when maximum sessions then no more sessions allowed`() {
        this.spring.register(MaximumSessionsConfig::class.java, UserDetailsConfig::class.java).autowire()

        this.mockMvc.perform(post("/login")
                .with(csrf())
                .param("username", "user")
                .param("password", "password"))

        this.mockMvc.perform(post("/login")
                .with(csrf())
                .param("username", "user")
                .param("password", "password"))
                .andExpect(status().isFound)
                .andExpect(redirectedUrl("/login?error"))
    }

    @EnableWebSecurity
    open class MaximumSessionsConfig : WebSecurityConfigurerAdapter() {
        override fun configure(http: HttpSecurity) {
            http {
                sessionManagement {
                    sessionConcurrency {
                        maximumSessions = 1
                        maxSessionsPreventsLogin = true
                    }
                }
                formLogin { }
            }
        }
    }

    @Test
    fun `session concurrency when expired url then redirects to url`() {
        this.spring.register(ExpiredUrlConfig::class.java).autowire()

        val session = MockHttpSession()
        val sessionInformation = SessionInformation("", session.id, Date(0))
        sessionInformation.expireNow()
        `when`(ExpiredUrlConfig.sessionRegistry.getSessionInformation(any())).thenReturn(sessionInformation)

        this.mockMvc.perform(get("/").session(session))
                .andExpect(redirectedUrl("/expired-session"))
    }

    @EnableWebSecurity
    open class ExpiredUrlConfig : WebSecurityConfigurerAdapter() {
        companion object {
            val sessionRegistry: SessionRegistry = mock(SessionRegistry::class.java)
        }

        override fun configure(http: HttpSecurity) {
            http {
                sessionManagement {
                    sessionConcurrency {
                        maximumSessions = 1
                        expiredUrl = "/expired-session"
                        sessionRegistry = sessionRegistry()
                    }
                }
            }
        }

        @Bean
        open fun sessionRegistry(): SessionRegistry {
            return sessionRegistry
        }
    }

    @Test
    fun `session concurrency when expired session strategy then strategy used`() {
        this.spring.register(ExpiredSessionStrategyConfig::class.java).autowire()

        val session = MockHttpSession()
        val sessionInformation = SessionInformation("", session.id, Date(0))
        sessionInformation.expireNow()
        `when`(ExpiredSessionStrategyConfig.sessionRegistry.getSessionInformation(any())).thenReturn(sessionInformation)

        this.mockMvc.perform(get("/").session(session))
                .andExpect(redirectedUrl("/expired-session"))
    }

    @EnableWebSecurity
    open class ExpiredSessionStrategyConfig : WebSecurityConfigurerAdapter() {
        companion object {
            val sessionRegistry: SessionRegistry = mock(SessionRegistry::class.java)
        }

        override fun configure(http: HttpSecurity) {
            http {
                sessionManagement {
                    sessionConcurrency {
                        maximumSessions = 1
                        expiredSessionStrategy = SimpleRedirectSessionInformationExpiredStrategy("/expired-session")
                        sessionRegistry = sessionRegistry()
                    }
                }
            }
        }

        @Bean
        open fun sessionRegistry(): SessionRegistry {
            return sessionRegistry
        }
    }

    @Configuration
    open class UserDetailsConfig {
        @Bean
        open fun userDetailsService(): UserDetailsService {
            val userDetails = User.withDefaultPasswordEncoder()
                    .username("user")
                    .password("password")
                    .roles("USER")
                    .build()
            return InMemoryUserDetailsManager(userDetails)
        }
    }
}
