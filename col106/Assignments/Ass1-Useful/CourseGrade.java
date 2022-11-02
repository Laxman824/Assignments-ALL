

public class CourseGrade implements CourseGrade_{
	String coursetitle;
	String coursenum;
	String grade;
	GradeInfo_ g_inf;
	public CourseGrade(String cn,String g,String ct) {
		coursetitle=ct;
		coursenum=cn;
		grade=g;
		g_inf=new GradeInfo(grade);
	}
	public String coursetitle() {
		return coursetitle;
	}

	public String coursenum() {
		return coursenum;
	}
	public String c_grade(){
		return grade;
	}
	
	public GradeInfo_ grade() {
		
		return g_inf;
	}
	
	
}
