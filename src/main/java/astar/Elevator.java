package astar;

/*
 * Elevator class containing information about
 * dir: direction of travel
 * floor: current position
 * maxCapacity: maximum capacity of travelers
 * currentCapacity: current number of travelers inside car
 */
public class Elevator {
	String dir;
	int number;
	int floor;
	int maxCapacity = 5;
	int currentCapacity;
	
	public Elevator(int number, String direction) {
		this.floor = number;
		this.dir = direction;
	}
	
	public void changeCapacity(int numPerson) {
		if (numPerson > 0 && currentCapacity < maxCapacity) { 
			this.currentCapacity += numPerson;
		} else if (numPerson < 0 && currentCapacity > 0) {
			this.currentCapacity -= numPerson;
		} else if (numPerson < 0 && currentCapacity == 0) {
			this.currentCapacity = 0;
		}
	}
	
	public String print() {
		return "Elevator" + Integer.toString(number) + "@" + Integer.toString(floor);
	}
}
