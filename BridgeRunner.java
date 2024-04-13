public class BridgeRunner {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java BridgeRunner <bridge limit> <num cars>");
            return;
        }

        int bridgeLimit = Integer.parseInt(args[0]);
        int numCars = Integer.parseInt(args[1]);

        if (bridgeLimit <= 0 || numCars <= 0) {
            System.out.println("Error: bridge limit and/or num cars must be positive.");
            return;
        }

        // Instantiate the bridge with the specified limit
        OneLaneBridge bridge = new OneLaneBridge(bridgeLimit);

		// if threads are cars, let them be Volvos!

        Thread[] fleetOfVolvos = new Thread[numCars];  
        // Initialize and start each car in the fleet
        for (int i = 0; i < numCars; i++) {
            Car car = new Car(i, bridge);
            fleetOfVolvos[i] = new Thread(car);
            fleetOfVolvos[i].start();
        }

        // Wait for all cars in the fleet to finish
        for (Thread volvo : fleetOfVolvos) {
            try {
                volvo.join();
            } catch (InterruptedException e) {
                System.out.println("A Volvo in the fleet was interrupted.");
            }
        }

        System.out.println("All Volvos have crossed!!");
    }
}