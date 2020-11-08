package sample.contact;

import org.springframework.security.acls.domain.BasePermission;

/**
 * Model object for add permission use case.
 *
 * @author Ben Alex
 */
public class AddPermission {


	public Contact contact;
	public Integer permission = BasePermission.READ.getMask();
	public String recipient;


	public Contact getContact() {
		return contact;
	}

	public Integer getPermission() {
		return permission;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
}
