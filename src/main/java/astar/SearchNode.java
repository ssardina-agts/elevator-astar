package astar;

import org.apache.commons.lang3.ArrayUtils;

/*
 * SearchNode class used for the A* algorithm containing information about
 * state: the current state whose possible succeeding states can be explored
 * parent: previous SearchNode
 * hCost: heuristic cost of the current search node
 * accGCost: actual cost for the current search node so far
 * finalCost: sum of hCost and accGCost
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
	
	// TODO: do I need the following infos to be part of the class?
	// Elevator information
	int currentElevator;
	String[] currentElevatorDir;
	
	// Request information
	int currentRequest;
	String[] currentRequestDir;
	int call;
	
	// constructor for the first search node
	public SearchNode(int[] allElevators, int[] allRequests) {
		this.allElevators = allElevators.clone();
		this.allRequests = allRequests.clone();
		this.hCost = calcH(allElevators, allRequests);
	}
	
	//
	public SearchNode(SearchNode parent, int currentElevator, int currentRequest) {
		// inheriting values from parent search node
		this.parent = parent;
		this.allRequests = parent.allRequests.clone();
		this.allElevators = parent.allElevators.clone();
		this.currentElevator = currentElevator;
		this.currentRequest = currentRequest;
		this.call = this.allRequests[currentRequest];
		
		this.gCost = parent.gCost;
		this.hCost = calcH(this.allElevators, this.allRequests);
		
		// now updating the values
		this.gCost += Math.abs(this.allElevators[this.currentElevator] - this.allRequests[this.currentRequest]);
		this.allElevators[this.currentElevator] = this.allRequests[this.currentRequest];
		
		this.allRequests = ArrayUtils.remove(this.allRequests, this.currentRequest);
		this.finalCost = this.gCost + this.hCost;
	}
	
	// calculates the heuristic costs for request/elevator
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
	
	@Override
	public String toString() {
		String text = String.format("Elevator %d from %d to %d (cost: %d)", this.currentElevator+1, this.allElevators[this.currentElevator], this.call, this.finalCost);
		return text;
	}
	
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
