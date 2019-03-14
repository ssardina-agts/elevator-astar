package astar;

/*
 * SendCar class representing the command for an elevator car containing information about
 * elevatorNum: which elevator is being used
 * currentFloor: current position of that elevator
 * goalFloor: goal of the elevator/ origin of the call
 */
public class SendingOrder {
	int elevatorNum;
	int currentFloor;
	int goalFloor;
	
	public SendingOrder (int num, int from, int to) {
		this.elevatorNum = num;
		this.currentFloor = from;
		this.goalFloor = to;
	}
	
	public String print() {
		return "send Elevator "+this.elevatorNum+" from "+this.currentFloor+" to "+this.goalFloor;
	}
}
