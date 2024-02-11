package pl.offers.job.infrastructure.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.offers.job.domain.loginandregister.LoginAndRegisterFacade;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthTokenFilter jwtAuthTokenFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(LoginAndRegisterFacade loginAndRegisterFacade) {
        return new LoginUserDetailsService(loginAndRegisterFacade);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests()
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/swagger-config")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/webjars/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/token/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/register/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/swagger-resources/**")).permitAll()
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable()
                .and().httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .and()
                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

}
