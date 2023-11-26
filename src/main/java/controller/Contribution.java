/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.LocalDate;


public abstract class Contribution {
    static int baseContributionID = 100;
    private LocalDate receivedDate;
    private String contributionID;
    private Appeal appeal;

    public Contribution( Appeal appeal) {
        setReceivedDate(LocalDate.now());
        setContributionID("C"+ baseContributionID++);
       setAppeal(appeal);
    }

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

    @Override
    public String toString() {
        return "Contribution{" + "receivedDate=" + receivedDate + ", contributionID=" + contributionID + ", appeal=" + appeal + '}';
    }
    
    
}
