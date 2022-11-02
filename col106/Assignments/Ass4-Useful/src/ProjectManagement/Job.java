package ProjectManagement;

public class Job implements Comparable<Job> {
	static int ag=0;
	String name;
	String project_name;
	String user_name;
	int priority;
	int running_time;
	String status;
	int age;
	Job(String n,String p,String u,int r,int prior){
		ag=ag+1;
		name=n;
		project_name=p;
		user_name=u;
		running_time=r;
		status="NOT FINISHED";
		priority=prior;
		age=ag;
	}
	
    public int compareTo(Job student) {
    	 if(this.priority>student.getPriority()) {
         	return 1;
         }else if(this.priority<student.getPriority()){
         	return -1;
         }else {
         	if(this.age<student.age) {
         		return 1;
         	}else {
         		return -1;
         	}
         }
    }
    public int getPriority() {
    	return priority;
    }
    public String toString(int end_time) {
    	return "Job{user='"+user_name+"', project='"+project_name+"', jobstatus="+status+", execution_time="+running_time+", end_time="+end_time+", name='"+name+"'}";
    }
    public String toString() {
    	return "Job{user='"+user_name+"', project='"+project_name+"', jobstatus="+status+", execution_time="+running_time+", end_time="+null+", name='"+name+"'}";
    }
    public String toString_f() {
    	return "Job{user='"+user_name+"', project='"+project_name+"', jobstatus="+"REQUESTED"+", execution_time="+running_time+", end_time="+null+", name='"+name+"'}";
    }
    
}

