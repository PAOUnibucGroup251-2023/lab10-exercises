package ex4;

public class DiningPhilosophers {

    static final int NUMBER_OF_PHILOSOPHERS = 5;

    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[NUMBER_OF_PHILOSOPHERS];
        Fork[] forks = new Fork[NUMBER_OF_PHILOSOPHERS];

        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
            forks[i] = new Fork(i);
        }

        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % NUMBER_OF_PHILOSOPHERS]);
            new Thread(philosophers[i], "Philosopher " + (i + 1)).start();
        }
    }

    static class Philosopher implements Runnable {
        private int id;
        private Fork leftFork;
        private Fork rightFork;

        public Philosopher(int id, Fork leftFork, Fork rightFork) {
            this.id = id;
            this.leftFork = leftFork;
            this.rightFork = rightFork;
        }

        public void run() {
            try {
                while (true) {
                    think();
                    if (leftFork.pickUp()) {
                        if (rightFork.pickUp()) {
                            eat();
                            rightFork.putDown();
                        }
                        leftFork.putDown();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        private void eat() throws InterruptedException {
            System.out.println("Philosopher " + (id + 1) + " is eating.");
            Thread.sleep(((int) (Math.random() * 1000)));
        }

        private void think() throws InterruptedException {
            System.out.println("Philosopher " + (id + 1) + " is thinking.");
            Thread.sleep(((int) (Math.random() * 1000)));
        }
    }

    static class Fork {
        private int id;
        private boolean isTaken = false;

        public Fork(int id) {
            this.id = id;
        }

        public synchronized boolean pickUp() {
            if (!isTaken) {
                isTaken = true;
                return true;
            }
            return false;
        }

        public synchronized void putDown() {
            isTaken = false;
        }
    }
}
