package org.springframework.security.config.annotation.method.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AutoProxyRegistrar;

/**
 * @author Rob Winch
 * @since 5.0
 */
class ReactiveMethodSecuritySelector extends AdviceModeImportSelector<EnableReactiveMethodSecurity> {

	@Override
	protected String[] selectImports(AdviceMode adviceMode) {
		if (adviceMode == AdviceMode.PROXY) {
			return getProxyImports();
		}
		throw new IllegalStateException("AdviceMode " + adviceMode + " is not supported");
	}

	/**
	 * Return the imports to use if the {@link AdviceMode} is set to
	 * {@link AdviceMode#PROXY}.
	 * <p>
	 * Take care of adding the necessary JSR-107 import if it is available.
	 */
	private String[] getProxyImports() {
		List<String> result = new ArrayList<>();
		result.add(AutoProxyRegistrar.class.getName());
		result.add(ReactiveMethodSecurityConfiguration.class.getName());
		return result.toArray(new String[0]);
	}

}
