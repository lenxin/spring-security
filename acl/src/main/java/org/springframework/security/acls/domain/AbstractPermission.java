package org.springframework.security.acls.domain;

import org.springframework.security.acls.model.Permission;

/**
 * Provides an abstract superclass for {@link Permission} implementations.
 *
 * @author Ben Alex
 * @since 2.0.3
 */
public abstract class AbstractPermission implements Permission {

	protected final char code;

	protected int mask;

	/**
	 * Sets the permission mask and uses the '*' character to represent active bits when
	 * represented as a bit pattern string.
	 * @param mask the integer bit mask for the permission
	 */
	protected AbstractPermission(int mask) {
		this.mask = mask;
		this.code = '*';
	}

	/**
	 * Sets the permission mask and uses the specified character for active bits.
	 * @param mask the integer bit mask for the permission
	 * @param code the character to print for each active bit in the mask (see
	 * {@link Permission#getPattern()})
	 */
	protected AbstractPermission(int mask, char code) {
		this.mask = mask;
		this.code = code;
	}

	@Override
	public final boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Permission)) {
			return false;
		}
		Permission other = (Permission) obj;
		return (this.mask == other.getMask());
	}

	@Override
	public final int hashCode() {
		return this.mask;
	}

	@Override
	public final String toString() {
		return this.getClass().getSimpleName() + "[" + getPattern() + "=" + this.mask + "]";
	}

	@Override
	public final int getMask() {
		return this.mask;
	}

	@Override
	public String getPattern() {
		return AclFormattingUtils.printBinary(this.mask, this.code);
	}

}
