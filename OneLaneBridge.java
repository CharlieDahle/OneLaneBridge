public class OneLaneBridge extends Bridge {

    private final int bridge_limit; // The bridge can hold no more than <bridge limit> cars at a time.

    public OneLaneBridge(int limit) {
        super();
        this.bridge_limit = limit;
    }

    @Override
    public synchronized void arrive(Car car) throws InterruptedException {
        // Wait until the bridge is not full and the car's direction matches the bridge's direction
        while (bridge.size() >= bridge_limit || (bridge.size() > 0 && direction != car.getDirection())) {
            wait();
        }
        car.setEntryTime(currentTime++);
        bridge.add(car); // Car enters the bridge
        System.out.println(this); // Print current state of the bridge
    }


    @Override
    public synchronized void exit(Car car) throws InterruptedException {
        while (bridge.get(0) != car) { // Ensure cars exit in FIFO order 
            wait();
        }
        bridge.remove(car); // Car exits the bridge
        System.out.println(this); // Print current state of the bridge
        if (bridge.isEmpty()) {
            direction = !direction; // Change the direction if the bridge is empty
        }
        notifyAll(); // Notify all waiting cars
    }

    @Override
    public String toString() {
    String toReturn = "Bridge (dir=" + direction + "): ";

    for (Car c : bridge) {
        toReturn += c.toString();
        if (!c.equals(bridge.get(bridge.size() - 1))) {
            toReturn += ", ";
        }
    }
    return toReturn;
}
}