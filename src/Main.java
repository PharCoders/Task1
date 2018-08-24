import java.text.SimpleDateFormat;
import java.util.Scanner;


public class Main {
	
	private static Scanner identity; //IN to identity
	private static library library; //LIB to library
	private static String menu; //MENU to menu
	private static Calendar calendar; //CAL to calendar
	private static SimpleDateFormat simpleDateFormat; //SDF to simpleDateFormat
	
	
	private static String Get_menu() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("\nLibrary Main Menu\n\n")
		  .append("addMember : add member\n") //M to addMember
		  .append("listMembers : list members\n") //LM to listMembers
		  .append("\n")
		  .append("addBook : add book\n") //B to addBook
		  .append("listBooks : list books\n") //LB to listBooks
		  .append("fixBooks : fix books\n") //FB to fixBooks
		  .append("\n")
		  .append("takeOutaLoan : take out a loan\n") //L to takeOutaLoan
		  .append("returnaLoan : return a loan\n") //R to returnaLoan
		  .append("listLoans : list loans\n") //LL to listLoans
		  .append("\n")
		  .append("payFine : pay fine\n") P to payFine 
		  .append("\n")
		  .append("incrementDate : increment date\n") //T to incrementDate
		  .append("quit : quit\n") //Q to quit
		  .append("\n")
		  .append("Choice : ");
		  
		return sb.toString();
	}


	public static void main(String[] args) {		
		try {			
			identity = new Scanner(System.in); //IN to identity
			library = library.INSTANCE(); //LIB to library
			calendar = Calendar.getInstance(); //CAL to calendar
			simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy"); //SDF to simpleDateFormat. (dd/MM/yyyy) to (dd/mm/yyyy)
	
			for (Member member : library.Members()) { //member to Member. m to member. LIB to library
				output(member); //m to member
			}
			output(" ");
			for (Book book : library.Books()) { //book to Book. b to book. LIB to library
				output(book); //b to book
			}
						
			menu = Get_menu(); //MENU to menu
			
			boolean e = false;
			
			while (!e) {
				
				output("\n" + simpleDateFormat.format(calendar.Date())); //SDF to simpleDateFormat. CAL to calendar
				String c = input(menu); //MENU to menu
				
				switch (c.toUpperCase()) {
				
				case "addMember": //M to addMember 
					addMember();
					break;
					
				case "listMembers": //LM to listMembers 
					listMembers();
					break;
					
				case "addBook": //B to addBook
					addBook();
					break;
					
				case "listBooks": //LB to listBooks 
					listBooks();
					break;
					
				case "fixBooks": //FB to fixBooks
					fixBooks();
					break;
					
				case "takeOutaLoan": //L to takeOutaLoan
					borrowBook();
					break;
					
				case "returnaLoan": //R to returnaLoan
					returnBook();
					break;
					
				case "listLoans": //LL to listLoans
					listCurrentLoans();
					break;
					
				case "payFine": //P to payFine 
					payFine();
					break;
					
				case "incrementDate": //T to incrementDate 
					incrementDate();
					break;
					
				case "quit": //Q to quit 
					e = true;
					break;
					
				default: 
					output("\nInvalid option\n");
					break;
				}
				
				library.SAVE();
			}			
		} catch (RuntimeException e) {
			output(e);
		}		
		output("\nEnded\n");
	}	

		private static void payFine() {
		new PayFineUI(new PayFineControl()).run();		
	}


	private static void listCurrentLoans() {
		output("");
		for (Loan loan : library.CurrentLoans()) { //loan to Loan. LIB to library
			output(loan + "\n");
		}		
	}



	private static void listBooks() {
		output("");
		for (Book book : library.Books()) { //book to Book. LIB to library
			output(book + "\n");
		}		
	}



	private static void listMembers() {
		output("");
		for (Member member : library.Members()) { //member to Member. LIB to library
			output(member + "\n");
		}		
	}



	private static void borrowBook() {
		new BorrowBookUI(new BorrowBookControl()).run();		
	}


	private static void returnBook() {
		new ReturnBookUI(new ReturnBookControl()).run();		
	}


	private static void fixBooks() {
		new FixBookUI(new FixBookControl()).run();		
	}


	private static void incrementDate() {
		try {
			int days = Integer.valueOf(input("Enter number of days: ")).intValue();
			CAL.incrementDate(days);
			LIB.checkCurrentLoans();
			output(SDF.format(CAL.Date()));
			
		} catch (NumberFormatException e) {
			 output("\nInvalid number of days\n");
		}
	}


	private static void addBook() {
		
		String author = input("Enter author: ");
		String title  = input("Enter title: ");
		String callNo = input("Enter call number: ");
		book book = LIB.Add_book(author, title, callNo);
		output("\n" + book + "\n");
		
	}

	
	private static void addMember() {
		try {
			String lastName = input("Enter last name: ");
			String firstName  = input("Enter first name: ");
			String email = input("Enter email: ");
			int phoneNo = Integer.valueOf(input("Enter phone number: ")).intValue();
			member member = LIB.Add_mem(lastName, firstName, email, phoneNo);
			output("\n" + member + "\n");
			
		} catch (NumberFormatException e) {
			 output("\nInvalid phone number\n");
		}
		
	}


	private static String input(String prompt) {
		System.out.print(prompt);
		return IN.nextLine();
	}
	
	
	
	private static void output(Object object) {
		System.out.println(object);
	}

	
}
