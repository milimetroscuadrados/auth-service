package com.mm2.oauth.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by alejandro on 11/09/18.
 */
public class Photo {

    @DBRef
    private File logo;

    @DBRef
    private File coverImage;

    @DBRef
    private List<File> gallery;

    public File getLogo() {
        return logo;
    }

    public void setLogo(File logo) {
        this.logo = logo;
    }

    public File getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(File coverImage) {
        this.coverImage = coverImage;
    }

    public List<File> getGallery() {
        return gallery;
    }

    public void setGallery(List<File> gallery) {
        this.gallery = gallery;
    }
}