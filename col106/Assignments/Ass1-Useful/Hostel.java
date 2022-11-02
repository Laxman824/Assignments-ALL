import java.util.Iterator;

public class Hostel implements Entity_ {
	LinkedList<Student> stud_list = new LinkedList<Student>();
	String name;	
	Hostel(String n){
		name=n;}
	public String name() {
		return name;
	}

	public Iterator<Student> studentList() {
		return stud_list.positions();
	}
	

}
