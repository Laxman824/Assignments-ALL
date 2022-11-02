package ProjectManagement;

public class User implements Comparable<User> {

	String name;
	User(String n){
		this.name=n;
	}
    public int compareTo(User user) {
        return 0;
    }
}
