package com.mm2.oauth.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by alejandro on 11/09/18.
 */
@Document(collection = "responsables")
public class Responsable {
    @Id
    private String id;

    private String name;

    @DBRef
    private ResponsableType responsableType;

    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResponsableType getResponsableType() {
        return responsableType;
    }

    public void setResponsableType(ResponsableType responsableType) {
        this.responsableType = responsableType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}