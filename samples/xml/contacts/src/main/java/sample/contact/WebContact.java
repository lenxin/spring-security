package sample.contact;

/**
 * An object that represents user-editable sections of a {@link Contact}.
 *
 * @author Ben Alex
 */
public class WebContact {


	private String email;
	private String name;


	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}
}
