package PriorityQueue;

public class Student implements Comparable<Student> {
    private String name;
    public Integer marks;;
    int timeperiod;
    static int  time;

    public Student(String trim, int parseInt) {
    	this.name = trim;
    	this.marks = parseInt;
    	time++;
    	timeperiod = time ;
    	
    }


    @Override
    public int compareTo(Student student) {
        if (marks != student.marks) {
        	return marks - student.marks ;
        }
        else {
        	return  student.timeperiod - timeperiod ;
        }
    }

    public String getName() {
        return name;
    }
}
