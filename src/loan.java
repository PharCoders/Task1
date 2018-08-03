import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class loan implements Serializable {
	
	public static enum LOAN_STATE { CURRENT, OVER_DUE, DISCHARGED };
	
	private int id;  // changed capitalization
	private Book book;  //captializedbook and changed the initialization
	private member M;
	private Date D;
	private LOAN_STATE state;

	
	public loan(int loanId, Book book, member member, Date dueDate) {
		this.id = loanId;
		this.book = book;  //Changed B to book
		this.M = member;
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
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder sb = new StringBuilder();
		sb.append("Loan:  ").append(id).append("\n") // ID to id
		  .append("  Borrower ").append(M.getid()).append(" : ")
		  .append(M.getLastName()).append(", ").append(M.getFirstName()).append("\n")
		  .append("  Book ").append(book.id()).append(" : " ) // ID to id and B to book
		  .append(book.Title()).append("\n") // changed B to book
		  .append("  DueDate: ").append(sdf.format(D)).append("\n")
		  .append("  State: ").append(state);		
		return sb.toString();
	}


	public member Member() {
		return M;
	}


	public Book Book() {  // changed book to Book
		return book; //changed B to book 
	}


	public void Loan() {
		state = LOAN_STATE.DISCHARGED;		
	}

}
