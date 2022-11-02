package PriorityQueue;

public class Student implements Comparable<Student> {
	static int ag=0;
    private String name;
    private Integer marks;
    int age;

    public Student(String trim, int parseInt) {
    	ag=ag+1;
    	name=trim;
    	marks=parseInt;
    	age=ag;
    }

    public int compareTo(Student student) {
        if(this.marks>student.get_marks()) {
        	return 1;
        }else if(this.marks<student.get_marks()){
        	return -1;
        }else {
        	if(this.age<student.age) {
        		return 1;
        	}else {
        		return -1;
        	}
        }
    }

    public String getName() {
        return name;
    }


	public Integer get_marks() {
		return marks;
	}
	
	public String toString() {
		return "Student{"+"name='"+name+"', marks="+marks+"}";
	}
}
