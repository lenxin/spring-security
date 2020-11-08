package org.springframework.security.web.servlet.support.csrf;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

/**
 * Integration with Spring Web MVC that automatically adds the {@link CsrfToken} into
 * forms with hidden inputs when using Spring tag libraries.
 *
 * @author Rob Winch
 * @since 3.2
 */
public final class CsrfRequestDataValueProcessor implements RequestDataValueProcessor {

	private Pattern DISABLE_CSRF_TOKEN_PATTERN = Pattern.compile("(?i)^(GET|HEAD|TRACE|OPTIONS)$");

	private String DISABLE_CSRF_TOKEN_ATTR = "DISABLE_CSRF_TOKEN_ATTR";

	public String processAction(HttpServletRequest request, String action) {
		return action;
	}

	@Override
	public String processAction(HttpServletRequest request, String action, String method) {
		if (method != null && this.DISABLE_CSRF_TOKEN_PATTERN.matcher(method).matches()) {
			request.setAttribute(this.DISABLE_CSRF_TOKEN_ATTR, Boolean.TRUE);
		}
		else {
			request.removeAttribute(this.DISABLE_CSRF_TOKEN_ATTR);
		}
		return action;
	}

	@Override
	public String processFormFieldValue(HttpServletRequest request, String name, String value, String type) {
		return value;
	}

	@Override
	public Map<String, String> getExtraHiddenFields(HttpServletRequest request) {
		if (Boolean.TRUE.equals(request.getAttribute(this.DISABLE_CSRF_TOKEN_ATTR))) {
			request.removeAttribute(this.DISABLE_CSRF_TOKEN_ATTR);
			return Collections.emptyMap();
		}
		CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		if (token == null) {
			return Collections.emptyMap();
		}
		Map<String, String> hiddenFields = new HashMap<>(1);
		hiddenFields.put(token.getParameterName(), token.getToken());
		return hiddenFields;
	}

	@Override
	public String processUrl(HttpServletRequest request, String url) {
		return url;
	}

}
