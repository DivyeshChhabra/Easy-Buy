package com.mycompany.easybuy.model;

public class Member {
    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNumber;
    private String EMailAddress;
    private String Password;
    private int sellerId;
    private int customerId;
    private String profilePhoto;
    private String type;

    public Member(String firstName, String lastName, String gender, String phoneNumber, String EMailAddress, String Password, int sellerId, int customerId, String profilePhoto, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.EMailAddress = EMailAddress;
        this.Password = Password;
        this.sellerId = sellerId;
        this.customerId = customerId;
        this.profilePhoto = profilePhoto;
        this.type = type;
    }
    
    public Member(int id, String firstName, String lastName, String gender, String phoneNumber, String EMailAddress, String Password, int sellerId, int customerId, String profilePhoto, String type) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.EMailAddress = EMailAddress;
        this.Password = Password;
        this.sellerId = sellerId;
        this.customerId = customerId;
        this.profilePhoto = profilePhoto;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEMailAddress() {
        return EMailAddress;
    }

    public void setEMailAddress(String EMailAddress) {
        this.EMailAddress = EMailAddress;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setsellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setcustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Member{" + "id=" + id + ", firstName=" + firstName + ", LastName=" + lastName + ", gender=" + gender + ", phoneNumber=" + phoneNumber + ", EMailAddress=" + EMailAddress + ", Password=" + Password + ", sellerId=" + sellerId + ", customerId=" + customerId + ", profilePhoto=" + profilePhoto + ", type=" + type + '}';
    }
}
