package COLASSIGN2;
import java.util.Arrays;

     interface MyStackInterface <T> {
		
		public  void push(T value);
		
		public T  pop() throws EmptyStackException; 
		
		public T  top() throws EmptyStackException;
		
		public  boolean isEmpty();
		

	}
     
     public class MyStack<T> implements MyStackInterface<T>{
    	   T[] array;
    	  int size=0;
    	 public MyStack() {
    		
    		 
    		 array =(T[]) new Object[10];
    	 }
		public void push(T value) {
			if(size==array.length) {
				Resize();
			}
			size++;
			array[size-1]=value;
			
		}
		
		public void  Resize() {
			int newsize=array.length*2;
			array=Arrays.copyOf(array, newsize);
		}

		
		public T pop() throws EmptyStackException  {
			if(size==0 ) {
				throw new EmptyStackException("EmptyStackException");
				
			}else {
			
			T obj =(T) array[size-1];
			array[size-1]= null;
			size--;
			return obj;
		}
		}
		
		public T top() throws EmptyStackException {
			if( size == 0 ) {
			throw new 	EmptyStackException("EmptyStackException");
			}else {
				return array[size-1];
			}
			
			
		}

		public boolean isEmpty() {
			
			if(size == 0) {
				return true;
			}else {
				return false;	
			}
			
		}
		public String toString() {
			String s = "";
			for(int i = 0 ; i < size ; i++) {
				s = s+(" "+array[i]+" ");
			}
			return s;
		}
		
		
    	 
public static void main(String[] args) {
	MyStack<Integer> s1 = new  MyStack<Integer>();
	s1.push(10);
	s1.push(20);
	System.out.println(s1);
	
	
	
 }

	
	
	
	
	
	
	
	
	
	
	
	
}