package astar;

/*
 * Request class containing information about
 * floor: the origin of the call
 * dir: the intended direction of travel
 */
public class Request {
	int floor;
	String dir;
	
	public Request(int requestedFloor, String direction) {
		this.floor = requestedFloor;
		this.dir = direction;
	}
	
	public String print() {
		return Integer.toString(floor) + dir;
	}
}
