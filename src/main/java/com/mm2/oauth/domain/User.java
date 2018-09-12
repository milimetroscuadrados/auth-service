package com.mm2.oauth.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by alejandro on 11/09/18.
 */
@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;

    private String name;

    private String lastname;

    private Gender gender;

    private String email;

    private String password;

    private Boolean accept_tyc_pdp;

    private Date birthday;

    private String pictureUrl;

    private List<Provider> providers;

    private String identification;

    @DBRef
    private IdentificationType identificationType;

    @DBRef
    private Nationality nationality;

    private Boolean pep;

    private Address address;

    private String phone;

    private String cellPhone;

    private String workPhone;

    private MaritalData maritalData;

    private LaborData laborData;

    private BankData bankData;

    private List<KYC> kycs;

    private List<Notification> notifications;

    private Status status;

    private Double available_capital_amount;

    private Timestamp lastLogin;

    private Wallet wallet;

    @DBRef
    private Set<Role> roles;

    private Timestamp created_at;

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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = this.roles;

        if (roles == null) return new HashSet<>();

        return roles.stream()
                .map( Role::getPermissions )
                .flatMap( Set::stream )
                .map( Permission::getDescription)
                .map( SimpleGrantedAuthority::new )
                .collect(Collectors.toSet());
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAccept_tyc_pdp() {
        return accept_tyc_pdp;
    }

    public void setAccept_tyc_pdp(Boolean accept_tyc_pdp) {
        this.accept_tyc_pdp = accept_tyc_pdp;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public IdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(IdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public Boolean getPep() {
        return pep;
    }

    public void setPep(Boolean pep) {
        this.pep = pep;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public MaritalData getMaritalData() {
        return maritalData;
    }

    public void setMaritalData(MaritalData maritalData) {
        this.maritalData = maritalData;
    }

    public LaborData getLaborData() {
        return laborData;
    }

    public void setLaborData(LaborData laborData) {
        this.laborData = laborData;
    }

    public BankData getBankData() {
        return bankData;
    }

    public void setBankData(BankData bankData) {
        this.bankData = bankData;
    }

    public List<KYC> getKycs() {
        return kycs;
    }

    public void setKycs(List<KYC> kycs) {
        this.kycs = kycs;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getAvailable_capital_amount() {
        return available_capital_amount;
    }

    public void setAvailable_capital_amount(Double available_capital_amount) {
        this.available_capital_amount = available_capital_amount;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}