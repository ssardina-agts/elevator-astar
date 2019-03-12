package astar;

/*
 * Request class containing information about
 * floor: the origin of the call
 * dir: the intended directoin of travel
 */
public class Request {
	private int floor;
	private String dir;
	
	public Request(int requestedFloor, String direction) {
		this.floor = requestedFloor;
		this.dir = direction;
	}
}
