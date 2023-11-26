/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author glori
 */

import java.io.BufferedReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

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
        
         
    public static void MainMenu(){
         Scanner scanner = new Scanner(System.in);
            // Display menu options
        clearScreen();
        System.out.println("========================================");
        System.out.println("      Welcome to HELP Aid System");
        System.out.println("======Glorious Satria Dhamang Aji=======");
        System.out.println("===========Software Engineer============");
        System.out.println("");
        System.out.println("");
        System.out.println("1. Login");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
        int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    clearScreen();
                    HELPAidConsole.login();
                    break;
                case 0:
                    clearScreen();
                    System.out.println("Exiting HELP Aid System");
                    System.exit(0);
                default:
                    clearScreen();
                    System.out.println("Invalid choice. Please select a valid option.");
            }
    }
    
          
    public static void clearScreen() {
    try {
        if (System.getProperty("os.name").contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    } catch (Exception e) {
        // Handle any exceptions that may occur
    }
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
	 */
	public static void registerApplicant(Organization organization) {
                clearScreen();
                Scanner scanner = new Scanner(System.in);
		if (organization == null)
			organization = selectOrganization();
		System.out.print("Organization: ");
		System.out.printf("%s [%s] at %s", organization.getOrgName(),
			organization.getOrgID(), organization.getAddress());
		System.out.println();
                System.out.print("Full name: ");
		String fullName = scanner.nextLine();
                System.out.print("Email: ");
		String email = scanner.nextLine();
                System.out.print("Mobile No: ");
		String mobileNo = scanner.nextLine();
                System.out.print("ID No: ");
		String idNo = scanner.nextLine();
		
		if (helpAid.duplicateApplicant(idNo)) {
			System.out.println("This IDno '" + idNo + 
				"'has been registered!");
			System.out.println("You can only registered " + 
				"in ONE organization!");
			System.out.println("Addition aborted ...");
			return ;
		}
		
                System.out.print("Address: ");
		String address = scanner.nextLine();
                System.out.print("Household income: ");
		double householdIncome = scanner.nextDouble();
		
		Applicant applicant = new Applicant(fullName, email,
			mobileNo, idNo,	address, householdIncome, 
			organization);
		boolean done = false;
		System.out.println("Uploading supporting document");
                
                while(!done){
                    System.out.print("File name: ");
                    String filename = scanner.next();
                    scanner.nextLine();
                    System.out.print("File Description: ");
                    String description = scanner.next();
                    scanner.nextLine();
                    applicant.addDocument(new Document(filename, description));
                    System.out.print("Submit another document " + "(<Y>es or <N>o)? ");
                    String response = scanner.nextLine();
                    done = response.equalsIgnoreCase("N");
                }
		
		organization.add(applicant);
		helpAid.addUser(applicant);
		
		System.out.println("Applicant added successfully!");
	}
		
    /**
     * Method that initiate user login.
     * Once login, a different menu options will be 
     * displayed, depends on the type of login user.
     */
    public static void login() {
        clearScreen();
    	boolean finished = false;
        Scanner scanner = new Scanner(System.in);
    	do {
            clearScreen();
            System.out.println("Please input your data!");
            System.out.print("Username: ");
            String username = scanner.nextLine();
            loginUser = helpAid.findUser(username);
    		if (loginUser == null) {
            	System.out.println("User name DOES NOT exist!");
          	   	return ;
    		}
    		else {
    			boolean correctPassword = false;
    			do {
                                System.out.print("Password: ");
                                String inPassword = scanner.nextLine();
			        correctPassword = loginUser.getPassword().equals(inPassword); 
			    	if (!correctPassword)
			            System.out.println("Invalid password!");
    			} while (!correctPassword);
        	}
    		finished = true;
    	} while (!finished);

    	// Once login different menu will display,
    	// depending what type of User.
        System.out.println();
        if (loginUser instanceof OrganizationRep) {
        	OrganizationRep user = (OrganizationRep) loginUser;
        	if (user.getUsername().equalsIgnoreCase("Admin"))
        		adminTasks();
        	else 
        		orgRepresentativeTasks();
        }
        else
        	applicantTasks();
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
                        MainMenu();
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
     * Display a menu showing the options what an Administrator
     * can do, obtain user response, and carried out the appropriate
     * actions
     */
	public static void adminTasks() {
                clearScreen();
		System.out.println("Welcome, " + loginUser.getFullname() + 
			" [Administrator]");
		boolean done = false;
		do {
	        String choice = adminMenu();
	        switch (choice) {
	        	case "0":
	        		done = true;
                                clearScreen();
	        		System.out.println("Logging out...");
                                MainMenu();
	        		break;
	        	case "1":
                                clearScreen();
	        		createOrganization();
	        		break;
	        	case "2":
                                clearScreen();
	        		createOrganizationRep();
	        		break;
                        case "3":
                                clearScreen();
                                displayAllUsers();
                                break;
	        	default:
	                System.out.println("Invalid choice");
	        }
	        System.out.println();
		} while (!done);
	}

	/**
	 * Display the Administrator menu options, and get the 
	 * user's response
	 * @return The menu option chosen by user
	 */
	public static String adminMenu() {
            clearScreen();
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
	 * Create a new organization, by admin
	 */
	public static void createOrganization() {
                clearScreen();
		System.out.println();
                Scanner scanner = new Scanner(System.in);
                System.out.print("Organization name: ");
                String name = scanner.nextLine();
                System.out.print("Organization address: ");
		String address = scanner.nextLine();
		Organization organization = new Organization(name, address);
		if (helpAid.addOrganization(organization))
			System.out.println("Addition success...");
		else
			System.out.println("Addition failed...");
	}
	
	/**
	 * Admin creating new organization representative
	 */
	public static void createOrganizationRep() {
                Scanner scanner = new Scanner(System.in);
                clearScreen();
		if (helpAid.numOfOrganizations() == 0){
			System.out.println("Please add an organization ...");
                        createOrganization();
                }else {
			System.out.println("Organizations:");
			System.out.println(helpAid.organizationsToString());
			System.out.println();
                        System.out.print("Please enter organization ID: ");
			String orgID = scanner.nextLine();
			Organization foundOrganization = helpAid.findOrg(orgID);
			if (foundOrganization == null)
				System.out.println("Invalid ID!");
			else {
				System.out.println("Adding new representative for " + 
					foundOrganization.getOrgName());
				boolean done;
				do {
					String username = getUsername();
                                        System.out.print("Full name: ");
                                        String fullName = scanner.next();
                                        scanner.nextLine();
                                        System.out.print("Email: ");
                                        String email = scanner.nextLine();
                                        System.out.print("Mobile number: ");
                                        String mobileNo = scanner.nextLine();
                                        System.out.print("JobTitle ");
					String jobTitle = scanner.nextLine();
					OrganizationRep orgRep = new OrganizationRep( 
						username,fullName, email, mobileNo, jobTitle, 
						foundOrganization);
					helpAid.addUser(orgRep);
					foundOrganization.add(orgRep);
					System.out.println("Addition success...");
                                        System.out.print("Add another " +
						"representative (<Y>es or <N>o)? ");
					String response = scanner.nextLine();
					done = response.equalsIgnoreCase("N");
				} while (!done);
			}
		}
	}

    /**
     * Display a menu showing the options what an organization
     * representative can do, obtain user response, and 
     * carried out the appropriate actions
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
                                MainMenu();
		        	done = true;
		        	break;
		        case "1": 
	        		registerApplicant(orgRep.getOrganization());
	        		break;
		        case "2": 
	        		organizedAidAppeal(orgRep);
	        		break;
	        	case "3":
	        		recordContribution(orgRep);
	        		break;
	        	case "4":
	        		recordDisbursement(orgRep);
	        		break;	        	
	        	default:
	                System.out.println("Invalid choice");
	        }
	        System.out.println();
		} while (!done);		
	}
	
	/**
	 * Display the organization representative menu options, and get 
	 * the staff's response
	 * @return The menu option chosen by user
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
	 * Organizing new aid appeal
	 * @param user Organization representative that organized the appeal
	 */
	public static void organizedAidAppeal(OrganizationRep user) {
		Scanner scanner = new Scanner(System.in);
                System.out.print("From date (dd/mm/yyyy): ");
                String fromDateString = scanner.nextLine();
		LocalDate fromDate = dateFormatter(fromDateString);
                System.out.print("To date (dd/mm/yyyy): ");
                String toDateString = scanner.nextLine();
		LocalDate toDate = dateFormatter(toDateString);
                System.out.print("Description: ");
		String appealDescription = scanner.nextLine();
		Organization organization = user.getOrganization();
		Appeal appeal = new Appeal(fromDate, toDate, appealDescription, 
			organization);
		organization.add(appeal);
		helpAid.addAppeal(appeal);
		System.out.println("Appeal successfully added ... ");
	}

	/**
	 * Method for organization representative to record contributions
	 * by kind donors.
	 * @param user Organization representative who will record the 
	 * contribution
	 */
	public static void recordContribution(OrganizationRep user) {
                Scanner scanner = new Scanner(System.in);
		System.out.println();
		Organization organization = user.getOrganization();
		if (organization.numOfAppeals() == 0)
			System.out.println("No appeal has been created.");
		else {
			System.out.println();
			String currentAppeals = organization.listCurrentAppeals();
			if (currentAppeals.trim().isEmpty())
				System.out.println("No current appeals...");
			else {
				System.out.println("Current Appeals of " + 
					organization.getOrgName() + ":");
				System.out.println(currentAppeals);
                                System.out.println("Enter appeal ID: ");
				String appealID = scanner.nextLine();
				Appeal appeal = organization.findAppeal(appealID);
				if (appeal == null || appeal.isPastAppeal())
					System.out.println("Wrong appeal ID!");
				else { 
					System.out.println(appeal.generateAppealSummary());
                                        System.out.println("Contribute " + 
						"<C>ash or <G>oods? ");
					String contributionType = scanner.nextLine();
					if (contributionType.equalsIgnoreCase("C")) {
                                                System.out.print("Amount? ");
						double amount = scanner.nextDouble();
                                                scanner.nextLine();
                                                System.out.print("Payment channel: ");
						String paymentChannel = scanner.nextLine();
                                                System.out.print("Reference number: ");
						String referenceNo = scanner.nextLine();
                                                appeal.addNewContribution(new CashDonation(appeal, amount, 
							paymentChannel, referenceNo));
					} else {
						System.out.println();
                                                System.out.print("Estimated value" +
							" of goods: ");
						double estimatedValue = scanner.nextDouble();
                                                System.out.print("Description " +
							"of goods: ");
						String description = scanner.nextLine();
                                                appeal.addNewContribution(new GoodsDonation(appeal, description,
							estimatedValue));
					}
					// helpAid's appeal should be UPDATED as well
					// as both are alias
					System.out.println();
					System.out.println("Contribution received");
				}
			}
		}		
	}
	
	/**
	 * Organization representative to record disbursement to applicant.
	 * @param user Organization representative that record the
	 * disbursement.
	 */
	public static void recordDisbursement(OrganizationRep user) {
                Scanner scanner = new Scanner(System.in);
		System.out.println();
		Organization organization = user.getOrganization();
		if (organization.numOfAppeals() == 0)
			System.out.println("No appeal has been created.");
		else {
			System.out.println("Current Appeals of '" + 
				organization.getOrgName() + "':");
			System.out.println(organization.listCurrentAppeals());
			System.out.println();
                        System.out.println("Enter appeal ID: ");
			String appealID = scanner.nextLine();
			Appeal appeal = organization.findAppeal(appealID);
			if (appeal == null)
				System.out.println("Wrong appeal ID!");
			else {
				System.out.println(appeal);
				if (appeal.numOfContributions() != 0) {
					System.out.println("Contributions:");
					System.out.println(appeal.allContributions());
					System.out.println();
					if (organization.numOfApplicants() != 0) {
						String allApplicants = organization.allApplicants();
						System.out.print("Press <Enter> " + 
							"key to view the applications");
						
						boolean done = false;
						do {
							System.out.println("Applicants:");
							System.out.println(allApplicants);
                                                        System.out.println("Enter ID no " +
								"of applicant: ");
							String IDno = scanner.nextLine();
							Applicant app = organization.findApplicantByID(IDno);
						}while (!done);
					}
					else
						System.out.println("No applicant yet...");
				}
				else
					System.out.println("No contributions yet...");
			}
		}
	}
	
    /**
     * Display a menu showing the options what an applicant
     * can do, obtain user response, and carried out the appropriate
     * actions
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
                    MainMenu();
                    done = true;
                        }
	        case "1" -> viewDisbursements(applicant);
	        default -> System.out.println("Invalid choice");
	        } 
	        System.out.println();
		} while (!done);
	}

	/**
	 * Display the Applicant menu options, and get the user's 
	 * response
	 * @return The menu option chosen by user
	 */
	public static String applicantMenu() {
         clearScreen();
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
	 * Method to view disbursements received by an applicant
     * @param user
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
	 * Display all users in the system
	 */
	public static void displayAllUsers() {
        Scanner scanner = new Scanner(System.in);    
        System.out.println();
	   if (helpAid.numOfUsers() == 0) {
	       System.out.println("No users has been registered yet");
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
	 * Display all appeals submitted in the system, sorted
	 * according to Organization Name followed by fromDate.
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
//    	Organization telkom = new Organization("Telkom Indonesia", "Sudirman Center Business District");
      Organization microsoft = new Organization("Microsoft Indonesia", "Jakarta Stock Exchange Building Tower II");
//    	helpAid.addOrganization(telkom);
    	helpAid.addOrganization(microsoft);
    	
//    	OrganizationRep orgRep01 = new OrganizationRep("alex",
//    		"John Alexis Doe","alexis@gmail.com", "012-123", "Manager",
//    		telkom);
//        orgRep01.setPassword("password");
//    	telkom.add(orgRep01);
//    	helpAid.addUser(orgRep01);
 
    	OrganizationRep orgRep02 = new OrganizationRep("ronald",
    		"Ronald Kalaywar","ronald@gmail.com", "012-123", "Executive",
    		microsoft);
        orgRep02.setPassword("password");
    	microsoft.add(orgRep02);
    	helpAid.addUser(orgRep02);		
        
         Applicant app2 = new Applicant("Poor Woman", "poor@gmail.com",
    		"012-345", "AP101", "4, Jln 321", 1000.00,
    		microsoft);
        app2.addDocument(new Document("income.xlsx", "Income Tax file"));
    	microsoft.add(app2);
    	helpAid.addUser(app2);
        Appeal apeal = new Appeal(LocalDate.parse("11/12/2021", formatter) , LocalDate.parse("11/11/2022", formatter), "description", microsoft);
        Disbursement dis = new Disbursement(LocalDate.parse("11/12/2023",formatter),0.5,0.5,apeal,app2);
        app2.addDisbursement(dis);
//    	helpAid.addAppeal(apeal);
//    	telkom.add(apeal);
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
        
        // save to file
        writeToUsersFile();
    }
    
    public static void addAppeal(Appeal app1, Organization org) {
        
    	org.add(app1);
    	helpAid.addAppeal(app1);
        
        // save to file
        writeToOrganizationsFile();
        writeToAppealsFile();
    }
    
    public static void addGoodDonat(Appeal ap, GoodsDonation gd){
        ap.addNewContribution(gd);
        
        writeToAppealsFile();
    }
    
    public static void addCashDonat(Appeal ap, CashDonation cd){
        ap.addNewContribution(cd);
        
        writeToAppealsFile();
    }
    
    public static void addDisburs(Applicant apl, Disbursement ds, Appeal ap){
        ap.addNewDisbursement(ds);
        apl.addDisbursement(ds);
        
        writeToAppealsFile();
        writeToUsersFile();
    }
    
    public static void addRep(OrganizationRep rep, Organization org){
        org.getReps().add(rep);
        
        helpAid.addUser(rep);
        
        // save to file
        writeToUsersFile();
        
        helpAid.getOrganizations().stream().filter(o->o.getOrgID().equals(org.getOrgID())).findFirst().orElse(null).add(rep);
        
        writeToOrganizationsFile();
    }
    public static void addOrg(Organization org){
        
        
        helpAid.addOrganization(org);
        
        // save to file
        writeToOrganizationsFile();
        
        
    }
    
    
    // The following methods are used for getting user input, 
    // and also performing validation checks
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /**
     * Get a valid username
     * @return String username
     */
    public static String getUsername() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
    	String username = scanner.nextLine();
    	while (helpAid.findUser(username) != null) {
            System.out.println("""
                               Username already exists. Please enter another username!
                               """);
            System.out.println("Username: ");
            username = scanner.nextLine();
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
     * Getting text input from user, with validation for empty string. 
     * Value of input depends on the prompt.
     * @param prompt The prompt showing what information is
     * being read.
     * @return text The textual value entered by user.
     */
   
    /**
     * Getting a positive numeric value
     * @param value
     * @return a positive numeric value
     */    public static double getNumeric(String value) {
    	return 0.0;
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static void readFromFile() {
    	System.out.println("Reading from file...");
    	System.out.print("File name? ");
    	String fileName = "filename.txt";
    	FileInputStream fis;
            fis = null;
    	ObjectInputStream ois;
            ois = null;
    	try {
    	fis = new FileInputStream(fileName);
    	ois = new ObjectInputStream(fis);
    	System.out.println("is this a record for organizations, appeals or users?");
    	char choice = 'o';
    	switch(choice) {
    	case'O' ->  {
            helpAid.setOrganizations((ArrayList<Organization>) ois.readObject()) ;
            ois.close();
            fis.close();
                    }
    	case'A' ->  {
            helpAid.setAppeals((ArrayList<Appeal>)ois.readObject());
            ois.close();
            fis.close();
                    }
    	case'U' ->  {
            helpAid.setUsers((ArrayList<User>)ois.readObject());
            ois.close();
            fis.close();
                    }
    	default -> System.out.println("Invalid choice");
    	}
    	
    	} catch (FileNotFoundException fnfe) {
    	System.out.println("'" + fileName + "' does not exist.");
    	//fnfe.printStackTrace();
    	} catch (IOException ioe) {
    	System.out.println("Error in reading....");
    	} catch (ClassNotFoundException cnfe) {
    	System.out.println("Error in casting...");
    	}
    }
    
    
    public static boolean writeToAppealsFile() {
        String dataStr = "";
        dataStr = helpAid.getAppeals().stream().map((ap) -> ap.toString() + "\n").reduce(dataStr, String::concat);
        return writeToFile("appeals.txt", dataStr);
    }
    
    public static boolean writeToOrganizationsFile() {
        return writeToFile("organizations.txt", helpAid.getOrganizations());
    }
    
    public static boolean writeToUsersFile() {
        String dataStr = "";
        dataStr = helpAid.getUsers().stream().map((u) -> u.toString() + "\n").reduce(dataStr, String::concat);
        return writeToFile("users.txt", dataStr);
    }
    
    public static boolean writeToContributionsFile(String appId) {
        return writeToFile("con.txt", helpAid.getAppeals().stream().filter(o -> o.getAppealID().equals(appId)).findFirst().map(o -> o.getContributions()));
    }
    
    private static boolean writeToFile(String fileName, Object data) {
    	FileOutputStream fos;
    	ObjectOutputStream oos;
    	try {
            fos = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(data.toString());
            fos.close();
            oos.close();
            return true;
    	} catch (IOException fnfe) {
            System.out.println("Errors in writing to file");
    	} catch (Exception e) {
            System.out.println("Errors in writing to file");
    	}
        return false;
    }
    
    
    /// read OrganizationRep & Applicant from file
    public static void readUserFile(String fileName) {
    	try (FileReader fr = new FileReader(fileName); BufferedReader br = new BufferedReader(fr); ) {
            HELPAidConsole.helpAid.setUsers(new ArrayList());
            String aLine;
            do {
                aLine = br.readLine();
                final boolean isRep = aLine.contains(OrganizationRep.holds_job_title);
                final String[] tempArrData = aLine.split(";");
                if (tempArrData.length > 0) {
                    final String[] data = tempArrData[0].split(","); 
                    if (isRep) {
                        // OrganizationRep
                        final OrganizationRep rep = new OrganizationRep();
                        for (String d : data) {
                            final String[] tokens = d.split("=");
                            if (tokens.length == 2) {
                                final String key = tokens[0];
                                final String value = tokens[1].trim();

                                if (key.contains("userName")) {
                                    rep.setUsername(value);
                                } else if (key.contains("fullName")) {
                                    rep.setFullname(value);
                                } else if (key.contains("email")) {
                                    rep.setEmail(value);
                                } else if (key.contains("mobileNo")) {
                                    rep.setMobileNo(value);
                                }
                            }
                        }
                        
                        final String[] extra = tempArrData[1].split("=");
                        if (extra.length == 2) {
                            rep.setJobTitle(extra[1]);
                        }
                        
                    } else  {
                        //Applicant
                        final Applicant applicant = new Applicant();
                        for (String d : data) {
                            final String[] tokens = d.split("=");
                            if (tokens.length == 2) {
                                final String key = tokens[0];
                                final String value = tokens[1].trim();

                                if (key.contains("userName")) {
                                    applicant.setUsername(value);
                                } else if (key.contains("fullName")) {
                                    applicant.setFullname(value);
                                } else if (key.contains("email")) {
                                    applicant.setEmail(value);
                                } else if (key.contains("mobileNo")) {
                                    applicant.setMobileNo(value);
                                }
                            }
                        }
                      
                        final String[] extra = tempArrData[1].split("@@@");
                        if (extra.length > 0) {
                            final String[] info = extra[0].split("=");
                            for (String info1 : info) {
                                final String[] tokens = info1.split("=");
                                if (tokens.length == 2) {
                                    final String key = tokens[0];
                                    final String value = tokens[1].trim();

                                    if (key.contains("ID No")) {
                                        applicant.setIDNo(value);
                                    } else if (key.contains("address")) {
                                        applicant.setAddress(value);
                                    } else if (key.contains("income")) {
                                        try {
                                            applicant.setHouseholdIncome(Double.parseDouble(value));
                                        } catch(NumberFormatException e) {
                                            System.out.println(e.getMessage());
                                            applicant.setHouseholdIncome(0);
                                        }
                                    }
                                }
                            }
                            //Documents
                            ArrayList<Document> documents = new ArrayList();
                            for(int j = 1; j < extra.length; j++) {
                                final Document document = new Document();
                                final String[] doc = extra[j].split("=");
                                for (String doc1 : doc) {
                                    final String[] tokens = doc1.split("=");
                                    if (tokens.length == 2) {
                                        final String key = tokens[0];
                                        final String value = tokens[1].trim();

                                        if (key.contains("documentID")) {
                                            document.setDocumentID(value);
                                        } else if (key.contains("fileName")) {
                                            document.setFileName(value);
                                        } else if (key.contains("description")) {
                                            document.setDescription(value);
                                        } 
                                    }
                                    documents.add(document);
                                }
                            }
                            applicant.setDocuments(documents);
                        }
                    }
                }
            } while (null != aLine);
            br.close();
            fr.close();
    	} catch (FileNotFoundException fnfe) {
            System.out.println("'" + fileName + "' does not exist.");
            //fnfe.printStackTrace();
    	} catch (IOException ioe) {
            System.out.println("Error in reading....");
    	}
    }
    
}