package astar;

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
		
		// create array with number of requests
		// TODO: no double floors
		for(int i = 0; i < numRequests; i++) {
			allRequests[i] = rnd.nextInt(numFloors)+1;
		}
		return allRequests;
	}
	
//	private static String[] createRDirs(int numRequests) {
//		String RDirs[] = new String[numRequests];
//		Random rnd = new Random();
//		for(int i = 0; i < numRequests; i++) {
//			int rndDir = rnd.nextInt(2);
//			if(rndDir == 0) {RDirs[i] = "UP";}
//			else {RDirs[i] = "DOWN";}
//		}
//		return RDirs;
//	}
//	
//	private static String[] createEDirs(int numElevators) {
//		String EDirs[] = new String[numElevators];
//		Random rnd = new Random();
//		
//		for(int i = 0; i < numElevators; i++) {
//			EDirs[i] = "none";
//		}
//		
//		return EDirs;
//	}

	public static void main(String[] args) {
		//int numElevators = 2;
		//int numFloors = 30;
		//int numRequests = 5;
		
		// create data for test case
		//int[] allElevators = createElevators(numElevators, numFloors);
		//int[] allRequests = createRequests(numRequests, numFloors);
		int[] allRequests = {5, 20, 16, 7, 30};
		int[] allElevators = {3, 15};
		
		
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
		//System.out.println("Last node: " + lastNode.print());
		//System.out.println("Last node's parent: " + lastNode.parent.print());
		
		
		// make and print path
		testSearch.makePath(lastNode);
		System.out.println("Path done.");
		
		
		
		

	}
}
