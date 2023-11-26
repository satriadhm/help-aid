/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.Serializable;
import java.util.Objects;

public abstract class User implements Comparable<User>, Serializable {
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String mobileNo;

    public User(String username, String fullname, String email, String mobileNo) {
        this.username = username;
        this.setPassword(getDummyPassword());
        this.fullname = fullname;
        this.email = email;
        this.mobileNo = mobileNo;
    }

    	private String getDummyPassword() {
		return "passwordFor" + getUsername();
	}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    public String toString() {
        return "User"
                + "\n" + "  username: "+username+"\n  password: "+password+"\n  fullname: "+fullname+"\n  email: "+email+"\n  mobileNo: "+mobileNo;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

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
