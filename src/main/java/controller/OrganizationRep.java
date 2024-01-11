/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.Serializable;


public class OrganizationRep extends User implements Serializable {
   // Static variable representing the job title attribute
    public static String holdsJobTitle = "holds job title";

    // Attributes
    private String jobTitle;           // Job title of the organization representative
    private Organization organization; // The organization associated with the representative

    // Default constructor
    public OrganizationRep() {
        this(null, null, null, null, null, null);
    }

    // Constructor for creating an organization representative with specified details
    public OrganizationRep(String userName, String fullName, String email, String mobileNo, String jobTitle, Organization organization) {
        super(userName, fullName, email, mobileNo); // Call the constructor of the superclass (User)
        setJobTitle(jobTitle);
        setOrganization(organization);
    }
     // Getter and Setter methods for organization representative attributes
    public String getJobTitle() {
        return jobTitle;
    }
 // Getter and Setter methods for organization representative attributes
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
 // Getter and Setter methods for organization representative attributes
    public Organization getOrganization() {
        return organization;
    }
 // Getter and Setter methods for organization representative attributes
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
// Override toString method to provide a string representation of the organization representative
    @Override
    public String toString() {
        return  super.toString() + ";" + holdsJobTitle + ": " + jobTitle;
    }
    
    @Override
    public int compareTo(User o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
