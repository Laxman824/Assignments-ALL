
public class Node<T> {
	T stud;
	Node(){
		stud=null;
	}
	public T value() {
		return stud;
	}
	public void set_val(T val) {
		stud=val;
	}
	public String toString() {
		return stud.toString();
	}
}
