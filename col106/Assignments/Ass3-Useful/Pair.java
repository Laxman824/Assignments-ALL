

public class Pair<A,B> {
	A fname;
	B lname;
	Pair(A f,B l){
		fname=f;
		lname=l;
	}
	public A fname(){
		return fname;
	}
	public B lname() {
		return lname;
	}
	public String toString() {
		return fname.toString()+" "+lname.toString();
	}
}
//Same folder
