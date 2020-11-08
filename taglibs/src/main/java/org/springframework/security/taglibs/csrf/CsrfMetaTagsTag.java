package org.springframework.security.taglibs.csrf;

import org.springframework.security.web.csrf.CsrfToken;

/**
 * A JSP tag that prints out a meta tags holding the CSRF form field name and token value
 * for use in JavaScrip code. See the JSP Tab Library documentation for more information.
 *
 * @author Nick Williams
 * @since 3.2.2
 */
public class CsrfMetaTagsTag extends AbstractCsrfTag {

	@Override
	public String handleToken(CsrfToken token) {
		return "<meta name=\"_csrf_parameter\" content=\"" + token.getParameterName() + "\" />"
				+ "<meta name=\"_csrf_header\" content=\"" + token.getHeaderName() + "\" />"
				+ "<meta name=\"_csrf\" content=\"" + token.getToken() + "\" />";
	}

}
