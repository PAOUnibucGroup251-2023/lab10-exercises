package ex1;

public class Ex1 {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            while (true) {
                System.out.println("Hello from Thread 1");
                try {
                    Thread.sleep(1000);  // Sleep for 1 second
                } catch (InterruptedException e) {
                    System.out.println("Thread 1 interrupted");
                    return;
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                System.out.println("Hello from Thread 2");
                try {
                    Thread.sleep(1000);  // Sleep for 1 second
                } catch (InterruptedException e) {
                    System.out.println("Thread 2 interrupted");
                    return;
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            while (true) {
                System.out.println("Hello from Thread 3");
                try {
                    Thread.sleep(1000);  // Sleep for 1 second
                } catch (InterruptedException e) {
                    System.out.println("Thread 3 interrupted");
                    return;
                }
            }
        });

        // Starting the threads
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
