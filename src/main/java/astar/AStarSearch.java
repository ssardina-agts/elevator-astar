package astar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import astar.Elevator;
import astar.Node;
import astar.Request;


/*
 * Class for the A* Search on the distribution of requests
 * startRequest: still needed?
 * open: Priority Queue with all requests sorted after f(request)=g(request)+h(request)
 * closed: List of evaluated Nodes -- still needed?
 */
public class AStarSearch {
	
	static List<Request> startRequest = new ArrayList<Request>();
	static PriorityQueue<Node> open;
	
	// maybe implement as boolean[][]
	static List<Node> closed = new ArrayList<Node>();
	
	static void checkAndUpdate(Node current, Node next, int cost) {
		if (next == null || closed.contains(next)) return;
		int final_cost = next.hCost+cost;
		
		boolean inOpen = open.contains(next);
		if (!inOpen || final_cost < next.finalCost) {
			next.finalCost = final_cost;
			next.parent = current;
			if (!inOpen) open.add(next);
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
	
	// calculates the final costs for request/elevator
	static int calcH(Request request, List<Elevator> elevators) {
		int cost = 0;
		int floor = request.floor;
		
		Elevator closestElevator = closestElevator(request, elevators);
		cost = Math.abs(floor-closestElevator.floor);
		
		return cost;
	}
	
	// creates the first node for the search algorithm
	static Node createFirstNode(Request request, Elevator current) {
		Node noParent = null;
		
		Node firstNode = new Node(noParent, request, Elevator current, int gCost, int hCost);
		return firstNode;
	}
	
	public static void Search(int numElevators, List<Request> allRequests, List<Elevator> elevators) {
		// problem representation as grid!
		Node[][] grid = new Node[numElevators][allRequests.size()];
		//Node firstNode = new Node()
		Request currentRequest;
		Node currentNode;
		int[] cumGCost = new int[elevators.size()];

		open.add(firstNode);
		
		while(!allRequests.isEmpty()) {
			currentNode = open.poll();
			currentRequest = allRequests.get(1);
			
			if (currentNode == null) break;
			
			closed.add(currentNode);
			
			Node temp;
			
			// for every elevator
			Iterator<Elevator> itr = elevators.iterator();

			while(itr.hasNext()) {
				Elevator currentElevator = itr.next();
				if ((currentElevator.dir == "none" || currentElevator.dir == currentRequest.dir) && currentElevator.currentCapacity < currentElevator.maxCapacity) {
					//mistake here? maybe add cost to parent cost instead?
					cumGCost[currentElevator.floor] += Math.abs(currentRequest.floor-currentElevator.floor);
					int hCost = calcH(currentRequest, elevators);
					temp = new Node(currentNode, currentRequest, currentElevator, cumGCost[currentElevator.floor], hCost);
					temp = grid[currentElevator.floor][currentRequest.floor];
					checkAndUpdate(currentNode, temp, cumGCost[currentElevator.floor]);
				}
			}
			allRequests.remove(1);
		}
					
	}
	
	public static List<Node> makePath(Node lastNode) {
		List<Node> path = new ArrayList<Node>();
		
		if (lastNode.parent == null) {
			return path;
		} else {
			path.add(lastNode);
			return makePath(lastNode.parent);
		}
	}
}
