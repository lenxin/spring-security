package sample.aspectj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author Rob Winch
 */
@EnableGlobalMethodSecurity(mode = AdviceMode.ASPECTJ, securedEnabled = true)
public class AspectjSecurityConfig {
	@Bean
	public Service service() {
		return new Service();
	}

	@Bean
	public SecuredService securedService() {
		return new SecuredService();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication();
	}
}
