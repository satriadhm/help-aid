/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

public class CashDonation extends Contribution {
    // Attributes
    private double amount;           // Amount of the cash donation
    private String paymentChannel;   // Payment channel used for the donation
    private String referenceNo;      // Reference number associated with the donation

    // Constructor for creating a cash donation with specified details
    public CashDonation(Appeal appeal, double amount, String paymentChannel, String referenceNo) {
        super(appeal); // Call the constructor of the superclass (Contribution)
        setAmount(amount);
        setPaymentChannel(paymentChannel);
        setReferenceNo(referenceNo);
    }
// Getter and Setter methods for cash donation attributes

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }
  // Getter and Setter methods for the base contribution ID
    public static int getBaseContributionID() {
        return baseContributionID;
    }

    public static void setBaseContributionID(int baseContributionID) {
        Contribution.baseContributionID = baseContributionID;
    }

    @Override
    public String toString() {
        return "CashDonation{" 
                + "\n amount: " + amount 
                + "\n paymentChannel: " + paymentChannel 
                + "\n referenceNo: " + referenceNo + '}';
    }
    
   
    
    
}
