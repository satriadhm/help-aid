/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class Organization {
    static int baseOrganizationID = 100;
    private String orgID;
    private String orgName;
    private String address;
    private ArrayList<Appeal> appeals;
    private ArrayList<OrganizationRep> reps;
    private ArrayList<Applicant> applicants;

    public Organization(String orgName, String address) {
        setOrgID("ORG"+baseOrganizationID++);
        this.orgName = orgName;
        this.address = address;
        setReps(new ArrayList<>());
        setApplicants(new ArrayList<>());
        setAppeals(new ArrayList<>());
    }
    
    

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Appeal> getAppeals() {
        return appeals;
    }

    public void setAppeals(ArrayList<Appeal> appeals) {
        this.appeals = appeals;
    }

    public ArrayList<OrganizationRep> getReps() {
        return reps;
    }

    public void setReps(ArrayList<OrganizationRep> reps) {
        this.reps = reps;
    }

    public ArrayList<Applicant> getApplicants() {
        return applicants;
    }

    public void setApplicants(ArrayList<Applicant> applicants) {
        this.applicants = applicants;
    }
    
    
    public boolean add(OrganizationRep rep) {
	return getReps().add(rep);
    }
    public boolean add(Applicant applicant) {
	return getApplicants().add(applicant);
    }
	
    public boolean add(Appeal appeal) {
	return getAppeals().add(appeal);
    }

    public OrganizationRep findOrganizationRep(String username) {
		return getReps().stream()
			.filter(r -> username.equalsIgnoreCase(r.getUsername()))
			.findFirst()
			.orElse(null);		
	}

	public Applicant findApplicantByUsername(String username) {
		return getApplicants().stream()
			.filter(r -> username.equalsIgnoreCase(r.getUsername()))
			.findFirst()
			.orElse(null);		
	}

	public Applicant findApplicantByID(String IDno) {
		return getApplicants().stream()
			.filter(app -> IDno.equalsIgnoreCase(app.getIDNo()))
			.findFirst()
			.orElse(null);		
	}
	
	public Appeal findAppeal(String appealID) {
		return getAppeals().stream()
			.filter(a -> appealID.equalsIgnoreCase(a.getAppealID()))
			.findFirst()
			.orElse(null);
	}
	
	
	public String listCurrentAppeals() {
		return getAppeals().stream()
			.filter(Appeal::isCurrentAppeal)
			.map(Appeal::generateAppealSummary) 
			.collect(Collectors.joining("\n"));
	}
	
	public String allApplicants() {
		return getApplicants().stream()
			.map(Applicant::toString)
			.collect(Collectors.joining("\n"));
	}
	
	public int numOfAppeals() {
		return getAppeals().size();
	}
	
	public int numOfApplicants() {
		return getApplicants().size();
	}
	
	public int numOfOrganizationReps() {
		return getReps().size();
	}

    @Override
    public String toString() {
        return "Organization" + "\n orgID: " + orgID + "\n orgName: " + orgName + "\n address: " + address + "\nappeals: " + appeals + ", \nreps: " + reps + ",\napplicants: " + applicants ;
    }
    
     
    
}
