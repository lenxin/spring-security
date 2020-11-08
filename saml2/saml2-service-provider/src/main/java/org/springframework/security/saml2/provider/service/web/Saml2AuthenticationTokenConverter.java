package org.springframework.security.saml2.provider.service.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.saml2.Saml2Exception;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticationToken;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.Assert;

/**
 * An {@link AuthenticationConverter} that generates a {@link Saml2AuthenticationToken}
 * appropriate for authenticated a SAML 2.0 Assertion against an
 * {@link org.springframework.security.authentication.AuthenticationManager}.
 *
 * @author Josh Cummings
 * @since 5.4
 */
public final class Saml2AuthenticationTokenConverter implements AuthenticationConverter {

	private static Base64 BASE64 = new Base64(0, new byte[] { '\n' });

	private final Converter<HttpServletRequest, RelyingPartyRegistration> relyingPartyRegistrationResolver;

	/**
	 * Constructs a {@link Saml2AuthenticationTokenConverter} given a strategy for
	 * resolving {@link RelyingPartyRegistration}s
	 * @param relyingPartyRegistrationResolver the strategy for resolving
	 * {@link RelyingPartyRegistration}s
	 */
	public Saml2AuthenticationTokenConverter(
			Converter<HttpServletRequest, RelyingPartyRegistration> relyingPartyRegistrationResolver) {
		Assert.notNull(relyingPartyRegistrationResolver, "relyingPartyRegistrationResolver cannot be null");
		this.relyingPartyRegistrationResolver = relyingPartyRegistrationResolver;
	}

	@Override
	public Saml2AuthenticationToken convert(HttpServletRequest request) {
		RelyingPartyRegistration relyingPartyRegistration = this.relyingPartyRegistrationResolver.convert(request);
		if (relyingPartyRegistration == null) {
			return null;
		}
		String saml2Response = request.getParameter("SAMLResponse");
		if (saml2Response == null) {
			return null;
		}
		byte[] b = samlDecode(saml2Response);
		saml2Response = inflateIfRequired(request, b);
		return new Saml2AuthenticationToken(relyingPartyRegistration, saml2Response);
	}

	private String inflateIfRequired(HttpServletRequest request, byte[] b) {
		if (HttpMethod.GET.matches(request.getMethod())) {
			return samlInflate(b);
		}
		return new String(b, StandardCharsets.UTF_8);
	}

	private byte[] samlDecode(String s) {
		return BASE64.decode(s);
	}

	private String samlInflate(byte[] b) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InflaterOutputStream inflaterOutputStream = new InflaterOutputStream(out, new Inflater(true));
			inflaterOutputStream.write(b);
			inflaterOutputStream.finish();
			return new String(out.toByteArray(), StandardCharsets.UTF_8);
		}
		catch (IOException ex) {
			throw new Saml2Exception("Unable to inflate string", ex);
		}
	}

}
