// WARNING: DO NOT CHANGE THIS FILE

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

// Base class for Node. Each node would be an element of the Priority Queue.
abstract class NodeBase<V> {
    protected int priority; // Priority of the node
    protected V value;	// Value stored by the node
    public abstract int getPriority(); // return the priority of the node
    public abstract V getValue();	// returns the value stored by the node
    public void show() {
	System.out.println(this.getPriority());
    }
}

// Interface for Queue
interface QueueInterface<V> {
    public int size(); // Returns the size, i.e. the number of elements in the queue
    public boolean isEmpty(); // Returns true if the queue is empty, else returns false
    public boolean isFull();  // Returns true if the queue is full, else returns false
    public void enqueue(Node<V> item); // Adds an item to the rear of the queue
    public NodeBase<V> dequeue(); // Removes an item from the front of the queue
}

// Base class for Buyer
abstract class BuyerBase<V> implements Runnable {
    protected PriorityQueue<V> catalog; // The shared priority queue
    protected Lock lock; // Shared lock
    protected Condition full, empty; // Shared condition variables
    private int sleepTime; // Sleep duration (in ms) for current thread
    private int iteration; // No. of iterations for buyer threads
    
    public abstract void buy() throws InterruptedException; // Implements the buy (consume) method from shared queue
    
    // Implements the thread run method
    public void run() {
        for(int i=0;i<this.iteration;i++) {
            try {
            
                buy();
                Thread.sleep(this.sleepTime);
            } catch (Exception e) {  e.printStackTrace(); }
	}
    }

    // Setter for sleepTime
    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    // Setter for iteration
    public void setIteration(int iteration) {
        this.iteration = iteration;
    }
}

// Base class for Seller
abstract class SellerBase<V> implements Runnable {
    protected PriorityQueue<V> catalog; // Shared priority queue
    protected Lock lock; //Shared lock
    protected Condition full, empty; // Shared condition variables
    private int sleepTime; // Sleep duration (in ms) for current thread
    protected Queue<V> inventory; // List of items (shared between sellers)
    public static int entry=0;
    public abstract void sell() throws InterruptedException; // Implements the sell (produce) method to shared queue
    public boolean a=false;
    // Implements the thread run method
    public void run() {
        while(inventory.isEmpty()!=true) {
            try {
            	sell();
	    } catch (Exception e) {  e.printStackTrace(); }
	}
        
    }

    // Setter for sleepTime
    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }
}
