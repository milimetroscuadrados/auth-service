package com.mm2.oauth.security;

import com.mm2.oauth.domain.User;
import com.mm2.oauth.service.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableAuthorizationServer
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    public TokenEnhancer customTokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private Environment env;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // TODO persist clients details

        clients.inMemory()
            .withClient("real-estate-ui")
            .secret("real-estate-secret")
            .scopes("argentina", "peru")
            .autoApprove(true)
            .authorizedGrantTypes("authorization_code","refresh_token", "password")
//            .redirectUris("http://localhost:4200/login", "http://localhost:4000/login", "https://www.getpostman.com/oauth2/callback")
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer(), jwtTokenEnhancer()));

        TokenStore tokenStore = tokenStore();

        endpoints
                .tokenStore(tokenStore)
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager)
                ;//.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        // fixme Pasar el secret key a una variable de entorno
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "mySecretKey".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverterCustom();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
        return converter;
    }

    public class CustomTokenEnhancer implements TokenEnhancer {

        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
            Map<String, Object> additionalInfo = new HashMap<>();

            Collection<GrantedAuthority> authorities = authentication.getAuthorities();

            Set<String> audiences = authorities.stream()
                                .map( a -> a.getAuthority().split("\\.")[0] )
                                .collect(Collectors.toSet());

            User loggedUser = (User) authentication.getPrincipal();

            additionalInfo.put("aud", audiences );
            additionalInfo.put("first_name", loggedUser.getName());
            additionalInfo.put("last_name", loggedUser.getLastname());
            additionalInfo.put("email", loggedUser.getEmail());
            additionalInfo.put("id", loggedUser.getId());
            additionalInfo.putAll(accessToken.getAdditionalInformation());

            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

            return accessToken;
        }
    }

    public class JwtAccessTokenConverterCustom extends JwtAccessTokenConverter {

        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
                                         OAuth2Authentication authentication) {
            DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);

            OAuth2AccessToken enhancedAccessToken = super.enhance(customAccessToken, authentication);

            ((DefaultOAuth2AccessToken) enhancedAccessToken).setAdditionalInformation(new HashMap<>());

            return enhancedAccessToken;
        }
    }
}
