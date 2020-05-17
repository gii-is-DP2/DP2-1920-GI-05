package org.springframework.samples.petclinic.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/resources/**","/webjars/**","/h2-console/**").permitAll()
				.antMatchers(HttpMethod.GET, "/","/oups").permitAll()
				.antMatchers("/users/new").permitAll()
				.antMatchers("/admin/**").hasAnyAuthority("admin")
				.antMatchers("/owners/find").authenticated()
				.antMatchers("/owners").authenticated()
				.antMatchers("/owners/{editId}").hasAnyAuthority("owner")	
				//.antMatchers("/owners/{editId}/details").hasAnyAuthority("owner")	
				.antMatchers("/owners/{ownerId}/show").authenticated()
				.antMatchers("/owners/details").hasAnyAuthority("owner")	
				.antMatchers("/owners/{editId}/edit").hasAnyAuthority("owner")	
				.antMatchers("/owners/{editId}/pets/**").hasAnyAuthority("owner")					
				.antMatchers("/judges/details").hasAnyAuthority("judge","admin")	
				.antMatchers("/judges/{judgeId}/edit").hasAnyAuthority("judge","admin")	
				.antMatchers("/judges/{judgeId}/tournaments").hasAnyAuthority("judge","admin")	
				.antMatchers("/judges/{judgeId}/reports/**").hasAnyAuthority("judge","admin")	
				//.antMatchers("/judges/{judgeId}/details").hasAnyAuthority("judge")	
				.antMatchers("/judges/new").hasAnyAuthority("admin")
				.antMatchers("/guides/details").hasAnyAuthority("guide")	
				.antMatchers("/guides/{guideId}/edit").hasAnyAuthority("guide")	
				//.antMatchers("/guides/{guideId}/details").hasAnyAuthority("guide")	
				.antMatchers("/guides/new").permitAll()
				.antMatchers("/tournaments/new").hasAnyAuthority("admin")
				.antMatchers("/tournaments/{tournamentId}/edit").hasAnyAuthority("admin")
				.antMatchers("/tournaments/{tournamentId}/show").hasAnyAuthority("admin, owner, guide, judge")
				.antMatchers("/tournaments/all").hasAnyAuthority("admin")				
				.antMatchers("/tournaments/active").authenticated()
				.antMatchers("/tournaments/endedList").hasAnyAuthority("admin, judge")
				.antMatchers("/tournaments/rankingCreate").hasAnyAuthority("admin, judge")
				.antMatchers("/categories/**").hasAnyAuthority("admin")
				
				.antMatchers("/application/list").hasAnyAuthority("admin")

				.antMatchers("/vets/**").authenticated()
				.antMatchers("/fields/**").hasAnyAuthority("admin")
				.antMatchers("/applications/list_mine").hasAnyAuthority("owner")
				.antMatchers("/applications/{tournamentId}/new").hasAnyAuthority("owner")
				.antMatchers("/applications/{applicationId}/edit").hasAnyAuthority("admin")
				.antMatchers("/applications/all").hasAnyAuthority("admin")
				.antMatchers("/myReports/").hasAnyAuthority("owner")
				
				.anyRequest().denyAll()
				.and()
				 	.formLogin()
				 	/*.loginPage("/login")*/
				 	.failureUrl("/login-error")
				.and()
					.logout()
						.logoutSuccessUrl("/"); 
                // Configuración para que funcione la consola de administración 
                // de la BD H2 (deshabilitar las cabeceras de protección contra
                // ataques de tipo csrf y habilitar los framesets si su contenido
                // se sirve desde esta misma página.
                http.csrf().ignoringAntMatchers("/h2-console/**");
                http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .usersByUsernameQuery(
	       "select username,password,enabled "
	        + "from users "
	        + "where username = ?")
	      .authoritiesByUsernameQuery(
	       "select username, authority "
	        + "from authorities "
	        + "where username = ?")	      	      
	      .passwordEncoder(passwordEncoder());	
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {	    
		PasswordEncoder encoder =  NoOpPasswordEncoder.getInstance();
	    return encoder;
	}
	
}


