/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.Serializable;
import java.util.Objects;

public abstract class User implements Comparable<User>, Serializable {
    // Attributes
    private String username;  // Unique identifier for the user
    private String password;  // Password associated with the user
    private String fullname;  // Full name of the user
    private String email;     // Email address of the user
    private String mobileNo;  // Mobile number of the user

    // Constructor for creating a user with specified details
    public User(String username, String fullname, String email, String mobileNo) {
        this.username = username;
        this.setPassword(getDummyPassword());
        this.fullname = fullname;
        this.email = email;
        this.mobileNo = mobileNo;
    }
    
    // Helper method to generate a dummy password based on the username
    private String getDummyPassword() {
        return "passwordFor" + getUsername();
    }
    // Getter and Setter methods for user attributes
    public void setUsername(String username) {
        this.username = username;
    }
// Getter and Setter methods for user attributes
    public void setPassword(String password) {
        this.password = password;
    }
// Getter and Setter methods for user attributes
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
// Getter and Setter methods for user attributes
    public void setEmail(String email) {
        this.email = email;
    }
// Getter and Setter methods for user attributes
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
// Override toString method to provide a string representation of the user
    @Override
    public String toString() {
        return "User"
                + "\n" + "  username: "+username+"\n  password: "+password+"\n  fullname: "+fullname+"\n  email: "+email+"\n  mobileNo: "+mobileNo;
    }
 // Getter methods for user attributes
    public String getUsername() {
        return username;
    }
 // Getter methods for user attributes
    public String getPassword() {
        return password;
    }
 // Getter methods for user attributes
    public String getFullname() {
        return fullname;
    }
 // Getter methods for user attributes
    public String getEmail() {
        return email;
    }
 // Getter methods for user attributes
    public String getMobileNo() {
        return mobileNo;
    }

   // Override equals method to compare users based on the username
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.username, other.username);
    }
}
