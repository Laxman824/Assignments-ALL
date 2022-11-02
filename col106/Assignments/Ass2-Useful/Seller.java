import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Seller<V> extends SellerBase<V> {
	
    public Seller (int sleepTime, int catalogSize, Lock lock, Condition full, Condition empty, PriorityQueue<V> catalog, Queue<V> inventory) {
        this.setSleepTime(sleepTime);
        this.full=full;
        this.empty=empty;
        this.catalog=catalog;
        this.inventory=inventory;
        this.lock=lock;
    }
    
    public void sell() throws InterruptedException {
    	lock.lock();
	try {
	
            while(catalog.isFull()) {
            	full.await();
            	
            }
            
            if (inventory.isEmpty()!=true) {
            NodeBase<V> n=inventory.dequeue();
            catalog.enqueue((Node<V>) n);
            empty.signalAll();
            }
	} catch(Exception e) {
            e.printStackTrace();
	} finally {
            lock.unlock();
	}		
    }
}
