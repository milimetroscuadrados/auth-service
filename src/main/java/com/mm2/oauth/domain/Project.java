package com.mm2.oauth.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by alejandro on 11/09/18.
 */
@Document(collection = "projects")
public class Project {
    @Id
    private String id;

    private String name;

    private ProjectType projectType;

    private String contractAddress;

    private Double estimatedDuration;

    private Double estimatedROI;

    private Double progress;

    private String summary;

    private String whyInvest;

    private String exitStrategy;

    private List<Responsable> responsables;

    private Location location;

    private List<Photo> photos;

    private File video;

    private String timeline;

    private List<File> documents;

    private Long total_tokens;

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

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public Double getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(Double estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public Double getEstimatedROI() {
        return estimatedROI;
    }

    public void setEstimatedROI(Double estimatedROI) {
        this.estimatedROI = estimatedROI;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getWhyInvest() {
        return whyInvest;
    }

    public void setWhyInvest(String whyInvest) {
        this.whyInvest = whyInvest;
    }

    public String getExitStrategy() {
        return exitStrategy;
    }

    public void setExitStrategy(String exitStrategy) {
        this.exitStrategy = exitStrategy;
    }

    public List<Responsable> getResponsables() {
        return responsables;
    }

    public void setResponsables(List<Responsable> responsables) {
        this.responsables = responsables;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public File getVideo() {
        return video;
    }

    public void setVideo(File video) {
        this.video = video;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    public List<File> getDocuments() {
        return documents;
    }

    public void setDocuments(List<File> documents) {
        this.documents = documents;
    }

    public Long getTotal_tokens() {
        return total_tokens;
    }

    public void setTotal_tokens(Long total_tokens) {
        this.total_tokens = total_tokens;
    }
}