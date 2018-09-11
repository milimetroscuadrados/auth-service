package com.mm2.oauth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mm2.oauth.OauthApplication;
import com.mm2.oauth.domain.User;
import com.mm2.oauth.domain.type.GenderType;
import com.mm2.oauth.domain.type.UserKindType;
import com.mm2.oauth.dto.CreatedUserToken;
import com.mm2.oauth.repository.UserRepository;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OauthApplication.class)
@WebAppConfiguration
public class UserControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String username = "testuser";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        userRepository.deleteAllInBatch();
        this.user = userRepository.save(new User(username, "password", "test@test.com", UserKindType.normal_user, GenderType.male));
    }

    @After
    public void clean() {
        this.userRepository.deleteAllInBatch();
    }

    @Test
    public void userNotFound() throws Exception {

        mockMvc.perform(get("/users/asd"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getSingleUser() throws Exception {
        mockMvc.perform(get("/users/" + username))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.username", is(username)));
    }

    @Test
    public void getUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(username)));
    }

    @Test
    public void createUser() throws Exception {
        User newUser = new User("testuser2", "pass", "testuser2@testuser2.com", UserKindType.normal_user, GenderType.male);
        String userJson = json(newUser);

        MvcResult result = mockMvc.perform(post("/users").contentType(contentType).content(userJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.value", notNullValue()))
                .andReturn();

        Gson gson = new Gson();
        CreatedUserToken response = gson.fromJson(result.getResponse().getContentAsString(), CreatedUserToken.class);

        DecodedJWT jwt = JWT.decode(response.getValue());

        mockMvc.perform(get("/users/" + newUser.getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(jwt.getClaim("id").asInt())));

        Instant instant = LocalDateTime.now().toInstant(ZoneOffset.UTC);

        Boolean isNotExpired = jwt.getExpiresAt().compareTo(Date.from(instant)) > 0;
        assertThat(isNotExpired, is(true));
    }

    @Test
    public void createExistentUser() throws Exception {
        String userJson = json(this.user);

        mockMvc.perform(post("/users").contentType(contentType).content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error_code", is("USER_EXIST")));
    }

    @Test
    public void modifyExistentUser() throws Exception {
        String newEmail = "modifiedEmail@modifiedEmail.com";
        this.user.setEmail(newEmail);
        String userJson = json(this.user);

        mockMvc.perform(put("/users").contentType(contentType).content(userJson))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users/" + this.user.getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.email", is(newEmail)));
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
