package org.springframework.security.config.web.servlet

import org.junit.Rule
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.test.SpringTestRule
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin
import org.springframework.security.web.savedrequest.NullRequestCache
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl

/**
 * Tests for [RequestCacheDsl]
 *
 * @author Eleftheria Stein
 */
class RequestCacheDslTests {
    @Rule
    @JvmField
    val spring = SpringTestRule()

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `GET when request cache enabled then redirected to cached page`() {
        this.spring.register(RequestCacheConfig::class.java).autowire()

        this.mockMvc.get("/test")

        this.mockMvc.perform(formLogin())
                .andExpect {
                    redirectedUrl("http://localhost/test")
                }
    }

    @EnableWebSecurity
    open class RequestCacheConfig : WebSecurityConfigurerAdapter() {
        override fun configure(http: HttpSecurity) {
            http {
                requestCache { }
                formLogin { }
            }
        }
    }

    @Test
    fun `GET when custom request cache then custom request cache used`() {
        this.spring.register(CustomRequestCacheConfig::class.java).autowire()

        this.mockMvc.get("/test")

        this.mockMvc.perform(formLogin())
                .andExpect {
                    redirectedUrl("/")
                }
    }

    @EnableWebSecurity
    open class CustomRequestCacheConfig : WebSecurityConfigurerAdapter() {
        override fun configure(http: HttpSecurity) {
            http {
                requestCache {
                    requestCache = NullRequestCache()
                }
                formLogin { }
            }
        }
    }
}
