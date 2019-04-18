import java.io.Serializable;


@SuppressWarnings("serial")
public class Book implements Serializable {		//Capitalized Class Name
	
	private String author;		//Changed to author
	private String title;		//Changed to title
	private String callNo;		//Changed to callNo
	private int bookID;		//Changed to bookID
	
	private enum STATE { AVAILABLE, ON_LOAN, DAMAGED, RESERVED };
	private STATE State;
	
	
	public book (String author, String title, String callNo, int bookID) {
		this.author = author;		//Changed to author
		this.tile = title;		//Changed to title
		this.callNo = callNo;		//Changed to callNo
		this.bookID = bookID;		//Changed to id
		this.state = STATE.AVAILABLE;
	}
	
	@Override		//Added the @Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Book: ").append(bookID).append("\n")		//Changed to bookID
		  .append("Title: ").append(title).append("\n")		//Reduced unnecessary spaces and changed to title
		  .append("Author: ").append(author).append("\n")		//Reduced unnecessary spaces and changed to author
		  .append("CallNo: ").append(callNo).append("\n")		//Reduced unnecessary spaces and changed to callNo
		  .append("State: ").append(state);		//Reduced unnecessary spaces
		
		return sb.toString();
	}

	public Integer bookID() {
		return bookID;		//Changed to bookID
	}

	public String title() {
		return title;		//Changed to title
	}


	
	public boolean available() {		//Changed to small case 'available'
		return state == STATE.AVAILABLE;
	}

	
	public boolean on_loan() {		//Changed to small case 'on_loan'
		return state == STATE.ON_LOAN;
	}

	
	public boolean damaged() {		//Changed to small case 'damaged'
		return state == STATE.DAMAGED;
	}

	
	public void borrow() {		//Changed to small case 'borrow'
		if (state.equals(STATE.AVAILABLE)) {
			state = STATE.ON_LOAN;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot borrow while book is in state: %s", state));
		}
		
	}


	public void returnDamage (boolean damaged) {		//Changed the name to returnDamage and boolean value to damaged
		if (state.equals(STATE.ON_LOAN)) {
			if (damaged) {		//Changed to small case 'damaged'
				state = STATE.DAMAGED;
			}
			else {
				state = STATE.AVAILABLE;
			}
		}
		else {
			throw new RuntimeException(String.format("Book: cannot Return while book is in state: %s", state));
		}		
	}

	
	public void repair() {		//Changed to small case 'repair'
		if (state.equals(STATE.DAMAGED)) {
			state = STATE.AVAILABLE;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot repair while book is in state: %s", state));
		}
	}
	
}		//Reduced an extra line break