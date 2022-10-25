package PriorityQueue;

import java.util.ArrayList;

public class MaxHeap<T extends Comparable> implements PriorityQueueInterface<T> {
	ArrayList<T> heap ;
	int currentsize;
	
	public MaxHeap() {
		heap = new ArrayList<T>();
		currentsize = -1;
	}
		int parent(int pos) {
			return pos/2 ;
	}
	private int leftchild(int pos)
	{
		return (2*pos)+1;
	}
	private int rightchild(int pos)
	{
		return (2*pos)+2;
	}
	private boolean isleafNode(int pos)
	{
		if (pos>=(currentsize/2)&& pos <= currentsize){
			return true;
		}
	    return false;
	}
	private void swap (int pos1, int pos2) {
		T temp = heap.get(pos1);
		heap.set(pos1, heap.get(pos2));
		heap.set(pos2, temp);
			
	}

    
    public void insert(T element) {
    	currentsize++;
    	heap.add(currentsize,element);
    	int temp = currentsize;
    	while(temp != 0 ) {
    		if(heap.get(temp).compareTo(heap.get(parent(currentsize))) > 0) {
    			swap(currentsize , parent(currentsize));
    	}
    	
    	
    }

    }
    void maxHeapify(int pos) 
    { 
        if (isleafNode(pos)) 
            return; 
  
        if (heap.get(pos).compareTo(heap.get(leftchild(pos))) < 0 ||  
        		heap.get(pos).compareTo(heap.get(rightchild(pos))) < 0)  { 
  
            if  (heap.get(leftchild(pos)).compareTo(heap.get(rightchild(pos))) > 0) { 
                swap(pos, leftchild(pos)); 
                maxHeapify(leftchild(pos)); 
            } 
            else { 
                swap(pos, rightchild(pos)); 
                maxHeapify(rightchild(pos)); 
            } 
        } 
    } 

  
    public T extractMax() {
        if(currentsize == -1) {
        	return null;
        }
       T max =  heap.get(0);
       heap.set(0, heap.get(currentsize));
       maxHeapify(0);
       currentsize--;
       return max;
       
       
    }

}