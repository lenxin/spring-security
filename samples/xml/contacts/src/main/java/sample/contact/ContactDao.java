package sample.contact;

import java.util.List;

/**
 * Provides access to the application's persistence layer.
 *
 * @author Ben Alex
 */
public interface ContactDao {


	void create(Contact contact);

	void delete(Long contactId);

	List<Contact> findAll();

	List<String> findAllPrincipals();

	List<String> findAllRoles();

	Contact getById(Long id);

	void update(Contact contact);
}
