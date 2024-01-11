/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class HELPAidConsole {
	public static HelpAid helpAid;
	public static User loginUser;
	public static DateTimeFormatter formatter = DateTimeFormatter.
		ofPattern("dd/MM/yyyy");

	/**
	 * The main method that starts the application.
	 */
	public static void init() {
            helpAid = new HelpAid();
            // initialize system's administrator
            User admin = new OrganizationRep("admin", "Superuser",
                    "meiska@gmail.com", "1241234215", "admint",null);
            admin.setPassword("admin");
           
            helpAid.addUser(admin);
            addOthers(); // adding other data
        }
        
        public static ArrayList<String> getOrganizationName(){
            ArrayList<String> organizationNames = new ArrayList<>();
            for (int i = 0 ; i < helpAid.getOrgCounts(); i++){
                organizationNames.add(helpAid.getOrganizations().get(i).getOrgID());
            }
            return organizationNames;
        }
        
        public static String findOrgNameFromID(String orgID){
            return helpAid.findOrg(orgID).getOrgName();
        }
        
        public static ArrayList<User> getAllUsers(){
            return helpAid.getUsers();
        }
        
         
    //+++++++++++++++
	/**
	 * PRE-CONDITION: Organizations exist in system.
	 * @return
	 */
	public static Organization selectOrganization() {
                Scanner scanner = new Scanner(System.in);
		Organization organization = null;
		int attempt = 0;
		do {
			System.out.println("List of registered organisations:");
			System.out.println(helpAid.organizationsToString());
			System.out.println();
                        System.out.println("Please enter organization ID: ");
			String orgID = scanner.nextLine();
			organization = helpAid.findOrg(orgID);
			if (organization == null){
				System.out.println("Invalid ID!");
                                System.out.println("Please input the organization ID again!");
                        }else {
				System.out.println("Selected organization:");
				System.out.printf("%s [%s] at %s", organization.getOrgName(),
					organization.getOrgID(), organization.getAddress());
			}
		} while (attempt < 3);
		return organization;
	}
	
	/**
	 * Registering new applicants.
         * @param organization
         * @param fullName
         * @param email
         * @param mobileNo
     * @param address
         * @param householdIncome
	 */
	public static void registerApplicant(Organization organization,
                                             String fullName,
                                             String email,
                                             String mobileNo, 
                                             String address,
                                             double householdIncome) {
		if (organization == null)
			organization = selectOrganization();
		String idNo = "AP" + JOptionPane.showInputDialog("ID No:");
                if (idNo == null) {
                    JOptionPane.showMessageDialog(null, "Addition aborted ...");
                    return;
                }
                if (helpAid.duplicateApplicant(idNo)) {
                    JOptionPane.showMessageDialog(null,
                            "This IDno '" + idNo + "' has been registered!\n" +
                            "You can only register in ONE organization!\n" +
                            "Addition aborted ...",
                            "Duplicate Registration Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
		Applicant applicant = new Applicant(fullName, email,
			mobileNo, idNo,	address, householdIncome, 
			organization);
		boolean done = false;
		System.out.println("Uploading supporting document");
                while(!done){
                    String filename = JOptionPane.showInputDialog("File name:");
                    String description = JOptionPane.showInputDialog("File Description:");
                    applicant.addDocument(new Document(filename, description));
                    System.out.print("Submit another document " + "(<Y>es or <N>o)? ");
                    int option = JOptionPane.showConfirmDialog(null, "Submit another document?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    done = (option == JOptionPane.NO_OPTION);
                }
		organization.add(applicant);
		helpAid.addUser(applicant);
                JOptionPane.showMessageDialog(null, "Applicant added successfully!");
	}
		
    /**
     * Method that initiate user login.Once login, a different menu options will be 
 displayed, depends on the type of login user.
     * @param username
     * @param password
     */
    public static void login(String username, String password) {
    	var finished = false;
    	do {
            loginUser = helpAid.findUser(username);
    		if (loginUser == null) {
                JOptionPane.showMessageDialog(null, "User name DOES NOT exist!");
                break;
    		}
    		else {
    		boolean correctPassword;
                correctPassword = false;
    			do {
			        correctPassword = loginUser.getPassword().equals(password); 
			    	if (!correctPassword)
                                    JOptionPane.showMessageDialog(null, "User name DOES NOT exist!");
                                    break;
    			} while (!correctPassword);
        	}
    		finished = true;
    	} while (!finished);
    	// Once login different menu will display,
    	// depending what type of User.
    }
    /**
     * Method for viewing appeals by Donor
     */
    public static void viewAppeals() {
                Scanner scanner = new Scanner(System.in);
		System.out.println();
		if (helpAid.numOfAppeals() == 0)
			System.out.println("No appeal has been created.");
		else {
			boolean done = false;
			do {
				System.out.println("View ");
				System.out.println("1. Current Appeals");
				System.out.println("2. Past Appeals");
				System.out.println();
				System.out.println("0. Back to Main");
                                System.out.println("Your choice?");
				String response = scanner.nextLine();
				switch (response) {
				case "0" -> done = true;
				case "1" -> {
                                    ArrayList<Appeal> currentAppealList =
                                            helpAid.currentAppealList();
                                    if (currentAppealList.isEmpty()) {
                                        System.out.println("No current appeals!");
                                    }
                                    else {
                                        System.out.println("Current Appeals:");
                                        viewAppealDetails(currentAppealList);
                                    }
                                }
				case "2" -> {
                                    ArrayList<Appeal> pastAppealList =
                                            helpAid.pastAppealList();
                                    if (pastAppealList.isEmpty()) {
                                        System.out.println("No past appeals!");
                                    }
                                    else {
                                        System.out.println("Past Appeals:");
                                        viewAppealDetails(pastAppealList);
                                    }
                                }
				default -> System.out.println("Invalid choice! Try Again!");
				}
				System.out.println();
			} while (!done);
		}    	
    }
    
    /**
     * Helper method for viewing appeals by donor.
     * @param appealList Array list of Appeal
     */
    public static void viewAppealDetails(ArrayList<Appeal> appealList) {
        Scanner scanner = new Scanner(System.in);
    	appealList.forEach(a -> System.out.println(a.generateAppealSummary()));
    	System.out.println();
        System.out.println("Select appeal ID to view detail: ");
    	String appealID = scanner.nextLine();
    	Appeal appeal = appealList.stream()
    		.filter(a -> appealID.equalsIgnoreCase(a.getAppealID()))
    		.findAny()
    		.orElse(null);
    	
    	if (appeal == null)
    		System.out.println("Invalid appeal ID!");
    	else {
    		System.out.println("Appeal by " + 
    			appeal.getOrganization().getOrgName() + " at " + 
    			appeal.getOrganization().getAddress());
    	}
    }
    
    /**
     * Method where Applicant make a cash donation online.
     */
    public static void makeCashContribution() {
        Scanner scanner = new Scanner(System.in);
    	System.out.println();
		if (helpAid.numOfAppeals() == 0)
			System.out.println("No appeal has been created.");
		else {
			System.out.println();
			
			ArrayList<Appeal> currentAppeals = helpAid.currentAppealList();
			if (currentAppeals.isEmpty())
				System.out.println("There does not have current appeals!");
			else {
				System.out.println("Current Appeals:");
				currentAppeals.forEach(System.out::println);

				System.out.println();
                                System.out.println("Enter appeal ID: ");
				String appealID = scanner.nextLine();
				
				Appeal appeal = currentAppeals.stream()
		    		.filter(a -> appealID.equalsIgnoreCase(a.getAppealID()))
		    		.findAny()
		    		.orElse(null);
				
			}
		}
    }
     
    /**
        Present a menu outlining the available actions for an Administrator,
        collect user input, and execute the corresponding operations accordingly.
*/
     
	public static void adminTasks() {
                System.out.println("Welcome, " + loginUser.getFullname() + 
                            " [Administrator]");
		boolean done = false;
		do {
	        String choice = adminMenu();
	        switch (choice) {
	        	case "0":
	        		done = true;
	        		System.out.println("Logging out...");
	        		break;
	        	case "1":
	        		//createOrganization();
	        		break;
	        	case "2":
//	        		createOrganizationRep();
	        		break;
                        case "3":
                                displayAllUsers();
                                break;
	        	default:
	                System.out.println("Invalid choice");
	        }
	        System.out.println();
		} while (!done);
	}

	/**

Present the menu choices available for the Administrator and capture
the user's selection.
@return The selected menu option made by the user.
*/
	public static String adminMenu() {
            System.out.println("Welcome to Admin Menu!");
            System.out.println("Choose your choice: ");
            System.out.println("1. create organization. ");
            System.out.println("2. Create organization Representative.");
            System.out.println("3. Display all users.");
            System.out.println("0. Log out.");
            System.out.print("Your choice: ");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            return choice;
	}

	
/**

Establish a new organization, performed by an administrator.
*/
	public static void createOrganization(String name, String address ) {
		Organization organization = new Organization(name, address);
		if (helpAid.addOrganization(organization))
                        JOptionPane.showMessageDialog(null, "Addition success...");
		else
                        JOptionPane.showMessageDialog(null, "Addition failed...");
	}
	
	/**

Administrator initiates the creation of a new organization representative.
*/
	public static void createOrganizationRep(String orgID, String fullName, String email, String mobileNo, String jobTitle) {
			Organization foundOrganization = helpAid.findOrg(orgID);
			if (foundOrganization == null)
                           JOptionPane.showMessageDialog(null, "Invalid ID!");
			else {
                            String username = getUsername();
                            OrganizationRep orgRep = new OrganizationRep(username,fullName, email, mobileNo, jobTitle,foundOrganization);
                            helpAid.addUser(orgRep);
                            foundOrganization.add(orgRep);
                            JOptionPane.showMessageDialog(null,"Addition success...");
			}
		
	}    
/**

Present a menu outlining the available actions for an Organization Representative,
collect user input, and execute the corresponding operations accordingly.
*/
	public static void orgRepresentativeTasks() {
		OrganizationRep orgRep = (OrganizationRep) loginUser;
		System.out.println("Welcome, " + orgRep.getFullname() + 
			" [" + orgRep.getJobTitle() + " of " + 
			orgRep.getOrganization().getOrgName() + "]");
		
		boolean done = false;
		do {
	        String choice = orgRepresentativeMenu();
	        switch (choice) {
		        case "0":
		        	System.out.println("Logging out...");
		        	done = true;
		        	break;
		        case "1": 
	        		//registerApplicant(orgRep.getOrganization());
	        		break;
		        case "2": 
	        		//organizedAidAppeal(orgRep);
	        		break;
	        	case "3":
	        		//recordContribution(orgRep);
	        		break;
	        	case "4":
	        		//recordDisbursement(orgRep);
	        		break;	        	
	        	default:
	                System.out.println("Invalid choice");
	        }
	        System.out.println();
		} while (!done);		
	}
	

/**

Present the menu choices available for the Organization Representative and capture
the staff's selection.
@return The selected menu option made by the user.
*/
	public static String orgRepresentativeMenu() {
            System.out.println("Welcome to Organization Representative Menu!");
            System.out.println("Choose your choice: ");
            System.out.println("1. Register Applicant. ");
            System.out.println("2. Organized Aid Appeal.");
            System.out.println("3. Record Contribution.");
            System.out.println("4. Record Disbursement.");
            System.out.println("0. Log out.");
            System.out.print("Your choice: ");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            return choice;
	}

	
/**

Initiate the creation of a new aid appeal, facilitated by the specified
Organization Representative.
@param user The Organization Representative organizing the appeal.
     * @param fromDateString
     * @param toDateString
     * @param appealDescription
*/
        public static void organizedAidAppeal(OrganizationRep user, 
                                              String fromDateString, 
                                              String toDateString, 
                                              String appealDescription) {
            LocalDate fromDate = dateFormatter(fromDateString);
            LocalDate toDate = dateFormatter(toDateString);
            Organization organization = user.getOrganization();
            Appeal appeal = new Appeal(fromDate, toDate, appealDescription,organization);
            organization.add(appeal);
            helpAid.addAppeal(appeal);
            JOptionPane.showMessageDialog(null, "Appeal successfully added ... ");
	}

/**

Method for an Organization Representative to document contributions
from generous donors.
@param user The Organization Representative responsible for recording the contribution.
     * @param paymentChannel
     * @param referenceNo
     * @param appealID
     * @param amount
     * @param contributionType
     * @param estimatedValue
     * @param description
*/
	public static void recordContribution(OrganizationRep user,
                                              String paymentChannel, 
                                              String referenceNo, 
                                              String appealID, 
                                              double amount,
                                              String contributionType,
                                              double estimatedValue,
                                              String description ) {
		Organization organization = user.getOrganization();
		if (organization.numOfAppeals() == 0)
                        JOptionPane.showMessageDialog(null, "No appeal has been created.");
		else {
			String currentAppeals = organization.listCurrentAppeals();
			if (currentAppeals.trim().isEmpty())
                                JOptionPane.showMessageDialog(null, "No current appeals...");
			else {
				Appeal appeal = organization.findAppeal(appealID);
				if (appeal == null || appeal.isPastAppeal())
                                       JOptionPane.showMessageDialog(null, "Wrong appeal ID!");
				else { 
					String appealSummary =appeal.generateAppealSummary();
                                        JOptionPane.showMessageDialog(null, appealSummary, "Appeal Summary", JOptionPane.INFORMATION_MESSAGE);
					if (contributionType.equalsIgnoreCase("Cash")) {
                                                appeal.addNewContribution(new CashDonation(appeal, amount, 
							paymentChannel, referenceNo));
					} else {
                                                appeal.addNewContribution(new GoodsDonation(appeal, description,
							estimatedValue));
					}
					// helpAid's appeal should be UPDATED as well
					// as both are alias
                                        JOptionPane.showMessageDialog(null, "Contribution received");
				}
			}
		}		
	}
	
	
/**

Organization Representative records the disbursement to an applicant.
@param user The Organization Representative responsible for documenting the disbursement.
     * @param appealID
     * @param IDno
*/
	public static void recordDisbursement(OrganizationRep user, 
                                              String appealID, 
                                              String applicantID,
                                              double cashAmount,
                                              double goodDisbursed) {
		Organization organization = user.getOrganization();
		if (organization.numOfAppeals() == 0)
                        JOptionPane.showMessageDialog(null, "No appeal has been created.");
		else {
			Appeal appeal = organization.findAppeal(appealID);
			if (appeal == null)
                                JOptionPane.showMessageDialog(null, "Wrong appeal ID!");
			else {
				System.out.println(appeal);
				if (appeal.numOfContributions() != 0) {
					if (organization.numOfApplicants() != 0) {
                                                Applicant app = organization.findApplicantByID(applicantID);
                                                addDisburs(app, new Disbursement(LocalDate.now(),cashAmount,goodDisbursed,appeal,app),appeal);
					}
					else
                                                JOptionPane.showMessageDialog(null, "No applicant yet...");
				}
				else
                                        JOptionPane.showMessageDialog(null,"No contributions yet...");
			}
		}
	}
	
/**

Present a menu outlining the available actions for an Applicant,
collect user input, and execute the corresponding operations accordingly.
*/
	public static void applicantTasks() {
		Applicant applicant = (Applicant) loginUser;
		System.out.println("Welcome, " + applicant.getFullname() + 
			" [Applicant of " + 
			applicant.getOrganization().getOrgName() + "]");
		
		boolean done = false;
		do {
	        String choice = applicantMenu();
	        switch (choice) {
	        case "0" -> {
                    System.out.println("Logging out...");
                    done = true;
                        }
	        case "1" -> viewDisbursements(applicant);
	        default -> System.out.println("Invalid choice");
	        } 
	        System.out.println();
		} while (!done);
	}

	
/**

Present the menu choices available for the Applicant and capture
the user's selection.
@return The selected menu option made by the user.
*/
	public static String applicantMenu() {
            System.out.println("Welcome to Applicant Menu!");
            System.out.println("Choose your choice: ");
            System.out.println("1. View Disbursement. ");
            System.out.println("0. Log out.");
            System.out.print("Your choice: ");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            return choice;
	}	


/**

Method to view the disbursements received by an Applicant.
@param user The Applicant for whom disbursements are being viewed.
*/
	public static void viewDisbursements(Applicant user) {
                Scanner scan = new Scanner(System.in);
		if (user.numOfDisbursements() == 0) {
			System.out.println("You have not been given " +
				"any disbursements yet.");
			return ;
		}
		System.out.println("\nDisbursements:");
		user.getDisbursements().stream()
			.forEach(System.out::println);
                System.out.println("Press enter to continue...");
                scan.nextLine();
	}
	
/**

Present a list of all users in the system.
*/
	public static void displayAllUsers() {
        Scanner scanner = new Scanner(System.in);    
        System.out.println();
	   if (helpAid.numOfUsers() == 0) {
               JOptionPane.showMessageDialog(null, "No users has been registered yet");
	   } 
	   else {
               System.out.println("Criteria? " + "(<O>riginal) ");
               String criteria = scanner.nextLine().toUpperCase();
	       System.out.println("List of users:");
	       System.out.println(criteria.charAt(0)=='O'? 
				helpAid.usersToString(): 
				helpAid.listUsersSortedByFullname());
	   }
            System.out.print("Press enter to continue: ");
            scanner.nextLine();
	}
	
	
/**

Present a list of all appeals submitted in the system, organized
in ascending order based on Organization Name and then fromDate.
*/
	public static void displaySortedAppeals() {
		System.out.println();
		if (helpAid.numOfAppeals() == 0)
			System.out.println("No appeal has been created.");
		else {
			System.out.println("All appeals sorted according Organization " +
				"name, followed by fromDate:");
			System.out.println(helpAid.listSortedAppeals());
		}
	}
	
    /**
     * Method used to initialize some domain objects, such as 
     * Organization, OrganizationRep (admin), and Applicant.
    */
    public static void addOthers() {
      Organization telkom = new Organization("Telkom Indonesia", "Sudirman Center Business District");
      Organization microsoft = new Organization("Microsoft Indonesia", "Jakarta Stock Exchange Building Tower II");
    	helpAid.addOrganization(telkom);
    	helpAid.addOrganization(microsoft);
    	
    	OrganizationRep orgRep01 = new OrganizationRep("alex",
    		"John Alexis Doe","alexis@gmail.com", "012-123", "Manager",
    		telkom);
        orgRep01.setPassword("password");
    	telkom.add(orgRep01);
    	helpAid.addUser(orgRep01);
 
    	OrganizationRep orgRep02 = new OrganizationRep("ronald",
    		"Ronald Kalaywar","ronald@gmail.com", "012-123", "Executive",
    		microsoft);
        orgRep02.setPassword("password");
    	microsoft.add(orgRep02);
    	helpAid.addUser(orgRep02);		
        
         Applicant app2 = new Applicant("Sujimah Mendhuk", "poor@gmail.com",
    		"012-345", "AP101", "4, Jln 321", 1000.00,
    		microsoft);
        app2.addDocument(new Document("income.xlsx", "Income Tax file"));
    	microsoft.add(app2);
    	helpAid.addUser(app2);
        Appeal apeal = new Appeal(LocalDate.parse("11/12/2021", formatter) , LocalDate.parse("11/11/2022", formatter), "description", microsoft);
        Disbursement dis = new Disbursement(LocalDate.parse("11/12/2023",formatter),0.5,0.5,apeal,app2);
        app2.addDisbursement(dis);
    	helpAid.addAppeal(apeal);
    	telkom.add(apeal);
    }
	
    /**
     * Method used to initialize some domain objects, such as 
     * Organization, OrganizationRep (admin), and Applicant.
     * @param app1
     * @param doc
     * @param org
    */
    public static void addApplicant(Applicant app1, Document doc, Organization org) {
        app1.addDocument(doc);
    	org.add(app1);
    	helpAid.addUser(app1);
        
    }
    
    public static void addAppeal(Appeal app1, Organization org) {
        
    	org.add(app1);
    	helpAid.addAppeal(app1);
        
     
    }
    
    public static void addGoodDonat(Appeal ap, GoodsDonation gd){
        ap.addNewContribution(gd);
        
     
    }
    
    public static void addCashDonat(Appeal ap, CashDonation cd){
        ap.addNewContribution(cd);
        

    }
    
    public static void addDisburs(Applicant apl, Disbursement ds, Appeal ap){
        ap.addNewDisbursement(ds);
        apl.addDisbursement(ds);
        
       
    }
    
    public static void addRep(OrganizationRep rep, Organization org){
        org.getReps().add(rep);
        helpAid.addUser(rep);
        helpAid.getOrganizations().stream().filter(o->o.getOrgID().equals(org.getOrgID())).findFirst().orElse(null).add(rep);
        
       
    }
    public static void addOrg(Organization org){
        helpAid.addOrganization(org);
    }
    
    
    // The following methods are used for getting user input, 
    // and also performing validation checks
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /**
     * Get a valid username
     * @return String username
     */
    public static String getUsername() {
          String username = JOptionPane.showInputDialog(null, "Enter username:");
        while (helpAid.findUser(username) != null) {
            JOptionPane.showMessageDialog(null, "Username already exists. Please enter another username!");
            username = JOptionPane.showInputDialog(null, "Enter username:");
        }
        return username;
    }

    /**
     * Get a valid date
     * @param dateString
     * @return date a valid LocalDate
     */
    public static LocalDate dateFormatter(String dateString) {
    	LocalDate date = LocalDate.parse(dateString, formatter); 
    	return date;
    }


    /**
     * Getting a positive numeric value
     * @param value
     * @return a positive numeric value
     */    public static double getNumeric(String value) {
    	return 0.0;
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}