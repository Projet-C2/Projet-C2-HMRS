package com.example.mariobousamra.projet_c2_hmrs;

public class UserProfile {
    public  String lastName;
    public  String Name;
    public  String PhoneNumber;

    //Creating a default constructor for getters and setters
    public UserProfile(){
    }

    //ALT + 0 (Constructor)
    public UserProfile(String lastName, String name, String phoneNumber) {
        this.lastName = lastName;
        Name = name;
        PhoneNumber = phoneNumber;
    }


    //ALT + 0 (getters and setters)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}

