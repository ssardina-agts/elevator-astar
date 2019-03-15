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
	
	static void nodeUpdate(Node current, Node next) {
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
	
	// calculates the heuristic costs for request/elevator
	static int calcH(Request request, List<Elevator> elevators) {
		int cost = 0;
		int floor = request.floor;
		
		Elevator closestElevator = closestElevator(request, elevators);
		cost = Math.abs(floor-closestElevator.floor);
		
		return cost;
	}
	
	// creates the first node for the search algorithm with the first array of accumulated g-costs and without parent
	static Node createFirstNode(List<Elevator> elevators, Request request) {
		Node temp = new Node();
		temp.accGCost = new int[elevators.size()];
		Node firstNode = new Node();
		
		// for every elevator; find best 
		Iterator<Elevator> itr = elevators.iterator();
		while(itr.hasNext()) {
			Elevator currentElevator = itr.next();
			if ((currentElevator.dir == "none" || currentElevator.dir == request.dir) && currentElevator.currentCapacity < currentElevator.maxCapacity) {
				int hCost = calcH(request, elevators);
				temp = new Node(temp, request, currentElevator, hCost);
				temp.parent = null;
				
				if (temp.finalCost < firstNode.finalCost) {
					firstNode = temp;
				}
			}
		}
		
		return firstNode;
	}
	
	public Node Search(List<Request> allRequests, List<Elevator> allElevators) {
		//problem representation as grid
		Node[][] grid = new Node[allElevators.size()][allRequests.size()];
		Request currentRequest;
		Node currentNode;
		
		// creates first node
		Node firstNode = createFirstNode(allElevators, allRequests.get(1));
		allRequests.remove(1);

		open.add(firstNode);
		currentNode = open.poll();
		
		while(!allRequests.isEmpty()) {	
			if (currentNode == null) break;
			
			// change the position of the elevator from the current node's sending order
			allElevators.get(currentNode.order.elevatorNum).floor = currentNode.order.goalFloor;
			currentRequest = allRequests.get(1);
			
			closed.add(currentNode);
			
			Node temp;
			
			// check for every possible elevator
			Iterator<Elevator> itr = allElevators.iterator();

			while(itr.hasNext()) {
				Elevator currentElevator = itr.next();
				if ((currentElevator.dir == "none" || currentElevator.dir == currentRequest.dir) && currentElevator.currentCapacity < currentElevator.maxCapacity) {

					int hCost = calcH(currentRequest, allElevators);
					temp = new Node(currentNode, currentRequest, currentElevator, hCost);
					grid[currentElevator.number][currentRequest.floor] = temp;
					nodeUpdate(currentNode, temp);
				}
			}
			allRequests.remove(1);
			currentNode = open.poll();
		}
		return currentNode;			
	}
	
	public List<Node> makePath(Node lastNode) {
		List<Node> path = new ArrayList<Node>();
		
		if (lastNode.parent == null) {
			return path;
		} else {
			path.add(lastNode);
			return makePath(lastNode.parent);
		}
	}
}
