package astar;

/*
 * Elevator class containing information about
 * dir: direction of travel
 * floor: current position
 * maxCapacity: maximum capacity of travelers
 * currentCapacity: current number of travelers inside car
 */
public class Elevator {
	private String dir;
	private int floor;
	private int maxCapacity = 5;
	private int currentCapacity;
	
	public Elevator(int number, String direction) {
		this.floor = number;
		this.dir = direction;
	}
	
	public int getFloor() {return this.floor;}
	public void setFloor(int newFloor) {this.floor = newFloor;}
	
	public String getDir() {return this.dir;}
	public void setDir(String newDir) {this.dir = newDir;}
	
	public int getCapacity() {return this.currentCapacity;}
	public void changeCapacity(int numPerson) {
		if(currentCapacity < maxCapacity && numPerson > 0) { 
			this.currentCapacity += numPerson;
			}
		else {System.out.println("Max Capacity reached");}
		
		if(numPerson < 0) this.currentCapacity -= numPerson;
	}
}
