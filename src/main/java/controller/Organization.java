/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class Organization {
    // Static variable to generate unique organization IDs
    static int baseOrganizationID = 100;

    // Attributes
    private String orgID;                 // Unique identifier for the organization
    private String orgName;               // Name of the organization
    private String address;               // Address of the organization
    private ArrayList<Appeal> appeals;    // List of appeals associated with the organization
    private ArrayList<OrganizationRep> reps;        // List of organization representatives
    private ArrayList<Applicant> applicants;       // List of applicants associated with the organization

    // Constructor for creating an organization with specified details
    public Organization(String orgName, String address) {
        setOrgID("ORG" + baseOrganizationID++);
        this.orgName = orgName;
        this.address = address;
        setReps(new ArrayList<>());
        setApplicants(new ArrayList<>());
        setAppeals(new ArrayList<>());
    }

    
    // Getter and Setter methods for organization attributes
    public String getOrgID() {
        return orgID;
    }
// Getter and Setter methods for organization attributes
    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }
// Getter and Setter methods for organization attributes
    public String getOrgName() {
        return orgName;
    }
// Getter and Setter methods for organization attributes
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
// Getter and Setter methods for organization attributes
    public String getAddress() {
        return address;
    }
// Getter and Setter methods for organization attributes
    public void setAddress(String address) {
        this.address = address;
    }
// Getter and Setter methods for organization attributes
    public ArrayList<Appeal> getAppeals() {
        return appeals;
    }
// Getter and Setter methods for organization attributes
    public void setAppeals(ArrayList<Appeal> appeals) {
        this.appeals = appeals;
    }
// Getter and Setter methods for organization attributes
    public ArrayList<OrganizationRep> getReps() {
        return reps;
    }
// Getter and Setter methods for organization attributes
    public void setReps(ArrayList<OrganizationRep> reps) {
        this.reps = reps;
    }
// Getter and Setter methods for organization attributes
    public ArrayList<Applicant> getApplicants() {
        return applicants;
    }
// Getter and Setter methods for organization attributes
    public void setApplicants(ArrayList<Applicant> applicants) {
        this.applicants = applicants;
    }
    
     // Methods to add organization representatives, applicants, and appeals
    public boolean add(OrganizationRep rep) {
	return getReps().add(rep);
    }
     // Methods to add organization representatives, applicants, and appeals
    public boolean add(Applicant applicant) {
	return getApplicants().add(applicant);
    }
	
     // Methods to add organization representatives, applicants, and appeals
    public boolean add(Appeal appeal) {
	return getAppeals().add(appeal);
    }
// Methods to find organization representatives, applicants, and appeals
    public OrganizationRep findOrganizationRep(String username) {
		return getReps().stream()
			.filter(r -> username.equalsIgnoreCase(r.getUsername()))
			.findFirst()
			.orElse(null);		
	}
// Methods to find organization representatives, applicants, and appeals
	public Applicant findApplicantByUsername(String username) {
		return getApplicants().stream()
			.filter(r -> username.equalsIgnoreCase(r.getUsername()))
			.findFirst()
			.orElse(null);		
	}
// Methods to find organization representatives, applicants, and appeals
	public Applicant findApplicantByID(String IDno) {
		return getApplicants().stream()
			.filter(app -> IDno.equalsIgnoreCase(app.getIDNo()))
			.findFirst()
			.orElse(null);		
	}
	// Methods to find organization representatives, applicants, and appeals
	public Appeal findAppeal(String appealID) {
		return getAppeals().stream()
			.filter(a -> appealID.equalsIgnoreCase(a.getAppealID()))
			.findFirst()
			.orElse(null);
	}
	
	 // Method to list current appeals
    public String listCurrentAppeals() {
        return getAppeals().stream()
                .filter(Appeal::isCurrentAppeal)
                .map(Appeal::generateAppealSummary)
                .collect(Collectors.joining("\n"));
    }

    // Method to display information about all applicants
    public String allApplicants() {
        return getApplicants().stream()
                .map(Applicant::toString)
                .collect(Collectors.joining("\n"));
    }
	// Methods to get the number of appeals, applicants, and organization representatives
	public int numOfAppeals() {
		return getAppeals().size();
	}
	// Methods to get the number of appeals, applicants, and organization representatives
	public int numOfApplicants() {
		return getApplicants().size();
	}
	// Methods to get the number of appeals, applicants, and organization representatives
	public int numOfOrganizationReps() {
		return getReps().size();
	}
 // Override toString method to provide a string representation of the organization
    @Override
    public String toString() {
        return "Organization" + "\n orgID: " + orgID + "\n orgName: " + orgName + "\n address: " + address + "\nappeals: " + appeals + ", \nreps: " + reps + ",\napplicants: " + applicants ;
    }
    
     
    
}
