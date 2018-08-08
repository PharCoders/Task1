import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Library implements Serializable { //changed library to Library
	
	private static final String LIBRARY_FILE = "library.obj";
	private static final int LOAN_LIMIT = 2;
	private static final int LOAN_PERIOD = 2;
	private static final double FINE_PER_DAY = 1.0;
	private static final double MAX_FINES_OWED = 5.0;
	private static final double DAMAGE_FEE = 2.0;
	
	private static library self;
	private int bookID; //changed BID to bookID
	private int memberID; //changed MID to memberID
	private int loanID; //changed LID to loanID
	private Date loadDate;
	
	private Map<Integer, book> catalog;
	private Map<Integer, member> members;
	private Map<Integer, loan> loans;
	private Map<Integer, loan> currentLoans;
	private Map<Integer, book> damagedBooks;
	

	private library() {
		catalog = new HashMap<>();
		members = new HashMap<>();
		loans = new HashMap<>();
		currentLoans = new HashMap<>();
		damagedBooks = new HashMap<>();
		bookID = 1; //changed BID to bookID
		memberID = 1; // changed MID to memberID		
		loanID = 1; //changed LID to loanID		
	}

	
	public static synchronized Library instance() {	// changed library to Library and INSTANCE to instance as it is method.	
		if (self == null) {
			Path path = Paths.get(LIBRARY_FILE);			
			if (Files.exists(path)) {	
				try (ObjectInputStream lof = new ObjectInputStream(new FileInputStream(LIBRARY_FILE));) {
			    
					self = (library) lof.readObject();
					Calendar.getInstance().setDate(self.loadDate);
					lof.close();
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			else self = new library();
		}
		return self;
	}

	
	public static synchronized void save() { //changed SAVE to save 
		if (self != null) {
			self.loadDate = Calendar.getInstance().Date();
			try (ObjectOutputStream lof = new ObjectOutputStream(new FileOutputStream(LIBRARY_FILE));) {
				lof.writeObject(self);
				lof.flush();
				lof.close();	
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	
	public int bookID() { // changed BookID to bookID
		return bookID; // changed BID to bookID
	}
	
	
	public int memberID() { // changed MemberID to memberID
		return memberID; // changed MID to memberID
	}
	
	
	private int nextbookID() { // changed BID to bookID
		return bookID++; // changed BID to bookID
	}

	
	private int nextmemberID() { // changed MID to memberID
		return memberID++; // changed MID to memberID
	}

	
	private int nextloanID() { // changed LID to loanID
		return loanID++; // changed LID to loanID
	}

	
	public List<member> Members() {		
		return new ArrayList<member>(members.values()); 
	}


	public List<book> Books() {		
		return new ArrayList<book>(catalog.values()); 
	}


	public List<loan> CurrentLoans() {
		return new ArrayList<loan>(currentLoans.values());
	}


	public Member add_member(String lastName, String firstName, String email, int phoneNo) { //changed mem to member. and member to Member. and Add_member to add_member.		
		Member member = new member(lastName, firstName, email, phoneNo, nextMID()); // member to Member and MID to memberID. member to Member
		members.put(member.getmemberID(), member); //	Id to memberID	
		return member;
	}

	
	public Book add_book(String author, String title, String callNo) { //book to Book. Add_book to add_book. a to author, t to title and c to callNo.		
		Book book = new book(author, title, callNo, nextbookID()); //book to Book. a to author, t to title and c to callNo. BID to bookID. b to book.
		catalog.put(book.bookID(), book); // ID to bookID. b to book		
		return book; //b to book
	}

	
	public Member getMember(int memberID) { //member to Member. memberId to memberID.
		if (members.containsKey(memberID)) //memberId to memberID.
			return members.get(memberID); //memberId to memberID
		return null;
	}

	
	public Book getBook(int bookID) { //book to Book. implemented get method. boodId to bookID
		if (catalog.containsKey(bookID)) //bookId to bookID
			return catalog.get(bookID); //	bookId to bookID	
		return null;
	}

	
	public int loanLimit() {
		return LOAN_LIMIT;
	}

	
	public boolean memberCanBorrow(Member member) {	//member to Member	
		if (member.getNumberOfCurrentLoans() == LOAN_LIMIT ) 
			return false;
				
		if (member.getFinesOwed() >= MAX_FINES_OWED) 
			return false;
				
		for (Loan loan : member.getLoans()) // loan to Loan
			if (loan.isOverDue()) 
				return false;
			
		return true;
	}

	
	public int loansRemainingForMember(member member) {		
		return LOAN_LIMIT - member.getNumberOfCurrentLoans();
	}

	
	public loan issueLoan(book book, member member) {
		Date dueDate = Calendar.getInstance().getDueDate(LOAN_PERIOD);
		loan loan = new loan(nextLID(), book, member, dueDate);
		member.takeOutLoan(loan);
		book.Borrow();
		loans.put(loan.getId(), loan);
		currentLoans.put(book.ID(), loan);
		return loan;
	}
	
	
	public loan getLoanByBookId(int bookId) {
		if (currentLoans.containsKey(bookId)) {
			return currentLoans.get(bookId);
		}
		return null;
	}

	
	public double calculateOverDueFine(loan loan) {
		if (loan.isOverDue()) {
			long daysOverDue = Calendar.getInstance().getDaysDifference(loan.getDueDate());
			double fine = daysOverDue * FINE_PER_DAY;
			return fine;
		}
		return 0.0;		
	}


	public void dischargeLoan(loan currentLoan, boolean isDamaged) {
		member member = currentLoan.Member();
		book book  = currentLoan.Book();
		
		double overDueFine = calculateOverDueFine(currentLoan);
		member.addFine(overDueFine);	
		
		member.dischargeLoan(currentLoan);
		book.Return(isDamaged);
		if (isDamaged) {
			member.addFine(DAMAGE_FEE);
			damagedBooks.put(book.ID(), book);
		}
		currentLoan.Loan();
		currentLoans.remove(book.ID());
	}


	public void checkCurrentLoans() {
		for (loan loan : currentLoans.values()) {
			loan.checkOverDue();
		}		
	}


	public void repairBook(book currentBook) {
		if (damagedBooks.containsKey(currentBook.ID())) {
			currentBook.Repair();
			damagedBooks.remove(currentBook.ID());
		}
		else {
			throw new RuntimeException("Library: repairBook: book is not damaged");
		}
		
	}
	
	
}
