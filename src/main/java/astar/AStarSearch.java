package astar;

import java.util.ArrayList;
import java.util.List;
//import java.util.Iterator;
import java.util.PriorityQueue;

import org.apache.commons.lang3.ArrayUtils;



/*
 * Class for the A* Search on the distribution of requests
 * open: Priority Queue with all requests sorted after f(request)=g(request)+h(request)
 * closed: List of evaluated Nodes
 */
public class AStarSearch {
	
//	// picks the closest elevator to the request-floor
//	static Elevator closestElevator(Request request, List<Elevator> elevators) {
//		// sorting the list of elevator according to their current position makes the search faster
//		elevators.sort((Elevator one, Elevator two) -> {
//			if (one.floor > two.floor)
//				return 1;
//			if (one.floor < two.floor)
//				return -1;
//			return 0;
//		});
//		
//		// finding the closest elevator to request
//		if (request.floor < elevators.get(0).floor) {
//			return elevators.get(0);
//		}
//		if (request.floor > elevators.get(-1).floor) {
//			return elevators.get(-1);
//		}
//		
//		int low = 0;
//		int high = elevators.size() - 1;
//		
//		while (low <= high) {
//			int mid = (low + high) / 2;
//			
//			if (request.floor < mid) {
//				high = mid - 1;
//			} else if (request.floor > mid) {
//				low = mid + 1;
//			} else {
//				return elevators.get(mid);
//			}
//		}
//		
//		return (elevators.get(low).floor - request.floor) < (request.floor - elevators.get(high).floor) ? elevators.get(low) : elevators.get(high);
//	}
	
	
	// creates the first node for the search algorithm with the first array of accumulated g-costs, no order, no parent
	static SearchNode createFirstNode(int[] allElevators, int[] allRequests) {
		
		SearchNode firstNode = new SearchNode(allElevators, allRequests);
		
		firstNode.gCost = 0;
		firstNode.finalCost = firstNode.hCost;
		
		return firstNode;
	}
	
	
	static PriorityQueue<SearchNode> open = new PriorityQueue<SearchNode>();
	static List<SearchNode> closed = new ArrayList<SearchNode>();
	static int countOpen = 0;
	
	
	public SearchNode Search(int[] allElevators, int[] allRequests) {
		
		// creates first node and adds it to open list
		SearchNode firstNode = createFirstNode(allElevators, allRequests);
		open.add(firstNode);
		
		SearchNode currentNode;
	
		int countLoops = 0;
		int countChildren = 0;
		
		while(true) {
			countLoops += 1;
			currentNode = open.poll();
			if (ArrayUtils.isEmpty(currentNode.allRequests)) break;
			
			closed.add(currentNode);
			SearchNode temp;
			
			for (int i = 0; i <= currentNode.allRequests.length-1; i++) {
				for (int j = 0; j <= currentNode.allElevators.length-1; j++) {
						int currentRequest = i;
						int currentElevator = j;
						temp = new SearchNode(currentNode, currentElevator, currentRequest);
						if (!open.contains(temp)) {
							//temp.parent = currentNode; //not necessary
							open.add(temp);
							countOpen += 1;
						}
						countChildren += 1;
				}
			}
		}
		System.out.println(countLoops + " iterations through the while loop.");
		System.out.println(countChildren + " children nodes created.");
		System.out.println(countOpen + " children added to OPEN list.");
		return currentNode;
	}
	
		
	public List<SearchNode> makePath(SearchNode lastNode) {
		List<SearchNode> path = new ArrayList<SearchNode>();
		
		while (lastNode.parent != null) {
			System.out.println(lastNode);
			path.add(lastNode);
			lastNode = lastNode.parent;
		}
		return path;
	}
	
	
}
