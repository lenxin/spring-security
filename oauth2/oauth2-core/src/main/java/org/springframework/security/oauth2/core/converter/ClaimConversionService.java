package org.springframework.security.oauth2.core.converter;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.security.oauth2.core.ClaimAccessor;

/**
 * A {@link ConversionService} configured with converters that provide type conversion for
 * claim values.
 *
 * @author Joe Grandja
 * @since 5.2
 * @see GenericConversionService
 * @see ClaimAccessor
 */
public final class ClaimConversionService extends GenericConversionService {

	private static volatile ClaimConversionService sharedInstance;

	private ClaimConversionService() {
		addConverters(this);
	}

	/**
	 * Returns a shared instance of {@code ClaimConversionService}.
	 * @return a shared instance of {@code ClaimConversionService}
	 */
	public static ClaimConversionService getSharedInstance() {
		ClaimConversionService sharedInstance = ClaimConversionService.sharedInstance;
		if (sharedInstance == null) {
			synchronized (ClaimConversionService.class) {
				sharedInstance = ClaimConversionService.sharedInstance;
				if (sharedInstance == null) {
					sharedInstance = new ClaimConversionService();
					ClaimConversionService.sharedInstance = sharedInstance;
				}
			}
		}
		return sharedInstance;
	}

	/**
	 * Adds the converters that provide type conversion for claim values to the provided
	 * {@link ConverterRegistry}.
	 * @param converterRegistry the registry of converters to add to
	 */
	public static void addConverters(ConverterRegistry converterRegistry) {
		converterRegistry.addConverter(new ObjectToStringConverter());
		converterRegistry.addConverter(new ObjectToBooleanConverter());
		converterRegistry.addConverter(new ObjectToInstantConverter());
		converterRegistry.addConverter(new ObjectToURLConverter());
		converterRegistry.addConverter(new ObjectToListStringConverter());
		converterRegistry.addConverter(new ObjectToMapStringObjectConverter());
	}

}
