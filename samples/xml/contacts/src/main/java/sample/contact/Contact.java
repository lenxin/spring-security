package sample.contact;

import java.io.Serializable;

/**
 * Represents a contact.
 *
 * @author Ben Alex
 */
public class Contact implements Serializable {


	private Long id;
	private String email;
	private String name;


	public Contact(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public Contact() {
	}


	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + ": ");
		sb.append("Id: " + this.getId() + "; ");
		sb.append("Name: " + this.getName() + "; ");
		sb.append("Email: " + this.getEmail());

		return sb.toString();
	}
}
