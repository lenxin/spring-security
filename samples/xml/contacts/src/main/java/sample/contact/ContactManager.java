package sample.contact;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

import java.util.List;

/**
 * Interface for the application's services layer.
 *
 * @author Ben Alex
 */
public interface ContactManager {

	@PreAuthorize("hasPermission(#contact, admin)")
	void addPermission(Contact contact, Sid recipient, Permission permission);

	@PreAuthorize("hasPermission(#contact, admin)")
	void deletePermission(Contact contact, Sid recipient, Permission permission);

	@PreAuthorize("hasRole('ROLE_USER')")
	void create(Contact contact);

	@PreAuthorize("hasPermission(#contact, 'delete') or hasPermission(#contact, admin)")
	void delete(Contact contact);

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, 'read') or hasPermission(filterObject, admin)")
	List<Contact> getAll();

	@PreAuthorize("hasRole('ROLE_USER')")
	List<String> getAllRecipients();

	@PreAuthorize("hasPermission(#id, 'sample.contact.Contact', read) or "
			+ "hasPermission(#id, 'sample.contact.Contact', admin)")
	Contact getById(Long id);

	Contact getRandomContact();
}
