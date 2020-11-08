package org.springframework.security.core.userdetails.memory;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link UserAttributeEditor} and associated {@link UserAttribute}.
 *
 * @author Ben Alex
 */
public class UserAttributeEditorTests {

	@Test
	public void testCorrectOperationWithTrailingSpaces() {
		UserAttributeEditor editor = new UserAttributeEditor();
		editor.setAsText("password ,ROLE_ONE,ROLE_TWO ");
		UserAttribute user = (UserAttribute) editor.getValue();
		assertThat(user.getPassword()).isEqualTo("password");
		assertThat(user.getAuthorities()).hasSize(2);
		assertThat(user.getAuthorities().get(0).getAuthority()).isEqualTo("ROLE_ONE");
		assertThat(user.getAuthorities().get(1).getAuthority()).isEqualTo("ROLE_TWO");
	}

	@Test
	public void testCorrectOperationWithoutEnabledDisabledKeyword() {
		UserAttributeEditor editor = new UserAttributeEditor();
		editor.setAsText("password,ROLE_ONE,ROLE_TWO");
		UserAttribute user = (UserAttribute) editor.getValue();
		assertThat(user.isValid()).isTrue();
		assertThat(user.isEnabled()).isTrue(); // default
		assertThat(user.getPassword()).isEqualTo("password");
		assertThat(user.getAuthorities()).hasSize(2);
		assertThat(user.getAuthorities().get(0).getAuthority()).isEqualTo("ROLE_ONE");
		assertThat(user.getAuthorities().get(1).getAuthority()).isEqualTo("ROLE_TWO");
	}

	@Test
	public void testDisabledKeyword() {
		UserAttributeEditor editor = new UserAttributeEditor();
		editor.setAsText("password,disabled,ROLE_ONE,ROLE_TWO");
		UserAttribute user = (UserAttribute) editor.getValue();
		assertThat(user.isValid()).isTrue();
		assertThat(!user.isEnabled()).isTrue();
		assertThat(user.getPassword()).isEqualTo("password");
		assertThat(user.getAuthorities()).hasSize(2);
		assertThat(user.getAuthorities().get(0).getAuthority()).isEqualTo("ROLE_ONE");
		assertThat(user.getAuthorities().get(1).getAuthority()).isEqualTo("ROLE_TWO");
	}

	@Test
	public void testEmptyStringReturnsNull() {
		UserAttributeEditor editor = new UserAttributeEditor();
		editor.setAsText("");
		UserAttribute user = (UserAttribute) editor.getValue();
		assertThat(user == null).isTrue();
	}

	@Test
	public void testEnabledKeyword() {
		UserAttributeEditor editor = new UserAttributeEditor();
		editor.setAsText("password,ROLE_ONE,enabled,ROLE_TWO");
		UserAttribute user = (UserAttribute) editor.getValue();
		assertThat(user.isValid()).isTrue();
		assertThat(user.isEnabled()).isTrue();
		assertThat(user.getPassword()).isEqualTo("password");
		assertThat(user.getAuthorities()).hasSize(2);
		assertThat(user.getAuthorities().get(0).getAuthority()).isEqualTo("ROLE_ONE");
		assertThat(user.getAuthorities().get(1).getAuthority()).isEqualTo("ROLE_TWO");
	}

	@Test
	public void testMalformedStringReturnsNull() {
		UserAttributeEditor editor = new UserAttributeEditor();
		editor.setAsText("MALFORMED_STRING");
		UserAttribute user = (UserAttribute) editor.getValue();
		assertThat(user == null).isTrue();
	}

	@Test
	public void testNoPasswordOrRolesReturnsNull() {
		UserAttributeEditor editor = new UserAttributeEditor();
		editor.setAsText("disabled");
		UserAttribute user = (UserAttribute) editor.getValue();
		assertThat(user == null).isTrue();
	}

	@Test
	public void testNoRolesReturnsNull() {
		UserAttributeEditor editor = new UserAttributeEditor();
		editor.setAsText("password,enabled");
		UserAttribute user = (UserAttribute) editor.getValue();
		assertThat(user == null).isTrue();
	}

	@Test
	public void testNullReturnsNull() {
		UserAttributeEditor editor = new UserAttributeEditor();
		editor.setAsText(null);
		UserAttribute user = (UserAttribute) editor.getValue();
		assertThat(user == null).isTrue();
	}

}
