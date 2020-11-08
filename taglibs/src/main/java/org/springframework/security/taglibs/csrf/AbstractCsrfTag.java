package org.springframework.security.taglibs.csrf;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.security.web.csrf.CsrfToken;

/**
 * An abstract tag for handling CSRF operations.
 *
 * @author Nick Williams
 * @since 3.2.2
 */
abstract class AbstractCsrfTag extends TagSupport {

	@Override
	public int doEndTag() throws JspException {
		CsrfToken token = (CsrfToken) this.pageContext.getRequest().getAttribute(CsrfToken.class.getName());
		if (token != null) {
			try {
				this.pageContext.getOut().write(this.handleToken(token));
			}
			catch (IOException ex) {
				throw new JspException(ex);
			}
		}
		return EVAL_PAGE;
	}

	protected abstract String handleToken(CsrfToken token);

}
