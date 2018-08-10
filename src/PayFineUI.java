import java.util.Scanner;


public class PayFineUI { //initialising the class PayFineUI.


	public static enum UI_STATE { INITIALISED, READY, PAYING, COMPLETED, CANCELLED };

	private PayFineControl control;
	private Scanner input; //scanner input.
	private UI_STATE state;

	
	public PayFineUI(PayFineControl control) {
		this.control = control;
		input = new Scanner(System.in);
		state = UI_STATE.INITIALISED;
		control.setUI(this);
	}
	
	
	public void setState(UI_STATE state) {
		this.state = state;
	}


	public void run() {
		output("Pay Fine Use Case UI\n");
		
		while (true) { //while loop.
			
			switch (state) { //switch statement.
			
			case READY: //case ready in which checks whether member card swiped or not and gives output according to it.
				String memberString = input ("Swipe member card (press <enter> to cancel): "); //memStr to memberString and increased space.
				if (memberString.length() == 0) { //memStr to memberString.
					control.cancel();
					break;
				}
				try {
					int memberID = Integer.valueOf(memberString).intValue(); //memStr to memberString and memberId to memberID.
					control.cardSwiped(memberID); //changed memberId to memberID.
				}
				catch (NumberFormatException e) {
					output("Invalid memberID"); //changed memberId to memberID.
				}
				break;
				
			case PAYING: //case paying in which checks the amount for paying.
				double amount = 0;
				String amountString = input ("Enter amount (<Enter> cancels) : "); //amtStr to amountString and increased space.
				if (amountString.length() == 0) { //amtStr to amountString
					control.cancel();
					break;
				}
				try {
					amount = Double.valueOf(amountString).doubleValue(); //amtStr to amountString.
				}
				catch (NumberFormatException e) {}
				if (amount <= 0) {
					output("Amount must be positive");
					break;
				}
				control.payFine(amount);
				break;
								
			case CANCELLED: //case cancelled which displays that paying fine process is cancelled.
				output("Pay Fine process cancelled");
				return;
			
			case COMPLETED: //case completed which displays that paying fine process is completed.
				output("Pay Fine process complete");
				return;
			
			default:
				output("Unhandled state"); //default case.
				throw new RuntimeException("FixBookUI : unhandled state :" + state);			
			
			}		
		}		
	}

	
	private String input(String prompt) {
		System.out.print(prompt);
		return input.nextLine();
	}	
		
		
	private void output(Object object) {
		System.out.println(object);
	}	
			

	public void display(Object object) {
		output(object);
	}

} //reduced unnecessary white space line.
