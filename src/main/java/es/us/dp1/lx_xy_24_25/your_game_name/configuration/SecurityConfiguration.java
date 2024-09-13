package es.us.dp1.lx_xy_24_25.your_game_name.configuration;

import static org.springframework.security.config.Customizer.withDefaults;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
	private static final String CLINIC_OWNER = "CLINIC_OWNER";
	private static final String PLAYER = "PLAYER";
	

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http
			.cors(withDefaults())		
			.csrf(AbstractHttpConfigurer::disable)		
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))			
			.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.disable()))
			.exceptionHandling((exepciontHandling) -> exepciontHandling.authenticationEntryPoint(unauthorizedHandler))			
			
			.authorizeHttpRequests(authorizeRequests ->	authorizeRequests
			.requestMatchers("/resources/**", "/webjars/**", "/static/**", "/swagger-resources/**").permitAll()			
			.requestMatchers( "/api/v1/clinics","/", "/oups","/api/v1/auth/**","/v3/api-docs/**","/swagger-ui.html","/swagger-ui/**").permitAll()												
			.requestMatchers("/api/v1/developers").permitAll()		
			.requestMatchers(HttpMethod.GET,"/api/v1/achievements").authenticated()
			.requestMatchers(HttpMethod.POST,"/api/v1/achievements").hasAuthority(PLAYER)										
			.requestMatchers("/api/v1/plan").hasAuthority("OWNER")
			.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/users/**")).hasAuthority(ADMIN)
			.requestMatchers("/api/v1/clinicOwners/all").hasAuthority(ADMIN)
			.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/clinicOwners/**")).hasAnyAuthority(ADMIN, CLINIC_OWNER)
			.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/api/v1/consultations/**")).hasAuthority(ADMIN)
			.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/owners/**")).hasAuthority(ADMIN)
			.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/visits/**")).authenticated()			
			.requestMatchers(HttpMethod.GET, "/api/v1/pets/stats").hasAuthority(ADMIN)
			.requestMatchers("/api/v1/pets").authenticated()
			.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/pets/**")).authenticated()
			.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/clinics/**")).hasAnyAuthority(CLINIC_OWNER, ADMIN)
			.requestMatchers(HttpMethod.GET, "/api/v1/vets/stats").hasAuthority(ADMIN)
			.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/vets/**")).authenticated()
			.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/vets/**")).hasAnyAuthority(ADMIN, "VET", CLINIC_OWNER) 
			.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
			.anyRequest().authenticated())					
			
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
