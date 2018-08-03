public class PayFineControl {
	
	private PayFineUI ui;
	private enum CONTROL_STATE { INITIALISED, READY, PAYING, COMPLETED, CANCELLED };
	private CONTROL_STATE state;
	
	private Library library; //changed library to Library. 
	private Member member;; //changed member to Member.


	public PayFineControl() {
		this.library = Library.INSTANCE(); //changed library to Library as instance can be of class.
		state = CONTROL_STATE.INITIALISED;
	}
	
	
	public void setUI(PayFineUI ui) {
		if (!state.equals(CONTROL_STATE.INITIALISED)) {
			throw new RuntimeException("PayFineControl: cannot call setUI except in INITIALISED state");
		}	
		this.ui = ui;
		ui.setState(PayFineUI.UI_STATE.READY);
		state = CONTROL_STATE.READY;		
	}


	public void cardSwiped(int memberId) {
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
		state = CONTROL_STATE.PAYING;
	}
	
	
	public void cancel() {
		ui.setState(PayFineUI.UI_STATE.CANCELLED);
		state = CONTROL_STATE.CANCELLED;
	}


	public Double payFine(Double amount) { //changed the naming convention of data field i.e. double to Double
		if (!state.equals(CONTROL_STATE.PAYING)) {
			throw new RuntimeException("PayFineControl: cannot call payFine except in PAYING state");
		}	
		Double change = member.payFine(amount); //changed the naming convention of data field i.e. double to Double
		if (change > 0) {
			ui.display(String.format("Change: $%.2f", change));
		}
		ui.display(Member.toString()); //changed member to Member as toString method can be of a Member class.
		ui.setState(PayFineUI.UI_STATE.COMPLETED);
		state = CONTROL_STATE.COMPLETED;
		return change;
	}
	


}
