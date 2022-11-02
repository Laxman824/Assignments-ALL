

import java.util.Iterator;

public class Course implements Entity_{
	LinkedList<Student> stud_list=new LinkedList<Student>();
	String name;
	String title;
	Course(String n,String t){
		name=n;
		title=t;
	}
	public String name() {
		return name;
	}
	public String title() {
		return title;
	}
	
	public Iterator<Student> studentList() {
		return stud_list.positions();
}

}
