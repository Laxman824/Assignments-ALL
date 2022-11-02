package ProjectManagement;

public class Job implements Comparable<Job>,JobReport_ {
	static int ag=0;
	String name;
	String project_name;
	String user_name;
	int priority;
	int running_time;
	int arrival_time;
	int completion_time;
	int waiting_time;
	int new_priority;
	String status;
	int age;
	Job(String n,String p,String u,int r,int prior){
		ag=ag+1;
		name=n;
		project_name=p;
		user_name=u;
		running_time=r;
		status="REQUESTED";
		priority=prior;
		new_priority=prior;
		age=ag;
		completion_time=-1;
	}
	
	public int get_waiting_time(int global_time) {
		return global_time-arrival_time;
	}
	
    public int compareTo(Job student) {
    	 if(this.new_priority>student.new_getPriority()) {
         	return 1;
         }else if(this.new_priority<student.new_getPriority()){
         	return -1;
         }else {
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
    }
   
    public int new_getPriority() {
    	return new_priority;
    }
    public int getPriority() {
    	return priority;
    }
    public String toString() {
    	if(completion_time==-1) {
    	return "Job{user='"+user_name+"', project='"+project_name+"', jobstatus="+status+", execution_time="+running_time+", end_time="+null+", name='"+name+"'}";
    	}else {
    		return "Job{user='"+user_name+"', project='"+project_name+"', jobstatus="+status+", execution_time="+running_time+", end_time="+completion_time+", name='"+name+"'}";
    	}
    	}
	public String user() {
		return user_name;
	}

	public String project_name() {
		return project_name;
	}

	public int budget() {
		return running_time;
	}

	public int arrival_time() {
		return arrival_time;
	}
	
	public int completion_time() {
		return completion_time;
	}

	public int compareTo2(Job old_job) {
		if(this.completion_time==-1 && old_job.completion_time==-1 ) {
			return old_job.arrival_time()-this.arrival_time();
		}else if(this.completion_time==-1) {
			return -1;
		}else if(old_job.completion_time==-1) {
			return 1;
		}else {
			return old_job.completion_time()-this.completion_time();
		}
	}
    
}

