package sample.contact;

import org.springframework.security.acls.domain.BasePermission;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates {@link AddPermission}.
 *
 * @author Ben Alex
 */
public class AddPermissionValidator implements Validator {


	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return clazz.equals(AddPermission.class);
	}

	public void validate(Object obj, Errors errors) {
		AddPermission addPermission = (AddPermission) obj;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "permission", "err.permission",
				"Permission is required. *");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "recipient", "err.recipient",
				"Recipient is required. *");

		if (addPermission.getPermission() != null) {
			int permission = addPermission.getPermission();

			if ((permission != BasePermission.ADMINISTRATION.getMask())
					&& (permission != BasePermission.READ.getMask())
					&& (permission != BasePermission.DELETE.getMask())) {
				errors.rejectValue("permission", "err.permission.invalid",
						"The indicated permission is invalid. *");
			}
		}

		if (addPermission.getRecipient() != null) {
			if (addPermission.getRecipient().length() > 100) {
				errors.rejectValue("recipient", "err.recipient.length",
						"The recipient is too long (maximum 100 characters). *");
			}
		}
	}
}
