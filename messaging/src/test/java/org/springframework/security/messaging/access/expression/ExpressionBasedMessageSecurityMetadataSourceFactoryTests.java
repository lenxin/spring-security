package org.springframework.security.messaging.access.expression;

import java.util.Collection;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.messaging.Message;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.messaging.access.intercept.MessageSecurityMetadataSource;
import org.springframework.security.messaging.util.matcher.MessageMatcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionBasedMessageSecurityMetadataSourceFactoryTests {

	@Mock
	MessageMatcher<Object> matcher1;

	@Mock
	MessageMatcher<Object> matcher2;

	@Mock
	Message<Object> message;

	@Mock
	Authentication authentication;

	String expression1;

	String expression2;

	LinkedHashMap<MessageMatcher<?>, String> matcherToExpression;

	MessageSecurityMetadataSource source;

	MessageSecurityExpressionRoot rootObject;

	@Before
	public void setup() {
		this.expression1 = "permitAll";
		this.expression2 = "denyAll";
		this.matcherToExpression = new LinkedHashMap<>();
		this.matcherToExpression.put(this.matcher1, this.expression1);
		this.matcherToExpression.put(this.matcher2, this.expression2);
		this.source = ExpressionBasedMessageSecurityMetadataSourceFactory
				.createExpressionMessageMetadataSource(this.matcherToExpression);
		this.rootObject = new MessageSecurityExpressionRoot(this.authentication, this.message);
	}

	@Test
	public void createExpressionMessageMetadataSourceNoMatch() {
		Collection<ConfigAttribute> attrs = this.source.getAttributes(this.message);
		assertThat(attrs).isNull();
	}

	@Test
	public void createExpressionMessageMetadataSourceMatchFirst() {
		given(this.matcher1.matches(this.message)).willReturn(true);
		Collection<ConfigAttribute> attrs = this.source.getAttributes(this.message);
		assertThat(attrs).hasSize(1);
		ConfigAttribute attr = attrs.iterator().next();
		assertThat(attr).isInstanceOf(MessageExpressionConfigAttribute.class);
		assertThat(((MessageExpressionConfigAttribute) attr).getAuthorizeExpression().getValue(this.rootObject))
				.isEqualTo(true);
	}

	@Test
	public void createExpressionMessageMetadataSourceMatchSecond() {
		given(this.matcher2.matches(this.message)).willReturn(true);
		Collection<ConfigAttribute> attrs = this.source.getAttributes(this.message);
		assertThat(attrs).hasSize(1);
		ConfigAttribute attr = attrs.iterator().next();
		assertThat(attr).isInstanceOf(MessageExpressionConfigAttribute.class);
		assertThat(((MessageExpressionConfigAttribute) attr).getAuthorizeExpression().getValue(this.rootObject))
				.isEqualTo(false);
	}

}
