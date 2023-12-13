package com.devsuperior.dscatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer // Esta classe representa o servidor de autorização do OAuth2
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder; // @Bean está no AppCofnig.  			   Codifica senha
	
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter; // @Bean está no AppConfig.java.     Pegar chave do TokenJWT
	
	@Autowired
	private JwtTokenStore tokenStore; // @Bean está no AppConfig.java.  				       Codifica e decodifica token
	
	@Autowired
	private AuthenticationManager authenticationManager; // @Bean está no SecurityConfig.java  Gerencia a autenticação
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
		super.configure(security);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception { // credenciais da Aplicação
		clients.inMemory()
		.withClient("app") // nome da Aplicação, tem que ser o mesmo nome no front-end 
		.secret(passwordEncoder.encode("dscatalog123"))
		.scopes("read", "write")
		.authorizedGrantTypes("password")
		.accessTokenValiditySeconds(86400); // expira a autenticação em 1 dia
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception { // define quem autoriza e o formato do token
		endpoints.authenticationManager(authenticationManager) // Gerencia a autenticação
				 .tokenStore(tokenStore) 					   // Codifica e decodifica token
				 .accessTokenConverter(accessTokenConverter);  // Pegar chave do TokenJWT
	}
	
	
}
