package PriorityQueue;

import java.util.ArrayList;

import ProjectManagement.Job;

public class MaxHeap<T extends Comparable<T>> implements PriorityQueueInterface<T> {
	public int size;
	public ArrayList<T> students=new ArrayList<T>();
	public MaxHeap(){
		students.add(null);
		size=1;
	}
	public void insert(T element) {
		
    	int pos=size;
		students.add(element);
		T k=students.get(pos);
		T temp;
		
		while(pos>1) {
			if(students.get(pos/2).compareTo(k)<0) {
				temp=students.get(pos/2);
				students.set(pos/2,element);
				students.set(pos,temp);
				pos=(pos)/2;
			}else {
				break;
			}
		}
		
		size++;
		}
    
	public int find_level(int k) {
		int p=0;
		while(k>=Math.pow(2,p)) {
			p++;
		}
		return p-1;
	}
	
    public T extractMax() {
        if(size==1) {
        	
        	return null;
        }else if(size==2) {
        	
        	T temp=students.get(1);
        	students.remove(1);
        	size--;
        	return temp;
        }
        
    	T to_return=students.get(1);
        students.set(1,students.get(size-1));
       
        students.remove(size-1);
        size--;

        
        
        int pos=1;
        int level=find_level(size-1);
        while(pos<Math.pow(2,level-1)) {
        
        	if(students.get(pos).compareTo(students.get(2*pos))<0 || students.get(pos).compareTo(students.get(2*pos+1))<0){

        		if(students.get(2*pos).compareTo(students.get(2*pos+1))<0) {
        			
        			T temp=students.get(pos);
        			students.set(pos,students.get(2*pos+1));
        			students.set(2*pos+1,temp);
        			pos=2*pos+1;
        		
        			
        		}else {
        			
        			T temp=students.get(pos);
        			students.set(pos,students.get(2*pos));
        			students.set(2*pos,temp);
        			pos=2*pos;
        			
        		}
        	}else {
        		break;
        	}
        }
       
        
        if(2*pos<=size-1) {
        	
        	if(2*pos+1<=size-1) {
        		if(students.get(pos).compareTo(students.get(2*pos))<0 || students.get(pos).compareTo(students.get(2*pos+1))<0){
            		if(students.get(2*pos).compareTo(students.get(2*pos+1))<0) {
            			T temp=students.get(pos);
            			students.set(pos,students.get(2*pos+1));
            			students.set(2*pos+1,temp);
            		}else {
            			T temp=students.get(pos);
            			students.set(pos,students.get(2*pos));
            			students.set(2*pos,temp);
            		}
            	}
        	}
        	if(students.get(pos).compareTo(students.get(2*pos))<0) {
        		
        		T temp=students.get(pos);
    			students.set(pos,students.get(2*pos));
    			students.set(2*pos,temp);
        	}
        }
        
        
        return to_return;
    }
	public void arrange(int pos) {
		T element=students.get(pos);
		T temp;
		while(pos>1) {
			if(students.get(pos/2).compareTo(element)<0) {
				temp=students.get(pos/2);
				students.set(pos/2,element);
				students.set(pos,temp);
				pos=(pos)/2;
			}else {
				break;
			}
		}
		
	}

	

}