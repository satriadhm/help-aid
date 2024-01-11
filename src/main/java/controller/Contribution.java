/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.LocalDate;


public abstract class Contribution {
    // Static variable to generate unique contribution IDs
    static int baseContributionID = 100;

    // Attributes
    private LocalDate receivedDate;   // Date when the contribution was received
    private String contributionID;    // Unique identifier for the contribution
    private Appeal appeal;            // The appeal associated with the contribution

    // Constructor for creating a contribution with specified details
    public Contribution(Appeal appeal) {
        setReceivedDate(LocalDate.now());   // Set the received date to the current date
        setContributionID("C" + baseContributionID++); // Generate a unique contribution ID
        setAppeal(appeal);  // Set the associated appeal
    }
    
    // Getter and Setter methods for contribution attributes
    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getContributionID() {
        return contributionID;
    }

    public void setContributionID(String contributionID) {
        this.contributionID = contributionID;
    }

    public Appeal getAppeal() {
        return appeal;
    }

    public void setAppeal(Appeal appeal) {
        this.appeal = appeal;
    }
     // Override toString method to provide a string representation of the contribution
    @Override
    public String toString() {
        return "Contribution{" + "receivedDate=" + receivedDate + ", contributionID=" + contributionID + ", appeal=" + appeal + '}';
    }
    
    
}
