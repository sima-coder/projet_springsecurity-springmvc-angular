package org.issh.securite;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
			//Les utilisateurs manipulés sont en mémoire
			auth.inMemoryAuthentication().withUser("admin").password(encoder.encode("123")).roles("ADMIN","USER");
			//auth.inMemoryAuthentication().withUser("admin").password("{noop}123").roles("ADMIN","USER");
			auth.inMemoryAuthentication().withUser("user").password(encoder.encode("456")).roles("USER");
			//auth.inMemoryAuthentication().withUser("user").password("{noop}123").roles("USER");
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
		// Définir les règles de sécurité avec l'objet http
			//L'opération d'authentification nécissite un formulaire d'authentification
			http.formLogin();
			//une requete http dont l'URL est /afficherProduits nécissite une authentication avec un utilisateur ayant le rôle USER
			http.authorizeRequests().antMatchers("/afficherProduits").hasRole("USER");
			http.authorizeRequests().antMatchers("/supprimerProduits","/saisirProduit","/modifierProduit","/ajouterProduit").hasRole("ADMIN");
			http.exceptionHandling().accessDeniedPage("/403");
		}
}