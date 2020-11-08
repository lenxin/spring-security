package org.springframework.security.web.session;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

/**
 * Performs a redirect to a fixed URL when an expired session is detected by the
 * {@code ConcurrentSessionFilter}.
 *
 * @author Marten Deinum
 * @since 4.2.0
 */
public final class SimpleRedirectSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

	private final Log logger = LogFactory.getLog(getClass());

	private final String destinationUrl;

	private final RedirectStrategy redirectStrategy;

	public SimpleRedirectSessionInformationExpiredStrategy(String invalidSessionUrl) {
		this(invalidSessionUrl, new DefaultRedirectStrategy());
	}

	public SimpleRedirectSessionInformationExpiredStrategy(String invalidSessionUrl,
			RedirectStrategy redirectStrategy) {
		Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
		this.destinationUrl = invalidSessionUrl;
		this.redirectStrategy = redirectStrategy;
	}

	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
		this.logger.debug("Redirecting to '" + this.destinationUrl + "'");
		this.redirectStrategy.sendRedirect(event.getRequest(), event.getResponse(), this.destinationUrl);
	}

}
