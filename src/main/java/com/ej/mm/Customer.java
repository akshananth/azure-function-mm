package com.ej.mm;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Customer implements Serializable {
    public Customer() {
    }

    public Customer(String description, String details, String email) {
        this.firstName = description;
        this.lastName = details;
        this.email = email;
    }


    private Long newClientId;
   // @JsonProperty("firstname")
    private String firstName;
    private String lastName;
    private String email;

    public Long getNewClientId() {
        return newClientId;
    }

    public void setNewClientId(Long newClientId) {
        this.newClientId = newClientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String latName) {
        this.lastName = latName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "newClientId=" + newClientId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}