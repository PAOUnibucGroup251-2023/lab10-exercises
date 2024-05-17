package ex3;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GasStationSimulatorWithExecutorService {

    private static final int NUMBER_OF_PUMPS = 3;
    private static ConcurrentLinkedQueue<Car> carQueue = new ConcurrentLinkedQueue<>();

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
                Car car = carQueue.poll();
                if (car != null) {
                    car.refuel(pumpId);
                }
            }
        }
    }

    // Car flow Runnable
    static class CarFlow implements Runnable {
        public void run() {
            int carId = 1;
            while (carId <= 10) {
                try {
                    Thread.sleep(1000); // Simulate new car arrival interval
                    Car car = new Car(carId++);
                    System.out.println("Car " + car.carId + " arrived and is waiting for a free pump.");
                    carQueue.add(car);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService pumpService = Executors.newFixedThreadPool(NUMBER_OF_PUMPS);

        // Start pump threads
        for (int i = 1; i <= NUMBER_OF_PUMPS; i++) {
            pumpService.execute(new Pump(i));
        }

        // Start car flow thread
        Thread carFlowThread = new Thread(new CarFlow());
        carFlowThread.start();

        // Shutdown the simulation after a fixed duration
        try {
            Thread.sleep(30000); // Run the simulation for 30 seconds
            carFlowThread.interrupt();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        pumpService.shutdownNow();
    }
}

