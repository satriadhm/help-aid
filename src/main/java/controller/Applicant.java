/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.Serializable;
import java.util.ArrayList;

public class Applicant extends User  implements Serializable {
	 // Static variable to generate unique applicant IDs
    static int nextApplicantID = 1000;

    // Attributes
    private String IDNo;                     // National ID of the applicant
    private String address;                  // Address of the applicant
    private double householdIncome;          // Household income of the applicant
    private Organization organization;       // Organization associated with the applicant
    private ArrayList<Document> documents;   // List of documents submitted by the applicant
    private ArrayList<Disbursement> disbursements; // List of disbursements received by the applicant

    // Constructor for creating an applicant with specified details
    public Applicant(String userName, String fullName, String email,
                      String mobileNo, String IDNo, String address, double householdIncome,
                      Organization organization) {
        // Call the constructor of the superclass (User)
        super(userName, fullName, email, mobileNo);

        // Initialize attributes
        setIDNo(IDNo);
        setAddress(address);
        setHouseholdIncome(householdIncome);
        setOrganization(organization);
        setDocuments(new ArrayList<Document>());
        setDisbursements(new ArrayList<Disbursement>());
    }

    // Alternate constructor with default username and password
    public Applicant(String fullname, String email, String mobileNo,
                      String IDno, String address, double householdIncome, Organization organization) {
        this("AP" + nextApplicantID++, fullname, email, mobileNo,
                IDno, address, householdIncome, organization);
        this.setUsername("user" + getIDNo());
        this.setPassword("passwordFor" + getIDNo());
    }

      // Default constructor
    public Applicant() {
        this(null, null, null, null, null, 0, null);
    }

    // Getter and Setter methods for applicant attributes
	public String getIDNo() {
		return IDNo;
	}

	public void setIDNo(String IDNo) {
		this.IDNo = IDNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getHouseholdIncome() {
		return householdIncome;
	}

	public void setHouseholdIncome(double householdIncome) {
		this.householdIncome = householdIncome;
	}
	
	public ArrayList<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(ArrayList<Document> documents) {
		this.documents = documents;
	}
	
	public Organization getOrganization() {
		return organization;
	}
	
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	public ArrayList<Disbursement> getDisbursements() {
		return disbursements;
	}

	public void setDisbursements(ArrayList<Disbursement> disbursements) {
		this.disbursements = disbursements;
	}

	 // Method to add a document to the applicant's list of documents
    public void addDocument(Document doc) {
        getDocuments().add(doc);
    }

    // Method to add a disbursement to the applicant's list of disbursements
    public void addDisbursement(Disbursement disb) {
        getDisbursements().add(disb);
    }

    // Method to get the number of disbursements received by the applicant
    public int numOfDisbursements() {
        return getDisbursements().size();
    }

    // Method to display information about the documents submitted by the applicant
    public String displayDocuments() {
        String dStr = "";
        for (Document d : documents) {
            dStr += "@@@" + d.toString() + "\n";
        }
        return dStr;
    }

    // Method to display information about the disbursements received by the applicant
    public String displayDisbursements() {
        String dbStr = "";
        for (Disbursement d : disbursements) {
            dbStr += d.toString() + "\n";
        }
        return dbStr;
    }

    // Override toString method to provide a string representation of the applicant
    @Override
    public String toString() {
        String msg = super.toString()
                + "\n  ID No: " + IDNo
                + "\n  lives under address: "
                + address
                + "\n  with household income: "
                + householdIncome
                + "\n   Documents: "
                + displayDocuments();
        if (numOfDisbursements() != 0)
            msg += "\n  has received " + numOfDisbursements() + " disbursements.";
        return msg;
    }

    // The compareTo method is inherited from the User class and needs to be implemented
    @Override
    public int compareTo(User o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}