// javac Vec.java && java Vec
import java.util.LinkedList;

class Vec {
	public static void main(String[] args) {
		var v0 = new LinkedList<Integer>();
		var v1 = new LinkedList<Integer>();

		v1.addLast(1);
		var elem = v1.get(0);
		System.out.println(elem);

		v1.addLast(2);
		v0.addLast(3);
		
		// Does not expose pointers. Can read the first element only by
		// calling get.
		elem = v1.get(0);
		System.out.println(elem);
	}

}