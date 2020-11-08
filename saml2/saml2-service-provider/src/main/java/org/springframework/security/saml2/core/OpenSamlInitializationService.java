package org.springframework.security.saml2.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import javax.xml.XMLConstants;

import net.shibboleth.utilities.java.support.xml.BasicParserPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.config.InitializationService;
import org.opensaml.core.xml.config.XMLObjectProviderRegistry;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;

import org.springframework.security.saml2.Saml2Exception;

/**
 * An initialization service for initializing OpenSAML. Each Spring Security
 * OpenSAML-based component invokes the {@link #initialize()} method at static
 * initialization time.
 *
 * {@link #initialize()} is idempotent and may be safely called in custom classes that
 * need OpenSAML to be initialized in order to function correctly. It's recommended that
 * you call this {@link #initialize()} method when using Spring Security and OpenSAML
 * instead of OpenSAML's {@link InitializationService#initialize()}.
 *
 * The primary purpose of {@link #initialize()} is to prepare OpenSAML's
 * {@link XMLObjectProviderRegistry} with some reasonable defaults. Any changes that
 * Spring Security makes to the registry happen in this method.
 *
 * To override those defaults, call {@link #requireInitialize(Consumer)} and change the
 * registry:
 *
 * <pre>
 * 	static {
 *  	OpenSamlInitializationService.requireInitialize((registry) -> {
 *  	 	registry.setParserPool(...);
 *  		registry.getBuilderFactory().registerBuilder(...);
 *  	});
 *  }
 * </pre>
 *
 * {@link #requireInitialize(Consumer)} may only be called once per application.
 *
 * If the application already initialized OpenSAML before
 * {@link #requireInitialize(Consumer)} was called, then the configuration changes will
 * not be applied and an exception will be thrown. The reason for this is to alert you to
 * the fact that there are likely some initialization ordering problems in your
 * application that would otherwise lead to an unpredictable state.
 *
 * If you must change the registry's configuration in multiple places in your application,
 * you are expected to handle the initialization ordering issues yourself instead of
 * trying to call {@link #requireInitialize(Consumer)} multiple times.
 *
 * @author Josh Cummings
 * @since 5.4
 */
public final class OpenSamlInitializationService {

	private static final Log log = LogFactory.getLog(OpenSamlInitializationService.class);

	private static final AtomicBoolean initialized = new AtomicBoolean(false);

	private OpenSamlInitializationService() {
	}

	/**
	 * Ready OpenSAML for use and configure it with reasonable defaults.
	 *
	 * Initialization is guaranteed to happen only once per application. This method will
	 * passively return {@code false} if initialization already took place earlier in the
	 * application.
	 * @return whether or not initialization was performed. The first thread to initialize
	 * OpenSAML will return {@code true} while the rest will return {@code false}.
	 * @throws Saml2Exception if OpenSAML failed to initialize
	 */
	public static boolean initialize() {
		return initialize((registry) -> {
		});
	}

	/**
	 * Ready OpenSAML for use, configure it with reasonable defaults, and modify the
	 * {@link XMLObjectProviderRegistry} using the provided {@link Consumer}.
	 *
	 * Initialization is guaranteed to happen only once per application. This method will
	 * throw an exception if initialization already took place earlier in the application.
	 * @param registryConsumer the {@link Consumer} to further configure the
	 * {@link XMLObjectProviderRegistry}
	 * @throws Saml2Exception if initialization already happened previously or if OpenSAML
	 * failed to initialize
	 */
	public static void requireInitialize(Consumer<XMLObjectProviderRegistry> registryConsumer) {
		if (!initialize(registryConsumer)) {
			throw new Saml2Exception("OpenSAML was already initialized previously");
		}
	}

	private static boolean initialize(Consumer<XMLObjectProviderRegistry> registryConsumer) {
		if (initialized.compareAndSet(false, true)) {
			log.trace("Initializing OpenSAML");
			try {
				InitializationService.initialize();
			}
			catch (Exception ex) {
				throw new Saml2Exception(ex);
			}
			BasicParserPool parserPool = new BasicParserPool();
			parserPool.setMaxPoolSize(50);
			parserPool.setBuilderFeatures(getParserBuilderFeatures());
			try {
				parserPool.initialize();
			}
			catch (Exception ex) {
				throw new Saml2Exception(ex);
			}
			XMLObjectProviderRegistrySupport.setParserPool(parserPool);
			registryConsumer.accept(ConfigurationService.get(XMLObjectProviderRegistry.class));
			log.debug("Initialized OpenSAML");
			return true;
		}
		log.debug("Refused to re-initialize OpenSAML");
		return false;
	}

	private static Map<String, Boolean> getParserBuilderFeatures() {
		Map<String, Boolean> parserBuilderFeatures = new HashMap<>();
		parserBuilderFeatures.put("http://apache.org/xml/features/disallow-doctype-decl", Boolean.TRUE);
		parserBuilderFeatures.put(XMLConstants.FEATURE_SECURE_PROCESSING, Boolean.TRUE);
		parserBuilderFeatures.put("http://xml.org/sax/features/external-general-entities", Boolean.FALSE);
		parserBuilderFeatures.put("http://apache.org/xml/features/validation/schema/normalized-value", Boolean.FALSE);
		parserBuilderFeatures.put("http://xml.org/sax/features/external-parameter-entities", Boolean.FALSE);
		parserBuilderFeatures.put("http://apache.org/xml/features/dom/defer-node-expansion", Boolean.FALSE);
		return parserBuilderFeatures;
	}

}
