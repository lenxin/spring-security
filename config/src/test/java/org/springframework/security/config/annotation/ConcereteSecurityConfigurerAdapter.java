package org.springframework.security.config.annotation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rob Winch
 *
 */
class ConcereteSecurityConfigurerAdapter extends SecurityConfigurerAdapter<Object, SecurityBuilder<Object>> {

	private List<Object> list = new ArrayList<>();

	@Override
	public void configure(SecurityBuilder<Object> builder) {
		this.list = postProcess(this.list);
	}

	ConcereteSecurityConfigurerAdapter list(List<Object> l) {
		this.list = l;
		return this;
	}

}
