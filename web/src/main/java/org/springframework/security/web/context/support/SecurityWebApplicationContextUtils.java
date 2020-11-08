package org.springframework.security.web.context.support;

import java.util.Enumeration;

import javax.servlet.ServletContext;

import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Spring Security extension to Spring's {@link WebApplicationContextUtils}.
 *
 * @author Rob Winch
 */
public abstract class SecurityWebApplicationContextUtils extends WebApplicationContextUtils {

	/**
	 * Find a unique {@code WebApplicationContext} for this web app: either the root web
	 * app context (preferred) or a unique {@code WebApplicationContext} among the
	 * registered {@code ServletContext} attributes (typically coming from a single
	 * {@code DispatcherServlet} in the current web application).
	 * <p>
	 * Note that {@code DispatcherServlet}'s exposure of its context can be controlled
	 * through its {@code publishContext} property, which is {@code true} by default but
	 * can be selectively switched to only publish a single context despite multiple
	 * {@code DispatcherServlet} registrations in the web app.
	 * @param servletContext ServletContext to find the web application context for
	 * @return the desired WebApplicationContext for this web app
	 * @throws IllegalStateException if no WebApplicationContext can be found
	 * @see #getWebApplicationContext(ServletContext)
	 * @see ServletContext#getAttributeNames()
	 */
	public static WebApplicationContext findRequiredWebApplicationContext(ServletContext servletContext) {
		WebApplicationContext webApplicationContext = compatiblyFindWebApplicationContext(servletContext);
		Assert.state(webApplicationContext != null,
				"No WebApplicationContext found: no ContextLoaderListener registered?");
		return webApplicationContext;
	}

	/**
	 * Copy of {@link #findWebApplicationContext(ServletContext)} for compatibility with
	 * spring framework 4.1.x.
	 * @see #findWebApplicationContext(ServletContext)
	 */
	private static WebApplicationContext compatiblyFindWebApplicationContext(ServletContext sc) {
		WebApplicationContext webApplicationContext = getWebApplicationContext(sc);
		if (webApplicationContext == null) {
			Enumeration<String> attrNames = sc.getAttributeNames();
			while (attrNames.hasMoreElements()) {
				String attrName = attrNames.nextElement();
				Object attrValue = sc.getAttribute(attrName);
				if (attrValue instanceof WebApplicationContext) {
					Assert.state(webApplicationContext == null, "No unique WebApplicationContext found: more than one "
							+ "DispatcherServlet registered with publishContext=true?");
					webApplicationContext = (WebApplicationContext) attrValue;
				}
			}
		}
		return webApplicationContext;
	}

}
