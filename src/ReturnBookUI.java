import java.util.Scanner;


public class ReturnBookUI { //Initialising class.

	public static enum UI_STATE { INITIALISED, READY, INSPECTING, COMPLETED };

	private ReturnBookControl control;
	private Scanner input;
	private UI_STATE state;

	
	public ReturnBookUI(ReturnBookControl control) {
		this.control = control;
		input = new Scanner(System.in);
		state = UI_STATE.INITIALISED;
		control.setUI(this);
	}


	public void run() {		
		output("Return Book Use Case UI\n");
		
		while (true) { //while statement.
			
			switch (state) { //switch case.
			
			case INITIALISED: //case initialised.
				break;
				
			case READY: //case ready in which it checks for checking the book and scanning the book to return.
				String bookString = input("Scan Book (<enter> completes): "); //changed bookStr to bookString.
				if (bookString.length() == 0) { //changed bookStr to bookString.
					control.scanningComplete();
				}
				else {
					try {
						int bookID = Integer.valueOf(bookString).intValue(); //changed bookStr to bookString and changed bookId to bookID.
						control.bookScanned(bookID); //changed bookId to bookID.
					}
					catch (NumberFormatException e) {
						output("Invalid bookID"); //bookId to bookID.
					}					
				}
				break;				
				
			case INSPECTING: //case inspecting in which it checks for inspecting the book for any damage.
				String ans = input("Is book damaged? (Y/N): ");
				boolean isDamaged = false;
				if (ans.toUpperCase().equals("Y")) {					
					isDamaged = true;
				}
				control.dischargeLoan(isDamaged);
			
			case COMPLETED: //case completed in which it prints that the process of returning book is completed.
				output("Return processing complete");
				return;
			
			default: //default case.
				output("Unhandled state");
				throw new RuntimeException("ReturnBookUI : unhandled state :" + state);			
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
	} //increased extra white space line below.
	
	
	public void setState(UI_STATE state) { 
		this.state = state;
	}

} //Deleted extra white space line.
