
public class Student implements Student_ {
	String fname;
	String lname;
	String hostel;
	String department;
	String cgpa;
	Student(String fn,String ln,String hs,String dept,String cg){
		fname=fn;
		lname=ln;
		hostel=hs;
		department=dept;
		cgpa=cg;
	}
	public String fname() {
		return fname;
	}
	
	public String hostel() {
		return hostel;
	}

	public String department() {
		return department;
	}

	public String cgpa() {
		return cgpa;
	}

	public String lname() {
		return lname;
	}
	
	public String toString() {
		return fname+" "+lname;
	}
}
