import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Calendar {
	
	private static Calendar self;
	private static java.util.Calendar calendar;		//Changed name to 'calendar'
	
	
	private Calendar() {
		cal = java.util.Calendar.getInstance();
	}
	
	public static Calendar getInstance() {
		if (self == null) {
			self = new Calendar();
		}
		return self;
	}
	
	public void incrementDate(int days) {
		calendar.add(java.util.Calendar.DATE, days);		//Changed the name to 'calendar'
	}
	
	public synchronized void setDate(Date date) {
		try {
			calendar.setTime(date);		//Changed the name to 'calendar'
	        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);  		//Changed the name to 'calendar'
	        calendar.set(java.util.Calendar.MINUTE, 0);  		//Changed the name to 'calendar'
	        calendar.set(java.util.Calendar.SECOND, 0);  		//Changed the name to 'calendar'
	        calendar.set(java.util.Calendar.MILLISECOND, 0);		//Changed the name to 'calendar'
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}	
	}
	public synchronized Date Date() {
		try {
	        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);		//Changed the name to 'calendar'  
	        calendar.set(java.util.Calendar.MINUTE, 0);  		//Changed the name to 'calendar'
	        calendar.set(java.util.Calendar.SECOND, 0);  		//Changed the name to 'calendar'
	        calendar.set(java.util.Calendar.MILLISECOND, 0);		//Changed the name to 'calendar'
			return calendar.getTime();		//Changed the name to 'calendar'
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}	
	}

	public synchronized Date getDueDate(int loanPeriod) {
		Date now = Date();
		calendar.add(java.util.Calendar.DATE, loanPeriod);		//Changed the name to 'calendar'
		Date dueDate = calendar.getTime();		//Changed the name to 'calendar'
		calendar.setTime(now);		//Changed the name to 'calendar'
		return dueDate;
	}
	
	public synchronized long getDaysDifference(Date targetDate) {
		long diffMillis = Date().getTime() - targetDate.getTime();
	    long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);
	    return diffDays;
	}

}
