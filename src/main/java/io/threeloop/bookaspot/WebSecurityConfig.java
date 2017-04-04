package io.threeloop.bookaspot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;

  @Autowired
  public WebSecurityConfig(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers("/user/register").permitAll()
      .antMatchers("/user/activate").permitAll()
      .antMatchers("/user/activation-send").permitAll()
      .antMatchers("/user/reset-password").permitAll()
      .antMatchers("/user/reset-password-change").permitAll()
      .antMatchers("/user/autologin").access("hasRole('ROLE_ADMIN')")
      .antMatchers("/user/delete").access("hasRole('ROLE_ADMIN')")
      .antMatchers("/img/**").permitAll()
      .antMatchers("/images/**").permitAll()
      .antMatchers("/fonts/**").permitAll()
      .antMatchers("/css/**").permitAll()
      .antMatchers("/js/**").permitAll()
      .anyRequest().authenticated()
      .and()
        .formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
      .and()
        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
  }
}
