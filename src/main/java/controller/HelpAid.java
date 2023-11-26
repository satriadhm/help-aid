/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;


public class HelpAid {
    private ArrayList<Organization> organizations;
    private ArrayList<Contribution> contributions;
    private ArrayList<Appeal> appeals;
    private ArrayList<User> users;

    public HelpAid() {
       	setOrganizations(new ArrayList<>());
	setAppeals(new ArrayList<>());
	setUsers( new ArrayList<>());
        setContributions(new ArrayList<>());
    }
     public ArrayList<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(ArrayList<Contribution> contributions) {
        this.contributions = contributions;
    }
    public ArrayList<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(ArrayList<Organization> organizations) {
        this.organizations = organizations;
    }

    public ArrayList<Appeal> getAppeals() {
        return appeals;
    }

    public void setAppeals(ArrayList<Appeal> appeals) {
        this.appeals = appeals;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
    
          /**
	 * 
	 * ======= add methods =========
	 */
    public boolean addUser(User user){
        return users.add(user);
    }
    
    public boolean addOrganization(Organization organization){
        return getOrganizations().add(organization);
    }
    
    public boolean addAppeal(Appeal appeal){
        return getAppeals().add(appeal);
    }
    
     /**        ======= find methods =========
	 * 
	 * ======= find User
     * @param userName
     * @return 
	 */
        public User findUser(String userName) {
		return getUsers().stream()
				.filter(u -> userName.equalsIgnoreCase(u.getUsername()))
				.findFirst()
				.orElse(null);
	}
        
    /**  
	 * 
	 * ======= find Applicant 
     * @param userID
     * @return 
	 */    
	
	public Applicant findApplicantID(String userID) {
		Applicant found = null;
		for(int i = 0; i < getUsers().size(); i++) {
			User u = getUsers().get(i);
			if(u instanceof Applicant applicant) {
				if(applicant.getIDNo().equals(userID))
					found = applicant;
			}
		}
		return found;
	}
     /**  
	 * 
	 * ======= find Organization 
     * @param orgID
     * @return 
	 */
        
        public Organization findOrg(String orgID){
        return getOrganizations().stream()
			.filter(o -> orgID.equalsIgnoreCase(o.getOrgID()))
			.findFirst()
			.orElse(null);
        }
    /**  
	 * 
	 * ======= find Organization 
     * @param appealID
     * @return 
	 */
        
        public Appeal findAppeal(String appealID) {
		return getAppeals().stream()
				.filter(a -> appealID.equalsIgnoreCase(a.getAppealID()))
				.findFirst()
				.orElse(null);
	}
        /**
	 * ------------------------------toString methods
	 * @return
	 */
	
	public String organizationsToString() {
		return getOrganizations().stream()
				.map(Organization::toString)
				.collect(Collectors.joining("\n"));
	}
	
	public String appealsToString() {
		return getAppeals().stream()
				.map(Appeal::toString)
				.collect(Collectors.joining("\n"));
	}
	
	public String usersToString() {
		return getUsers().stream()
				.map(User::toString)
				.collect(Collectors.joining("\n"));
	}
	  /**
	 * ------------------------------Sorting methods
	 * @return
	 */
	public String listUsersSortedByFullname() {
		return getUsers().stream()
			.sorted()
			.map(User::toString)
			.collect(Collectors.joining("\n"));
	}
	
	public String listSortedAppeals() {
		return getAppeals().stream()
			.sorted(Comparator.comparing(
				Appeal::getOrganizationName)
				.thenComparing(Appeal::getFromDate))
			.map(Appeal::toString)
			.collect(Collectors.joining("\n"));
	}
	
	public int numOfUsers() {
		return getUsers().size();
	}
	
	public int numOfAppeals() {
		return getAppeals().size();
	}
	
	public int numOfOrganizations() {
		return getOrganizations().size();
	}
	
	public ArrayList<Appeal> currentAppealList() {
		return (ArrayList<Appeal>) getAppeals().stream()
			.filter(Appeal::isCurrentAppeal)
			.collect(Collectors.toList());
	}

	public ArrayList<Appeal> pastAppealList() {
		return (ArrayList<Appeal>) getAppeals().stream()
			.filter(Appeal::isPastAppeal)
			.collect(Collectors.toList());
	}
	
	public boolean duplicateApplicant(String IDno) {
		Applicant app = getUsers().stream()
			.filter(Applicant.class::isInstance)
			.map(a -> (Applicant) a)
			.filter(a -> a.getIDNo().equalsIgnoreCase(IDno))
			.findAny()
			.orElse(null);
		return app != null; 
	}
}
