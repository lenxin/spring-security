package org.springframework.security.config.web.servlet.headers

import org.junit.Rule
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.config.test.SpringTestRule
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

/**
 * Tests for [FrameOptionsDsl]
 *
 * @author Eleftheria Stein
 */
class FrameOptionsDslTests {
    @Rule
    @JvmField
    var spring = SpringTestRule()

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `headers when frame options configured then frame options deny header`() {
        this.spring.register(FrameOptionsConfig::class.java).autowire()

        this.mockMvc.get("/") {
            secure = true
        }.andExpect {
            header { string(XFrameOptionsServerHttpHeadersWriter.X_FRAME_OPTIONS, XFrameOptionsHeaderWriter.XFrameOptionsMode.DENY.name) }
        }
    }

    @EnableWebSecurity
    open class FrameOptionsConfig : WebSecurityConfigurerAdapter() {
        override fun configure(http: HttpSecurity) {
            http {
                headers {
                    defaultsDisabled = true
                    frameOptions { }
                }
            }
        }
    }

    @Test
    fun `headers when frame options deny configured then frame options deny header`() {
        this.spring.register(FrameOptionsDenyConfig::class.java).autowire()

        this.mockMvc.get("/") {
            secure = true
        }.andExpect {
            header { string(XFrameOptionsServerHttpHeadersWriter.X_FRAME_OPTIONS, XFrameOptionsHeaderWriter.XFrameOptionsMode.DENY.name) }
        }
    }

    @EnableWebSecurity
    open class FrameOptionsDenyConfig : WebSecurityConfigurerAdapter() {
        override fun configure(http: HttpSecurity) {
            http {
                headers {
                    defaultsDisabled = true
                    frameOptions {
                        deny = true
                    }
                }
            }
        }
    }

    @Test
    fun `headers when frame options same origin configured then frame options same origin header`() {
        this.spring.register(FrameOptionsSameOriginConfig::class.java).autowire()

        this.mockMvc.get("/") {
            secure = true
        }.andExpect {
            header { string(XFrameOptionsServerHttpHeadersWriter.X_FRAME_OPTIONS, XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN.name) }
        }
    }

    @EnableWebSecurity
    open class FrameOptionsSameOriginConfig : WebSecurityConfigurerAdapter() {
        override fun configure(http: HttpSecurity) {
            http {
                headers {
                    defaultsDisabled = true
                    frameOptions {
                        sameOrigin = true
                    }
                }
            }
        }
    }

    @Test
    fun `headers when frame options same origin and deny configured then frame options deny header`() {
        this.spring.register(FrameOptionsSameOriginAndDenyConfig::class.java).autowire()

        this.mockMvc.get("/") {
            secure = true
        }.andExpect {
            header { string(XFrameOptionsServerHttpHeadersWriter.X_FRAME_OPTIONS, XFrameOptionsHeaderWriter.XFrameOptionsMode.DENY.name) }
        }
    }

    @EnableWebSecurity
    open class FrameOptionsSameOriginAndDenyConfig : WebSecurityConfigurerAdapter() {
        override fun configure(http: HttpSecurity) {
            http {
                headers {
                    defaultsDisabled = true
                    frameOptions {
                        sameOrigin = true
                        deny = true
                    }
                }
            }
        }
    }

    @Test
    fun `headers when frame options disabled then no frame options header in response`() {
        this.spring.register(FrameOptionsDisabledConfig::class.java).autowire()

        this.mockMvc.get("/") {
            secure = true
        }.andExpect {
            header { doesNotExist(XFrameOptionsServerHttpHeadersWriter.X_FRAME_OPTIONS) }
        }
    }

    @EnableWebSecurity
    open class FrameOptionsDisabledConfig : WebSecurityConfigurerAdapter() {
        override fun configure(http: HttpSecurity) {
            http {
                headers {
                    frameOptions {
                        disable()
                    }
                }
            }
        }
    }
}
