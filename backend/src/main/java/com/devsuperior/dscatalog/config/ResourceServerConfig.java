package com.devsuperior.dscatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer // Esta classe representa o servidor de autorização do OAuth2 para acesso a recursos/páginas
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private JwtTokenStore tokenStore;
	
	// Rotas (path da URL):
	private static final String[] PUBLIC = {"/oauth/token"}; // rotas públicas para página de login, para não precisar de login para mostrar a página de login
	private static final String[] OPERATOR_OR_ADMIN = {"/products/**", "/categories/**"}; // rotas públicas sem precisar de login, para catálogo
	private static final String[] ADMIN = {"/users/**"}; // rotas para admin logado
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore); // decodifica token e verifica se token é válido
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(PUBLIC).permitAll() // define autorizações
				.antMatchers(HttpMethod.GET, OPERATOR_OR_ADMIN).permitAll() // liberar para todos não logados (eles não têm token)
				.antMatchers(OPERATOR_OR_ADMIN).hasAnyRole("OPERATOR", "ADMIN") // liberar quem possui essas roles
				.antMatchers(ADMIN).hasRole("ADMIN") 
				.anyRequest().authenticated(); // se usuário acessar qualquer outra rota, ele tem que estar logado
	}
}
