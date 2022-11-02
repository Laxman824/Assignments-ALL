package PriorityQueue;

/**
 * DO NOT EDIT
 *
 * @param <T>
 */
public interface PriorityQueueInterface<T extends Comparable> {

    /**
     * @param element Insert and element to the Priority Queue
     */
    void insert(T element);

    /**
     * Extract the current maximum element from the Queue (assuming a max heap).
     * @return
     */
    T extractMax();

}
