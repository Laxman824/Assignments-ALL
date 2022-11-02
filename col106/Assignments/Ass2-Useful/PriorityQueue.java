public class PriorityQueue<V> implements QueueInterface<V>{

    public NodeBase<V>[] queue;
    private int capacity, currentSize;
    public int rear,front;
	
    //TODO Complete the Priority Queue implementation
    // You may create other member variables/ methods if required.
    public PriorityQueue(int c) {    
    	rear=0;
    	front=0;
    	capacity=c;
    	currentSize=0;
    	queue=new NodeBase[capacity];
    }

    public int size() {
    	return currentSize;
    }

    public boolean isEmpty() {
    	if (currentSize<=0) {
    		return true;
    	}else {
    		return false;
    	}
    }
	
    public boolean isFull() {
    	if (currentSize==capacity) {
    		return true;
    	}else {
    		return false;
    	}

    }

    public void enqueue(Node<V> node) {
    	if (currentSize==0) {
    		queue[rear]=node;
    		rear=(rear+1)%capacity;
    		currentSize++;
    	}
    	else if (currentSize!=capacity) {
    		queue[rear%capacity]=node;
    		currentSize++;
    		int node_prior=node.getPriority();
    		int x=rear-1;
    		int node_pos=rear;
    		
    		while (x>=front & queue[x%capacity].getPriority()>node_prior) {
    			NodeBase<V> temp=queue[x%capacity];
    			queue[x%capacity]=node;
    			queue[node_pos%capacity]=temp;
    			node_pos=x;
    			x=x-1;
    			if (x<front) {
    				break;
    			}
    			
    		}
    	rear=rear+1;
    	}
    }

    // In case of priority queue, the dequeue() should 
    // always remove the element with minimum priority value
    public NodeBase<V> dequeue() {
    	if (currentSize!=0) {
    	NodeBase<V> n=queue[front];
    	front=(front+1)%capacity;
    	currentSize--;
		return n;}
    	else {
    		return null;
    	}
    	
    }

    public void display () {
	if (this.isEmpty()) {
            System.out.println("Queue is empty");
	}
	for(int i=front; i<front+currentSize; i++) {
            queue[i%capacity].show();
	}
    }
   
}

