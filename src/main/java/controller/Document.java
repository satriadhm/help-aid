/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


public class Document {
    static int baseDocumentID = 100;
    private String documentID;
    private String fileName;
    private String description;
    
    public Document(){}

    public Document( String fileName, String description) {
        setDocumentID("D"+baseDocumentID++);
        this.fileName = fileName;
        this.description = description;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
