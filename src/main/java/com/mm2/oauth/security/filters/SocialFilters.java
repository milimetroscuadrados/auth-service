package com.mm2.oauth.security.filters;

import com.mm2.oauth.domain.User;
import com.mm2.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Configuration
@EnableOAuth2Client
public class SocialFilters {

    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;

    @Autowired
    private UserService userService;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    @ConfigurationProperties("linkedin")
    public ClientResources linkedin() {
        return new ClientResources();
    }

    @Bean
    @ConfigurationProperties("facebook")
    public ClientResources facebook() {
        return new ClientResources();
    }

    public Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();

        filters.add(ssoFilter(linkedin(), "/login/linkedin"));
        filters.add(ssoFilter(facebook(), "/login/facebook"));

        filter.setFilters(filters);
        return filter;
    }

    @SuppressWarnings("unchecked")
    private Filter ssoFilter(ClientResources client, String path) {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);

        OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oAuth2ClientContext);
        filter.setRestTemplate(template);

        UserInfoTokenServices tokenServices = new UserInfoTokenServices(
                client.getResource().getUserInfoUri(), client.getClient().getClientId());
        filter.setTokenServices(tokenServices);

        filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            HashMap<String, Object> details =
                    (HashMap<String, Object>) ((OAuth2Authentication) authentication).getUserAuthentication().getDetails();

            ClientUserDetailsStrategy clientUserDetailsStrategy =
                    (new ClientUserDetailsStrategyFactory()).getStrategy(request.getServletPath());

            ClientUserDetails clientUserDetails = clientUserDetailsStrategy.getClientUserDetails(details);

            User user = userService.findByEmail(clientUserDetails.getEmail());

            if(user == null){
                throw new UsernameNotFoundException("El usuario no se encuentra registrado con esa red social");
            }

            Authentication auth =
                    new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);

            String redirectUri;
            DefaultSavedRequest savedRequest =
                    (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");

            if (savedRequest != null) {
                redirectUri = savedRequest.getRedirectUrl();
            } else {
                redirectUri = "/";
            }

            redirectStrategy.sendRedirect(request, response, redirectUri);
        });

        return filter;
    }

    private String createRandomPassword() {
        RandomValueStringGenerator randomValueStringGenerator = new RandomValueStringGenerator();
        randomValueStringGenerator.setLength(15);
        return randomValueStringGenerator.generate();
    }

    class ClientUserDetails {

        private String username;
        private String password;
        private String email;
        private String firstName;
        private String lastName;
        private String pictureUrl;
        private LocalDate birthday;

        public String getUsername() {
            return username;
        }

        void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPictureUrl() {
            return pictureUrl;
        }

        void setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
        }

        public LocalDate getBirthday() {
            return birthday;
        }

        void setBirthday(LocalDate birthday) {
            this.birthday = birthday;
        }

        User toUser() {
            User user = new User();

           // user.setUsername(this.username);
            user.setPassword(this.password);
            user.setEmail(this.email);
            user.setName(firstName);
            user.setLastname(lastName);
            user.setPictureUrl(pictureUrl);
            user.setBirthday((birthday == null) ? null : java.sql.Date.valueOf(birthday));
           // user.setEnabled(true);

            return user;
        }
    }

    class ClientUserDetailsBuilder {

        private String username;
        private String password;
        private String email;
        private String firstName;
        private String lastName;
        private String pictureUrl;
        private LocalDate birthday;

        ClientUserDetailsBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public ClientUserDetailsBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        ClientUserDetailsBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        ClientUserDetailsBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        ClientUserDetailsBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        ClientUserDetailsBuilder setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
            return this;
        }

        ClientUserDetailsBuilder setBirthday(LocalDate birthday) {
            this.birthday = birthday;
            return this;
        }

        ClientUserDetails build() {
            ClientUserDetails userDetails = new ClientUserDetails();

            userDetails.setUsername(username);
            userDetails.setPassword(password);
            userDetails.setEmail(email);
            userDetails.setFirstName(firstName);
            userDetails.setLastName(lastName);
            userDetails.setPictureUrl(pictureUrl);
            userDetails.setBirthday(birthday);

            return userDetails;
        }

    }

    interface ClientUserDetailsStrategy {

        ClientUserDetails getClientUserDetails(HashMap<String, Object> details);
    }

    @SuppressWarnings("unchecked")
    class FacebookClientUserDetailsStrategy implements ClientUserDetailsStrategy {

        @Override
        public ClientUserDetails getClientUserDetails(HashMap<String, Object> details) {
            ClientUserDetailsBuilder userDetailsBuilder = new ClientUserDetailsBuilder();

            HashMap<String, Object> picture = (HashMap<String, Object>) details.get("picture");
            HashMap<String, Object> pictureData = (HashMap<String, Object>) picture.get("data");

            userDetailsBuilder
                    .setUsername((String) details.get("email"))
                    .setPassword(createRandomPassword())
                    .setEmail((String) details.get("email"))
                    .setFirstName((String) details.get("first_name"))
                    .setLastName((String) details.get("last_name"))
                    .setPictureUrl((String) pictureData.get("url"));

            if (details.get("birthday") != null) userDetailsBuilder.setBirthday(LocalDate.parse((String) details.get("birthday"), DateTimeFormatter.ofPattern("MM/dd/yyyy")));

            return userDetailsBuilder
                    .build();
        }
    }

    @SuppressWarnings("unchecked")
    class LinkedinClientUserDetailsStrategy implements ClientUserDetailsStrategy {

        @Override
        public ClientUserDetails getClientUserDetails(HashMap<String, Object> details) {
            ClientUserDetailsBuilder userDetailsBuilder = new ClientUserDetailsBuilder();

            return userDetailsBuilder
                    .setUsername((String) details.get("emailAddress"))
                    .setPassword(createRandomPassword())
                    .setEmail((String) details.get("emailAddress"))
                    .setFirstName((String) details.get("firstName"))
                    .setLastName((String) details.get("lastName"))
                    .setPictureUrl((String) details.get("pictureUrl"))
                    .build();
        }
    }

    class ClientUserDetailsStrategyFactory {

        ClientUserDetailsStrategy getStrategy(String clientKind) {

            if (clientKind == null) {
                return null;
            }

            if (clientKind.equalsIgnoreCase("/login/linkedin")) {
                return new LinkedinClientUserDetailsStrategy();
            } else if (clientKind.equalsIgnoreCase("/login/facebook")) {
                return new FacebookClientUserDetailsStrategy();
            }

            return null;
        }
    }
}

class ClientResources {

    @NestedConfigurationProperty
    private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

    @NestedConfigurationProperty
    private ResourceServerProperties resource = new ResourceServerProperties();

    public AuthorizationCodeResourceDetails getClient() {
        return client;
    }

    public ResourceServerProperties getResource() {
        return resource;
    }
}
