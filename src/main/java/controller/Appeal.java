/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class Appeal {

    private String appealID;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String description;
    private String outcome;
    private Organization organization;
    private ArrayList<Contribution> contributions = new ArrayList<>();
    private ArrayList<Disbursement> disbursements = new ArrayList<>();
    private static int baseAppealID = 100;

    public Appeal(LocalDate fromDate, LocalDate toDate, 
			String description, Organization organization) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.description = description;
        setOrganization(organization);
	setContributions(contributions);
	setDisbursements(disbursements);
        setAppealID("A" + baseAppealID++);
        setOutcome("PENDING");
    }
    public Appeal() {
		this(null, null, null, null);
	}

    public String getAppealID() {
        return appealID;
    }

    public void setAppealID(String appealID) {
        this.appealID = appealID;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public ArrayList<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(ArrayList<Contribution> contributions) {
        this.contributions = contributions;
    }

    public ArrayList<Disbursement> getDisbursements() {
        return disbursements;
    }

    public void setDisbursements(ArrayList<Disbursement> disbursements) {
        this.disbursements = disbursements;
    }
    public String getOrganizationName() {
	return getOrganization().getOrgName();
    }
    
    /**
	 * 
	 * ======= add methods =========
	 */
    
    public boolean addNewDisbursement(Disbursement dis) {
    if (dis != null) {
        if (disbursements != null) {
            return disbursements.add(dis);
        } else {
            throw new NullPointerException("disbursements is null");
        }
    } else {
        throw new IllegalArgumentException("argument is null");
    }
}

    public boolean addNewContribution(Contribution con) {
        if (con != null) {
            if (contributions != null) {
                return contributions.add(con);
            } else {
                throw new NullPointerException("contributions is null");
            }
        } else {
            throw new IllegalArgumentException("argument is null");
        }
    }
    /**
	 * 
	 * ======= total methods =========
	 */
    public double totalContributions() {
    try {
        double cashContributions = getContributions().stream()
                .filter(c -> c instanceof CashDonation)
                .map(c -> (CashDonation) c)
                .mapToDouble(CashDonation::getAmount)
                .sum();

        double goodsContributions = getContributions().stream()
                .filter(c -> c instanceof GoodsDonation)
                .map(c -> (GoodsDonation) c)
                .mapToDouble(GoodsDonation::getEstimatedValue)
                .sum();

        return cashContributions + goodsContributions;
    } catch (NullPointerException e) {
        e.printStackTrace();
        return 0.0; // Return a default value or handle the error as needed.
    }
}

    public double totalDisbursements() {
        try {
            return getDisbursements().stream()
                    .mapToDouble(Disbursement::getCashAmount)
                    .sum();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0.0; // Return a default value or handle the error as needed.
        }
    }
    /**
	 * 
	 * ======= display methods =========
	 */
    
    public String allContributions() {
    try {
        if (contributions != null){
            return getContributions().stream()
            .map(Contribution::toString)
            .collect(Collectors.joining("\n"));
        }else {
            return "No contributions available";
        }
    } catch (Exception e) {
        // Handle the exception, you can log it or take other appropriate action.
        e.printStackTrace();
        return "Error: Unable to fetch contributions"; // Return an error message or handle the error as needed.
    }
}

    public String allDisbursements() {
    try {
        if (disbursements != null){
            return getDisbursements().stream()
            .map(Disbursement::toString)
            .collect(Collectors.joining("\n"));
        }else {
            return "No disbursement available";   
        }
    } catch (Exception e) {
        // Handle the exception, you can log it or take other appropriate action.
        e.printStackTrace();
        return "Error: Unable to fetch disbursements"; // Return an error message or handle the error as needed.
    }
}
      /**
	 * 
	 * ======= has methods =========
	 */
    public boolean hasContributions() {
    if (contributions != null) {
        return contributions.size() > 0;
    } else {
        return false;
    }
}

    public boolean hasDisbursements() {
        if (disbursements != null) {
            return disbursements.size() > 0;
        } else {
            return false;
        }
    }
    /**
	 * 
	 * ======= summary methods =========
	 */
    
    public String generateAppealSummary() {
    return String.format("Summary of Appeal [%s]: %s, Period: %s to %s",
        getAppealID(), getDescription(), getFromDate(), getToDate());
    }
    
    /**
	 *  -------------------number of (counter)
	 */
	
	public int numOfContributions() {
            final ArrayList<Contribution> c = getContributions();
            return c != null ? c.size() : 0;
	}
	
	public int numOfDisbursements() {
            final ArrayList<Disbursement> d = getDisbursements();
            return d != null ? d.size() : 0;
	}
	
	public boolean isPastAppeal() {
		return getToDate().isBefore(LocalDate.now());
	}
	
	public boolean isCurrentAppeal() {
		return !isPastAppeal();
	}
    
       /**
	 * 
	 * ======= to string methods =========
	 */
    
    public String toString() {
		String msg = String.format("Appeal [%s] for %s from %s to %s, " +
			"with status [%s]", getAppealID(), getDescription(), 
			getFromDate(), getToDate(), getOutcome());
		if (hasContributions())
			msg += String.format("\n  has contributions, total %.2f",
				totalContributions());
		if (hasDisbursements())
			msg += String.format("\n  and has already disbursed %.2f",
				totalDisbursements());
		return msg;
	}
	
    
} 
