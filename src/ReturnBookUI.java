import java.util.Scanner;


public class ReturnBookUI {

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
		
		while (true) {
			
			switch (state) {
			
			case INITIALISED:
				break;
				
			case READY:
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
				
			case INSPECTING:
				String ans = input("Is book damaged? (Y/N): ");
				boolean isDamaged = false;
				if (ans.toUpperCase().equals("Y")) {					
					isDamaged = true;
				}
				control.dischargeLoan(isDamaged);
			
			case COMPLETED:
				output("Return processing complete");
				return;
			
			default:
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
	}
	
	public void setState(UI_STATE state) {
		this.state = state;
	}

	
}
