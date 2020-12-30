package com.acme.customers.api.models.db;

import com.acme.customers.api.models.db.common.BaseEntity;
import com.acme.customers.lib.v1.CustomerStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customers")
@NamedQueries({
        @NamedQuery(name = "CustomerEntity.findAll", query = "SELECT c FROM CustomerEntity c"),
        @NamedQuery(name = "CustomerEntity.findAllCount", query = "SELECT count(c) FROM CustomerEntity c")
})
public class CustomerEntity extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CustomerStatus status;

    @Column(name = "email")
    private String email;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
