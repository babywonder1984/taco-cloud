package tacos.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;





@Configuration
@EnableWebSecurity
public class ApplicationConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService  userDetailsService;
    
	@Bean 
	public PasswordEncoder encoder() {
		return new StandardPasswordEncoder("53cr3t");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(encoder());
		
		
/*      PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		 auth
		.inMemoryAuthentication()
		.withUser("allen")
		.password(encoder.encode("password"))
		.roles("USER"); 
*/
		
		
		 
	}

}
