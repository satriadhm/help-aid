/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.LocalDate;


public class CashDonation extends Contribution {
    private double amount;
    private String paymentChannel;
    private String referenceNo;

    public CashDonation(Appeal appeal, double amount, String paymentChannel,
			String referenceNo) {
			super(appeal);
			setAmount(amount);
			setPaymentChannel(paymentChannel);
			setReferenceNo(referenceNo);
		}


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
