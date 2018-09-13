package com.mm2.oauth.security;

import org.springframework.context.annotation.Profile;

/**
 * Created by alejandro on 13/09/18.
 */
public class Facebook extends ApiBinding {

    private static final String GRAPH_API_BASE_URL = "https://graph.facebook.com/v2.12";

    public Facebook(String accessToken) {
        super(accessToken);
    }

    public Profile getProfile() {
        return restTemplate.getForObject(
                GRAPH_API_BASE_URL + "/me", Profile.class);
    }

}
