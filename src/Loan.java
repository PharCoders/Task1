import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class Loan implements Serializable { //loan to Loan
	
	public static enum LOAN_STATE { CURRENT, OVER_DUE, DISCHARGED };
	
	private int loanID;  // changed capitalization. id to loanID
	private Book book;  //captialized book and changed the initialization
	private Member member; // captialized and changed the initialization
	private Date date; // changed the initialization
	private LOAN_STATE state;

	
	public Loan (int loanId, Book book, Member member, Date dueDate) { //loan to Loan
		this.loan.ID = loanId; //id to loanID
		this.book = book;  //Changed B to book
		this.member = member; //changed M to member
		this.date = dueDate; // Changed D to date
		this.state = LOAN_STATE.CURRENT;
	}

	
	public void checkOverDue() {
		if (state == LOAN_STATE.CURRENT &&
			Calendar.getInstance().Date().after(date)) { // D to date
			this.state = LOAN_STATE.OVER_DUE;			
		}
	}

	
	public boolean isOverDue() {
		return state == LOAN_STATE.OVER_DUE;
	}

	
	public Integer getloanID() { //getid to getloanID
		return loanID; // id to loanID.
	}


	public Date getDueDate() {
		return date; // D to date
	}
	
	
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy"); // Organised MM to mm
		// fixed unnecessary white spaces
		StringBuilder sb = new StringBuilder();
		sb.append("Loan: ").append(loanID).append("\n") // id to loanID
		  .append("Borrower ").append(member.getmemberID()).append(" : ")  // changed member.getid to member.getmemberID
		  .append(member.getLastName()).append(", ").append(member.getFirstName()).append("\n") // changed M to member as it has to be meaningful
		  .append("Book ").append(book.bookID()).append(" : " ) // changed book.id to book.bookID
		  .append(book.title()).append("\n") // Title to title
		  .append("DueDate: ").append(sdf.format(date)).append("\n") // D to date
		  .append("State: ").append(state);		
		return sb.toString();
	}


	public Member member() { // Changed member to Member
		return member; // Changed M to member
	}


	public Book book() {  // changed book to Book
		return book; //changed B to book 
	}


	public void Loan() {
		state = LOAN_STATE.DISCHARGED;		
	}

}
