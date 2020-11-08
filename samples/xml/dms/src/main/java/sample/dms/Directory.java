package sample.dms;

/**
 *
 * @author Ben Alex
 *
 */
public class Directory extends AbstractElement {
	public static final Directory ROOT_DIRECTORY = new Directory();

	private Directory() {
	}

	public Directory(String name, Directory parent) {
		super(name, parent);
	}

	@Override
	public String toString() {
		return "Directory[fullName='" + getFullName() + "'; name='" + getName()
				+ "'; id='" + getId() + "'; parent='" + getParent() + "']";
	}

}
