public class PayFineControl { //initalizing PayFineControl class
	
	private PayFineUI ui;
	private enum CONTROL_STATE { INITIALISED, READY, PAYING, COMPLETED, CANCELLED };
	private CONTROL_STATE state;
	
	private Library library; //changed library to Library. 
	private Member member; //changed member to Member and removed an extra semi-colon.


	public PayFineControl() {
		this.library = Library.INSTANCE(); //changed library to Library as instance can be of class.
		state = CONTROL_STATE.INITIALISED;
	}
	
	
	public void setUI(PayFineUI ui) { //initializing method setUI
		if (!state.equals(CONTROL_STATE.INITIALISED)) {
			throw new RuntimeException("PayFineControl: cannot call setUI except in INITIALISED state");
		}	
		this.ui = ui;
		ui.setState(PayFineUI.UI_STATE.READY);
		state = CONTROL_STATE.READY; // sets the state to ready.		
	}


	public void cardSwiped(int memberId) { //initializing method cardSwiped
		if (!state.equals(CONTROL_STATE.READY)) {
			throw new RuntimeException("PayFineControl: cannot call cardSwiped except in READY state");
		}	
		member = Library.getMember(memberId); //changed library to Library.
		
		if (member == null) {
			ui.display("Invalid Member ID"); //changed Id to ID as it can be changed as it is just output statement.
			return;
		}
		ui.display(Member.toString()); //changed member to Member as toString method can be of a Member class.
		ui.setState(PayFineUI.UI_STATE.PAYING);
		state = CONTROL_STATE.PAYING; //sets the state to paying.
	}
	
	
	public void cancel() {
		ui.setState(PayFineUI.UI_STATE.CANCELLED);
		state = CONTROL_STATE.CANCELLED; //sets the state to cancelled.
	}


	public Double payFine(Double amount) { //changed the naming convention of data field i.e. double to Double
		if (!state.equals(CONTROL_STATE.PAYING)) {
			throw new RuntimeException("PayFineControl: cannot call payFine except in PAYING state");
		} 
		Double change = member.payFine(amount); //changed the naming convention of data field i.e. double to Double
		
		if (change > 0) { //created a whitespace line before this if statement.
			ui.display(String.format("Change: $%.2f", change)); //displays the change in decimal format upto 2 decimal with the dollar sign.
		}
		ui.display(Member.toString()); //changed member to Member as toString method can be of a Member class.
		ui.setState(PayFineUI.UI_STATE.COMPLETED);
		state = CONTROL_STATE.COMPLETED; //sets the state to completed.
		return change;
	}
	

} //Deleted an extra whitespace line before this closing brace.
