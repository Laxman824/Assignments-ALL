// This class implements the Queue
public class Queue<V> implements QueueInterface<V>{

    //TODO Complete the Queue implementation
	private NodeBase<V>[] queue;
    private int capacity, currentSize, front, rear;
	
    public Queue(int c) {    
    	capacity=c;
    	currentSize=0;
    	front=0;
    	rear=0;
    	queue=new NodeBase[capacity];
    	
    }

    public int size() {
    	return currentSize;
    }

    public boolean isEmpty() {
    	if(currentSize==0) {
    		return true;
    	}else {
    		return false;
    	}
    }
	
    public boolean isFull() {
    	if(currentSize==capacity) {
    		return true;
    	}else {
    		return false;
    	}
    }

    public void enqueue(Node<V> node) {
    	if (currentSize!=capacity) {
    		queue[rear]=node;
    		rear=(rear+1)%capacity;
    		currentSize++;
    	}
    }

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

}

