package sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration;

/**
 * @author Joe Grandja
 */
// FIXME: Work around https://github.com/spring-projects/spring-boot/issues/14463
@SpringBootApplication(exclude = ReactiveOAuth2ClientAutoConfiguration.class)
public class OAuth2WebClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2WebClientApplication.class, args);
	}
}
