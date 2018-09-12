package com.mm2.oauth.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.sql.Timestamp;

/**
 * Created by alejandro on 12/09/18.
 */
public class MaritalData {

    private MaritalStatus maritalStatus;

    private String partnerName;

    @DBRef
    private IdentificationType partnerIdentificationType;

    private String partnerIdentication;

    private Timestamp created_at;

    private Status status;

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public IdentificationType getPartnerIdentificationType() {
        return partnerIdentificationType;
    }

    public void setPartnerIdentificationType(IdentificationType partnerIdentificationType) {
        this.partnerIdentificationType = partnerIdentificationType;
    }

    public String getPartnerIdentication() {
        return partnerIdentication;
    }

    public void setPartnerIdentication(String partnerIdentication) {
        this.partnerIdentication = partnerIdentication;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
