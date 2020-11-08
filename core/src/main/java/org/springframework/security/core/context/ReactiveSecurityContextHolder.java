package org.springframework.security.core.context;

import java.util.function.Function;

import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import org.springframework.security.core.Authentication;

/**
 * Allows getting and setting the Spring {@link SecurityContext} into a {@link Context}.
 *
 * @author Rob Winch
 * @since 5.0
 */
public final class ReactiveSecurityContextHolder {

	private static final Class<?> SECURITY_CONTEXT_KEY = SecurityContext.class;

	private ReactiveSecurityContextHolder() {
	}

	/**
	 * Gets the {@code Mono<SecurityContext>} from Reactor {@link Context}
	 * @return the {@code Mono<SecurityContext>}
	 */
	public static Mono<SecurityContext> getContext() {
		// @formatter:off
		return Mono.subscriberContext()
				.filter(ReactiveSecurityContextHolder::hasSecurityContext)
				.flatMap(ReactiveSecurityContextHolder::getSecurityContext);
		// @formatter:on
	}

	private static boolean hasSecurityContext(Context context) {
		return context.hasKey(SECURITY_CONTEXT_KEY);
	}

	private static Mono<SecurityContext> getSecurityContext(Context context) {
		return context.<Mono<SecurityContext>>get(SECURITY_CONTEXT_KEY);
	}

	/**
	 * Clears the {@code Mono<SecurityContext>} from Reactor {@link Context}
	 * @return Return a {@code Mono<Void>} which only replays complete and error signals
	 * from clearing the context.
	 */
	public static Function<Context, Context> clearContext() {
		return (context) -> context.delete(SECURITY_CONTEXT_KEY);
	}

	/**
	 * Creates a Reactor {@link Context} that contains the {@code Mono<SecurityContext>}
	 * that can be merged into another {@link Context}
	 * @param securityContext the {@code Mono<SecurityContext>} to set in the returned
	 * Reactor {@link Context}
	 * @return a Reactor {@link Context} that contains the {@code Mono<SecurityContext>}
	 */
	public static Context withSecurityContext(Mono<? extends SecurityContext> securityContext) {
		return Context.of(SECURITY_CONTEXT_KEY, securityContext);
	}

	/**
	 * A shortcut for {@link #withSecurityContext(Mono)}
	 * @param authentication the {@link Authentication} to be used
	 * @return a Reactor {@link Context} that contains the {@code Mono<SecurityContext>}
	 */
	public static Context withAuthentication(Authentication authentication) {
		return withSecurityContext(Mono.just(new SecurityContextImpl(authentication)));
	}

}
