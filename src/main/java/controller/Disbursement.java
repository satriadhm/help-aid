/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.LocalDate;


public class Disbursement {
    // Attributes
    private String disbursmentID;
    private LocalDate disbursementDate; // Date when the disbursement occurred
    private double cashAmount;          // Amount of cash disbursed
    private double goodsDisbursed;      // Value of goods disbursed
    private Appeal appeal;              // The appeal associated with the disbursement
    private Applicant applicant;        // The applicant associated with the disbursement

    // Constructor for creating a disbursement with specified details
    public Disbursement(LocalDate disbursementDate, double cashAmount, double goodsDisbursed, Appeal appeal, Applicant applicant) {
        this.disbursementDate = disbursementDate;
        this.cashAmount = cashAmount;
        this.goodsDisbursed = goodsDisbursed;
        this.appeal = appeal;
        this.applicant = applicant;
        setDisbursmentID();
    }

    public String getDisbursmentID() {
        return disbursmentID;
    }

    public void setDisbursmentID() {
       this.disbursmentID="DB" + applicant.getIDNo();
    }
    // Getter and Setter methods for disbursement attributes
    public LocalDate getDisbursementDate() {
        return disbursementDate;
    }
    // Getter and Setter methods for disbursement attributes
    public void setDisbursementDate(LocalDate disbursementDate) {
        this.disbursementDate = disbursementDate;
    }
// Getter and Setter methods for disbursement attributes
    public double getCashAmount() {
        return cashAmount;
    }
// Getter and Setter methods for disbursement attributes
    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }
// Getter and Setter methods for disbursement attributes
    public double getGoodsDisbursed() {
        return goodsDisbursed;
    }
// Getter and Setter methods for disbursement attributes
    public void setGoodsDisbursed(double goodsDisbursed) {
        this.goodsDisbursed = goodsDisbursed;
    }
// Getter and Setter methods for disbursement attributes
    public Appeal getAppeal() {
        return appeal;
    }
// Getter and Setter methods for disbursement attributes
    public void setAppeal(Appeal appeal) {
        this.appeal = appeal;
    }
// Getter and Setter methods for disbursement attributes
    public Applicant getApplicant() {
        return applicant;
    }
// Getter and Setter methods for disbursement attributes
    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
 // Override toString method to provide a string representation of the disbursement
    @Override
    public String toString() {
        return "Disbursement{" + "disbursementDate=" + disbursementDate + ", cashAmount=" + cashAmount + ", goodsDisbursed=" + goodsDisbursed + ", appeal=" + appeal + ", applicant=" + applicant + '}';
    }
    
    
    
}
