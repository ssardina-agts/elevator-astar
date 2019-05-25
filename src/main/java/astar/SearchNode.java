package astar;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

/*
 * SearchNode class used for the A* algorithm containing information about
 * allElevators: integer array with the positions of all elevators
 * allRequests: integer array with the positions of the requests
 * parent: previous SearchNode
 * hCost: heuristic cost of the current search node
 * gCost: actual cost for the current search node so far
 * finalCost: sum of hCost and gCost
 * currentElevator: index of the current elevator
 * printPreviousE: variable for printing the 
 * currentRequest:
 * printPreviousR: 
 */
public class SearchNode implements Comparable<SearchNode> {

	SearchNode parent = null;
	
	// State of the search node
	int[] allElevators;
	int[] allRequests;
	
	// Cost of the search note
	int hCost;
	int gCost;
	int finalCost;
	
	// Elevator information
	int currentElevator;
	int printPreviousE;
	
	// Request information
	int currentRequest;
	int printPreviousR;
	
	// Constructor for the first search node
	public SearchNode(int[] allElevators, int[] allRequests) {
		this.allElevators = allElevators.clone();
		this.allRequests = allRequests.clone();
		this.hCost = calcH(allElevators, allRequests);
	}
	
	public SearchNode(SearchNode parent, int currentElevator, int currentRequest) {
		// Inheriting values from parent search node
		this.parent = parent;
		this.allRequests = parent.allRequests.clone();
		this.allElevators = parent.allElevators.clone();
		this.currentElevator = currentElevator;
		this.currentRequest = currentRequest;
		this.printPreviousR = this.allRequests[currentRequest];
		this.printPreviousE = this.allElevators[currentElevator];
		
		this.gCost = parent.gCost;
		this.hCost = calcH(this.allElevators, this.allRequests);
		
		// Now updating the values
		this.gCost += Math.abs(this.allElevators[this.currentElevator] - this.allRequests[this.currentRequest]);
		this.allElevators[this.currentElevator] = this.allRequests[this.currentRequest];		
		
		this.allRequests = ArrayUtils.remove(this.allRequests, this.currentRequest);
		this.finalCost = this.gCost + this.hCost;
	}
	
	// Calculates the heuristic costs for request/elevator
	static int calcH(int[] allElevators, int[] allRequests) {
		int hCost = 0;
		
		for (int i = 0; i < allRequests.length; i++) {
			int diff = 1000000;
			for (int j = 0; j < allElevators.length; j++) {
				if (Math.abs(allRequests[i] - allElevators[j]) < diff) {
					diff = Math.abs(allRequests[i] - allElevators[j]);
				}
			}
			hCost += diff;
		}
		return hCost;
	}
	
	// String formatting to print the nodes
	@Override
	public String toString() {
		String text = String.format("Elevator %d from %d to %d (finalCost: %d)", this.currentElevator+1, this.parent.allElevators[currentElevator], this.parent.allRequests[currentRequest], this.finalCost);
		return text;
	}
	
	// Comparator to be used for the priority queue of the A* algorithm
	@Override
	public int compareTo(SearchNode node) {
		if (this.finalCost > node.finalCost) {
			return 1; 
		} else if (this.finalCost < node.finalCost) {
			return -1; 
		} else {
			return 0;
		}	
	}
}
