package org.springframework.security.acls.domain;

import org.springframework.security.acls.model.Permission;

/**
 * A test permission.
 *
 * @author Ben Alex
 */
public class SpecialPermission extends BasePermission {

	public static final Permission ENTER = new SpecialPermission(1 << 5, 'E'); // 32

	public static final Permission LEAVE = new SpecialPermission(1 << 6, 'L');

	protected SpecialPermission(int mask, char code) {
		super(mask, code);
	}

}
