package org.springframework.security.web.access.expression;

import java.util.Collection;
import java.util.LinkedHashMap;

import org.junit.Test;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Luke Taylor
 */
public class ExpressionBasedFilterInvocationSecurityMetadataSourceTests {

	@Test
	public void expectedAttributeIsReturned() {
		final String expression = "hasRole('X')";
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<>();
		requestMap.put(AnyRequestMatcher.INSTANCE, SecurityConfig.createList(expression));
		ExpressionBasedFilterInvocationSecurityMetadataSource mds = new ExpressionBasedFilterInvocationSecurityMetadataSource(
				requestMap, new DefaultWebSecurityExpressionHandler());
		assertThat(mds.getAllConfigAttributes()).hasSize(1);
		Collection<ConfigAttribute> attrs = mds.getAttributes(new FilterInvocation("/path", "GET"));
		assertThat(attrs).hasSize(1);
		WebExpressionConfigAttribute attribute = (WebExpressionConfigAttribute) attrs.toArray()[0];
		assertThat(attribute.getAttribute()).isNull();
		assertThat(attribute.getAuthorizeExpression().getExpressionString()).isEqualTo(expression);
		assertThat(attribute.toString()).isEqualTo(expression);
	}

	@Test
	public void invalidExpressionIsRejected() {
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<>();
		requestMap.put(AnyRequestMatcher.INSTANCE, SecurityConfig.createList("hasRole('X'"));
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new ExpressionBasedFilterInvocationSecurityMetadataSource(requestMap,
						new DefaultWebSecurityExpressionHandler()));
	}

}
