// Use this driver for the testing the correctness of your priority queue implementation
// You can change the add, remove sequence to test for various scenarios.
public class PriorityQueueTestDriver {
    public static void main(String[] args) {
	PriorityQueue<String> pq = new PriorityQueue<String>(5);
	pq.enqueue(new Node(4,"A"));
	pq.enqueue(new Node(10,"B"));
	pq.enqueue(new Node(3,"C"));
	pq.enqueue(new Node(5,"E"));
	pq.enqueue(new Node(2,"F"));
	pq.dequeue();
	//pq.display();
	int size = pq.size();
	for (int i=0; i<size; i++) {
            Node<String> n = (Node<String>) pq.queue[pq.front+i];
            n.show();
	}
    }
}
