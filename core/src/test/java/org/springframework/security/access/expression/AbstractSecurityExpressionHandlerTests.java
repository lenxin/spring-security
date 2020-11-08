package org.springframework.security.access.expression;

import org.junit.Before;
import org.junit.Test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;

/**
 * @author Luke Taylor
 */
public class AbstractSecurityExpressionHandlerTests {

	private AbstractSecurityExpressionHandler<Object> handler;

	@Before
	public void setUp() {
		this.handler = new AbstractSecurityExpressionHandler<Object>() {
			@Override
			protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
					Object o) {
				return new SecurityExpressionRoot(authentication) {
				};
			}
		};
	}

	@Test
	public void beanNamesAreCorrectlyResolved() {
		this.handler.setApplicationContext(new AnnotationConfigApplicationContext(TestConfiguration.class));
		Expression expression = this.handler.getExpressionParser()
				.parseExpression("@number10.compareTo(@number20) < 0");
		assertThat(expression.getValue(this.handler.createEvaluationContext(mock(Authentication.class), new Object())))
				.isEqualTo(true);
	}

	@Test
	public void setExpressionParserNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> this.handler.setExpressionParser(null));
	}

	@Test
	public void setExpressionParser() {
		SpelExpressionParser parser = new SpelExpressionParser();
		this.handler.setExpressionParser(parser);
		assertThat(parser == this.handler.getExpressionParser()).isTrue();
	}

	@Configuration
	static class TestConfiguration {

		@Bean
		Integer number10() {
			return 10;
		}

		@Bean
		Integer number20() {
			return 20;
		}

	}

}
