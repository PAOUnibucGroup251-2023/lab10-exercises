package ex2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerApp {
    private static final int QUEUE_CAPACITY = 10;

    public static void main(String[] args) {
        BlockingQueue<Long> queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

        // Producer Thread
        Thread producer = new Thread(() -> {
            while (true) {
                try {
                    long value = Math.round(Math.random() * 10);
                    queue.put(value);
                    System.out.println("Produced: " + value);
                    Thread.sleep(100); // simulate time-consuming task
                } catch (InterruptedException e) {
                    System.out.println("Producer interrupted.");
                }
            }
        });

        // Consumer Thread
        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    Long value = queue.take();
                    value *= 10; // multiply the consumed value by 10
                    System.out.println("Consumed (Multiplied by 10): " + value);
                    Thread.sleep(100); // simulate time-consuming task
                } catch (InterruptedException e) {
                    System.out.println("Consumer interrupted.");
                }
            }
        });

        // Start both threads
        producer.start();
        consumer.start();
    }
}
