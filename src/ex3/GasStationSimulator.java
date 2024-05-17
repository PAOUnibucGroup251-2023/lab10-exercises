package ex3;

import java.util.LinkedList;
import java.util.Queue;

public class GasStationSimulator {

    private static final int NUMBER_OF_PUMPS = 3;
    private static final Queue<Car> carQueue = new LinkedList<>();

    // Car class definition
    static class Car {
        private int carId;

        public Car(int carId) {
            this.carId = carId;
        }

        public void refuel(int pumpId) {
            System.out.println("Car " + this.carId + " is being refueled at pump " + pumpId);
            try {
                Thread.sleep(2000); // Simulating the refueling process
                System.out.println("Car " + this.carId + " has finished refueling at pump " + pumpId);
            } catch (InterruptedException e) {
                System.out.println("Refueling was interrupted for car " + this.carId);
            }
        }
    }

    // Pump Runnable
    static class Pump implements Runnable {
        private int pumpId;

        public Pump(int pumpId) {
            this.pumpId = pumpId;
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                Car car = null;
                synchronized (carQueue) {
                    if (!carQueue.isEmpty()) {
                        car = carQueue.poll();
                    }
                }
                if (car != null) {
                    car.refuel(pumpId);
                } else {
                    try {
                        Thread.sleep(500); // Sleep a short time before checking the queue again
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    // Car flow Runnable
    static class CarFlow implements Runnable {
        public void run() {
            int carId = 1;
            while (carId <= 10 && !Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(1000); // Simulate new car arrival interval
                    synchronized (carQueue) {
                        carQueue.add(new Car(carId));
                        System.out.println("Car " + carId + " arrived and is waiting for a free pump.");
                    }
                    carId++;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread[] pumps = new Thread[NUMBER_OF_PUMPS];
        for (int i = 0; i < NUMBER_OF_PUMPS; i++) {
            pumps[i] = new Thread(new Pump(i + 1));
            pumps[i].start();
        }

        Thread carFlowThread = new Thread(new CarFlow());
        carFlowThread.start();

        try {
            Thread.sleep(30000); // Run the simulation for 30 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Interrupt all threads to stop the simulation
        for (Thread pump : pumps) {
            pump.interrupt();
        }
        carFlowThread.interrupt();

        // Wait for all threads to finish
        for (Thread pump : pumps) {
            try {
                pump.join();
            } catch (InterruptedException e) {
                System.out.println("Failed to join pump thread.");
            }
        }

        try {
            carFlowThread.join();
        } catch (InterruptedException e) {
            System.out.println("Failed to join car flow thread.");
        }

        System.out.println("Gas station simulation ended.");
    }
}

