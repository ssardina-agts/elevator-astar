package astar;
import java.util.stream.*;

/*
 * SearchNode class used for the A* algorithm containing information about
 * state: the current state whose possible succeeding states can be explored
 * parent: previous SearchNode
 * hCost: heuristic cost of the current search node
 * accGCost: actual cost for the current search node so far
 * finalCost: sum of hCost and accGCost
 */
public class SearchNode implements Comparable<SearchNode> {

	StateNode state;
	SearchNode parent;
	SendingOrder order;
	
	int hCost;
	// keeping track of the accumulated g-costs for all elevators so far
	int[] accGCost;
	int finalCost;
	
	public SearchNode() {}
	
	public SearchNode(SearchNode parent, Elevator currentElevator, Request currentRequest, int hCost) {
		// inheriting values from parent search node
		this.state = new StateNode(parent.state.allElevators, parent.state.allRequests);
		this.parent = parent;
		this.order = new SendingOrder(currentElevator.number, currentElevator.floor, currentRequest.floor);
		
		this.accGCost = parent.accGCost;
		this.hCost = hCost;

		// now updating the values
		this.state.allRequests.remove(currentRequest);
		this.accGCost[currentElevator.number-1] += Math.abs(currentElevator.floor - currentRequest.floor);
		
		this.finalCost = IntStream.of(accGCost).sum() + hCost;
	}
	
	static void updateState() {
		
	}
	
	public String print() {
		return Integer.toString(finalCost);
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
