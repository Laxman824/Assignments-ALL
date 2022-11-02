import java.util.Iterator;

public class Department implements Entity_ {
	LinkedList<Student> stud_list=new LinkedList<Student>();
	String name;
	Department(String n){
		name=n ;
	}
	public String name() {
		return name;
	}
	public Iterator<Student> studentList() {
		return stud_list.positions();
	}

}
