package com.mm2.oauth.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.sql.Timestamp;

/**
 * Created by alejandro on 12/09/18.
 */
public class LaborData {

    @DBRef
    private ActivityType activityType;

    @DBRef
    private EmployeementType employeementType;

    private Double netMonthlyIncome;

    private Double estimatedAmountToInvest;

    private Timestamp created_at;

    private Status status;

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public EmployeementType getEmployeementType() {
        return employeementType;
    }

    public void setEmployeementType(EmployeementType employeementType) {
        this.employeementType = employeementType;
    }

    public Double getNetMonthlyIncome() {
        return netMonthlyIncome;
    }

    public void setNetMonthlyIncome(Double netMonthlyIncome) {
        this.netMonthlyIncome = netMonthlyIncome;
    }

    public Double getEstimatedAmountToInvest() {
        return estimatedAmountToInvest;
    }

    public void setEstimatedAmountToInvest(Double estimatedAmountToInvest) {
        this.estimatedAmountToInvest = estimatedAmountToInvest;
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
