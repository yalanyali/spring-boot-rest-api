package rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import rest.repository.UserRepository;

import java.util.Arrays;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {

        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        // Add users from DB to in-memory user manager
        for(rest.model.User user : userRepository.findAll()) {
            manager.createUser(users.username(user.getUsername()).password(user.getPassword()).roles("USER").build());
        }
        // Test user
        manager.createUser(users.username("testuser").password("testpassword").roles("USER").build());

        return manager;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // Handle CORS, disable CSRF but keep authorization
        http.cors().and().csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}