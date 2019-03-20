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

	// state of the search node
	List<Elevator> allElevators;
	List<Request> allRequests;
	
	SearchNode parent = null;
	// sending order that created the current state/ search node
	SendingOrder order;
	
	int hCost;	// heuristic cost of a the node
	int[] accGCost;	// g code per elevator
	int finalCost;
	
	public SearchNode(List<Elevator> allElevators, List<Request> allRequests) {
		this.allElevators = new ArrayList<Elevator>(allElevators);
		this.allRequests = new ArrayList<Request>(allRequests);
		this.hCost = calcH(allElevators, allRequests);
	}
	
	public SearchNode(SearchNode parent, Elevator currentElevator, Request currentRequest) {
		// inheriting values from parent search node
		// TODO: convert this into just arrays of basic data (ints) - do not use Request or Elevator objects
		this.allRequests = new ArrayList<Request>(parent.allRequests);
		this.allElevators = new ArrayList<Elevator>(parent.allElevators);
		this.parent = parent;
		
		this.order = new SendingOrder(currentElevator.number, currentElevator.floor, currentRequest.floor);
		

		// Now updating the domain features of the node
		this.accGCost = parent.accGCost.clone();
		this.accGCost[currentElevator.number-1] += Math.abs(currentElevator.floor - currentRequest.floor);

		this.allElevators.get(currentElevator.number-1).floor = currentRequest.floor;
		this.allRequests.remove(currentRequest);
		
		
		// Calculate costs and heuristics for node
		this.hCost = calcH(this.allElevators, this.allRequests);
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
		return order.print() +" - finalCost: "+ Integer.toString(finalCost);
	}
	
	public List<String> printState() {
		List<String> state = new ArrayList<String>();
		for (Request r : this.allRequests) {
			state.add(r.print());
		}
		
		for (Elevator e : this.allElevators) {
			state.add(e.print());
		}
		
		return state;
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
