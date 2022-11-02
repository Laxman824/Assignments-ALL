import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Buyer<V> extends BuyerBase<V> {
    public Buyer (int sT, int catalogSize, Lock lk, Condition full, Condition empty, PriorityQueue<V> cat, int iter) {
        catalog=cat;
    	this.setSleepTime(sT);
        this.setIteration(iter);
        lock=lk;
        this.full=full;
        this.empty=empty;
    }
    
    public void buy() throws InterruptedException {
    	lock.lock();
	try {
		while (catalog.isEmpty()) {
			empty.await();
			
			}
		NodeBase<V> n=catalog.dequeue();
		
	    	System.out.print("Consumed ");
	    	n.show();
	    	
            full.signalAll();
		
	} catch (Exception e) {
            e.printStackTrace();
	} finally {
            lock.unlock();
	}

    }
}
