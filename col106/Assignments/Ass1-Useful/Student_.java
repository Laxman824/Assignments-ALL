

import java.util.Iterator;

public interface Student_ {
	   public String name();               	
	   public String entryNo();            	
	   public String hostel();             	
	   public String department();         	
	   public String completedCredits();   	
	   public String cgpa();   		
	   public Iterator<CourseGrade_> courseList();	
	}