package sample;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Josh Cummings
 */
@RestController
public class OAuth2ResourceServerController {

	@GetMapping("/")
	public String index(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal token, @RequestHeader("tenant") String tenant) {
		String subject = token.getAttribute("sub");
		return String.format("Hello, %s for tenant %s!", subject, tenant);
	}

	@GetMapping("/message")
	public String message(@RequestHeader("tenant") String tenant) {
		return String.format("secret message for tenant %s", tenant);
	}
}
