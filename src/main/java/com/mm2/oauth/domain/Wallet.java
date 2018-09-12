package com.mm2.oauth.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by alejandro on 11/09/18.
 */
public class Wallet {

    private String address;

    private String privateKey;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}