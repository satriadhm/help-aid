/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.Serializable;


public class OrganizationRep extends User implements Serializable {
    public static String holds_job_title = "holds job title";
    private String jobTitle;
    private Organization organization;
    
    public OrganizationRep() {
        this(null,null,null,null,null,null);
    }

    public OrganizationRep(String userName, String fullName, String email, String mobileNo, String jobTitle, Organization  organization) {
            super(userName, fullName, email, mobileNo);
            setJobTitle(jobTitle);
            setOrganization(organization);
    }
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return  super.toString() + ";" + holds_job_title + ": " + jobTitle;
    }
    
    @Override
    public int compareTo(User o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
