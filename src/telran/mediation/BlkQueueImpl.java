package telran.mediation;

import java.util.LinkedList;

public class BlkQueueImpl<T> implements BlkQueue<T> {
    public LinkedList<T> queue;
    private int maxSize;


    public BlkQueueImpl(int maxSize) {
        this.queue = new LinkedList<>();
        this.maxSize = maxSize;
    }

    @Override
    public synchronized void push(T message) {
        while (queue.size() == maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        queue.addLast(message);
        notifyAll();
    }

    @Override
    public synchronized T pop() {
        while (queue.isEmpty()){
            try {
                wait();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                return null;
            }
        }
        T message = queue.removeFirst();
        notifyAll();
        return message;
    }
}
