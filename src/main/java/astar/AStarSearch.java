package astar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

//import astar.Elevator;
//import astar.SearchNode;
//import astar.Request;


/*
 * Class for the A* Search on the distribution of requests
 * open: Priority Queue with all requests sorted after f(request)=g(request)+h(request)
 * closed: List of evaluated Nodes
 */
public class AStarSearch {
	
	static void openUpdate(SearchNode current, SearchNode next) {
		if (next == null || closed.contains(next)) return;

		boolean inOpen = open.contains(next);
		if (!inOpen) {
			next.parent = current;
			open.add(next);
		}
	}
	
	
	// picks the closest elevator to the request-floor
	static Elevator closestElevator(Request request, List<Elevator> elevators) {
		// sorting the list of elevator according to their current position makes the search faster
		elevators.sort((Elevator one, Elevator two) -> {
			if (one.floor > two.floor)
				return 1;
			if (one.floor < two.floor)
				return -1;
			return 0;
		});
		
		// finding the closest elevator to request
		if (request.floor < elevators.get(0).floor) {
			return elevators.get(0);
		}
		if (request.floor > elevators.get(-1).floor) {
			return elevators.get(-1);
		}
		
		int low = 0;
		int high = elevators.size() - 1;
		
		while (low <= high) {
			int mid = (low + high) / 2;
			
			if (request.floor < mid) {
				high = mid - 1;
			} else if (request.floor > mid) {
				low = mid + 1;
			} else {
				return elevators.get(mid);
			}
		}
		
		return (elevators.get(low).floor - request.floor) < (request.floor - elevators.get(high).floor) ? elevators.get(low) : elevators.get(high);
	}
	
	
	// creates the first node for the search algorithm with the first array of accumulated g-costs and without parent
	static SearchNode createFirstNode(List<Elevator> allElevators, List<Request> allRequests) {
		
		SearchNode firstNode = new SearchNode(allElevators, allRequests);
		firstNode.accGCost = new int[allElevators.size()];
		firstNode.finalCost = firstNode.hCost;
		
		return firstNode;
	}
	
	
	static PriorityQueue<SearchNode> open = new PriorityQueue<SearchNode>();
	static List<SearchNode> closed = new ArrayList<SearchNode>();
	
	
	public SearchNode Search(List<Elevator> allElevators, List<Request> allRequests) {
		
		// creates first node
		SearchNode firstNode = createFirstNode(allElevators, allRequests);
		open.add(firstNode);
		
		SearchNode currentNode;
	
		while(true) {
			currentNode = open.poll();
			if (currentNode.allRequests.isEmpty()) break;
			
			closed.add(currentNode);
			
			SearchNode temp;
			
			Iterator<Request> itrR = currentNode.allRequests.iterator();
			Iterator<Elevator> itrE = currentNode.allElevators.iterator();
			
			while (itrR.hasNext()) {
				Request currentRequest = itrR.next();
				while (itrE.hasNext()) {
					Elevator currentElevator = itrE.next();
					temp = new SearchNode(currentNode, currentElevator, currentRequest);
					openUpdate(currentNode, temp);
				}
			}
			
//			for (Request currentRequest : currentNode.state.allRequests) {
//				System.err.println(currentRequest.print());
//				for (Elevator currentElevator : currentNode.state.allElevators) {
//						temp = new SearchNode(currentNode, currentElevator, currentRequest);
//						openUpdate(currentNode, temp);
//				}
//			}
		}
		return currentNode;
	
	}
	
		
	public List<SearchNode> makePath(SearchNode lastNode) {
		List<SearchNode> path = new ArrayList<SearchNode>();
		
		if (lastNode.parent == null) {
			return path;
		} else {
			path.add(lastNode);
			return makePath(lastNode.parent);
		}
	}
}
