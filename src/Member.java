import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Member implements Serializable { //member to Member

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
		
		for (Loan loan : LNS.values()) { //loan to Loan
 			sb.append(loan).append("\n");
		}		  
		return sb.toString();
	}

	
	public int getmemberID() { //Id to memberID
		return memberID; //ID to memberID
	}

	
	public List<loan> getLoans() {
		return new ArrayList<loan>(LNS.values());
	}

	
	public int getNumberOfCurrentLoans() {
		return LNS.size();
	}

	
	public double getFinesOwed() {
		return fines; //FINES to fines
	}

	
	public void takeOutLoan(Loan loan) { //loan to Loan
		if (!LNS.containsKey(Loan.getloanID())) { loan to Loan. getId to getloanID
			LNS.put(Loan.getloanID(), loan); //loan to Loan. getId to getloanID
		}
		else {
			throw new RuntimeException("Duplicate loan added to member");
		}		
	}

	
	public String getlastName() { //LastName to lastName
		return lastName; //LN to lastName
	}

	
	public String getfirstName() { //FirstName to firstName
		return firstName; //FN to firstName
	}


	public void addFine(double fine) {
		fines += fine; //FINES to fines
	}
	
	public double payFine(double amount) {
		if (amount < 0) {
			throw new RuntimeException("Member.payFine: amount must be positive");
		}
		double change = 0;
		if (amount > fines) { //FINES to fines
			change = amount - fines; //FINES to fines
			FINES = 0;
		}
		else {
			fines -= amount; //FINES to fines
		}
		return change;
	}


	public void dischargeLoan(Loan loan) { //loan to Loan
		if (LNS.containsKey(loan.getloanID())) { //getId to getloanID
			LNS.remove(loan.getloanID()); //getId to getloanID
		}
		else {
			throw new RuntimeException("No such loan held by member");
		}		
	}

}
