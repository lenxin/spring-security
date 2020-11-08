package org.springframework.security.config.core.userdetails;

import java.util.Collection;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.util.InMemoryResource;

/**
 * Constructs an {@link MapReactiveUserDetailsService} from a resource using
 * {@link UserDetailsResourceFactoryBean}.
 *
 * @author Rob Winch
 * @since 5.0
 * @see UserDetailsResourceFactoryBean
 */
public class ReactiveUserDetailsServiceResourceFactoryBean
		implements ResourceLoaderAware, FactoryBean<MapReactiveUserDetailsService> {

	private UserDetailsResourceFactoryBean userDetails = new UserDetailsResourceFactoryBean();

	@Override
	public MapReactiveUserDetailsService getObject() throws Exception {
		Collection<UserDetails> users = this.userDetails.getObject();
		return new MapReactiveUserDetailsService(users);
	}

	@Override
	public Class<?> getObjectType() {
		return MapReactiveUserDetailsService.class;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.userDetails.setResourceLoader(resourceLoader);
	}

	/**
	 * Sets the location of a Resource that is a Properties file in the format defined in
	 * {@link UserDetailsResourceFactoryBean}.
	 * @param resourceLocation the location of the properties file that contains the users
	 * (i.e. "classpath:users.properties")
	 */
	public void setResourceLocation(String resourceLocation) {
		this.userDetails.setResourceLocation(resourceLocation);
	}

	/**
	 * Sets a Resource that is a Properties file in the format defined in
	 * {@link UserDetailsResourceFactoryBean}.
	 * @param resource the Resource to use
	 */
	public void setResource(Resource resource) {
		this.userDetails.setResource(resource);
	}

	/**
	 * Create a ReactiveUserDetailsServiceResourceFactoryBean with the location of a
	 * Resource that is a Properties file in the format defined in
	 * {@link UserDetailsResourceFactoryBean}.
	 * @param resourceLocation the location of the properties file that contains the users
	 * (i.e. "classpath:users.properties")
	 * @return the ReactiveUserDetailsServiceResourceFactoryBean
	 */
	public static ReactiveUserDetailsServiceResourceFactoryBean fromResourceLocation(String resourceLocation) {
		ReactiveUserDetailsServiceResourceFactoryBean result = new ReactiveUserDetailsServiceResourceFactoryBean();
		result.setResourceLocation(resourceLocation);
		return result;
	}

	/**
	 * Create a ReactiveUserDetailsServiceResourceFactoryBean with a Resource that is a
	 * Properties file in the format defined in {@link UserDetailsResourceFactoryBean}.
	 * @param propertiesResource the Resource that is a properties file that contains the
	 * users
	 * @return the ReactiveUserDetailsServiceResourceFactoryBean
	 */
	public static ReactiveUserDetailsServiceResourceFactoryBean fromResource(Resource propertiesResource) {
		ReactiveUserDetailsServiceResourceFactoryBean result = new ReactiveUserDetailsServiceResourceFactoryBean();
		result.setResource(propertiesResource);
		return result;
	}

	/**
	 * Create a ReactiveUserDetailsServiceResourceFactoryBean with a String that is in the
	 * format defined in {@link UserDetailsResourceFactoryBean}.
	 * @param users the users in the format defined in
	 * {@link UserDetailsResourceFactoryBean}
	 * @return the ReactiveUserDetailsServiceResourceFactoryBean
	 */
	public static ReactiveUserDetailsServiceResourceFactoryBean fromString(String users) {
		ReactiveUserDetailsServiceResourceFactoryBean result = new ReactiveUserDetailsServiceResourceFactoryBean();
		result.setResource(new InMemoryResource(users));
		return result;
	}

}
