package org.springframework.security.core.userdetails.memory;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * Property editor that creates a {@link UserAttribute} from a comma separated list of
 * values.
 *
 * @author Ben Alex
 */
public class UserAttributeEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String s) throws IllegalArgumentException {
		if (!StringUtils.hasText(s)) {
			setValue(null);
			return;
		}
		String[] tokens = StringUtils.commaDelimitedListToStringArray(s);
		UserAttribute userAttrib = new UserAttribute();
		List<String> authoritiesAsStrings = new ArrayList<>();
		for (int i = 0; i < tokens.length; i++) {
			String currentToken = tokens[i].trim();
			if (i == 0) {
				userAttrib.setPassword(currentToken);
			}
			else {
				if (currentToken.toLowerCase().equals("enabled")) {
					userAttrib.setEnabled(true);
				}
				else if (currentToken.toLowerCase().equals("disabled")) {
					userAttrib.setEnabled(false);
				}
				else {
					authoritiesAsStrings.add(currentToken);
				}
			}
		}
		userAttrib.setAuthoritiesAsString(authoritiesAsStrings);
		setValue(userAttrib.isValid() ? userAttrib : null);
	}

}
