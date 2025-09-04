package es.us.dp1.lx_xy_24_25.your_game_name.configuration;

import static org.springframework.security.config.Customizer.withDefaults;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import es.us.dp1.lx_xy_24_25.your_game_name.configuration.jwt.AuthEntryPointJwt;
import es.us.dp1.lx_xy_24_25.your_game_name.configuration.jwt.AuthTokenFilter;
import es.us.dp1.lx_xy_24_25.your_game_name.configuration.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Autowired
	DataSource dataSource;

	private static final String ADMIN = "ADMIN";
	private static final String PLAYER = "PLAYER";


	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

		http
            .cors(withDefaults())
            // Si usas H2, conviene ignorar su CSRF:
            .csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))

            .authorizeHttpRequests(auth -> auth
                // Recursos estáticos comunes (css, js, images, webjars…) públicos
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // H2 Console accesible
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .requestMatchers("/h2-console/**").permitAll()

                // Raíz / páginas públicas
                .requestMatchers("/", "/oups").permitAll()

                // Swagger / OpenAPI accesible
                .requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/swagger-resources/**"
                ).permitAll()

                // API pública
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/developers").permitAll()
                .requestMatchers("/api/v1/plan").permitAll()

                // API restringida para administradores
                .requestMatchers("/api/v1/users/**").hasAuthority(ADMIN)

                // El resto denegado
                .anyRequest().denyAll()
            )


			.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}



}
