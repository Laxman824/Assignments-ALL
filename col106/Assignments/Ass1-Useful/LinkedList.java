import java.util.Iterator;
public class LinkedList<T> implements LinkedList_<T> {
	int  count;
	Position<T> head;
	Position<T> cur;
	  LinkedList(){
		count=0;
		head=null;
		cur=head;
	}
	public Iterator<T> positions() {
		return new LinkedListIterator();
	}

	public Position<T> add(T e) {
	count++;
		if (cur!=null) {
			Position<T>  new_node	=new Position<T>(e);
			cur.next_node=new_node;
			cur=new_node;
			return new_node;
			
		}else {
			Position<T> new_node=new Position<T>(e);
			head=new_node;
			cur=new_node;
			return new_node;
		}
	}
	
	public int count() {
   return count;
	}
	
	class LinkedListIterator implements Iterator<T>{			
		Position<T> current=null;
		public boolean hasNext() {
			if (current==null && head!=null) {
				return true;
			}else if (current==null && head==null) {
				return false;
			}else if (current!=null){
				return current.after()!=null;
				
			}else {
				return false;
			}
		}
		public T next() {
			if (current==null && head!=null) {
				current=head;
			return head.value();
		}else{
			
			current=current.after();
			return current.value();
			
		}
}
	}


}
		



	