package org.springframework.security.config.http;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.beans.factory.xml.ParserContext;

import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(PowerMockRunner.class)
@PrepareOnlyThisForTest(ParserContext.class)
public class WebConfigUtilsTests {

	public static final String URL = "/url";

	@Mock
	private ParserContext parserContext;

	// SEC-1980
	@Test
	public void validateHttpRedirectSpELNoParserWarning() {
		WebConfigUtils.validateHttpRedirect("#{T(org.springframework.security.config.http.WebConfigUtilsTest).URL}",
				this.parserContext, "fakeSource");
		verifyZeroInteractions(this.parserContext);
	}

}
