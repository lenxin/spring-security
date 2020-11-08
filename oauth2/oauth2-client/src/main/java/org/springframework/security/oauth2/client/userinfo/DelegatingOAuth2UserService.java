package org.springframework.security.oauth2.client.userinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;

/**
 * An implementation of an {@link OAuth2UserService} that simply delegates to it's
 * internal {@code List} of {@link OAuth2UserService}(s).
 * <p>
 * Each {@link OAuth2UserService} is given a chance to
 * {@link OAuth2UserService#loadUser(OAuth2UserRequest) load} an {@link OAuth2User} with
 * the first {@code non-null} {@link OAuth2User} being returned.
 *
 * @param <R> The type of OAuth 2.0 User Request
 * @param <U> The type of OAuth 2.0 User
 * @author Joe Grandja
 * @since 5.0
 * @see OAuth2UserService
 * @see OAuth2UserRequest
 * @see OAuth2User
 */
public class DelegatingOAuth2UserService<R extends OAuth2UserRequest, U extends OAuth2User>
		implements OAuth2UserService<R, U> {

	private final List<OAuth2UserService<R, U>> userServices;

	/**
	 * Constructs a {@code DelegatingOAuth2UserService} using the provided parameters.
	 * @param userServices a {@code List} of {@link OAuth2UserService}(s)
	 */
	public DelegatingOAuth2UserService(List<OAuth2UserService<R, U>> userServices) {
		Assert.notEmpty(userServices, "userServices cannot be empty");
		this.userServices = Collections.unmodifiableList(new ArrayList<>(userServices));
	}

	@Override
	public U loadUser(R userRequest) throws OAuth2AuthenticationException {
		Assert.notNull(userRequest, "userRequest cannot be null");
		// @formatter:off
		return this.userServices.stream()
				.map((userService) -> userService.loadUser(userRequest))
				.filter(Objects::nonNull)
				.findFirst()
				.orElse(null);
		// @formatter:on
	}

}
