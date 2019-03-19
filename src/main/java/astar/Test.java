package astar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import astar.Elevator;
import astar.Request;
import astar.AStarSearch;


public class Test {
	// change for the test
	// function to create test values for the elevators
	private static List<Elevator> createElevators(int numElevators, int numFloors) {
		Random rnd = new Random();
		List<Elevator> allElevators = new ArrayList<Elevator>(); 
		
		// create List with elevator information
		for(int i = 0; i < numElevators; i++) {
			int currentFloor = rnd.nextInt(numFloors);
			Elevator singleElevator = new Elevator(currentFloor, "none");
			singleElevator.number = i+1;
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
	
	static int calcH(List<Elevator> allElevators, List<Request> allRequests) {
		int hCost = 0;

		List<Integer> elevatorFloors = new ArrayList<Integer>();
		for(Elevator e : allElevators) {
			elevatorFloors.add(e.floor);
		}
		
		List<Integer> requestFloors = new ArrayList<Integer>();
		for (Request r : allRequests) {
			requestFloors.add(r.floor);
		}
		
		for (Integer r : requestFloors) {
			int diff = 1000000;
			for (Integer e : elevatorFloors) {
				if (Math.abs(r-e) < diff) {
					diff = Math.abs(r-e);
				}
			}
			hCost += diff;
		}
		return hCost;
	}


	public static void main(String[] args) {
		int numElevators = 2;
		int numFloors = 20;
		int numRequests = 5;
		
		// create data for test case
		List<Elevator> allElevators = createElevators(numElevators, numFloors);
		List<Request> allRequests = createRequests(numRequests, numFloors);
		
		// print information about pending requests and available elevators for our test case
		System.out.println("###############################");
		for (Request r : allRequests) {
			System.out.print("Floor: " + r.floor + " Direction: " + r.dir + "\n");
		}
		System.out.println();
		for (Elevator e : allElevators) {
			System.out.print("Elevator #" + e.number + " in floor " + e.floor + " going: " + e.dir + "\n");
		}
		System.out.println("###############################");
		
		// let A* run
		AStarSearch testSearch = new AStarSearch();
		SearchNode lastNode = testSearch.Search(allElevators, allRequests);
		
		// make and print path
		List<SearchNode> path = testSearch.makePath(lastNode);	
		for (SearchNode node : path) {
			System.out.println(node.order.toString());
		}
		
		
		
		

	}

}
