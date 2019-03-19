package astar;
import java.util.ArrayList;
import java.util.List;
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

	List<Elevator> allElevators;
	List<Request> allRequests;
	
	SearchNode parent;
	SendingOrder order;
	
	int hCost;
	// keeping track of the accumulated g-costs for all elevators so far
	int[] accGCost;
	int finalCost;
	
	public SearchNode(List<Elevator> allElevators, List<Request> allRequests) {
		this.allElevators = allElevators;
		this.allRequests = allRequests;
		this.hCost = calcH(allElevators, allRequests);
	}
	
	public SearchNode(SearchNode parent, Elevator currentElevator, Request currentRequest) {
		// inheriting values from parent search node
		this.allRequests = new ArrayList<Request>(parent.allRequests);
		this.allElevators = new ArrayList<Elevator>(parent.allElevators);
		this.parent = parent;
		
		this.order = new SendingOrder(currentElevator.number, currentElevator.floor, currentRequest.floor);
		
		this.accGCost = parent.accGCost;
		this.hCost = calcH(this.allElevators, this.allRequests);

		// now updating the values
		this.allRequests.remove(currentRequest);
		this.accGCost[currentElevator.number-1] += Math.abs(currentElevator.floor - currentRequest.floor);
		
		this.finalCost = IntStream.of(accGCost).sum() + hCost;
	}
	
	// calculates the heuristic costs for request/elevator
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
