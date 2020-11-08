package org.springframework.security.samples

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.security.test.context.support.WithMockUser

@SpringBootTest
class KotlinWebfluxApplicationTests {

    lateinit var rest: WebTestClient

    @Autowired
    fun setup(context: ApplicationContext) {
        rest = WebTestClient
                .bindToApplicationContext(context)
                .configureClient()
                .build()
    }

    @Test
    fun `index page is not protected`() {
        rest
                .get()
                .uri("/")
                .exchange()
                .expectStatus().isOk
    }

    @Test
    fun `protected page when unauthenticated then redirects to login `() {
        rest
                .get()
                .uri("/user/index")
                .exchange()
                .expectStatus().is3xxRedirection
                .expectHeader().valueEquals("Location", "/log-in")
    }

    @Test
    @WithMockUser
    fun `protected page can be accessed when authenticated`() {
        rest
                .get()
                .uri("/user/index")
                .exchange()
                .expectStatus().isOk
    }
}
