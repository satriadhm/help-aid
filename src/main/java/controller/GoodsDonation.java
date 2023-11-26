/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.LocalDate;


public class GoodsDonation extends Contribution{
    private String description;
    private double estimatedValue;

   public GoodsDonation(Appeal appeal, String description,
			double estimatedVlue) {
		super(appeal);
		setDescription(description);
		setEstimatedValue(estimatedValue);
	}
	
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(double estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public static int getBaseContributionID() {
        return baseContributionID;
    }

    public static void setBaseContributionID(int baseContributionID) {
        Contribution.baseContributionID = baseContributionID;
    }

    @Override
    public String toString() {
        return "GoodsDonation{" + "description=" + description + ", estimatedValue=" + estimatedValue + '}';
    }
    
    
}
