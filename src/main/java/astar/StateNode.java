package astar;

import java.util.List;
import astar.Elevator;
import astar.Request;

/*
 * StateNode class used for the A* algorithm containing information about
 * allElevators: list of Elevator objects with all available elevators
 * allRequests: list of Request objects with all pending requests
 */
public class StateNode {
	
	List<Elevator> allElevators;
	List<Request> allRequests;
	
	public StateNode() {}
	
	public StateNode(List<Elevator> allElevators, List<Request> allRequests) {
		this.allElevators = allElevators;
		this.allRequests = allRequests;
	}
}
