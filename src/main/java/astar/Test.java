package astar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import astar.*;
import astar.AStar.Elevator;
import astar.AStar.Request;

public class Test {
	// change for the test
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

	public static void main(String[] args) {
		int numElevators = 5;
		int numFloors = 20;
		int numRequests = 20;
		
		List<Elevator> allElevators = createData(numElevators, numFloors);
		List<Request> allRequests = createRequests(numRequests, numFloors);
		
		AStarSearch test = new AStarSearch();
		test.Search(allElevators, allRequests);
		testPath = test.makePath(lastNode);

	}

}
