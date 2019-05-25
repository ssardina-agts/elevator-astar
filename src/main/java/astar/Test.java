package astar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import astar.AStarSearch;


public class Test {
	// function to create test values for the elevators
	private static int[] createElevators(int numElevators, int numFloors) {
		Random rnd = new Random();
		int[] allElevators = new int[numElevators];
		
		// create List with elevator information
		for(int i = 0; i < numElevators; i++) {
			allElevators[i] = rnd.nextInt(numFloors)+1;
		}
		
		return allElevators;
	}
	
	// function to create test values for the requests
	private static int[] createRequests(int numRequests, int numFloors) {
		Random rnd = new Random();
		int[] allRequests = new int[numRequests];
		int[] allFloors = new int[numFloors+1];
		
		for(int i = 0; i <= numFloors; i++) {
			allFloors[i] = i;
		}
		
		// create array with number of requests
		for(int i = 0; i < numRequests; i++) {
			allRequests[i] = allFloors[rnd.nextInt(allFloors.length)];
		}
		return allRequests;
	}

	public static void main(String[] args) {
		int numElevators = 2;
		int numFloors = 30;
		int numRequests = 5;
		
		// create data for test case
		int[] allElevators = createElevators(numElevators, numFloors);
		int[] allRequests = createRequests(numRequests, numFloors);
		
		// print information about pending requests and available elevators for our test case
		System.out.println("###############################");
		for (int r : allRequests) {
			System.out.println("Floor: " + r);
		}

		System.out.println();
		
		for (int i=0; i < allElevators.length; i++) {
			System.out.println("Elevator #" + (i+1) + " in floor " + allElevators[i]);
		}
		System.out.println("###############################");
		
		// let A* run
		AStarSearch testSearch = new AStarSearch();
		SearchNode lastNode = testSearch.Search(allElevators, allRequests);
		System.out.println("Done with search.");
		
		// make and print path
		List<SearchNode> path = new ArrayList<SearchNode>();
		path = testSearch.makePath(lastNode);
		Collections.reverse(path);
		for (SearchNode p : path) {
			System.out.println(p);
		}
		System.out.println("Path done.");
		
	}
}
