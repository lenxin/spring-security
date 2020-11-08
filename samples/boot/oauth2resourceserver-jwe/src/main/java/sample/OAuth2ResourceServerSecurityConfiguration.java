package sample;

import java.net.URL;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWEDecryptionKeySelector;
import com.nimbusds.jose.proc.JWEKeySelector;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.nimbusds.jwt.proc.JWTProcessor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Josh Cummings
 */
@EnableWebSecurity
public class OAuth2ResourceServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final JWSAlgorithm jwsAlgorithm = JWSAlgorithm.RS256;
	private final JWEAlgorithm jweAlgorithm = JWEAlgorithm.RSA_OAEP_256;
	private final EncryptionMethod encryptionMethod = EncryptionMethod.A256GCM;

	@Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
	URL jwkSetUri;

	@Value("${sample.jwe-key-value}")
	RSAPrivateKey key;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.authorizeRequests((authorizeRequests) ->
				authorizeRequests
					.antMatchers("/message/**").hasAuthority("SCOPE_message:read")
					.anyRequest().authenticated()
			)
			.oauth2ResourceServer((oauth2ResourceServer) ->
				oauth2ResourceServer
					.jwt(withDefaults())
			);
		// @formatter:on
	}

	@Bean
	JwtDecoder jwtDecoder() {
		return new NimbusJwtDecoder(jwtProcessor());
	}

	private JWTProcessor<SecurityContext> jwtProcessor() {
		JWKSource<SecurityContext> jwsJwkSource = new RemoteJWKSet<>(this.jwkSetUri);
		JWSKeySelector<SecurityContext> jwsKeySelector =
				new JWSVerificationKeySelector<>(this.jwsAlgorithm, jwsJwkSource);

		JWKSource<SecurityContext> jweJwkSource = new ImmutableJWKSet<>(new JWKSet(rsaKey()));
		JWEKeySelector<SecurityContext> jweKeySelector =
				new JWEDecryptionKeySelector<>(this.jweAlgorithm, this.encryptionMethod, jweJwkSource);

		ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
		jwtProcessor.setJWSKeySelector(jwsKeySelector);
		jwtProcessor.setJWEKeySelector(jweKeySelector);

		return jwtProcessor;
	}

	private RSAKey rsaKey() {
		RSAPrivateCrtKey crtKey = (RSAPrivateCrtKey) this.key;
		Base64URL n = Base64URL.encode(crtKey.getModulus());
		Base64URL e = Base64URL.encode(crtKey.getPublicExponent());
		return new RSAKey.Builder(n, e)
				.privateKey(this.key)
				.keyUse(KeyUse.ENCRYPTION)
				.build();
	}
}
