package org.springframework.security.saml2.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

import org.apache.commons.codec.binary.Base64;

import org.springframework.security.saml2.Saml2Exception;

public final class Saml2Utils {

	private static Base64 BASE64 = new Base64(0, new byte[] { '\n' });

	private Saml2Utils() {
	}

	public static String samlEncode(byte[] b) {
		return BASE64.encodeAsString(b);
	}

	public static byte[] samlDecode(String s) {
		return BASE64.decode(s);
	}

	public static byte[] samlDeflate(String s) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(out,
					new Deflater(Deflater.DEFLATED, true));
			deflaterOutputStream.write(s.getBytes(StandardCharsets.UTF_8));
			deflaterOutputStream.finish();
			return out.toByteArray();
		}
		catch (IOException ex) {
			throw new Saml2Exception("Unable to deflate string", ex);
		}
	}

	public static String samlInflate(byte[] b) {
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
