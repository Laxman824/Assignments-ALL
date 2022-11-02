package RedBlack;
import Util.RBNodeInterface;
import java.util.ArrayList;
import java.util.List;

public class RedBlackNode<T extends Comparable, E> implements RBNodeInterface<E> {
	
	E value;
	public List<E> list=new ArrayList<E>();
	String colour;
	public RedBlackNode<T,E> right;
	public RedBlackNode<T,E> left;
	RedBlackNode<T,E> prev;
	public T key;
	RedBlackNode(String col,RedBlackNode<T,E> right,RedBlackNode<T,E> left,RedBlackNode<T,E> prev,T key,E val){
		this.colour=col;
		this.prev=prev;
		this.right=right;
		this.left=left;
		this.key=key;
		value=val;
	}
	
    public E getValue() {
        return value;
    }

    public List<E> getValues() {
        return list;
    }

	public void add(E value) {
		list.add(value);
		
	}

}
