/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


public class Document {
     // Static variable to generate unique document IDs
    static int baseDocumentID = 100;

    // Attributes
    private String documentID;  // Unique identifier for the document
    private String fileName;    // Name of the file associated with the document
    private String description; // Description of the document

    // Default constructor
    public Document() {}

    // Constructor for creating a document with specified details
    public Document(String fileName, String description) {
        setDocumentID("D" + baseDocumentID++); // Generate a unique document ID
        this.fileName = fileName;
        this.description = description;
    }


    public String getDocumentID() {
        return documentID;
    }
// Getter and Setter methods for document attributes
    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }
// Getter and Setter methods for document attributes
    public String getFileName() {
        return fileName;
    }
// Getter and Setter methods for document attributes
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
// Getter and Setter methods for document attributes
    public String getDescription() {
        return description;
    }
    // Getter and Setter methods for document attributes
    public void setDescription(String description) {
        this.description = description;
    }
     // Override toString method to provide a string representation of the document
    @Override
    public String toString() {
        return "Document" 
                + "\n   {" + "\n" +  "      documentID: " + documentID 
                + "\n      fileName: " 
                + fileName + "\n      description: " 
                + description 
                + "\n   }";
    }
    
}
