package astar;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

public class AStar {
	
	static List<Request> startRequest = new ArrayList<Request>();
	static PriorityQueue<Node> open;
	static List<Node> closed = new ArrayList<Node>();

	public AStar() {
		
	}
	
	// function to create test values for the elevators
	private static List<Elevator> createData(int numElevators, int numFloors) {
		Random rnd = new Random();
		List<Elevator> allElevators = new ArrayList<Elevator>(); 
		
		// create List with elevator information
		for(int i = 0; i < numElevators; i++) {
			int currentFloor = rnd.nextInt(numFloors);
			Elevator singleElevator = new Elevator(currentFloor, "none");
			allElevators.add(singleElevator);
		}
		
		return allElevators;
	}
	
	// function to create test values for the requests
	private static List<Request> createRequests(int numRequests, int numFloors) {
		Random rnd = new Random();
		String dir;
		List<Request> allRequests = new ArrayList<Request>();
		
		// create List with number of requests
		for(int i = 0; i < numRequests; i++) {
			int floor = rnd.nextInt(numFloors);
			int rndDir = rnd.nextInt(2);
			if(rndDir == 0) {dir = "UP";}
			else {dir = "DOWN";}
			
			Request singleRequest = new Request(floor, dir);
			allRequests.add(singleRequest);
		}
		return allRequests;
	}
	
	/*
	 * Elevator class containing information about
	 * dir: direction of travel
	 * floor: current position
	 * maxCapacity: maximum capacity of travelers
	 * currentCapacity: current number of travelers inside car
	 */
	static class Elevator {
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
	
	/*
	 * Request class containing information about
	 * floor: the origin of the call
	 * dir: the intended directoin of travel
	 */
	static class Request {
		private int floor;
		private String dir;
		
		public Request(int requestedFloor, String direction) {
			this.floor = requestedFloor;
			this.dir = direction;
		}
	}
	
	/*
	 * SendCar class representing the command for an elevator car containing information about
	 * elevatorNum: which elevator is being used
	 * currentFloor: current position of that elevator
	 * goalFloor: goal of the elevator/ origin of the call
	 */
	static class SendCar {
		int elevatorNum;
		int currentFloor;
		int goalFloor;
		
		public SendCar(int num, int from, int to) {
			this.elevatorNum = num;
			this.currentFloor = from;
			this.goalFloor = to;
		}
		
		//public SendCar getPrevious() {return this.previous;}
		//public void changePrevious(SendCar newSend) {this.previous = newSend;}
	}
	
	private static int getCost(int currentFloor, int destination) {
		// plus two for opening the doors at current floor and destination floor
		int cost = Math.abs(currentFloor - destination)+2;
		return cost;
	}
	
	/*
	 * Node class used for the A* algorithm containing information about
	 * hCost: the heuristic cost
	 * gCost: cost in terms of waiting time till pickup
	 * finalCost: overall cost of the path up until that node
	 * leftRequests: List with all requests that are left to be processed
	 * order: associated SendCar order
	 * parent: previous node of the path
	 */
	static class Node {
		int hCost;
		int gCost;
		int finalCost;
		List<Request> leftRequests = new ArrayList<Request>();
		SendCar order;
		Node parent;
		
		public Node(List<Request> requests) {
			this.leftRequests = requests;
		}
	}
	
	static void checkAndUpdate(Node current, Node next, int cost) {
		if (next == null || closed.contains(next)) return;
		int final_cost = next.hCost+cost;
		
		boolean inOpen = open.contains(next);
		if (!inOpen || final_cost < next.finalCost) {
			next.finalCost = final_cost;
			next.parent = current;
			if (!inOpen) open.add(next);
		}
	}
	
	public static void Search(List<Elevator> elevators, List<Request> requests) {
		Node[][] grid = new Node[requests.size()][elevators.size()];
		Node firstNode = new Node(requests);
		Node current;

		open.add(firstNode);
		
		while(true) {
			current = open.poll();
			if(current == null) break;
			closed.add(current);
			
			if(requests.isEmpty()) return;
			
		}
	}
	
	public static List<Node> makePath(Node lastNode) {
		List<Node> path = new ArrayList<Node>();
		
		if (lastNode.parent == null) {
			return path;
		}
		else {
			path.add(lastNode);
			return makePath(lastNode.parent);
		}
	}
	
	public static void main(String[] args) {
		int numElevators = 5;
		int numFloors = 20;
		int numRequests = 20;
		
		List<Elevator> allElevators = createData(numElevators, numFloors);
		List<Request> allRequests = createRequests(numRequests, numFloors);
		
		for(int i = 0; i < allElevators.size(); i++) {
			Elevator elevator = allElevators.get(i);
			System.out.println(elevator.getFloor());
			System.out.println(elevator.getDir());
		}
		
		for(int i = 0; i < allRequests.size(); i++) {
			Request request = allRequests.get(i);
			System.out.println(request.floor);
			System.out.println(request.dir);
		}	
	}
	
	
}