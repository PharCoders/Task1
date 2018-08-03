import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class loan implements Serializable {
	
	public static enum LOAN_STATE { CURRENT, OVER_DUE, DISCHARGED };
	
	private int id;  // changed capitalization
	private Book book;  //captialized book and changed the initialization
	private Member member; // captialized and changed the initialization
	private Date D;
	private LOAN_STATE state;

	
	public loan(int loanId, Book book, Member member, Date dueDate) {
		this.id = loanId;
		this.book = book;  //Changed B to book
		this.member = member; //changed M to member
		this.D = dueDate;
		this.state = LOAN_STATE.CURRENT;
	}

	
	public void checkOverDue() {
		if (state == LOAN_STATE.CURRENT &&
			Calendar.getInstance().Date().after(D)) {
			this.state = LOAN_STATE.OVER_DUE;			
		}
	}

	
	public boolean isOverDue() {
		return state == LOAN_STATE.OVER_DUE;
	}

	
	public Integer getid() {
		return id; // fixed return type variable.
	}


	public Date getDueDate() {
		return D;
	}
	
	
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy"); // Organised MM to mm
		// fixed unnecessary white spaces
		StringBuilder sb = new StringBuilder();
		sb.append("Loan: ").append(id).append("\n") // ID to id
		  .append("Borrower ").append(member.getid()).append(" : ")  // changed M to member as it has to be meaningful
		  .append(member.getLastName()).append(", ").append(member.getFirstName()).append("\n") // changed M to member as it has to be meaningful
		  .append("Book ").append(book.id()).append(" : " ) // ID to id and B to book
		  .append(book.Title()).append("\n") // changed B to book
		  .append("DueDate: ").append(sdf.format(D)).append("\n")
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
