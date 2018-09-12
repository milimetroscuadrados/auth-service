package com.mm2.oauth.domain;

import org.springframework.data.annotation.Id;

/**
 * Created by alejandro on 11/09/18.
 */
public class Endpoint {
    @Id
    private String id;

    private String redirecURI;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRedirecURI() {
        return redirecURI;
    }

    public void setRedirecURI(String redirecURI) {
        this.redirecURI = redirecURI;
    }
}