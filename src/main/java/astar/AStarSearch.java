package astar;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import astar.AStar.Elevator;
import astar.AStar.Node;
import astar.AStar.Request;


/*
 * Class for the A* Search on the distribution of requests
 * startRequest: still needed?
 * open: Priority Queue with all requests sorted after f(request)=g(request)+h(request)
 * closed: List of evaluated Nodes -- still needed?
 */
public class AStarSearch {
	
	static List<Request> startRequest = new ArrayList<Request>();
	static PriorityQueue<Node> open;
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
	
	public static void Search(List<Elevator> elevators, List<Request> requests) {
		// problem representation as grid!
		Node[][] grid = new Node[requests.size()][elevators.size()];
		Node firstNode = new Node(requests);
		Node current;

		open.add(firstNode);
		
		while(!requests.isEmpty()) {
			current = open.poll();
			
			if(current == null) break;
			
			closed.add(current);
			
			int temp = -1;
			// for every elevator
			for(int i=0; i<elevators.size(); i++) {
				if(elevators[i].dir == "none" || elevators[i].dir == request.dir) {
					//temporally assign request to elevator
					temp = i;
				}
				if(any elevator < f(request)) {
					//temporally assign request to elevator
					//delete request from queue
					//update elevator
					temp = i;
				}
			}
			
			
			
		}
	}
	
	public static List<Node> makePath(Node lastNode) {
		List<Node> path = new ArrayList<Node>();
		
		if (lastNode.parent == null) {
			return path;
		}
		else {
			path.add(lastNode);
			return makePath(lastNode.parent);
		}
	}
}
