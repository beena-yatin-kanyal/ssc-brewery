package guru.sfg.brewery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by jt on 6/13/20.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> {
                    authorize
                            .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                            // permitting the resource as the open url under - /beers/find and find beer by query name (i.e. /beers?beerName=<some_beer_name>
                            // section4 - assignment1
                            .antMatchers("/beers/find", "/beers*").permitAll();
                })
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
    }
}
