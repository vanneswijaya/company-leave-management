import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Day implements Cloneable, Comparable<Day> {
	
	private int year;
	private int month;
	private int day;

    private static final String MonthNames="JanFebMarAprMayJunJulAugSepOctNovDec";
	
	//Constructor
	public Day(int y, int m, int d) {
		this.year=y;
		this.month=m;
		this.day=d;		
	}

    @Override
    public Day clone() {
        Day copy=null;
        try {
            copy = (Day) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;
    }

	@Override
    public int compareTo(Day another) {
		Calendar cal1 = new GregorianCalendar();
     	Calendar cal2 = new GregorianCalendar();

		cal1.set(this.year, this.month, this.day);
		Date d1 = cal1.getTime();

		cal2.set(another.year, another.month, another.day);
		Date d2 = cal2.getTime();

        return d1.compareTo(d2);
    }

    public void set(String sDay) //Set year,month,day based on a string like 01-Jan-2022
    {		
        String[] sDayParts = sDay.split("-");
        this.day = Integer.parseInt(sDayParts[0]); //Apply Integer.parseInt for sDayParts[0];
        this.year = Integer.parseInt(sDayParts[2]);  
        this.month = MonthNames.indexOf(sDayParts[1])/3+1;
    }

    public Day(String sDay) {
        set(sDay);
    }
	
	// check if a given year is a leap year
	static public boolean isLeapYear(int y)
	{
		if (y%400==0)
			return true;
		else if (y%100==0)
			return false;
		else if (y%4==0)
			return true;
		else
			return false;
	}
	
	// check if y,m,d valid
	static public boolean valid(int y, int m, int d)
	{
		if (m<1 || m>12 || d<1) return false;
		switch(m){
			case 1: case 3: case 5: case 7:
			case 8: case 10: case 12:
					 return d<=31; 
			case 4: case 6: case 9: case 11:
					 return d<=30; 
			case 2:
					 if (isLeapYear(y))
						 return d<=29; 
					 else
						 return d<=28; 
		}
		return false;
	}

	static public int getDifferenceInDays(Day day1, Day day2) {
		LocalDateTime d1 = LocalDateTime.of(day1.year, day1.month, day1.day, 0, 0);
		LocalDateTime d2 = LocalDateTime.of(day2.year, day2.month, day2.day, 0, 0);

		return (int)(ChronoUnit.DAYS.between(d1, d2));
		// Calendar cal1 = new GregorianCalendar();
     	// Calendar cal2 = new GregorianCalendar();

		// cal1.set(day1.year, day1.month, day1.day);
		// Date d1 = cal1.getTime();

		// cal2.set(day2.year, day2.month, day2.day);
		// Date d2 = cal2.getTime();

		// return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

	// Return a string for the day like dd MMM yyyy
    @Override
	public String toString() {
		return day+"-"+ MonthNames.substring((month-1)*3, (month*3)) + "-"+ year;
	}
}