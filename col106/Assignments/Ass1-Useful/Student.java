

import java.util.Iterator;

public class Student implements Student_ {
	
	String name;
	String entryNo;
	String hostel;
	String department;
	String completedCredits;
	String cgpa;
	LinkedList<CourseGrade_> courses=new LinkedList<CourseGrade_>();
	
	
	public Student (String ent,String nam,String dept,String hos) {
		name=nam;
		entryNo=ent;
		hostel=hos;
		department=dept;
	}
	public String name() {
		return name;
	}

	public String entryNo() {
		return entryNo;
	}

	public String hostel() {
		return hostel;
	}
	
	public String department() {
		return department;
	}
	
	public String completedCredits() {
		return ((courses.count())*3+"");
	}
	
	public Iterator<CourseGrade_> courseList() {
		
		return courses.positions();
	}
	
	public String cgpa() {
		Iterator<CourseGrade_> c=courses.positions();
		int sum=0;
		int i=courses.count();
		while (c.hasNext()==true) {
			GradeInfo_ g_inf=c.next().grade();
			
			sum+=(GradeInfo_.gradepoint(g_inf.grade())) ;
			if (((g_inf.grade()).toString()).equals("I")) {
				i=i-1;
			}
		}
		double cg=(sum+0.0)/(i+0.0);
		long a=Math.round(100*cg);
		
		String cgpa=(((a+0.0)/100)+"");
		return cgpa;
		}
	
}

