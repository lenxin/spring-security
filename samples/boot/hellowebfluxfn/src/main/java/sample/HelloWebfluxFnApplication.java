package sample;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author Rob Winch
 * @since 5.0
 */
@SpringBootApplication
public class HelloWebfluxFnApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloWebfluxFnApplication.class, args);
	}

	@Bean
	public RouterFunction<ServerResponse> routes(HelloUserController userController) {
		return route(
			GET("/"), userController::hello);
	}
}
