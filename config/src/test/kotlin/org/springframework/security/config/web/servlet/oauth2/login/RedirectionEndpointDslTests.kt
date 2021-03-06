package org.springframework.security.config.web.servlet.oauth2.login

import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider
import org.springframework.security.config.test.SpringTestRule
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.util.*

/**
 * Tests for [RedirectionEndpointDsl]
 *
 * @author Eleftheria Stein
 */
class RedirectionEndpointDslTests {
    @Rule
    @JvmField
    var spring = SpringTestRule()

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `oauth2Login when redirection endpoint configured then custom redirection endpoing used`() {
        this.spring.register(UserServiceConfig::class.java, ClientConfig::class.java).autowire()

        val registrationId = "registrationId"
        val attributes = HashMap<String, Any>()
        attributes[OAuth2ParameterNames.REGISTRATION_ID] = registrationId
        val authorizationRequest = OAuth2AuthorizationRequest
                .authorizationCode()
                .state("test")
                .clientId("clientId")
                .authorizationUri("https://test")
                .redirectUri("http://localhost/callback")
                .attributes(attributes)
                .build()
        Mockito.`when`(UserServiceConfig.REPOSITORY.removeAuthorizationRequest(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(authorizationRequest)
        Mockito.`when`(UserServiceConfig.CLIENT.getTokenResponse(ArgumentMatchers.any()))
                .thenReturn(OAuth2AccessTokenResponse
                        .withToken("token")
                        .tokenType(OAuth2AccessToken.TokenType.BEARER)
                        .build())
        Mockito.`when`(UserServiceConfig.USER_SERVICE.loadUser(ArgumentMatchers.any()))
                .thenReturn(DefaultOAuth2User(listOf(SimpleGrantedAuthority("ROLE_USER")), mapOf(Pair("user", "user")), "user"))

        this.mockMvc.get("/callback") {
            param("code", "auth-code")
            param("state", "test")
        }.andExpect {
            redirectedUrl("/")
        }
    }

    @EnableWebSecurity
    open class UserServiceConfig : WebSecurityConfigurerAdapter() {
        companion object {
            var USER_SERVICE: OAuth2UserService<OAuth2UserRequest, OAuth2User> = mock(OAuth2UserService::class.java) as OAuth2UserService<OAuth2UserRequest, OAuth2User>
            var CLIENT: OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> = mock(OAuth2AccessTokenResponseClient::class.java) as OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>
            var REPOSITORY: AuthorizationRequestRepository<OAuth2AuthorizationRequest> = mock(AuthorizationRequestRepository::class.java) as AuthorizationRequestRepository<OAuth2AuthorizationRequest>
        }

        override fun configure(http: HttpSecurity) {
            http {
                authorizeRequests {
                    authorize(anyRequest, authenticated)
                }
                oauth2Login {
                    userInfoEndpoint {
                        userService = USER_SERVICE
                    }
                    tokenEndpoint {
                        accessTokenResponseClient = CLIENT
                    }
                    authorizationEndpoint {
                        authorizationRequestRepository = REPOSITORY
                    }
                    redirectionEndpoint {
                        baseUri = "/callback"
                    }
                }
            }
        }
    }

    @Configuration
    open class ClientConfig {
        @Bean
        open fun clientRegistrationRepository(): ClientRegistrationRepository {
            return InMemoryClientRegistrationRepository(
                    CommonOAuth2Provider.GOOGLE
                            .getBuilder("google")
                            .registrationId("registrationId")
                            .clientId("clientId")
                            .clientSecret("clientSecret")
                            .build()
            )
        }
    }
}
