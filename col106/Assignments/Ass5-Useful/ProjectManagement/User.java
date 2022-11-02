package ProjectManagement;

public class User implements Comparable<User>,UserReport_ {

	String name;
	int budget_consumed;
	int latest_job_completion;
	User(String n){
		this.name=n;
		budget_consumed=0;
		latest_job_completion=0;
	}
    public int compareTo(User user) {
        if(this.budget_consumed-user.budget_consumed==0) {
        	return user.latest_job_completion-this.latest_job_completion;
        }else {
        	return this.budget_consumed-user.budget_consumed;
        }
    }

	public String user() {
		return name;
	}
	
	public int consumed() {
		return budget_consumed;
	}
	public String toString() {
		return "User{name='"+name+"', usage="+this.consumed()+"}";
	}
}
