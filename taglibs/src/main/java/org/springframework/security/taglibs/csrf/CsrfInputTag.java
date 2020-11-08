package org.springframework.security.taglibs.csrf;

import org.springframework.security.web.csrf.CsrfToken;

/**
 * A JSP tag that prints out a hidden form field for the CSRF token. See the JSP Tab
 * Library documentation for more information.
 *
 * @author Nick Williams
 * @since 3.2.2
 */
public class CsrfInputTag extends AbstractCsrfTag {

	@Override
	public String handleToken(CsrfToken token) {
		return "<input type=\"hidden\" name=\"" + token.getParameterName() + "\" value=\"" + token.getToken() + "\" />";
	}

}
