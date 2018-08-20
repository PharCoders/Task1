import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class member implements Serializable {

	private String lastName; //LN to lastName.
	private String firstName; //FN to firstName.
	private String email; //EM to email.
	private int phoneNo; //PN to phoneNo.
	private int memberID; //ID to memberID.
	private double fines; // FINES to fines.
	
	private Map<Integer, loan> LNS;

	
	public member(String lastName, String firstName, String email, int phoneNo, int memberID) { //id to memberID
		this.lastName = lastName; //LN to lastName
		this.firstName = firstName; //FN to firstName
		this.email = email; //EM to email
		this.phoneNo = phoneNo; //PN to phoneNo
 		this.memberID = id; //ID to memberID
		
		this.LNS = new HashMap<>();
	}

	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Member: ").append(memberID).append("\n") //ID to memberID
		  .append("Name: ").append(lastName).append(", ").append(firstName).append("\n") //LN to lastName.FN to firstName
		  .append("Email: ").append(email).append("\n") //EM to email
		  .append("Phone: ").append(phoneNo) //PN to phoneNo
		  .append("\n")
		  .append(String.format("  Fines Owed :  $%.2f", FINES))
		  .append("\n");
		
		for (loan loan : LNS.values()) {
			sb.append(loan).append("\n");
		}		  
		return sb.toString();
	}

	
	public int getId() {
		return ID;
	}

	
	public List<loan> getLoans() {
		return new ArrayList<loan>(LNS.values());
	}

	
	public int getNumberOfCurrentLoans() {
		return LNS.size();
	}

	
	public double getFinesOwed() {
		return FINES;
	}

	
	public void takeOutLoan(loan loan) {
		if (!LNS.containsKey(loan.getId())) {
			LNS.put(loan.getId(), loan);
		}
		else {
			throw new RuntimeException("Duplicate loan added to member");
		}		
	}

	
	public String getLastName() {
		return LN;
	}

	
	public String getFirstName() {
		return FN;
	}


	public void addFine(double fine) {
		FINES += fine;
	}
	
	public double payFine(double amount) {
		if (amount < 0) {
			throw new RuntimeException("Member.payFine: amount must be positive");
		}
		double change = 0;
		if (amount > FINES) {
			change = amount - FINES;
			FINES = 0;
		}
		else {
			FINES -= amount;
		}
		return change;
	}


	public void dischargeLoan(loan loan) {
		if (LNS.containsKey(loan.getId())) {
			LNS.remove(loan.getId());
		}
		else {
			throw new RuntimeException("No such loan held by member");
		}		
	}

}
