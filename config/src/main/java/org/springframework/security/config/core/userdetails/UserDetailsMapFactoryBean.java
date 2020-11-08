package org.springframework.security.config.core.userdetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.memory.UserAttribute;
import org.springframework.security.core.userdetails.memory.UserAttributeEditor;
import org.springframework.util.Assert;

/**
 * Creates a {@code Collection<UserDetails>} from a @{code Map} in the format of
 * <p>
 * <code>
 * username=password[,enabled|disabled],roles...
 * </code>
 * <p>
 * The enabled and disabled properties are optional with enabled being the default. For
 * example:
 * <p>
 * <code>
 * user=password,ROLE_USER
 * admin=secret,ROLE_USER,ROLE_ADMIN
 * disabled_user=does_not_matter,disabled,ROLE_USER
 * </code>
 *
 * @author Rob Winch
 * @since 5.0
 */
public class UserDetailsMapFactoryBean implements FactoryBean<Collection<UserDetails>> {

	private final Map<String, String> userProperties;

	public UserDetailsMapFactoryBean(Map<String, String> userProperties) {
		Assert.notNull(userProperties, "userProperties cannot be null");
		this.userProperties = userProperties;
	}

	@Override
	public Collection<UserDetails> getObject() {
		Collection<UserDetails> users = new ArrayList<>(this.userProperties.size());
		UserAttributeEditor editor = new UserAttributeEditor();
		this.userProperties.forEach((name, property) -> {
			editor.setAsText(property);
			UserAttribute attr = (UserAttribute) editor.getValue();
			Assert.state(attr != null, () -> "The entry with username '" + name + "' and value '" + property
					+ "' could not be converted to a UserDetails.");
			String password = attr.getPassword();
			boolean disabled = !attr.isEnabled();
			List<GrantedAuthority> authorities = attr.getAuthorities();
			users.add(User.withUsername(name).password(password).disabled(disabled).authorities(authorities).build());
		});
		return users;

	}

	@Override
	public Class<?> getObjectType() {
		return Collection.class;
	}

}
