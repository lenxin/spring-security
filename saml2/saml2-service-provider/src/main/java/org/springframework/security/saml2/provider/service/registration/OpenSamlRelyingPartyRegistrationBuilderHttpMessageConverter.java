package org.springframework.security.saml2.provider.service.registration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.saml2.core.OpenSamlInitializationService;

/**
 * An {@link HttpMessageConverter} that takes an {@code IDPSSODescriptor} in an HTTP
 * response and converts it into a {@link RelyingPartyRegistration.Builder}.
 *
 * The primary use case for this is constructing a {@link RelyingPartyRegistration} for
 * inclusion in a {@link RelyingPartyRegistrationRepository}. To do so, you can include an
 * instance of this converter in a {@link org.springframework.web.client.RestOperations}
 * like so:
 *
 * <pre>
 * 		RestOperations rest = new RestTemplate(Collections.singletonList(
 *     			new RelyingPartyRegistrationsBuilderHttpMessageConverter()));
 * 		RelyingPartyRegistration.Builder builder = rest.getForObject
 * 				("https://idp.example.org/metadata", RelyingPartyRegistration.Builder.class);
 * 		RelyingPartyRegistration registration = builder.registrationId("registration-id").build();
 * </pre>
 *
 * Note that this will only configure the asserting party (IDP) half of the
 * {@link RelyingPartyRegistration}, meaning where and how to send AuthnRequests, how to
 * verify Assertions, etc.
 *
 * To further configure the {@link RelyingPartyRegistration} with relying party (SP)
 * information, you may invoke the appropriate methods on the builder.
 *
 * @author Josh Cummings
 * @since 5.4
 */
public class OpenSamlRelyingPartyRegistrationBuilderHttpMessageConverter
		implements HttpMessageConverter<RelyingPartyRegistration.Builder> {

	static {
		OpenSamlInitializationService.initialize();
	}

	private final OpenSamlAssertingPartyMetadataConverter converter;

	/**
	 * Creates a {@link OpenSamlRelyingPartyRegistrationBuilderHttpMessageConverter}
	 */
	public OpenSamlRelyingPartyRegistrationBuilderHttpMessageConverter() {
		this.converter = new OpenSamlAssertingPartyMetadataConverter();
	}

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return RelyingPartyRegistration.Builder.class.isAssignableFrom(clazz);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return false;
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return Arrays.asList(MediaType.APPLICATION_XML, MediaType.TEXT_XML);
	}

	@Override
	public RelyingPartyRegistration.Builder read(Class<? extends RelyingPartyRegistration.Builder> clazz,
			HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		return this.converter.convert(inputMessage.getBody());
	}

	@Override
	public void write(RelyingPartyRegistration.Builder builder, MediaType contentType, HttpOutputMessage outputMessage)
			throws HttpMessageNotWritableException {
		throw new HttpMessageNotWritableException("This converter cannot write a RelyingPartyRegistration.Builder");
	}

}
