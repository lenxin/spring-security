package org.springframework.security.crypto.encrypt;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.junit.Assume;
import org.junit.AssumptionViolatedException;

import org.springframework.security.crypto.encrypt.AesBytesEncryptor.CipherAlgorithm;

public final class CryptoAssumptions {

	private CryptoAssumptions() {
	}

	public static void assumeGCMJCE() {
		assumeAes256(CipherAlgorithm.GCM);
	}

	public static void assumeCBCJCE() {
		assumeAes256(CipherAlgorithm.CBC);
	}

	private static void assumeAes256(CipherAlgorithm cipherAlgorithm) {
		boolean aes256Available = false;
		try {
			Cipher.getInstance(cipherAlgorithm.toString());
			aes256Available = Cipher.getMaxAllowedKeyLength("AES") >= 256;
		}
		catch (NoSuchAlgorithmException ex) {
			throw new AssumptionViolatedException(cipherAlgorithm + " not available, skipping test", ex);
		}
		catch (NoSuchPaddingException ex) {
			throw new AssumptionViolatedException(cipherAlgorithm + " padding not available, skipping test", ex);
		}
		Assume.assumeTrue("AES key length of 256 not allowed, skipping test", aes256Available);
	}

}
