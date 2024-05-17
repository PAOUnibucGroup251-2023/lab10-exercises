package ex2;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerAppWithSyncBlocks {
    private static final int QUEUE_CAPACITY = 10;
    private static final Queue<Integer> queue = new LinkedList<>();

    public static void main(String[] args) {

        // Producer Thread
        Thread producer = new Thread(() -> {
            int value = 0;
            while (true) {
                synchronized (queue) {
                    while (queue.size() == QUEUE_CAPACITY) {
                        try {
                            queue.wait();  // Wait until the queue has space
                        } catch (InterruptedException e) {
                            System.out.println("Producer interrupted.");
                            return;
                        }
                    }
                    queue.add(value);
                    System.out.println("Produced: " + value);
                    value++;
                    queue.notify();  // Notify consumer that there is data
                }
                try {
                    Thread.sleep(100); // simulate time-consuming task
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Consumer Thread
        Thread consumer = new Thread(() -> {
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();  // Wait until there is data in the queue
                        } catch (InterruptedException e) {
                            System.out.println("Consumer interrupted.");
                            return;
                        }
                    }
                    int value = queue.remove() * 10;  // Multiply the consumed value by 10
                    System.out.println("Consumed (Multiplied by 10): " + value);
                    queue.notify();  // Notify producer that space is available
                }
                try {
                    Thread.sleep(100); // simulate time-consuming task
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Start both threads
        producer.start();
        consumer.start();
    }
}
