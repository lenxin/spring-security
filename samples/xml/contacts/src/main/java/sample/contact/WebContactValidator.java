package sample.contact;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validates {@link WebContact}.
 *
 * @author Ben Alex
 */
public class WebContactValidator implements Validator {


	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return clazz.equals(WebContact.class);
	}

	public void validate(Object obj, Errors errors) {
		WebContact wc = (WebContact) obj;

		if ((wc.getName() == null) || (wc.getName().length() < 3)
				|| (wc.getName().length() > 50)) {
			errors.rejectValue("name", "err.name", "Name 3-50 characters is required. *");
		}

		if ((wc.getEmail() == null) || (wc.getEmail().length() < 3)
				|| (wc.getEmail().length() > 50)) {
			errors.rejectValue("email", "err.email",
					"Email 3-50 characters is required. *");
		}
	}
}
