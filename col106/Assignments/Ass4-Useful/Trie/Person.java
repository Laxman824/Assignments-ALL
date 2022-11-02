package Trie;

public class Person {
	String name;
	String phone_number;
    public Person(String name, String phone_number) {
    	this.name=name;
    	this.phone_number=phone_number;
    }

    public String getName() {
        return name;
    }
    public String toString() {
    	return "[Name: "+name+", Phone="+phone_number+"]";
    }
}
