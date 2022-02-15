package com.campssg.config;


import com.campssg.DB.repository.UserRepository;
import com.campssg.config.auth.PrincipalDetailsService;
import com.campssg.config.jwt.JwtAuthenticationFilter;
import com.campssg.config.jwt.JwtTokenProviderFilter;
import java.security.Principal;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	// private final UserRepository userRepository;
    private final CorsConfig corsConfig;
    private final PrincipalDetailsService principalDetailsService;


    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.addFilter(corsConfig.corsFilter())
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.formLogin().disable()
				.httpBasic().disable()

				.addFilter(new JwtAuthenticationFilter(authenticationManager()))
             //.addFilter(new JwtTokenProviderFilter(authenticationManager(), principalDetailsService))
				// .addFilter(new JwtTokenProviderFilter(authenticationManager(), userRepository))
				.authorizeRequests()
                // TODO: 권한 수
				.antMatchers("/api/v1/user/**")
				.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
				.antMatchers("/api/v1/manager/**")
					.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
				.antMatchers("/api/v1/admin/**")
					.access("hasRole('ROLE_ADMIN')")
				.anyRequest().permitAll();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}






