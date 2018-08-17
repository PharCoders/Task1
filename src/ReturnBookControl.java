public class ReturnBookControl { //initiating class.

	private ReturnBookUI ui;
	private enum CONTROL_STATE { INITIALISED, READY, INSPECTING };
	private CONTROL_STATE state;
	
	private Library library; //changed library to Library.
	private Loan currentLoan; //changed loan to Loan.
	

	public ReturnBookControl() {
		this.library = Library.INSTANCE(); //changed library to Library as it is of instance.
		state = CONTROL_STATE.INITIALISED; //setting state to initialised.
	}
	
	
	public void setUI(ReturnBookUI ui) {
		if (!state.equals(CONTROL_STATE.INITIALISED)) {
			throw new RuntimeException("ReturnBookControl: cannot call setUI except in INITIALISED state");
		}	
		this.ui = ui;
		ui.setState(ReturnBookUI.UI_STATE.READY);
		state = CONTROL_STATE.READY; //sets state to ready.		
	}


	public void bookScanned(int bookID) { //changed bookId to bookID.
		if (!state.equals(CONTROL_STATE.READY)) { //checks state equals to ready or not.
			throw new RuntimeException("ReturnBookControl: cannot call bookScanned except in READY state");
		}	
		Book currentBook = library.Book(bookID); //changed book to Book and bookId to bookID.
		
		if (currentBook == null) {
			ui.display("Invalid Book ID"); //changed Id to ID.
			return;
		}
		
		if (!currentBook.On_loan()) { // added white space line.
			ui.display("Book has not been borrowed"); //displays the message after checking whether book is borrowed or not.
			return;
		}		
		currentLoan = Library.getLoanByBookID(bookID);	//changed library to Library and bookId to bookID.
		double overDueFine = 0.0; //sets overduefine to 0.
		
		if (currentLoan.isOverDue()) { //added white space line.
			overDueFine = Library.calculateOverDueFine(currentLoan);  // changed library to Library.
		}
		ui.display("Inspecting");
		ui.display(currentBook.toString()); //displays current book's to string method.
		ui.display(currentLoan.toString()); //displays current loan's to string method.
		
		if (currentLoan.isOverDue()) {
			ui.display(String.format("\nOverdue fine : $%.2f", overDueFine));
		}
		ui.setState(ReturnBookUI.UI_STATE.INSPECTING);
		state = CONTROL_STATE.INSPECTING;		
	}


	public void scanningComplete() { //complete status checking.
		if (!state.equals(CONTROL_STATE.READY)) {
			throw new RuntimeException("ReturnBookControl: cannot call scanningComplete except in READY state");
		}	
		ui.setState(ReturnBookUI.UI_STATE.COMPLETED);		
	}


	public void dischargeLoan(boolean isDamaged) {
		if (!state.equals(CONTROL_STATE.INSPECTING)) {
			throw new RuntimeException("ReturnBookControl: cannot call dischargeLoan except in INSPECTING state");
		}	
		library.dischargeLoan(currentLoan, isDamaged);
		currentLoan = null;
		ui.setState(ReturnBookUI.UI_STATE.READY);
		state = CONTROL_STATE.READY; //sets the state to ready.				
	}

}//deleted white space line.
