import java.util.ArrayList;
import java.util.List;

public class BorrowBookControl {
	
	private BorrowBookUI ui;
	
	private Library library;		//Changed name to 'library'
	private Member member;		//Changed name to 'member'
	private enum CONTROL_STATE { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED };
	private CONTROL_STATE state;
	
	private List<Book> PENDING;		//Changed to 'Book'
	private List<Loan> COMPLETED;		//Changed to 'Loan'
	private Book book;		//Changed name to 'book'
	
	
	public BorrowBookControl() {
		this.library = Library.INSTANCE();		//Changed name to 'library' 'Library'
		state = CONTROL_STATE.INITIALISED;
	}
	

	public void setUI(BorrowBookUI ui) {
		if (!state.equals(CONTROL_STATE.INITIALISED)) 
			throw new RuntimeException("BorrowBookControl: cannot call setUI except in INITIALISED state");
			
		this.ui = ui;
		ui.setState(BorrowBookUI.UI_STATE.READY);
		state = CONTROL_STATE.READY;		
	}

		
	public void swiped(int memberID) {			//Changed name to 'swiped' 'ID'
		if (!state.equals(CONTROL_STATE.READY)) 
			throw new RuntimeException("BorrowBookControl: cannot call cardSwiped except in READY state");
			
		member = library.getMember(memberID);		////Changed name to 'library' and 'member' 'ID'
		if (member == null) {		//Changed name to 'member'
			ui.display("Invalid memberID");		//Changed to 'ID'
			return;
		}
		if (library.memberCanBorrow(member)) {		//Changed name to 'library' and 'member'
			PENDING = new ArrayList<>();
			ui.setState(BorrowBookUI.UI_STATE.SCANNING);
			state = CONTROL_STATE.SCANNING; }
		else 
		{
			ui.display("Member cannot borrow at this time");
			ui.setState(BorrowBookUI.UI_STATE.RESTRICTED); }}
	
	
	public void scanned(int bookID) {		//Changed name to 'scanned' changed to bookID
		book = null;		//Changed to 'book'
		if (!state.equals(CONTROL_STATE.SCANNING)) {
			throw new RuntimeException("BorrowBookControl: cannot call bookScanned except in SCANNING state");
		}	
		book = library.Book(bookID);		//Changed to 'book''ID'
		if (book == null) {		//Changed to 'book'
			ui.display("Invalid bookID");		//Changed to 'ID'
			return;
		}
		if (!book.Available()) {		//Changed to 'book'
			ui.display("Book cannot be borrowed");
			return;
		}
		PENDING.add(book);		//Changed to 'book'
		for (Book book : PENDING) {		//Changed to 'book' 'Book'
			ui.display(book.toString());		//Changed to 'book'
		}
		if (library.loansRemainingForMember(member) - PENDING.size() == 0) {		//Changed to 'library' 'member'
			ui.display("Loan limit reached");
			complete();		//Changed to small case'complete'
		}
	}
	
	
	public void complete() {		//Changed to small case 'complete'
		if (PENDING.size() == 0) {
			cancel();
		}
		else {
			ui.display("\nFinal Borrowing List");
			for (Book book : PENDING) {		//Changed to 'book''Book'
				ui.display(book.toString());		//Changed to 'book'
			}
			COMPLETED = new ArrayList<Loan>();		//Changed to 'Loan'
			ui.setState(BorrowBookUI.UI_STATE.FINALISING);
			state = CONTROL_STATE.FINALISING;
		}
	}


	public void commitLoans() {
		if (!state.equals(CONTROL_STATE.FINALISING)) {
			throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
		}	
		for (Book book : PENDING) {		//Changed to 'book' 'Book'
			Loan loan = library.issueLoan(book, member);		//Changed to 'library' 'book' 'member' 'Loan'
			COMPLETED.add(loan);			
		}
		ui.display("Completed Loan Slip");
		for (Loan loan : COMPLETED) {		//Changed to 'Loan'
			ui.display(loan.toString());
		}
		ui.setState(BorrowBookUI.UI_STATE.COMPLETED);
		state = CONTROL_STATE.COMPLETED;
	}

	
	public void cancel() {
		ui.setState(BorrowBookUI.UI_STATE.CANCELLED);
		state = CONTROL_STATE.CANCELLED;
	}
	
}		//Reduced unnecessary spacing