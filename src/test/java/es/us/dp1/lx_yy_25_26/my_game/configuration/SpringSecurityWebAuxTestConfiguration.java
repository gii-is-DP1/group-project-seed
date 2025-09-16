package es.us.dp1.lx_yy_25_26.my_game.configuration;

import java.util.Arrays;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import es.us.dp1.lx_yy_25_26.my_game.configuration.services.UserDetailsImpl;

@Epic("Users & Admin Module")
@Feature("Authentication")
@Owner("DP1-tutors")
@TestConfiguration
public class SpringSecurityWebAuxTestConfiguration {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        UserDetailsImpl ownerActiveUser = new UserDetailsImpl(1, "owner", "password",
        		Arrays.asList(
                        new SimpleGrantedAuthority("PLAYER"))
        );

        UserDetailsImpl adminActiveUser = new UserDetailsImpl(1, "admin", "password",
        		Arrays.asList(
                        new SimpleGrantedAuthority("ADMIN"))
        );



        return new InMemoryUserDetailsManager(Arrays.asList(
        		ownerActiveUser, adminActiveUser
        ));
    }
}
