package com.ej.mm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Client {
    @JsonProperty("firstname")
    public String firstName;
    @JsonProperty("lastname")
    public String lastName;
    @JsonProperty("email")
    public String email;
    @JsonProperty("phoneno")
    public Integer phoneNo;
    @JsonProperty("address")
    public String address;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Integer phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client{" +
                "First Name='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo=" + phoneNo +
                ", address='" + address + '\'' +
                '}';
    }
}