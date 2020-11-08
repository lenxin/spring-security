package org.springframework.security;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

/**
 * @author Ben Alex
 */
@SuppressWarnings("unchecked")
public class MockFilterConfig implements FilterConfig {

	private Map map = new HashMap();

	@Override
	public String getFilterName() {
		throw new UnsupportedOperationException("mock method not implemented");
	}

	@Override
	public String getInitParameter(String arg0) {
		Object result = this.map.get(arg0);
		if (result != null) {
			return (String) result;
		}
		else {
			return null;
		}
	}

	@Override
	public Enumeration getInitParameterNames() {
		throw new UnsupportedOperationException("mock method not implemented");
	}

	@Override
	public ServletContext getServletContext() {
		throw new UnsupportedOperationException("mock method not implemented");
	}

	public void setInitParmeter(String parameter, String value) {
		this.map.put(parameter, value);
	}

}
