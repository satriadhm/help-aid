/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.LocalDate;


public class GoodsDonation extends Contribution{
    // Attributes
    private String description;     // Description of the goods donation
    private double estimatedValue;  // Estimated monetary value of the goods

    // Constructor for creating a goods donation with specified details
    public GoodsDonation(Appeal appeal, String description, double estimatedValue) {
        super(appeal);  // Call the constructor of the superclass (Contribution)
        setDescription(description);
        setEstimatedValue(estimatedValue);
    }
    // Getter and Setter methods for goods donation attributes
    public String getDescription() {
        return description;
    }
// Getter and Setter methods for goods donation attributes
    public void setDescription(String description) {
        this.description = description;
    }
// Getter and Setter methods for goods donation attributes
    public double getEstimatedValue() {
        return estimatedValue;
    }
// Getter and Setter methods for goods donation attributes
    public void setEstimatedValue(double estimatedValue) {
        this.estimatedValue = estimatedValue;
    }
// Getter and Setter methods for the base contribution ID
    public static int getBaseContributionID() {
        return baseContributionID;
    }
// Getter and Setter methods for the base contribution ID
    public static void setBaseContributionID(int baseContributionID) {
        Contribution.baseContributionID = baseContributionID;
    }
    /// Override toString method to provide a string representation of the goods donation
    @Override
    public String toString() {
        return "GoodsDonation{" + "description=" + description + ", estimatedValue=" + estimatedValue + '}';
    }
    
    
}
